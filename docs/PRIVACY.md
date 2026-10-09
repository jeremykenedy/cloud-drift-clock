# Privacy

Cloud Drift Clock runs offline. The APK requests no Internet or network-state permission and includes no ads, analytics, telemetry, crash reporting, tracking, or remote configuration. It does not collect or report device, identity, usage, or diagnostic data.

When a user runs `install.py` without `--apk`, the installer makes a user-initiated HTTPS request to GitHub's release API and asset hosts to retrieve release metadata, the APK, and its checksum. It sends no device settings or usage information. Local APK installation and all screensaver runtime behavior work without a network request.
