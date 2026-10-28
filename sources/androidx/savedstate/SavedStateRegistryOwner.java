package androidx.savedstate;

import androidx.lifecycle.LifecycleOwner;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface SavedStateRegistryOwner extends LifecycleOwner {
    SavedStateRegistry getSavedStateRegistry();
}
