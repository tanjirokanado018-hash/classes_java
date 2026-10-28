package androidx.activity.contextaware;

import android.content.Context;
import kotlin.jvm.internal.k;
import p.l;
import w.C0036f;
import w.InterfaceC0035e;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ContextAwareKt$withContextAvailable$2$listener$1 implements OnContextAvailableListener {
    final /* synthetic */ InterfaceC0035e $co;
    final /* synthetic */ l $onContextAvailable;

    public ContextAwareKt$withContextAvailable$2$listener$1(InterfaceC0035e interfaceC0035e, l lVar) {
        this.$co = interfaceC0035e;
        this.$onContextAvailable = lVar;
    }

    @Override // androidx.activity.contextaware.OnContextAvailableListener
    public void onContextAvailable(Context context) {
        Object objF;
        k.g(context, "context");
        InterfaceC0035e interfaceC0035e = this.$co;
        try {
            objF = this.$onContextAvailable.invoke(context);
        } catch (Throwable th) {
            objF = y.f(th);
        }
        ((C0036f) interfaceC0035e).resumeWith(objF);
    }
}
