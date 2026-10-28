package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.d;
import kotlin.jvm.internal.k;
import p.l;
import t.c;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@ViewModelFactoryDsl
public final class InitializerViewModelFactoryBuilder {
    private final List<ViewModelInitializer<?>> initializers = new ArrayList();

    public final <T extends ViewModel> void addInitializer(c clazz, l initializer) {
        k.g(clazz, "clazz");
        k.g(initializer, "initializer");
        List<ViewModelInitializer<?>> list = this.initializers;
        Class clsA = ((d) clazz).a();
        k.e(clsA, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        list.add(new ViewModelInitializer<>(clsA, initializer));
    }

    public final ViewModelProvider.Factory build() {
        ViewModelInitializer[] viewModelInitializerArr = (ViewModelInitializer[]) this.initializers.toArray(new ViewModelInitializer[0]);
        return new InitializerViewModelFactory((ViewModelInitializer[]) Arrays.copyOf(viewModelInitializerArr, viewModelInitializerArr.length));
    }
}
