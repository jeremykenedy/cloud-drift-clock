# Settings provider

The exported provider authority is `com.jeremykenedy.clouddriftclock.settings`. A host can query the schema and current choices, then update one supported key. This interface is local to the device and performs no network requests.

| URI | Operation | Columns |
| --- | --- | --- |
| `content://com.jeremykenedy.clouddriftclock.settings/schema` | Query | `key`, `title`, `type`, `default`, `choices`, `randomAllowed` |
| `content://com.jeremykenedy.clouddriftclock.settings/settings` | Query | `key`, `value` |
| `content://com.jeremykenedy.clouddriftclock.settings/settings` | Update | Supply `key` and `value` in `ContentValues` |

Choice updates use the exact schema values, including `random` where allowed. The `randomize_all` and `show_clock` booleans use the strings `true` or `false`. `show_clock` defaults to `true` for existing installations, is not randomizable, and remains off when `randomize_all` is enabled. The provider stores it as a boolean; both boolean and canonical string values are read safely from existing preferences. Invalid keys and values are rejected. Insert and delete are unsupported. Settings persist in the app's default shared preferences.

The settings query now includes nine values. The added row is `show_clock`, with type `boolean`, default `true`, choices `true|false` and `randomAllowed=false`. Existing saved clock size, movement, time format and seconds values remain unchanged when visibility is disabled. Host editors should discover rows by key rather than assume a fixed count.
