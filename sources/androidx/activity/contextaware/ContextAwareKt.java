package androidx.activity.contextaware;

import android.content.Context;
import i.e;
import p.l;
import w.C0036f;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ContextAwareKt {
    public static final <R> Object withContextAvailable(ContextAware contextAware, l lVar, e eVar) {
        Context contextPeekAvailableContext = contextAware.peekAvailableContext();
        if (contextPeekAvailableContext != null) {
            return lVar.invoke(contextPeekAvailableContext);
        }
        C0036f c0036f = new C0036f(y.u(eVar));
        c0036f.l();
        ContextAwareKt$withContextAvailable$2$listener$1 contextAwareKt$withContextAvailable$2$listener$1 = new ContextAwareKt$withContextAvailable$2$listener$1(c0036f, lVar);
        contextAware.addOnContextAvailableListener(contextAwareKt$withContextAvailable$2$listener$1);
        c0036f.n(new ContextAwareKt$withContextAvailable$2$1(contextAware, contextAwareKt$withContextAvailable$2$listener$1));
        return c0036f.k();
    }

    private static final <R> Object withContextAvailable$$forInline(ContextAware contextAware, l lVar, e eVar) {
        Context contextPeekAvailableContext = contextAware.peekAvailableContext();
        if (contextPeekAvailableContext != null) {
            return lVar.invoke(contextPeekAvailableContext);
        }
        C0036f c0036f = new C0036f(y.u(eVar));
        c0036f.l();
        ContextAwareKt$withContextAvailable$2$listener$1 contextAwareKt$withContextAvailable$2$listener$1 = new ContextAwareKt$withContextAvailable$2$listener$1(c0036f, lVar);
        contextAware.addOnContextAvailableListener(contextAwareKt$withContextAvailable$2$listener$1);
        c0036f.n(new ContextAwareKt$withContextAvailable$2$1(contextAware, contextAwareKt$withContextAvailable$2$listener$1));
        return c0036f.k();
    }
}
