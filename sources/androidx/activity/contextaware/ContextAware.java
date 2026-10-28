package androidx.activity.contextaware;

import android.content.Context;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface ContextAware {
    void addOnContextAvailableListener(OnContextAvailableListener onContextAvailableListener);

    Context peekAvailableContext();

    void removeOnContextAvailableListener(OnContextAvailableListener onContextAvailableListener);
}
