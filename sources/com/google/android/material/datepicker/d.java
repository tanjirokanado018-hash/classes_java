package com.google.android.material.datepicker;

import android.view.View;
import android.widget.EditText;
import com.google.android.material.internal.ViewUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class d {
    public static /* synthetic */ void a(EditText[] editTextArr, View view, boolean z2) {
        for (EditText editText : editTextArr) {
            if (editText.hasFocus()) {
                return;
            }
        }
        ViewUtils.hideKeyboard(view, false);
    }

    public static void c(final EditText... editTextArr) {
        if (editTextArr.length == 0) {
            return;
        }
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.google.android.material.datepicker.c
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                d.a(editTextArr, view, z2);
            }
        };
        for (EditText editText : editTextArr) {
            editText.setOnFocusChangeListener(onFocusChangeListener);
        }
        EditText editText2 = editTextArr[0];
        editText2.postDelayed(new com.google.android.material.bottomappbar.a(editText2, 1), 100L);
    }
}
