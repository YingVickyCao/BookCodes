/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack45;

import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection utils to call SharedPreferences$Editor.apply when possible,
 * falling back to commit when apply isn't available.
 */
public class OldSharedPreferencesCompat {
    private static final String TAG = OldSharedPreferencesCompat.class.getCanonicalName();
    private static final Method sApplyMethod = findApplyMethod();

    private static Method findApplyMethod() {
        try {
            Class<?> cls = SharedPreferences.Editor.class;
            return cls.getMethod("apply");
        } catch (NoSuchMethodException unused) {
            // fall through
        }
        return null;
    }

    public static void apply(SharedPreferences.Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor);
                Log.d(TAG, "Apply method was invoked!");
                return;
            } catch (InvocationTargetException unused) {
                // fall through
            } catch (IllegalAccessException unused) {
                // fall through
            }
        }
        editor.commit();
        Log.d(TAG, "Commit method was invoked!");
    }
}
