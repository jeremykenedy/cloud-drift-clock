# Settings provider

The exported provider authority is `com.jeremykenedy.clouddriftclock.settings`. A host can query the schema and current choices, then update one supported key. This interface is local to the device and performs no network requests.

| URI | Operation | Columns |
| --- | --- | --- |
| `content://com.jeremykenedy.clouddriftclock.settings/schema` | Query | `key`, `title`, `type`, `default`, `choices`, `randomAllowed` |
| `content://com.jeremykenedy.clouddriftclock.settings/settings` | Query | `key`, `value` |
| `content://com.jeremykenedy.clouddriftclock.settings/settings` | Update | Supply `key` and `value` in `ContentValues` |

Choice updates use the exact schema values, including `random` where allowed. The `randomize_all` boolean uses the strings `true` or `false`. Invalid keys and values are rejected. Insert and delete are unsupported. Settings persist in the app's default shared preferences.
