package androidx.core.text;

import android.text.TextUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class CharSequenceKt {
    public static final boolean isDigitsOnly(CharSequence charSequence) {
        return TextUtils.isDigitsOnly(charSequence);
    }

    public static final int trimmedLength(CharSequence charSequence) {
        return TextUtils.getTrimmedLength(charSequence);
    }
}
