package androidx.activity;

import g.i;
import kotlin.jvm.internal.j;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public /* synthetic */ class OnBackPressedDispatcher$addCancellableCallback$1 extends j implements p.a {
    public OnBackPressedDispatcher$addCancellableCallback$1(Object obj) {
        super(0, obj, OnBackPressedDispatcher.class, "updateEnabledCallbacks", "updateEnabledCallbacks()V");
    }

    @Override // p.a
    public /* bridge */ /* synthetic */ Object invoke() {
        m4invoke();
        return i.f423a;
    }

    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m4invoke() {
        ((OnBackPressedDispatcher) this.receiver).updateEnabledCallbacks();
    }
}
