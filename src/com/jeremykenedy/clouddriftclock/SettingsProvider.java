package com.jeremykenedy.clouddriftclock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.preference.PreferenceManager;

public final class SettingsProvider extends ContentProvider {
    public static final String AUTHORITY = "com.jeremykenedy.clouddriftclock.settings";
    private static final int SCHEMA = 1;
    private static final int SETTINGS = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "schema", SCHEMA);
        URI_MATCHER.addURI(AUTHORITY, "settings", SETTINGS);
    }

    @Override
    public boolean onCreate() { return true; }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        int match = URI_MATCHER.match(uri);
        if (match == SCHEMA) return schemaCursor();
        if (match == SETTINGS) return settingsCursor();
        throw new IllegalArgumentException("Unknown settings URI");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) != SETTINGS || values == null) {
            throw new IllegalArgumentException("Unknown settings URI");
        }
        String key = values.getAsString("key");
        String value = values.getAsString("value");
        if (!SettingsValues.isSupported(key, value)) {
            throw new IllegalArgumentException("Unsupported setting value");
        }
        Context context = getContext();
        if (context == null) return 0;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if ("randomize_all".equals(key)) {
            editor.putBoolean(key, Boolean.parseBoolean(value));
        } else {
            editor.putString(key, value);
        }
        editor.apply();
        return 1;
    }

    private Cursor schemaCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[] {"key", "title", "type", "default", "choices", "randomAllowed"});
        addChoice(cursor, "palette", "Sky palette", "day", "day|sunset|night|random");
        addChoice(cursor, "density", "Cloud density", "balanced", "light|balanced|full|random");
        addChoice(cursor, "speed", "Cloud motion", "gentle", "slow|gentle|drifting|random");
        addChoice(cursor, "clock_size", "Clock size", "standard", "subtle|standard|large|random");
        addChoice(cursor, "clock_drift", "Clock movement", "short", "anchored|short|wide|random");
        addChoice(cursor, "clock_format", "Time format", "12h", "12h|24h|random");
        addChoice(cursor, "show_seconds", "Show seconds", "false", "false|true|random");
        cursor.addRow(new Object[] {"randomize_all", "Randomize every setting on start", "boolean", "false", "true|false", false});
        return cursor;
    }

    private void addChoice(MatrixCursor cursor, String key, String title, String fallback,
            String choices) {
        cursor.addRow(new Object[] {key, title, "choice", fallback, choices, true});
    }

    private Cursor settingsCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[] {"key", "value"});
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getContext());
        addValue(cursor, "palette", SettingsValues.normalizeChoice("palette", p.getAll().get("palette"), "day"));
        addValue(cursor, "density", SettingsValues.normalizeChoice("density", p.getAll().get("density"), "balanced"));
        addValue(cursor, "speed", SettingsValues.normalizeChoice("speed", p.getAll().get("speed"), "gentle"));
        addValue(cursor, "clock_size", SettingsValues.normalizeChoice("clock_size", p.getAll().get("clock_size"), "standard"));
        addValue(cursor, "clock_drift", SettingsValues.normalizeChoice("clock_drift", p.getAll().get("clock_drift"), "short"));
        addValue(cursor, "clock_format", SettingsValues.normalizeChoice("clock_format", p.getAll().get("clock_format"), "12h"));
        addValue(cursor, "show_seconds", SettingsValues.normalizeChoice("show_seconds", p.getAll().get("show_seconds"), "false"));
        addValue(cursor, "randomize_all", Boolean.toString(p.getBoolean("randomize_all", false)));
        return cursor;
    }

    private void addValue(MatrixCursor cursor, String key, String value) {
        cursor.addRow(new Object[] {key, value});
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        if (match == SCHEMA) return "vnd.android.cursor.dir/vnd.clouddriftclock.setting-schema.v1";
        if (match == SETTINGS) return "vnd.android.cursor.dir/vnd.clouddriftclock.setting.v1";
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Insert is not supported");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Delete is not supported");
    }
}
