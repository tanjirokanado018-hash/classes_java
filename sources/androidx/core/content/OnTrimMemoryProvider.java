package androidx.core.content;

import androidx.core.util.Consumer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface OnTrimMemoryProvider {
    void addOnTrimMemoryListener(Consumer<Integer> consumer);

    void removeOnTrimMemoryListener(Consumer<Integer> consumer);
}
