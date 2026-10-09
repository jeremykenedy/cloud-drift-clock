# Testing

Run the complete host suite with:

```bash
./test.sh
```

This runs Java unit tests with JaCoCo line and branch coverage gates, then tests the installer with Python coverage. Both gates require 100 percent coverage for the project-owned testable logic included in their reports. Android framework classes are not included in the host coverage boundary.

Also run `./build.sh --unsigned` to compile the complete app, and use an Android TV emulator to inspect settings and preview behavior. Review permissions and package identity with `aapt dump badging` and `aapt dump permissions`. Emulator preview checks do not prove vendor DreamService selection, auto-start, 4K composition, or physical-device performance.
