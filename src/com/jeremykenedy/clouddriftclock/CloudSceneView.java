package com.jeremykenedy.clouddriftclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

final class CloudSceneView extends View {
    private final Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint clockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect source = new Rect();
    private final Rect foregroundSource = new Rect();
    private final RectF destination = new RectF();
    private final RectF foregroundDestination = new RectF();
    private final Bitmap cloudscape;
    private ClockOptions options;
    private long startedAt;
    private boolean running;

    CloudSceneView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        cloudscape = BitmapFactory.decodeResource(getResources(), R.drawable.cloudscape);
        loadOptions();
    }

    void start() {
        if (!running) {
            running = true;
            startedAt = SystemClock.uptimeMillis();
            postInvalidateOnAnimation();
        }
    }

    void stop() {
        running = false;
        removeCallbacks(invalidator);
    }

    void release() {
        stop();
        if (cloudscape != null && !cloudscape.isRecycled()) cloudscape.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() <= 0 || getHeight() <= 0 || cloudscape == null) return;
        float time = (SystemClock.uptimeMillis() - startedAt) / 1000f;
        drawCloudscape(canvas, time);
        drawClock(canvas, time);
        if (running) postDelayed(invalidator, 33L);
    }

    private final Runnable invalidator = new Runnable() {
        @Override public void run() { if (running) invalidate(); }
    };

    private void loadOptions() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        options = ClockOptions.resolve(
                SettingsValues.normalizeChoice("palette", preferences.getAll().get("palette"), "day"),
                SettingsValues.normalizeChoice("speed", preferences.getAll().get("speed"), "gentle"),
                SettingsValues.normalizeChoice("density", preferences.getAll().get("density"), "balanced"),
                SettingsValues.normalizeChoice("clock_size", preferences.getAll().get("clock_size"), "standard"),
                SettingsValues.normalizeChoice("clock_drift", preferences.getAll().get("clock_drift"), "short"),
                SettingsValues.normalizeChoice("clock_format", preferences.getAll().get("clock_format"), "12h"),
                SettingsValues.normalizeChoice("show_seconds", preferences.getAll().get("show_seconds"), "false"),
                preferences.getBoolean("randomize_all", false),
                new Random(System.currentTimeMillis()));
    }

    private void drawCloudscape(Canvas canvas, float time) {
        int width = cloudscape.getWidth();
        int height = cloudscape.getHeight();
        int cropTop = CloudLayout.sourceTop(options.cloudCount, height);
        source.set(0, cropTop, width, height);
        float driftX = CloudLayout.cameraX(time, options.cloudSpeed, getWidth());
        float driftY = CloudLayout.cameraY(time, options.cloudSpeed, getHeight());
        destination.set(-getWidth() * 0.035f + driftX, -getHeight() * 0.035f + driftY,
                getWidth() * 1.035f + driftX, getHeight() * 1.035f + driftY);
        canvas.drawBitmap(cloudscape, source, destination, bitmapPaint);
        drawForegroundCloudLayer(canvas, time);
        int overlay = CloudLayout.paletteOverlay(options.palette);
        if (overlay != 0) canvas.drawColor(overlay);
    }

    private void drawForegroundCloudLayer(Canvas canvas, float time) {
        int alpha = CloudLayout.foregroundAlpha(options.cloudCount);
        if (alpha == 0) return;
        foregroundSource.set(0, cloudscape.getHeight() / 2, cloudscape.getWidth(), cloudscape.getHeight());
        float drift = CloudLayout.foregroundX(time, options.cloudSpeed, getWidth());
        float top = getHeight() * 0.45f;
        foregroundDestination.set(-getWidth() * 0.045f + drift, top,
                getWidth() * 1.045f + drift, getHeight() * 1.16f);
        foregroundPaint.setAlpha(alpha);
        canvas.drawBitmap(cloudscape, foregroundSource, foregroundDestination, foregroundPaint);
        foregroundPaint.setAlpha(255);
    }

    private void drawClock(Canvas canvas, float time) {
        float cx = CloudLayout.clockX(time, getWidth(), options.drift);
        float cy = CloudLayout.clockY(time, getHeight(), options.drift);
        String pattern = CloudLayout.timePattern(options.use24Hour, options.showSeconds);
        String value = new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date());
        clockPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        clockPaint.setTextAlign(Paint.Align.CENTER);
        clockPaint.setTextSize(getHeight() * options.clockSize / 720f);
        clockPaint.setColor(CloudLayout.clockColor(options.palette));
        clockPaint.setShadowLayer(14f, 0f, 3f, CloudLayout.shadowColor(options.palette));
        canvas.drawText(value, cx, cy, clockPaint);
        clockPaint.clearShadowLayer();
    }
}
