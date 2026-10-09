# Releasing

Use SemVer. Before tagging, run all test, style, documentation, privacy, build, manifest, and coverage checks on the exact release commit. Confirm the app ID, service component, permissions, `minSdkVersion`, version name/code, signing certificate, and APK checksum.

Build the signed APK with the preserved release key. Attach `cloud-drift-clock.apk` and its sibling `cloud-drift-clock.apk.sha256` to the GitHub release. Verify the downloaded artifact matches the release checksum. Keep release notes in `docs/releases/vX.Y.Z.md`, identify changes and breaking behavior, state the upgrade path, and keep `CHANGELOG.md`, package version, tag, and README consistent.

Never overwrite a published tag. Fix a released defect in a new patch version. Signing keys and passwords remain outside source control and CI.
