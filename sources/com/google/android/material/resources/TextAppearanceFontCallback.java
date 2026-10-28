package com.google.android.material.resources;

import android.graphics.Typeface;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class TextAppearanceFontCallback {
    public abstract void onFontRetrievalFailed(int i2);

    public abstract void onFontRetrieved(Typeface typeface, boolean z2);
}
