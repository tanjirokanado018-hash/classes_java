package androidx.core.os;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.util.Locale;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
interface LocaleListInterface {
    Locale get(int i2);

    @Nullable
    Locale getFirstMatch(@NonNull String[] strArr);

    Object getLocaleList();

    @IntRange(from = RecyclerView.NO_ID)
    int indexOf(Locale locale);

    boolean isEmpty();

    @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY)
    int size();

    String toLanguageTags();
}
