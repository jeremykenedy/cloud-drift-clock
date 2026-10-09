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
        description.setText("Tune the cloud scene and floating clock. Changes apply the next time the screensaver starts.");
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
        addChoice(content, "Sky palette", "palette",
                new String[] {"day", "sunset", "night", "random"},
                new String[] {"Daylight", "Golden hour", "Moonlit", "Random"}, "day");
        addChoice(content, "Cloud density", "density",
                new String[] {"light", "balanced", "full", "random"},
                new String[] {"Light", "Balanced", "Full", "Random"}, "balanced");
        addChoice(content, "Cloud motion", "speed",
                new String[] {"slow", "gentle", "drifting", "random"},
                new String[] {"Slow", "Gentle", "Drifting", "Random"}, "gentle");
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
        addCheckBox(content, "Randomize every setting when the screensaver starts", "randomize_all", false);
        setContentView(scroll);
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
        String selected = preferences.getString(key, fallback);
        int selection = 0;
        for (int i = 0; i < values.length; i++) if (values[i].equals(selected)) selection = i;
        spinner.setSelection(selection);
        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view,
                    int position, long id) {
                preferences.edit().putString(key, values[position]).apply();
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
        checkBox.setChecked(preferences.getBoolean(key, fallback));
        checkBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton button, boolean checked) {
                preferences.edit().putBoolean(key, checked).apply();
            }
        });
        content.addView(checkBox);
    }
}
