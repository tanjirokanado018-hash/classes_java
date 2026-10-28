package androidx.savedstate;

import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.lifecycle.Lifecycle;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SavedStateRegistryController {
    public static final Companion Companion = new Companion(null);
    private boolean attached;
    private final SavedStateRegistryOwner owner;
    private final SavedStateRegistry savedStateRegistry;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(f fVar) {
            this();
        }

        public final SavedStateRegistryController create(SavedStateRegistryOwner owner) {
            k.g(owner, "owner");
            return new SavedStateRegistryController(owner, null);
        }
    }

    private SavedStateRegistryController(SavedStateRegistryOwner savedStateRegistryOwner) {
        this.owner = savedStateRegistryOwner;
        this.savedStateRegistry = new SavedStateRegistry();
    }

    public static final SavedStateRegistryController create(SavedStateRegistryOwner savedStateRegistryOwner) {
        return Companion.create(savedStateRegistryOwner);
    }

    public final SavedStateRegistry getSavedStateRegistry() {
        return this.savedStateRegistry;
    }

    @MainThread
    public final void performAttach() {
        Lifecycle lifecycle = this.owner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.INITIALIZED) {
            throw new IllegalStateException("Restarter must be created only during owner's initialization stage".toString());
        }
        lifecycle.addObserver(new Recreator(this.owner));
        this.savedStateRegistry.performAttach$savedstate_release(lifecycle);
        this.attached = true;
    }

    @MainThread
    public final void performRestore(Bundle bundle) {
        if (!this.attached) {
            performAttach();
        }
        Lifecycle lifecycle = this.owner.getLifecycle();
        if (!lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            this.savedStateRegistry.performRestore$savedstate_release(bundle);
        } else {
            throw new IllegalStateException(("performRestore cannot be called when owner is " + lifecycle.getCurrentState()).toString());
        }
    }

    @MainThread
    public final void performSave(Bundle outBundle) {
        k.g(outBundle, "outBundle");
        this.savedStateRegistry.performSave(outBundle);
    }

    public /* synthetic */ SavedStateRegistryController(SavedStateRegistryOwner savedStateRegistryOwner, f fVar) {
        this(savedStateRegistryOwner);
    }
}
