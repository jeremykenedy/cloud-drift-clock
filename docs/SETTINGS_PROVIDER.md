# Settings provider

The authority remains `com.jeremykenedy.clouddriftclock.settings`. The interface is local and performs no network requests.

| URI | Operation | Columns |
| --- | --- | --- |
| `content://com.jeremykenedy.clouddriftclock.settings/schema` | Query | `key`, `title`, `type`, `default`, `choices`, `randomAllowed` |
| `content://com.jeremykenedy.clouddriftclock.settings/settings` | Query | `key`, `value` |
| `content://com.jeremykenedy.clouddriftclock.settings/settings` | Update | Supply one `key` and `value` in `ContentValues` |

| Key | Values | Default | Version 2 meaning |
| --- | --- | --- | --- |
| `palette` | `day`, `sunset`, `night`, `random` | `day` | Retained and validated, inactive for filmed scenery |
| `density` | `light`, `balanced`, `full`, `random` | `balanced` | Retained and validated, inactive for filmed scenery |
| `speed` | `slow`, `gentle`, `drifting`, `random` | `gentle` | Actual playback at 0.75x, 1x, 1.25x, or a random supported rate |
| `show_clock` | `true`, `false` | `true` | All clock visibility; never randomized |
| `clock_size` | `subtle`, `standard`, `large`, `random` | `standard` | Clock text size |
| `clock_drift` | `anchored`, `short`, `wide`, `random` | `short` | Clock travel range |
| `clock_format` | `12h`, `24h`, `random` | `12h` | Local time format |
| `show_seconds` | `false`, `true`, `random` | `false` | Seconds visibility when the clock is enabled |
| `randomize_all` | `true`, `false` | `false` | Playback speed and clock style randomization |

The two inactive schema rows identify that limitation in their titles. Host editors must not present them as controls that alter cloud cover or lighting. Stored values are preserved to avoid destroying older saved configuration.

`show_clock` and `randomize_all` are stored as booleans. Canonical strings are also read safely for clock visibility. Choice values are stored as strings. Unsupported keys or values are rejected, and insert/delete are unsupported. Successful updates finish the SharedPreferences write before returning; a failed write raises an error. Changes take effect when the next scene is created.

The package, provider authority, nine keys and component names remain stable across the upgrade. No new scene selector is advertised while only one film is bundled.
