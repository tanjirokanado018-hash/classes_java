package com.google.android.material.color;

import android.os.Build;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class b {
    public static ColorResourcesOverride a() {
        int i2 = Build.VERSION.SDK_INT;
        if (30 <= i2 && i2 <= 33) {
            return ResourcesLoaderColorResourcesOverride.getInstance();
        }
        if (i2 >= 34) {
            return ResourcesLoaderColorResourcesOverride.getInstance();
        }
        return null;
    }
}
