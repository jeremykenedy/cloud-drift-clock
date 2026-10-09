# Project standards

- Keep the Android screensaver offline. Do not add network permission, ads, analytics, tracking, telemetry, crash reporting, or remote configuration.
- Preserve the package ID and production release signing key for updates.
- The clock is the only text intentionally rendered in the scene. Do not add logos or watermarks.
- Render to the Android-provided surface size. Do not force 4K without physical-device measurements.
- Stop frame scheduling and release graphics resources when the view or DreamService stops.
- Keep settings D-pad accessible and expose supported values through the documented settings provider.
- Keep runtime dependencies out of the app unless a feature requires them.
- Keep signing keys, passwords, device state backups, and build outputs out of Git.
- Use Apache License 2.0 and retain notices for redistributed content.
- Do not add Aikido or Scrutinizer integrations or badges.
- Run `./test.sh`, `./build.sh --unsigned`, `bash scripts/check-style.sh`, and `python3 scripts/check-docs.py` before release.
- Record emulator and physical-device evidence separately. Request missing-device reports with model, OS/API, resolution, behavior, and results.
