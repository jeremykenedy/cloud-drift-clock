# Device verification

| Device | Status | Notes |
| --- | --- | --- |
| Android TV emulator, API 31, 1920 by 1080 | Settings screen opened; Fire TV UI's installed-screensaver preview started the actual DreamService and displayed the animated cloud scene; exiting returned to the catalog and restored the previous screensaver selection and enabled state | `dumpsys dreams`, UI screenshot, and secure-setting before/after checks, 2026-10-08 |
| Fire TV hardware | Untested, no device was available | Please report device model, Fire OS version, resolution, selection/start behavior, and results in a [device test issue](https://github.com/jeremykenedy/cloud-drift-clock/issues/new?template=device-test.yml) |
| Android TV hardware | Untested, no device was available | Please report device model, Android version, resolution, selection/start behavior, and results in a [device test issue](https://github.com/jeremykenedy/cloud-drift-clock/issues/new?template=device-test.yml) |
| Google TV hardware | Untested, no device was available | Please report device model, Android version, resolution, selection/start behavior, and results in a [device test issue](https://github.com/jeremykenedy/cloud-drift-clock/issues/new?template=device-test.yml) |

The emulator screenshot resolution is not evidence of 4K rendering. Automatic idle activation, hardware frame pacing, thermals, idle power, and physical vendor-specific picker behavior remain untested.
