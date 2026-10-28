package androidx.fragment.app;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface FragmentOnAttachListener {
    @MainThread
    void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment);
}
