package androidx.core.app;

import android.os.Parcelable;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextPaint;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class c {
    public static /* bridge */ /* synthetic */ android.app.Person f(Parcelable parcelable) {
        return (android.app.Person) parcelable;
    }

    public static /* bridge */ /* synthetic */ android.app.Person g(Object obj) {
        return (android.app.Person) obj;
    }

    public static /* synthetic */ PrecomputedText.Params.Builder m(TextPaint textPaint) {
        return new PrecomputedText.Params.Builder(textPaint);
    }

    public static /* bridge */ /* synthetic */ PrecomputedText o(Spannable spannable) {
        return (PrecomputedText) spannable;
    }

    public static /* bridge */ /* synthetic */ boolean y(Spannable spannable) {
        return spannable instanceof PrecomputedText;
    }
}
