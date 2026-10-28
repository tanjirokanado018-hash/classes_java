package androidx.core.text;

import android.text.Html;
import android.text.Spanned;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class HtmlKt {
    public static final Spanned parseAsHtml(String str, int i2, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        return HtmlCompat.fromHtml(str, i2, imageGetter, tagHandler);
    }

    public static /* synthetic */ Spanned parseAsHtml$default(String str, int i2, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        if ((i3 & 2) != 0) {
            imageGetter = null;
        }
        if ((i3 & 4) != 0) {
            tagHandler = null;
        }
        return HtmlCompat.fromHtml(str, i2, imageGetter, tagHandler);
    }

    public static final String toHtml(Spanned spanned, int i2) {
        return HtmlCompat.toHtml(spanned, i2);
    }

    public static /* synthetic */ String toHtml$default(Spanned spanned, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        return HtmlCompat.toHtml(spanned, i2);
    }
}
