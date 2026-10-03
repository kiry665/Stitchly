package com.kiry665.stitchly.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsRepository {

    private static final String PREFS_NAME = "stitchly_settings";

    private static final String KEY_VIBRATION =
            "vibration_enabled";

    private static final String KEY_KEEP_SCREEN_ON =
            "keep_screen_on";

    private final SharedPreferences preferences;

    public SettingsRepository(Context context) {
        preferences = context
                .getApplicationContext()
                .getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                );
    }

    public boolean isVibrationEnabled() {
        return preferences.getBoolean(
                KEY_VIBRATION,
                true
        );
    }

    public void setVibrationEnabled(boolean enabled) {
        preferences.edit()
                .putBoolean(KEY_VIBRATION, enabled)
                .apply();
    }

    public boolean isKeepScreenOnEnabled() {
        return preferences.getBoolean(
                KEY_KEEP_SCREEN_ON,
                false
        );
    }

    public void setKeepScreenOnEnabled(boolean enabled) {
        preferences.edit()
                .putBoolean(KEY_KEEP_SCREEN_ON, enabled)
                .apply();
    }

}
