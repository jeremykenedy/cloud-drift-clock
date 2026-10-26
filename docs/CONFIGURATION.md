# Configuration

The launcher opens the D-pad settings screen and its full-screen Preview. Changes are saved immediately and apply the next time a scene is created. Version 2 uses real sunset flight footage with photographed cloud cover, sunlight and camera banking.

| Setting | Options | Default | Behavior |
| --- | --- | --- | --- |
| Playback speed | 0.75x, 1x, 1.25x, Random | 1x | Changes actual film playback speed |
| Show clock | On or off | On, unchanged from version 1 | Off removes all scene text; randomization never changes visibility |
| Clock size | Subtle, Standard, Large, Random | Standard | Changes local time size |
| Clock movement | Centered, Gentle drift, Wide drift, Random | Gentle drift | Controls clock travel; independent of flight speed |
| Time format | 12-hour, 24-hour, Random | 12-hour | Chooses local time format |
| Show seconds | Off, On, Random | Off | Adds seconds when the clock is enabled |
| Randomize playback speed and clock style | On or off | Off | Resolves supported random choices when the scene is created |

The source film has 25 frames per second. Slow motion retains each source frame longer: 0.75x has 18.75 source frames per elapsed second. This is separate from display refresh or SurfaceFlinger presentation measurements. Playback does not invent intermediate frames.

The edited loop lasts 34 seconds at original speed. A 1.2-second dissolve joins similar flight headings; the camera did not fly a naturally closed path. The film retains its camera roll. This is one sunset cloud flight, not a choice of artificial weather conditions.

The nine existing preference keys remain valid. The old `palette` and `density` values are retained and validated for older integrations, but they do not change the filmed scene and are not offered as effective settings. The remaining seven controls work as described above. Stored `show_clock` values and the earlier implicit On default remain unchanged. There is no data-clearing migration.

See [Settings provider](SETTINGS_PROVIDER.md) for the local host interface and [Artwork](ARTWORK.md) for the source and license.
