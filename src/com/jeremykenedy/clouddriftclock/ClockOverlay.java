package com.jeremykenedy.clouddriftclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class ClockOverlay extends View {
    private final ClockOptions options;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final SimpleDateFormat format;
    private final long startedAt = SystemClock.uptimeMillis();
    private boolean running;
    private final Runnable update = () -> {
        if (running) {
            invalidate();
            postDelayed(this.update, 100L);
        }
    };

    ClockOverlay(Context context, ClockOptions options) {
        super(context);
        this.options = options;
        format = new SimpleDateFormat(CloudLayout.timePattern(options.use24Hour, options.showSeconds), Locale.getDefault());
        paint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        paint.setShadowLayer(8f, 0f, 2f, Color.BLACK);
        setVisibility(options.showClock ? VISIBLE : GONE);
    }

    void start() {
        if (!options.showClock || running) return;
        running = true;
        post(update);
    }

    void stop() {
        running = false;
        removeCallbacks(update);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!options.showClock) return;
        float seconds = (SystemClock.uptimeMillis() - startedAt) / 1000f;
        paint.setTextSize(getHeight() * options.clockSize / 720f);
        canvas.drawText(format.format(new Date()), CloudLayout.clockX(seconds, getWidth(), options.drift),
                CloudLayout.clockY(seconds, getHeight(), options.drift), paint);
    }
}
