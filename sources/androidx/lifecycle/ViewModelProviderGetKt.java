package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewModelProviderGetKt {
    public static final CreationExtras defaultCreationExtras(ViewModelStoreOwner owner) {
        k.g(owner, "owner");
        return owner instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) owner).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
    }

    @MainThread
    public static final /* synthetic */ <VM extends ViewModel> VM get(ViewModelProvider viewModelProvider) {
        k.g(viewModelProvider, "<this>");
        k.o();
        throw null;
    }
}
