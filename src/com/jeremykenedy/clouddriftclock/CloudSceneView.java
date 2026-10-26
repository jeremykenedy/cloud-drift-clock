package com.jeremykenedy.clouddriftclock;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Gravity;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;
import java.io.IOException;

final class CloudSceneView extends FrameLayout implements TextureView.SurfaceTextureListener {
    private final TextureView surface;
    private Surface videoSurface;
    private final Runnable playbackFailed;
    private final FlightOptions flight;
    private final ClockOverlay clock;
    private MediaPlayer player;
    private boolean resumed;
    private boolean fallback;
    private int position;

    CloudSceneView(Context context, Runnable playbackFailed) {
        super(context);
        this.playbackFailed = playbackFailed;
        android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        java.util.Random random = new java.util.Random();
        boolean randomize = SettingsValues.booleanValue(preferences.getAll().get("randomize_all"), false);
        flight = FlightOptions.resolve(
                SettingsValues.normalizeChoice("speed", preferences.getAll().get("speed"), "gentle"), randomize, random);
        ClockOptions options = ClockOptions.resolve("day", "gentle", "balanced",
                SettingsValues.normalizeChoice("clock_size", preferences.getAll().get("clock_size"), "standard"),
                SettingsValues.normalizeChoice("clock_drift", preferences.getAll().get("clock_drift"), "short"),
                SettingsValues.normalizeChoice("clock_format", preferences.getAll().get("clock_format"), "12h"),
                SettingsValues.normalizeChoice("show_seconds", preferences.getAll().get("show_seconds"), "false"),
                SettingsValues.booleanValue(preferences.getAll().get("show_clock"), true), randomize, random);
        clock = new ClockOverlay(context, options);
        setBackgroundColor(Color.BLACK);
        surface = new TextureView(context);
        addView(surface, new FrameLayout.LayoutParams(-1, -1, Gravity.CENTER));
        surface.setSurfaceTextureListener(this);
        addView(clock, new FrameLayout.LayoutParams(-1, -1));
        addOnLayoutChangeListener((v, l, t, r, b, ol, ot, or, ob) -> fit(r-l, b-t));
    }

    void start() {
        resumed = true;
        clock.start();
        preparePlayer();
    }

    void stop() {
        resumed = false;
        clock.stop();
        releasePlayer();
    }

    void release() { stop(); }

    private void fit(int width, int height) {
        if (width <= 0 || height <= 0) return;
        int fittedWidth = Math.min(width, height * 16 / 9);
        int fittedHeight = fittedWidth * 9 / 16;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) surface.getLayoutParams();
        if (params.width != fittedWidth || params.height != fittedHeight) {
            params.width = fittedWidth;
            params.height = fittedHeight;
            params.gravity = Gravity.CENTER;
            surface.setLayoutParams(params);
        }
    }

    @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
        videoSurface = new Surface(texture);
        preparePlayer();
    }

    @Override public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {}

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
        releasePlayer();
        if (videoSurface != null) videoSurface.release();
        videoSurface = null;
        return true;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture texture) {}

    private void preparePlayer() {
        if (!resumed || player != null || (videoSurface == null || !videoSurface.isValid())) return;
        MediaPlayer next = new MediaPlayer();
        player = next;
        next.setSurface(videoSurface);
        next.setVolume(0, 0);
        next.setLooping(true);
        next.setOnPreparedListener(ready -> {
            if (player != ready || !resumed) return;
            ready.setPlaybackParams(new android.media.PlaybackParams().setSpeed(flight.playbackSpeed));
            if (position > 0) {
                ready.setOnSeekCompleteListener(seeked -> {
                    if (player == seeked && resumed) seeked.start();
                });
                ready.seekTo(position);
            } else {
                ready.start();
            }
            Log.i("CloudPlayback", "Started " + (fallback ? "720p" : "1080p")
                    + " durationMs=" + ready.getDuration() + " metrics=" + metrics(ready));
        });
        next.setOnErrorListener((failed, what, extra) -> {
            if (player != failed) return true;
            Log.e("CloudPlayback", "Decoder error " + what + "/" + extra);
            releasePlayer();
            if (!fallback && resumed) {
                fallback = true;
                position = 0;
                preparePlayer();
            } else if (resumed) {
                playbackFailed.run();
            }
            return true;
        });
        try (AssetFileDescriptor file = getContext().getAssets().openFd(
                fallback ? "clouds-720.mp4" : "clouds-1080.mp4")) {
            next.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            next.prepareAsync();
        } catch (IOException error) {
            Log.e("CloudPlayback", "Bundled media unavailable", error);
            releasePlayer();
            playbackFailed.run();
        }
    }

    private String metrics(MediaPlayer current) {
        return android.os.Build.VERSION.SDK_INT >= 26 ? current.getMetrics().toString() : "unavailable before API 26";
    }

    private void releasePlayer() {
        MediaPlayer current = player;
        player = null;
        if (current == null) return;
        try {
            position = current.getCurrentPosition();
            Log.i("CloudPlayback", "Released positionMs=" + position
                    + " metrics=" + metrics(current));
        } catch (IllegalStateException ignored) {
            // Surface loss may happen before asynchronous preparation completes.
        }
        current.setOnPreparedListener(null);
        current.setOnErrorListener(null);
        current.setOnSeekCompleteListener(null);
        current.release();
    }
}
