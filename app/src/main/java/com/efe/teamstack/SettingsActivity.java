package com.efe.teamstack;

import android.app.IntentService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        sp.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //update preferences when settings are changed
        System.out.println("change " + key);
        if (key.equals("num_per_set")) {
            MainActivity.numPerSet = Integer.parseInt(sharedPreferences.getString(key, "4"));
        }
        if (key.equals("randomize")) {
            MainActivity.randomize = sharedPreferences.getBoolean(key, true);
        }
        if (key.equals("notify")) {
            MainActivity.notify = sharedPreferences.getBoolean(key, true);
        }
        if (key.equals("mutually_exclusive")) {
            MainActivity.mut_exc = sharedPreferences.getBoolean(key, false);
        }
        if (key.equals("leftover_players")) {
            MainActivity.show_leftover_players = sharedPreferences.getBoolean(key, false);
        }
    }

    /*@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.e("preference", "Pending Preference value is: " + newValue);
        return true;
    }*/

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}