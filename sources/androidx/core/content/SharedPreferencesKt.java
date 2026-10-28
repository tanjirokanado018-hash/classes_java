package androidx.core.content;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SharedPreferencesKt {
    @SuppressLint({"ApplySharedPref"})
    public static final void edit(SharedPreferences sharedPreferences, boolean z2, l lVar) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        lVar.invoke(editorEdit);
        if (z2) {
            editorEdit.commit();
        } else {
            editorEdit.apply();
        }
    }

    public static /* synthetic */ void edit$default(SharedPreferences sharedPreferences, boolean z2, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        lVar.invoke(editorEdit);
        if (z2) {
            editorEdit.commit();
        } else {
            editorEdit.apply();
        }
    }
}
