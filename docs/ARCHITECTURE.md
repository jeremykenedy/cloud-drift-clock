# Architecture

Cloud Drift Clock is a standalone Android application with one DreamService, a full-screen preview activity, a D-pad settings activity, and a small exported content provider for host-app settings access.

The scene uses a bundled 1920 by 1080 cloudscape texture. Canvas draws the image at the surface size and animates a restrained camera drift plus a separately moving, cropped foreground cloud layer. Density controls framing and foreground layer strength. A digital clock is drawn over the scene and follows a slow elliptical path. The renderer schedules at approximately 30 frames per second only while visible and releases its bitmap when the dream detaches.

Settings are validated by `SettingsValues`, mapped to scene values by `ClockOptions`, and stored in Android shared preferences. The app has no Internet permission, runtime dependency, updater, background worker, or data collection. The optional installer contacts GitHub only for a requested release download.
