package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.jvm.internal.k;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class InitializerViewModelFactoryKt {
    public static final /* synthetic */ <VM extends ViewModel> void initializer(InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder, l initializer) {
        k.g(initializerViewModelFactoryBuilder, "<this>");
        k.g(initializer, "initializer");
        k.o();
        throw null;
    }

    public static final ViewModelProvider.Factory viewModelFactory(l builder) {
        k.g(builder, "builder");
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder = new InitializerViewModelFactoryBuilder();
        builder.invoke(initializerViewModelFactoryBuilder);
        return initializerViewModelFactoryBuilder.build();
    }
}
