package androidx.lifecycle;

import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SavedStateHandlesProvider$viewModel$2 extends l implements p.a {
    final /* synthetic */ ViewModelStoreOwner $viewModelStoreOwner;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SavedStateHandlesProvider$viewModel$2(ViewModelStoreOwner viewModelStoreOwner) {
        super(0);
        this.$viewModelStoreOwner = viewModelStoreOwner;
    }

    @Override // p.a
    public final SavedStateHandlesVM invoke() {
        return SavedStateHandleSupport.getSavedStateHandlesVM(this.$viewModelStoreOwner);
    }
}
