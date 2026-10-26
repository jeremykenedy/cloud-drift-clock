# Integrations

## Fire TV Toolkit

Toolkit's existing `cloud-drift-clock` entry installs the latest signed upstream release and checks the published SHA-256. Installation and selection remain separate:

```bash
firetv-screensavers --install=cloud-drift-clock --yes
screensaver --set=cloud-drift-clock
```

Repeating install performs a replacement update while keeping app preferences. Remove with `firetv-screensavers --uninstall=cloud-drift-clock --yes --force`; removal deletes the app's private preferences. Toolkit additionally manages launcher choices, supported setting guards and screensaver/sleep timers. These are host features, not behavior inside this offline app.

Commands were checked against Toolkit's current [screensaver guide](https://github.com/jeremykenedy/fire-tv-toolkit/blob/main/docs/SCREENSAVERS.md) and [command reference](https://github.com/jeremykenedy/fire-tv-toolkit/blob/main/docs/COMMANDS.md). App-specific preferences remain available through native settings and the documented provider.

## Fire TV UI

The host catalog can install and select its verified pinned release. The version 2 pin and current preview must be updated after the upstream public APK and checksum are verified. A source candidate or screenshot is not a published catalog update.

## Developer contract

| Field | Value |
| --- | --- |
| Toolkit id | `cloud-drift-clock` |
| Repository | `jeremykenedy/cloud-drift-clock` |
| Package | `com.jeremykenedy.clouddriftclock` |
| Dream | `com.jeremykenedy.clouddriftclock/.CloudDreamService` |
| Settings | `com.jeremykenedy.clouddriftclock/.SettingsActivity` |
| Preview | `com.jeremykenedy.clouddriftclock/.PreviewActivity` |
| Schema | `content://com.jeremykenedy.clouddriftclock.settings/schema` |
| Values | `content://com.jeremykenedy.clouddriftclock.settings/settings` |
| Provider rows | Nine retained keys, seven effective controls |
| APK asset | `cloud-drift-clock.apk` |
| Checksum asset | `cloud-drift-clock.apk.sha256` |
| Preview | [Current word-free native capture](../art/screenshots/cloud-flight-preview-clock-off-fire-tv-api28.png) |

Use the actual public tag, byte count, SHA-256 and production signer when updating pins. The exact version 2 fields are recorded in [the integration contract](integration.json). Verify the actual public release before installing or updating host pins. Preserve existing preference values, installation identity and the user's selected Dream independently.
