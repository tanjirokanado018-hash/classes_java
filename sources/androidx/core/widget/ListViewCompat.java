package androidx.core.widget;

import android.widget.ListView;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@Deprecated
public final class ListViewCompat {
    private ListViewCompat() {
    }

    @Deprecated
    public static boolean canScrollList(@NonNull ListView listView, int i2) {
        return listView.canScrollList(i2);
    }

    @Deprecated
    public static void scrollListBy(@NonNull ListView listView, int i2) {
        listView.scrollListBy(i2);
    }
}
