# Installation and removal

## Install from a release

Download `cloud-drift-clock.apk` and `cloud-drift-clock.apk.sha256` from the [latest GitHub release](https://github.com/jeremykenedy/cloud-drift-clock/releases), verify the checksum, and install with ADB:

```bash
shasum -a 256 -c cloud-drift-clock.apk.sha256
adb install -r cloud-drift-clock.apk
```

Open Cloud Drift Clock from the launcher to configure it or preview the animation. Choose it from the device's screensaver settings to make it active.

## Use the standalone installer

```bash
python3 install.py --serial TV_IP:5555
```

The installer asks before install or update, confirms a connected device, downloads the latest release from this repository, validates the release asset URL and SHA-256, and installs only this package. It does not change the selected screensaver, device timers, sleep behavior, or other apps.

For a locally built APK use `python3 install.py --serial TV_IP:5555 --apk build/cloud-drift-clock.apk`.

## Remove

Run `python3 install.py --serial TV_IP:5555 --uninstall` and confirm the removal. The non-interactive form requires both `--yes` and `--force`:

```bash
python3 install.py --serial TV_IP:5555 --uninstall --yes --force
```

Removal deletes the app and its app-local settings. The installer never edits system screensaver choices or timers, so there are no system values to restore.
