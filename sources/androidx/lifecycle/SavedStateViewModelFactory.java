package androidx.lifecycle;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    private Application application;
    private Bundle defaultArgs;
    private final ViewModelProvider.Factory factory;
    private Lifecycle lifecycle;
    private SavedStateRegistry savedStateRegistry;

    public SavedStateViewModelFactory() {
        this.factory = new ViewModelProvider.AndroidViewModelFactory();
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass) {
        k.g(modelClass, "modelClass");
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName != null) {
            return (T) create(canonicalName, modelClass);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onRequery(ViewModel viewModel) {
        k.g(viewModel, "viewModel");
        if (this.lifecycle != null) {
            SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
            k.d(savedStateRegistry);
            Lifecycle lifecycle = this.lifecycle;
            k.d(lifecycle);
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, lifecycle);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner) {
        this(application, owner, null);
        k.g(owner, "owner");
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
        k.g(modelClass, "modelClass");
        k.g(extras, "extras");
        String str = (String) extras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (str == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        }
        if (extras.get(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY) == null || extras.get(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY) == null) {
            if (this.lifecycle != null) {
                return (T) create(str, modelClass);
            }
            throw new IllegalStateException("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
        }
        Application application = (Application) extras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
        boolean zIsAssignableFrom = AndroidViewModel.class.isAssignableFrom(modelClass);
        Constructor constructorFindMatchingConstructor = SavedStateViewModelFactoryKt.findMatchingConstructor(modelClass, (!zIsAssignableFrom || application == null) ? SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE : SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
        if (constructorFindMatchingConstructor == null) {
            return (T) this.factory.create(modelClass, extras);
        }
        return (!zIsAssignableFrom || application == null) ? (T) SavedStateViewModelFactoryKt.newInstance(modelClass, constructorFindMatchingConstructor, SavedStateHandleSupport.createSavedStateHandle(extras)) : (T) SavedStateViewModelFactoryKt.newInstance(modelClass, constructorFindMatchingConstructor, application, SavedStateHandleSupport.createSavedStateHandle(extras));
    }

    @SuppressLint({"LambdaLast"})
    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner, Bundle bundle) {
        k.g(owner, "owner");
        this.savedStateRegistry = owner.getSavedStateRegistry();
        this.lifecycle = owner.getLifecycle();
        this.defaultArgs = bundle;
        this.application = application;
        this.factory = application != null ? ViewModelProvider.AndroidViewModelFactory.Companion.getInstance(application) : new ViewModelProvider.AndroidViewModelFactory();
    }

    public final <T extends ViewModel> T create(String key, Class<T> modelClass) {
        T t2;
        Application application;
        k.g(key, "key");
        k.g(modelClass, "modelClass");
        Lifecycle lifecycle = this.lifecycle;
        if (lifecycle == null) {
            throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
        boolean zIsAssignableFrom = AndroidViewModel.class.isAssignableFrom(modelClass);
        Constructor constructorFindMatchingConstructor = SavedStateViewModelFactoryKt.findMatchingConstructor(modelClass, (!zIsAssignableFrom || this.application == null) ? SavedStateViewModelFactoryKt.VIEWMODEL_SIGNATURE : SavedStateViewModelFactoryKt.ANDROID_VIEWMODEL_SIGNATURE);
        if (constructorFindMatchingConstructor == null) {
            return this.application != null ? (T) this.factory.create(modelClass) : (T) ViewModelProvider.NewInstanceFactory.Companion.getInstance().create(modelClass);
        }
        SavedStateRegistry savedStateRegistry = this.savedStateRegistry;
        k.d(savedStateRegistry);
        SavedStateHandleController savedStateHandleControllerCreate = LegacySavedStateHandleController.create(savedStateRegistry, lifecycle, key, this.defaultArgs);
        if (!zIsAssignableFrom || (application = this.application) == null) {
            t2 = (T) SavedStateViewModelFactoryKt.newInstance(modelClass, constructorFindMatchingConstructor, savedStateHandleControllerCreate.getHandle());
        } else {
            k.d(application);
            t2 = (T) SavedStateViewModelFactoryKt.newInstance(modelClass, constructorFindMatchingConstructor, application, savedStateHandleControllerCreate.getHandle());
        }
        t2.setTagIfAbsent("androidx.lifecycle.savedstate.vm.tag", savedStateHandleControllerCreate);
        return t2;
    }
}
