package com.google.android.material.slider;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface BaseOnSliderTouchListener<S> {
    void onStartTrackingTouch(@NonNull S s2);

    void onStopTrackingTouch(@NonNull S s2);
}
