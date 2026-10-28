package com.google.android.material.sidesheet;

import android.view.View;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class SideSheetCallback implements SheetCallback {
    public void onLayout(@NonNull View view) {
    }

    @Override // com.google.android.material.sidesheet.SheetCallback
    public abstract void onSlide(@NonNull View view, float f2);

    @Override // com.google.android.material.sidesheet.SheetCallback
    public abstract void onStateChanged(@NonNull View view, int i2);
}
