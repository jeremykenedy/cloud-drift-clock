package com.jeremykenedy.clouddriftclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public final class SettingsActivity extends Activity {
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ScrollView scroll = new ScrollView(this);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(56, 30, 56, 32);
        scroll.addView(content);
        TextView title = new TextView(this);
        title.setText("Cloud Drift Clock settings");
        title.setTextSize(28);
        title.setPadding(0, 0, 0, 12);
        content.addView(title);
        TextView description = new TextView(this);
        description.setText("Real filmed flight with optional clock. Changes apply the next time the screensaver starts. Sunset cloud flight retains its photographed lighting and cloud cover; the old palette and density values are preserved but inactive.");
        description.setTextSize(16);
        description.setPadding(0, 0, 0, 12);
        content.addView(description);
        Button preview = new Button(this);
        preview.setText("Preview animation");
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, PreviewActivity.class));
            }
        });
        content.addView(preview);
        addChoice(content, "Playback speed", "speed",
                new String[] {"slow", "gentle", "drifting", "random"},
                new String[] {"0.75x slow motion", "1x original flight", "1.25x faster flight", "Random"}, "gentle");
        addCheckBox(content, "Show clock", "show_clock", true);
        addChoice(content, "Clock size", "clock_size",
                new String[] {"subtle", "standard", "large", "random"},
                new String[] {"Subtle", "Standard", "Large", "Random"}, "standard");
        addChoice(content, "Clock movement", "clock_drift",
                new String[] {"anchored", "short", "wide", "random"},
                new String[] {"Centered", "Gentle drift", "Wide drift", "Random"}, "short");
        addChoice(content, "Time format", "clock_format",
                new String[] {"12h", "24h", "random"},
                new String[] {"12-hour", "24-hour", "Random"}, "12h");
        addChoice(content, "Show seconds", "show_seconds",
                new String[] {"false", "true", "random"},
                new String[] {"Off", "On", "Random"}, "false");
        addCheckBox(content, "Randomize playback speed and clock style when the screensaver starts", "randomize_all", false);
        Button credits = new Button(this);
        credits.setText("Credits and licenses");
        credits.setOnClickListener(view -> showCredits());
        content.addView(credits);
        setContentView(scroll);
        preview.setFocusableInTouchMode(true);
        preview.requestFocus();
    }

    private void showCredits() {
        TextView text = new TextView(this);
        text.setTextSize(18);
        text.setPadding(32, 20, 32, 20);
        try {
            StringBuilder credits = new StringBuilder("Press Up to read. Press Right to return to Close.\n\n");
            for (String name : new String[] {"NOTICE.txt", "CC-BY-3.0.txt", "LICENSE.txt"}) {
                try (java.io.InputStream stream = getAssets().open(name)) {
                    java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = stream.read(buffer)) != -1) bytes.write(buffer, 0, count);
                    credits.append(new String(bytes.toByteArray(), java.nio.charset.StandardCharsets.UTF_8)).append("\n\n");
                }
            }
            text.setText(credits);
        } catch (java.io.IOException error) {
            text.setText("Credits could not be loaded.");
        }
        ScrollView scroll = new ScrollView(this);
        scroll.setFocusable(true);
        scroll.addView(text);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                .setTitle("Credits and licenses").setView(scroll)
                .setPositiveButton("Close", null).create();
        dialog.setOnShowListener(shown -> {
            Button close = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
            close.setFocusableInTouchMode(true);
            close.requestFocus();
            scroll.setOnKeyListener((view, keyCode, event) -> {
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN
                        && keyCode == android.view.KeyEvent.KEYCODE_DPAD_RIGHT) {
                    close.requestFocus();
                    return true;
                }
                return false;
            });
        });
        dialog.show();
    }

    private void addChoice(LinearLayout content, String title, String key, String[] values,
            String[] labels, String fallback) {
        TextView label = new TextView(this);
        label.setText(title);
        label.setTextSize(18);
        label.setPadding(0, 14, 0, 3);
        content.addView(label);
        Spinner spinner = new Spinner(this);
        spinner.setFocusable(true);
        spinner.setFocusableInTouchMode(true);
        spinner.setGravity(Gravity.CENTER_VERTICAL);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Object stored = preferences.getAll().get(key);
        String selected = SettingsValues.normalizeChoice(key, stored, fallback);
        if (stored != null && !(stored instanceof String)) {
            preferences.edit().putString(key, selected).commit();
        }
        int selection = 0;
        for (int i = 0; i < values.length; i++) if (values[i].equals(selected)) selection = i;
        spinner.setSelection(selection);
        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view,
                    int position, long id) {
                preferences.edit().putString(key, values[position]).commit();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
        content.addView(spinner);
    }

    private void addCheckBox(LinearLayout content, String title, String key, boolean fallback) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(title);
        checkBox.setTextSize(18);
        checkBox.setChecked(SettingsValues.booleanValue(preferences.getAll().get(key), fallback));
        checkBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton button, boolean checked) {
                preferences.edit().putBoolean(key, checked).commit();
            }
        });
        content.addView(checkBox);
    }
}
