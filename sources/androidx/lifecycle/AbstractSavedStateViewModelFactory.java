package androidx.lifecycle;

import android.os.Bundle;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class AbstractSavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    public static final Companion Companion = new Companion(null);
    public static final String TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag";
    private Bundle defaultArgs;
    private Lifecycle lifecycle;
    private SavedStateRegistry savedStateRegistry;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(kotlin.jvm.internal.f fVar) {
            this();
        }
    }

    public AbstractSavedStateViewModelFactory() {
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass) {
        k.g(modelClass, "modelClass");
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        if (this.lifecycle != null) {
            return (T) create(canonicalName, modelClass);
        }
        throw new UnsupportedOperationException("AbstractSavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
    }

    public abstract <T extends ViewModel> T create(String str, Class<T> cls, SavedStateHandle savedStateHandle);

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onRequery(ViewModel viewModel) {
        k.g(viewModel, "viewModel");
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        if (savedStateRegistry != null) {
            k.d(savedStateRegistry);
            Lifecycle lifecycle = this.lifecycle;
            k.d(lifecycle);
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, lifecycle);
        }
    }

    public AbstractSavedStateViewModelFactory(SavedStateRegistryOwner owner, Bundle bundle) {
        k.g(owner, "owner");
        this.savedStateRegistry = owner.getSavedStateRegistry();
        this.lifecycle = owner.getLifecycle();
        this.defaultArgs = bundle;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        k.g(modelClass, "modelClass");
        k.g(extras, "extras");
        String str = (String) extras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str != null) {
            return this.savedStateRegistry != null ? (T) create(str, modelClass) : (T) create(str, modelClass, SavedStateHandleSupport.createSavedStateHandle(extras));
        }
        throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
    }

    private final <T extends ViewModel> T create(String str, Class<T> cls) {
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        k.d(savedStateRegistry);
        Lifecycle lifecycle = this.lifecycle;
        k.d(lifecycle);
        SavedStateHandleController savedStateHandleControllerCreate = LegacySavedStateHandleController.create(savedStateRegistry, lifecycle, str, this.defaultArgs);
        T t2 = (T) create(str, cls, savedStateHandleControllerCreate.getHandle());
        t2.setTagIfAbsent("androidx.lifecycle.savedstate.vm.tag", savedStateHandleControllerCreate);
        return t2;
    }
}
