package androidx.core.text;

import android.text.Spannable;
import android.text.SpannableString;
import s.d;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SpannableStringKt {
    public static final void clearSpans(Spannable spannable) {
        for (Object obj : spannable.getSpans(0, spannable.length(), Object.class)) {
            spannable.removeSpan(obj);
        }
    }

    public static final void set(Spannable spannable, int i2, int i3, Object obj) {
        spannable.setSpan(obj, i2, i3, 17);
    }

    public static final Spannable toSpannable(CharSequence charSequence) {
        return SpannableString.valueOf(charSequence);
    }

    public static final void set(Spannable spannable, d dVar, Object obj) {
        spannable.setSpan(obj, dVar.b, dVar.f499c, 17);
    }
}
