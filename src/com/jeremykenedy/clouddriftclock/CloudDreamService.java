package com.jeremykenedy.clouddriftclock;

import android.service.dreams.DreamService;

public final class CloudDreamService extends DreamService {
    private CloudSceneView scene;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setScreenBright(false);
        scene = new CloudSceneView(this);
        setContentView(scene);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        if (scene != null) scene.start();
    }

    @Override
    public void onDreamingStopped() {
        if (scene != null) scene.stop();
        super.onDreamingStopped();
    }

    @Override
    public void onDetachedFromWindow() {
        if (scene != null) scene.release();
        scene = null;
        super.onDetachedFromWindow();
    }
}
