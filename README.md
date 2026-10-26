<p align="center">
  <a href="https://github.com/jeremykenedy/cloud-drift-clock#gh-light-mode-only"><picture><source media="(max-width: 600px) and (prefers-reduced-motion: reduce)" srcset="art/banner-mobile-still-light.40850f17c4f6.svg"><source media="(prefers-reduced-motion: reduce)" srcset="art/banner-still-light.bf068140f254.svg"><source media="(max-width: 600px)" srcset="art/banner-mobile-light.d2ed5d3a1189.svg"><img src="art/banner-light.8e348eabeaa7.svg#gh-light-mode-only" alt="Cloud Drift Clock" width="800"></picture></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock#gh-dark-mode-only"><picture><source media="(max-width: 600px) and (prefers-reduced-motion: reduce)" srcset="art/banner-mobile-still-dark.b9d55b67059f.svg"><source media="(prefers-reduced-motion: reduce)" srcset="art/banner-still-dark.b607e7d0e19b.svg"><source media="(max-width: 600px)" srcset="art/banner-mobile-dark.adaf0a6e7903.svg"><img src="art/banner-dark.61fdd9732194.svg#gh-dark-mode-only" alt="Cloud Drift Clock" width="800"></picture></a>
</p>

<p align="center">Real cloud flight with an optional drifting clock. Offline, no tracking or analytics.</p>

<p align="center">
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/releases"><img src="https://img.shields.io/github/downloads/jeremykenedy/cloud-drift-clock/total?label=downloads&amp;cacheSeconds=0" alt="GitHub release downloads"></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/releases"><img src="https://img.shields.io/github/v/release/jeremykenedy/cloud-drift-clock?display_name=tag&amp;label=release&amp;cacheSeconds=0" alt="Latest release"></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/ci.yml"><img src="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/ci.yml/badge.svg" alt="Build and tests"></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/style.yml"><img src="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/style.yml/badge.svg" alt="Code style"></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/docs.yml"><img src="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/docs.yml/badge.svg" alt="Documentation checks"></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/security.yml"><img src="https://github.com/jeremykenedy/cloud-drift-clock/actions/workflows/security.yml/badge.svg" alt="Privacy checks"></a>
  <a href="LICENSE"><img src="https://img.shields.io/github/license/jeremykenedy/cloud-drift-clock?label=Apache-2.0" alt="Apache License 2.0"></a>
</p>

<p align="center">
  <a href="https://github.com/jeremykenedy?tab=followers"><img src="https://img.shields.io/github/followers/jeremykenedy?label=follow%20Jeremy" alt="Follow Jeremy Kenedy"></a>
  <a href="https://github.com/jeremykenedy/cloud-drift-clock/stargazers"><img src="https://img.shields.io/github/stars/jeremykenedy/cloud-drift-clock?style=social" alt="Star this repository"></a>
  <a href="https://github.com/sponsors/jeremykenedy"><img src="https://img.shields.io/badge/Sponsor-Jeremy%20Kenedy-ea4aaa?logo=github-sponsors" alt="Sponsor Jeremy Kenedy"></a>
</p>

