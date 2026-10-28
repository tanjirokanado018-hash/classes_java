package androidx.savedstate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.Map;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@SuppressLint({"RestrictedApi"})
public final class SavedStateRegistry {
    private static final Companion Companion = new Companion(null);

    @Deprecated
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    private boolean attached;
    private final SafeIterableMap<String, SavedStateProvider> components = new SafeIterableMap<>();
    private boolean isAllowingSavingState = true;
    private boolean isRestored;
    private Recreator.SavedStateProvider recreatorProvider;
    private Bundle restoredState;

    public interface AutoRecreated {
        void onRecreated(SavedStateRegistryOwner savedStateRegistryOwner);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(f fVar) {
            this();
        }
    }

    public interface SavedStateProvider {
        Bundle saveState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void performAttach$lambda$4(SavedStateRegistry this$0, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        boolean z2;
        k.g(this$0, "this$0");
        k.g(lifecycleOwner, "<anonymous parameter 0>");
        k.g(event, "event");
        if (event == Lifecycle.Event.ON_START) {
            z2 = true;
        } else if (event != Lifecycle.Event.ON_STOP) {
            return;
        } else {
            z2 = false;
        }
        this$0.isAllowingSavingState = z2;
    }

    @MainThread
    public final Bundle consumeRestoredStateForKey(String key) {
        k.g(key, "key");
        if (!this.isRestored) {
            throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component".toString());
        }
        Bundle bundle = this.restoredState;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = bundle != null ? bundle.getBundle(key) : null;
        Bundle bundle3 = this.restoredState;
        if (bundle3 != null) {
            bundle3.remove(key);
        }
        Bundle bundle4 = this.restoredState;
        if (bundle4 == null || bundle4.isEmpty()) {
            this.restoredState = null;
        }
        return bundle2;
    }

    public final SavedStateProvider getSavedStateProvider(String key) {
        k.g(key, "key");
        for (Map.Entry<String, SavedStateProvider> components : this.components) {
            k.f(components, "components");
            String key2 = components.getKey();
            SavedStateProvider value = components.getValue();
            if (k.a(key2, key)) {
                return value;
            }
        }
        return null;
    }

    public final boolean isAllowingSavingState$savedstate_release() {
        return this.isAllowingSavingState;
    }

    @MainThread
    public final boolean isRestored() {
        return this.isRestored;
    }

    @MainThread
    public final void performAttach$savedstate_release(Lifecycle lifecycle) {
        k.g(lifecycle, "lifecycle");
        if (!(!this.attached)) {
            throw new IllegalStateException("SavedStateRegistry was already attached.".toString());
        }
        lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.savedstate.a
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                SavedStateRegistry.performAttach$lambda$4(this.b, lifecycleOwner, event);
            }
        });
        this.attached = true;
    }

    @MainThread
    public final void performRestore$savedstate_release(Bundle bundle) {
        if (!this.attached) {
            throw new IllegalStateException("You must call performAttach() before calling performRestore(Bundle).".toString());
        }
        if (!(!this.isRestored)) {
            throw new IllegalStateException("SavedStateRegistry was already restored.".toString());
        }
        this.restoredState = bundle != null ? bundle.getBundle(SAVED_COMPONENTS_KEY) : null;
        this.isRestored = true;
    }

    @MainThread
    public final void performSave(Bundle outBundle) {
        k.g(outBundle, "outBundle");
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        SafeIterableMap<String, SavedStateProvider>.IteratorWithAdditions iteratorWithAdditions = this.components.iteratorWithAdditions();
        k.f(iteratorWithAdditions, "this.components.iteratorWithAdditions()");
        while (iteratorWithAdditions.hasNext()) {
            Map.Entry next = iteratorWithAdditions.next();
            bundle.putBundle((String) next.getKey(), ((SavedStateProvider) next.getValue()).saveState());
        }
        if (bundle.isEmpty()) {
            return;
        }
        outBundle.putBundle(SAVED_COMPONENTS_KEY, bundle);
    }

    @MainThread
    public final void registerSavedStateProvider(String key, SavedStateProvider provider) {
        k.g(key, "key");
        k.g(provider, "provider");
        if (this.components.putIfAbsent(key, provider) != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered".toString());
        }
    }

    @MainThread
    public final void runOnNextRecreation(Class<? extends AutoRecreated> clazz) {
        k.g(clazz, "clazz");
        if (!this.isAllowingSavingState) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState".toString());
        }
        Recreator.SavedStateProvider savedStateProvider = this.recreatorProvider;
        if (savedStateProvider == null) {
            savedStateProvider = new Recreator.SavedStateProvider(this);
        }
        this.recreatorProvider = savedStateProvider;
        try {
            clazz.getDeclaredConstructor(new Class[0]);
            Recreator.SavedStateProvider savedStateProvider2 = this.recreatorProvider;
            if (savedStateProvider2 != null) {
                savedStateProvider2.add(clazz.getName());
            }
        } catch (NoSuchMethodException e2) {
            throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
        }
    }

    public final void setAllowingSavingState$savedstate_release(boolean z2) {
        this.isAllowingSavingState = z2;
    }

    @MainThread
    public final void unregisterSavedStateProvider(String key) {
        k.g(key, "key");
        this.components.remove(key);
    }
}
