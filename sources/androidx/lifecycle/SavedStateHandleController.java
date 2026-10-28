package androidx.lifecycle;

import androidx.savedstate.SavedStateRegistry;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SavedStateHandleController implements LifecycleEventObserver {
    private final SavedStateHandle handle;
    private boolean isAttached;
    private final String key;

    public SavedStateHandleController(String key, SavedStateHandle handle) {
        k.g(key, "key");
        k.g(handle, "handle");
        this.key = key;
        this.handle = handle;
    }

    public final void attachToLifecycle(SavedStateRegistry registry, Lifecycle lifecycle) {
        k.g(registry, "registry");
        k.g(lifecycle, "lifecycle");
        if (!(!this.isAttached)) {
            throw new IllegalStateException("Already attached to lifecycleOwner".toString());
        }
        this.isAttached = true;
        lifecycle.addObserver(this);
        registry.registerSavedStateProvider(this.key, this.handle.savedStateProvider());
    }

    public final SavedStateHandle getHandle() {
        return this.handle;
    }

    public final boolean isAttached() {
        return this.isAttached;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        k.g(source, "source");
        k.g(event, "event");
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.isAttached = false;
            source.getLifecycle().removeObserver(this);
        }
    }
}
