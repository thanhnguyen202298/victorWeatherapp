package com.victorthanh.utilslib.utils.helper;

import android.content.Context;

import com.victorthanh.utilslib.R;
import com.victorthanh.utilslib.data.preference.Preferences;

public class ThemeHelper {
    public static void setTheme(Context context, int theme) {
        Preferences prefs = new Preferences(context);
        prefs.put(context.getString(R.string.prefs_theme_key), theme);
    }

    public static int getTheme(Context context) {
        Preferences prefs = new Preferences(context);
        return prefs.get(context.getString(R.string.prefs_theme_key), Integer.class);
    }
}