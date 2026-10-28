package androidx.viewpager2.adapter;

import android.os.Parcelable;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface StatefulAdapter {
    void restoreState(@NonNull Parcelable parcelable);

    @NonNull
    Parcelable saveState();
}