If you enjoy Cloud Drift Clock, please star the repository on GitHub.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Settings](#settings)
- [Requirements](#requirements)
- [Installation](#installation)
- [Fire TV Toolkit](#fire-tv-toolkit)
- [Privacy](#privacy)
- [Device support](#device-support)
- [Build and test](#build-and-test)
- [Documentation](#documentation)
- [License](#license)

## Overview

Fly beside and through real sunset cloud banks with an optional local clock. The film includes nearby vapor, distant mountains and camera banking. It replaces version 1's cropped photograph animation.

The clock keeps its earlier On default. Turn **Show clock** off for a scene with no words. The 34-second film uses a disclosed 1.2-second dissolve; it is an edited loop rather than a naturally closed flight path.

## Features

| Feature | What it provides |
| --- | --- |
| [Real flight](docs/ARTWORK.md) | Bundled 1080p film and 720p decoder fallback |
| [Clock](docs/CONFIGURATION.md) | Visibility, size, movement, format and seconds |
| [Playback](docs/CONFIGURATION.md) | Actual 0.75x, 1x and 1.25x speed, with Random |
| [Remote controls](docs/SCREENSHOTS.md) | Native settings, Preview and offline Credits |
| [Saved settings](docs/SETTINGS_PROVIDER.md) | Nine retained keys; palette and density are inactive |
| [Privacy](docs/PRIVACY.md) | No Internet permission, advertising or reporting SDK |

## Screenshots

<table>
  <tr><th>Clock Off</th><th>Clock On</th><th>Clock drift</th></tr>
  <tr><td><img src="art/screenshots/cloud-flight-preview-final-fire-tv-api28.png" alt="Real sunset clouds passing close to the camera with no scene text" width="280"></td><td><img src="art/screenshots/cloud-flight-clock-on-fire-tv-api28.png" alt="Large 24-hour clock with seconds over the real cloud flight" width="280"></td><td><img src="art/screenshots/cloud-flight-clock-drift-fire-tv-api28.png" alt="The optional clock has moved toward the right as the filmed flight continues" width="280"></td></tr>
</table>

Actual physical Fire TV Preview captures, API 28, 1920 by 1080. See the [current native gallery and provenance](docs/SCREENSHOTS.md). These are not 4K-rendering claims.

## Settings

Use the launcher entry to open settings, Preview or Credits. Seven effective controls cover playback speed and clock appearance. The nine existing keys remain valid. Palette and density are inactive for filmed scenery, so the app does not offer them as cloud or lighting controls. Randomization never changes Show clock.

[Configuration](docs/CONFIGURATION.md) explains defaults, slow-motion source cadence and loop limitations. [Settings provider](docs/SETTINGS_PROVIDER.md) documents the stable local interface.

## Requirements

Android 6.0 (API 23) or later with DreamService and H264 playback support is the declared platform floor. Actual device results are listed below; an API declaration does not establish compatibility with every TV. Allow storage for the roughly 40 MB APK and normal installation staging.

The optional computer installer requires Python 3 and ADB. Fire TV Toolkit setup requires Node.js.

## Installation

Download the signed APK and its `.sha256` sidecar from [GitHub Releases](https://github.com/jeremykenedy/cloud-drift-clock/releases), verify it, then update in place:

```bash
shasum -a 256 -c cloud-drift-clock.apk.sha256
adb -s TV_SERIAL install -r cloud-drift-clock.apk
```

Or use the guided installer from a checkout:

```bash
python3 install.py --serial TV_IP:5555
```

The installer verifies the release checksum and leaves system timers and the selected screensaver unchanged. Do not uninstall first when upgrading; uninstalling deletes app preferences. The launcher opens native settings. Selection through Fire OS may require a supported host such as Toolkit or Fire TV UI.

## Fire TV Toolkit

[Fire TV Toolkit](https://github.com/jeremykenedy/fire-tv-toolkit) already includes `cloud-drift-clock`. Set it up on the computer managing the TV:

```bash
git clone https://github.com/jeremykenedy/fire-tv-toolkit.git
cd fire-tv-toolkit
node setup.js
```

Install, select, update or remove Cloud Drift Clock:

```bash
firetv-screensavers --install=cloud-drift-clock --yes
screensaver --set=cloud-drift-clock
firetv-screensavers --install=cloud-drift-clock --yes
firetv-screensavers --uninstall=cloud-drift-clock --yes --force
```

Repeating installation updates in place. Removal deletes this app's preferences. Toolkit also manages available launcher choices, screensaver/sleep timers and supported setting guards. This screensaver itself does not change those system settings. [Integration details](docs/INTEGRATIONS.md) distinguish installation, selection and native app preferences.

## Privacy

All footage is bundled. The app has no Internet permission, ads, analytics, telemetry, crash reporting or reporting SDK. The optional installer contacts GitHub only for a requested release download. See [Privacy details](docs/PRIVACY.md).

## Device support

| Platform | Current version 2 evidence |
| --- | --- |
| Fire TV AFTALMO, API 28, 1920 by 1080 capture | Signed update retained all nine provider choices; seven native D-pad controls, Preview and independent clock visibility/drift verified. Two normal non-test Dreams passed the unchanged five-minute idle path. Final signed APK, Credits navigation, Preview and strict restoration checks passed. |
| Other Fire TV hardware | Current filmed build not yet verified; volunteer device testing needed |
| Android TV hardware | Current filmed build not yet verified; volunteer device testing needed |
| Google TV hardware | Current filmed build not yet verified; volunteer device testing needed |

[Device verification](docs/VERIFICATION.md) separates current evidence from historical version 1 emulator results. Native 4K, sustained thermal behavior and power consumption are not claimed.

## Build and test

Use JDK 21, Android SDK platform 36/build-tools 36.0.0 and Python 3:

```bash
./test.sh
./build.sh --unsigned
./build.sh
```

Preserve the existing `~/.android/cloud-drift-clock.jks` and `.pass` files for updates. Keep signing material outside the repository and CI. [Build instructions](docs/BUILDING.md) describe outputs and overrides.

## Documentation

- [Architecture](docs/ARCHITECTURE.md)
- [Build instructions](docs/BUILDING.md)
- [CI checks](docs/CI.md)
- [Configuration](docs/CONFIGURATION.md)
- [Installation and removal](docs/INSTALLATION.md)
- [Integrations](docs/INTEGRATIONS.md)
- [Current native gallery](docs/SCREENSHOTS.md)
- [Privacy details](docs/PRIVACY.md)
- [Settings provider](docs/SETTINGS_PROVIDER.md)
- [Testing and coverage](docs/TESTING.md)
- [Troubleshooting](docs/TROUBLESHOOTING.md)
- [Device verification](docs/VERIFICATION.md)
- [Release process](docs/RELEASING.md)
- [Version 2.0 release notes](docs/releases/v2.0.0.md)
- [Earlier release notes](docs/releases/v1.0.1.md), [v1.0.0](docs/releases/v1.0.0.md)
- [Artwork and footage provenance](docs/ARTWORK.md)

## License

Application code is licensed under the [Apache License 2.0](LICENSE). The filmed scene retains its separate CC BY 3.0 license. See [NOTICE](NOTICE) and [footage provenance](docs/ARTWORK.md).
