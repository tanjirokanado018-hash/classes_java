package androidx.core.app;

import androidx.core.util.Consumer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface OnMultiWindowModeChangedProvider {
    void addOnMultiWindowModeChangedListener(Consumer<MultiWindowModeChangedInfo> consumer);

    void removeOnMultiWindowModeChangedListener(Consumer<MultiWindowModeChangedInfo> consumer);
}
