package androidx.core.content.res;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleableRes;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(26)
final class TypedArrayApi26ImplKt {
    public static final TypedArrayApi26ImplKt INSTANCE = new TypedArrayApi26ImplKt();

    private TypedArrayApi26ImplKt() {
    }

    @DoNotInline
    public static final Typeface getFont(TypedArray typedArray, @StyleableRes int i2) {
        Typeface font = typedArray.getFont(i2);
        k.d(font);
        return font;
    }
}
