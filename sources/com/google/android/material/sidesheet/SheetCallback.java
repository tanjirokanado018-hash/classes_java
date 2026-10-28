package com.google.android.material.sidesheet;

import android.view.View;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
interface SheetCallback {
    void onSlide(@NonNull View view, float f2);

    void onStateChanged(@NonNull View view, int i2);
}
