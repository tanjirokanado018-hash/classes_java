package androidx.lifecycle;

import androidx.lifecycle.viewmodel.CreationExtras;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface HasDefaultViewModelProviderFactory {
    CreationExtras getDefaultViewModelCreationExtras();

    ViewModelProvider.Factory getDefaultViewModelProviderFactory();
}
