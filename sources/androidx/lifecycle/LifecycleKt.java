package androidx.lifecycle;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.l;
import w.F;
import w.af;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LifecycleKt {
    public static final LifecycleCoroutineScope getCoroutineScope(Lifecycle lifecycle) {
        k.g(lifecycle, "<this>");
        while (true) {
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl = (LifecycleCoroutineScopeImpl) lifecycle.getInternalScopeRef().get();
            if (lifecycleCoroutineScopeImpl != null) {
                return lifecycleCoroutineScopeImpl;
            }
            F f2 = new F(null);
            kotlinx.coroutines.scheduling.d dVar = af.f522a;
            x.c context = ((x.c) l.f459a).f562e;
            k.g(context, "context");
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl2 = new LifecycleCoroutineScopeImpl(lifecycle, y.ac(f2, context));
            AtomicReference<Object> internalScopeRef = lifecycle.getInternalScopeRef();
            do {
                if (internalScopeRef.compareAndSet(null, lifecycleCoroutineScopeImpl2)) {
                    lifecycleCoroutineScopeImpl2.register();
                    return lifecycleCoroutineScopeImpl2;
                }
            } while (internalScopeRef.get() == null);
        }
    }
}
