package androidx.activity;

import androidx.lifecycle.LifecycleOwner;
import kotlin.jvm.internal.k;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class OnBackPressedDispatcherKt {
    public static final OnBackPressedCallback addCallback(OnBackPressedDispatcher onBackPressedDispatcher, LifecycleOwner lifecycleOwner, final boolean z2, final l onBackPressed) {
        k.g(onBackPressedDispatcher, "<this>");
        k.g(onBackPressed, "onBackPressed");
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(z2) { // from class: androidx.activity.OnBackPressedDispatcherKt$addCallback$callback$1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                onBackPressed.invoke(this);
            }
        };
        if (lifecycleOwner != null) {
            onBackPressedDispatcher.addCallback(lifecycleOwner, onBackPressedCallback);
        } else {
            onBackPressedDispatcher.addCallback(onBackPressedCallback);
        }
        return onBackPressedCallback;
    }

    public static /* synthetic */ OnBackPressedCallback addCallback$default(OnBackPressedDispatcher onBackPressedDispatcher, LifecycleOwner lifecycleOwner, boolean z2, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            lifecycleOwner = null;
        }
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        return addCallback(onBackPressedDispatcher, lifecycleOwner, z2, lVar);
    }
}
