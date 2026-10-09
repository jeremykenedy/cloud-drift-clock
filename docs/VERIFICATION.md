# Device verification

| Device | Status | Notes |
| --- | --- | --- |
| Android TV emulator, API 31, 1920 by 1080 | Settings screen and full-screen animation preview verified; settings schema and persisted updates queried through the provider; scene changed between captures | This emulator image does not expose the `cmd dream` service, so native DreamService selection and start were unavailable |
| Fire TV hardware | Untested, no device was available | Please report device model, Fire OS version, resolution, selection/start behavior, and results in a [device test issue](https://github.com/jeremykenedy/cloud-drift-clock/issues/new?template=device-test.yml) |
| Android TV hardware | Untested, no device was available | Please report device model, Android version, resolution, selection/start behavior, and results in a [device test issue](https://github.com/jeremykenedy/cloud-drift-clock/issues/new?template=device-test.yml) |
| Google TV hardware | Untested, no device was available | Please report device model, Android version, resolution, selection/start behavior, and results in a [device test issue](https://github.com/jeremykenedy/cloud-drift-clock/issues/new?template=device-test.yml) |

The emulator screenshot resolution is not evidence of 4K rendering. Physical activation, frame pacing, thermals, idle power, and vendor-specific picker behavior remain untested.
