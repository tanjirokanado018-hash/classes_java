package androidx.core.text;

import android.text.Spanned;
import android.text.SpannedString;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SpannedStringKt {
    public static final /* synthetic */ <T> T[] getSpans(Spanned spanned, int i2, int i3) {
        k.o();
        throw null;
    }

    public static /* synthetic */ Object[] getSpans$default(Spanned spanned, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            spanned.length();
        }
        k.o();
        throw null;
    }

    public static final Spanned toSpanned(CharSequence charSequence) {
        return SpannedString.valueOf(charSequence);
    }
}
