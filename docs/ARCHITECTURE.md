# Architecture

Cloud Drift Clock contains a DreamService, full-screen Preview, D-pad settings screen and exported settings provider. Version 2 replaces the cropped photograph animation with bundled real flight footage.

`CloudSceneView` gives a hardware-decoded MediaPlayer an owned Surface backed by TextureView. Video is fitted to the available 16:9 area without digital camera movement. The 1080p file and 720p decoder fallback are uncompressed APK assets, so the decoder opens an asset file descriptor rather than copying the whole film into memory. No media download or runtime network access is needed.

TextureView composes the film into the app window alongside the optional clock. This also makes the decoded picture capturable on devices whose separate SurfaceView video plane is omitted from screenshots. Actual hardware decoding and pacing still require device measurements; class selection alone is not proof.

A separate clock overlay draws only intentional local time. It schedules a lightweight 100ms update while visible and running, stops callbacks when paused, and draws or schedules nothing when Show clock is off. Clock movement uses elapsed time, independently of playback speed. The existing clock size, movement, format and seconds settings remain supported.

Pause, Dream stop and surface destruction release the decoder. Resume prepares a new decoder and seeks to the saved film position. Destroying the texture releases its owned Surface; Android releases the SurfaceTexture. Decoder failure retries the bundled 720p version once, then exits the scene rather than presenting a permanent blank view.

`SettingsValues` validates the unchanged nine preference keys. `FlightOptions` maps raw speed values to actual playback rates. Palette and density remain stored but inactive; they do not grade, crop or fabricate clouds. Settings writes are committed before the provider reports success. The app has no Internet permission, runtime framework dependency, updater, background worker or reporting SDK.
