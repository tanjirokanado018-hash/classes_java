package androidx.lifecycle;

import android.app.Application;
import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactory;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ViewModelProvider {
    private final CreationExtras defaultCreationExtras;
    private final Factory factory;
    private final ViewModelStore store;

    public static class AndroidViewModelFactory extends NewInstanceFactory {
        public static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";
        private static AndroidViewModelFactory sInstance;
        private final Application application;
        public static final Companion Companion = new Companion(null);
        public static final CreationExtras.Key<Application> APPLICATION_KEY = Companion.ApplicationKeyImpl.INSTANCE;

        public static final class Companion {

            public static final class ApplicationKeyImpl implements CreationExtras.Key<Application> {
                public static final ApplicationKeyImpl INSTANCE = new ApplicationKeyImpl();

                private ApplicationKeyImpl() {
                }
            }

            private Companion() {
            }

            public /* synthetic */ Companion(kotlin.jvm.internal.f fVar) {
                this();
            }

            public final Factory defaultFactory$lifecycle_viewmodel_release(ViewModelStoreOwner owner) {
                k.g(owner, "owner");
                return owner instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) owner).getDefaultViewModelProviderFactory() : NewInstanceFactory.Companion.getInstance();
            }

            public final AndroidViewModelFactory getInstance(Application application) {
                k.g(application, "application");
                if (AndroidViewModelFactory.sInstance == null) {
                    AndroidViewModelFactory.sInstance = new AndroidViewModelFactory(application);
                }
                AndroidViewModelFactory androidViewModelFactory = AndroidViewModelFactory.sInstance;
                k.d(androidViewModelFactory);
                return androidViewModelFactory;
            }
        }

        public AndroidViewModelFactory() {
            this(null, 0);
        }

        public static final AndroidViewModelFactory getInstance(Application application) {
            return Companion.getInstance(application);
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass) {
            k.g(modelClass, "modelClass");
            Application application = this.application;
            if (application != null) {
                return (T) create(modelClass, application);
            }
            throw new UnsupportedOperationException("AndroidViewModelFactory constructed with empty constructor works only with create(modelClass: Class<T>, extras: CreationExtras).");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public AndroidViewModelFactory(Application application) {
            this(application, 0);
            k.g(application, "application");
        }

        private final <T extends ViewModel> T create(Class<T> cls, Application application) {
            if (!AndroidViewModel.class.isAssignableFrom(cls)) {
                return (T) super.create(cls);
            }
            try {
                T tNewInstance = cls.getConstructor(Application.class).newInstance(application);
                k.f(tNewInstance, "{\n                try {\n…          }\n            }");
                return tNewInstance;
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Cannot create an instance of " + cls, e2);
            } catch (InstantiationException e3) {
                throw new RuntimeException("Cannot create an instance of " + cls, e3);
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException("Cannot create an instance of " + cls, e4);
            } catch (InvocationTargetException e5) {
                throw new RuntimeException("Cannot create an instance of " + cls, e5);
            }
        }

        private AndroidViewModelFactory(Application application, int i2) {
            this.application = application;
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
            k.g(modelClass, "modelClass");
            k.g(extras, "extras");
            if (this.application != null) {
                return (T) create(modelClass);
            }
            Application application = (Application) extras.get(APPLICATION_KEY);
            if (application != null) {
                return (T) create(modelClass, application);
            }
            if (AndroidViewModel.class.isAssignableFrom(modelClass)) {
                throw new IllegalArgumentException("CreationExtras must have an application by `APPLICATION_KEY`");
            }
            return (T) super.create(modelClass);
        }
    }

    public interface Factory {
        public static final Companion Companion = Companion.$$INSTANCE;

        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public final Factory from(ViewModelInitializer<?>... initializers) {
                k.g(initializers, "initializers");
                return new InitializerViewModelFactory((ViewModelInitializer[]) Arrays.copyOf(initializers, initializers.length));
            }
        }

        <T extends ViewModel> T create(Class<T> cls);

        <T extends ViewModel> T create(Class<T> cls, CreationExtras creationExtras);
    }

    public static class NewInstanceFactory implements Factory {
        public static final Companion Companion = new Companion(null);
        public static final CreationExtras.Key<String> VIEW_MODEL_KEY = Companion.ViewModelKeyImpl.INSTANCE;
        private static NewInstanceFactory sInstance;

        public static final class Companion {

            public static final class ViewModelKeyImpl implements CreationExtras.Key<String> {
                public static final ViewModelKeyImpl INSTANCE = new ViewModelKeyImpl();

                private ViewModelKeyImpl() {
                }
            }

            private Companion() {
            }

            public /* synthetic */ Companion(kotlin.jvm.internal.f fVar) {
                this();
            }

            public static /* synthetic */ void getInstance$annotations() {
            }

            @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
            public final NewInstanceFactory getInstance() {
                if (NewInstanceFactory.sInstance == null) {
                    NewInstanceFactory.sInstance = new NewInstanceFactory();
                }
                NewInstanceFactory newInstanceFactory = NewInstanceFactory.sInstance;
                k.d(newInstanceFactory);
                return newInstanceFactory;
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final NewInstanceFactory getInstance() {
            return Companion.getInstance();
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> modelClass) throws InvocationTargetException {
            k.g(modelClass, "modelClass");
            try {
                T tNewInstance = modelClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                k.f(tNewInstance, "{\n                modelC…wInstance()\n            }");
                return tNewInstance;
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e2);
            } catch (InstantiationException e3) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e3);
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e4);
            }
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public /* synthetic */ ViewModel create(Class cls, CreationExtras creationExtras) {
            return h.b(this, cls, creationExtras);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class OnRequeryFactory {
        public void onRequery(ViewModel viewModel) {
            k.g(viewModel, "viewModel");
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStore store, Factory factory) {
        this(store, factory, null, 4, null);
        k.g(store, "store");
        k.g(factory, "factory");
    }

    @MainThread
    public <T extends ViewModel> T get(Class<T> modelClass) {
        k.g(modelClass, "modelClass");
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName != null) {
            return (T) get("androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName), modelClass);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public ViewModelProvider(ViewModelStore store, Factory factory, CreationExtras defaultCreationExtras) {
        k.g(store, "store");
        k.g(factory, "factory");
        k.g(defaultCreationExtras, "defaultCreationExtras");
        this.store = store;
        this.factory = factory;
        this.defaultCreationExtras = defaultCreationExtras;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @MainThread
    public <T extends ViewModel> T get(String key, Class<T> modelClass) {
        T t2;
        k.g(key, "key");
        k.g(modelClass, "modelClass");
        T t3 = (T) this.store.get(key);
        if (!modelClass.isInstance(t3)) {
            MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(this.defaultCreationExtras);
            mutableCreationExtras.set(NewInstanceFactory.VIEW_MODEL_KEY, key);
            try {
                t2 = (T) this.factory.create(modelClass, mutableCreationExtras);
            } catch (AbstractMethodError unused) {
                t2 = (T) this.factory.create(modelClass);
            }
            this.store.put(key, t2);
            return t2;
        }
        Object obj = this.factory;
        OnRequeryFactory onRequeryFactory = obj instanceof OnRequeryFactory ? (OnRequeryFactory) obj : null;
        if (onRequeryFactory != null) {
            k.d(t3);
            onRequeryFactory.onRequery(t3);
        }
        k.e(t3, "null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
        return t3;
    }

    public /* synthetic */ ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras, int i2, kotlin.jvm.internal.f fVar) {
        this(viewModelStore, factory, (i2 & 4) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStoreOwner owner) {
        this(owner.getViewModelStore(), AndroidViewModelFactory.Companion.defaultFactory$lifecycle_viewmodel_release(owner), ViewModelProviderGetKt.defaultCreationExtras(owner));
        k.g(owner, "owner");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelProvider(ViewModelStoreOwner owner, Factory factory) {
        this(owner.getViewModelStore(), factory, ViewModelProviderGetKt.defaultCreationExtras(owner));
        k.g(owner, "owner");
        k.g(factory, "factory");
    }
}
