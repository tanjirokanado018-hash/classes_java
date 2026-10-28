package com.google.android.material.resources;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@Deprecated
public class TextAppearanceConfig {
    private static boolean shouldLoadFontSynchronously;

    public static void setShouldLoadFontSynchronously(boolean z2) {
        shouldLoadFontSynchronously = z2;
    }

    public static boolean shouldLoadFontSynchronously() {
        return shouldLoadFontSynchronously;
    }
}
