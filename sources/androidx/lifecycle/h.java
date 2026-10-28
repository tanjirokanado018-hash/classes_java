package androidx.lifecycle;

import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class h {
    static {
        ViewModelProvider.Factory.Companion companion = ViewModelProvider.Factory.Companion;
    }

    public static ViewModel a(ViewModelProvider.Factory factory, Class modelClass) {
        k.g(modelClass, "modelClass");
        throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
    }

    public static ViewModel b(ViewModelProvider.Factory factory, Class modelClass, CreationExtras extras) {
        k.g(modelClass, "modelClass");
        k.g(extras, "extras");
        return factory.create(modelClass);
    }

    public static ViewModelProvider.Factory c(ViewModelInitializer... viewModelInitializerArr) {
        return ViewModelProvider.Factory.Companion.from(viewModelInitializerArr);
    }
}
