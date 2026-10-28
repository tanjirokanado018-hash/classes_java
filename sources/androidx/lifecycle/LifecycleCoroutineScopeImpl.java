package androidx.lifecycle;

import i.j;
import k.i;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.l;
import p.p;
import w.af;
import w.ar;
import w.as;
import w.ay;
import w.t;
import w.w;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LifecycleCoroutineScopeImpl extends LifecycleCoroutineScope implements LifecycleEventObserver {
    private final j coroutineContext;
    private final Lifecycle lifecycle;

    /* JADX INFO: renamed from: androidx.lifecycle.LifecycleCoroutineScopeImpl$register$1, reason: invalid class name */
    @k.e(c = "androidx.lifecycle.LifecycleCoroutineScopeImpl$register$1", f = "Lifecycle.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends i implements p {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(i.e eVar) {
            super(eVar);
        }

        @Override // k.a
        public final i.e create(Object obj, i.e eVar) {
            AnonymousClass1 anonymousClass1 = LifecycleCoroutineScopeImpl.this.new AnonymousClass1(eVar);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // k.a
        public final Object invokeSuspend(Object obj) throws Throwable {
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            y.aj(obj);
            w wVar = (w) this.L$0;
            if (LifecycleCoroutineScopeImpl.this.getLifecycle$lifecycle_common().getCurrentState().compareTo(Lifecycle.State.INITIALIZED) >= 0) {
                LifecycleCoroutineScopeImpl.this.getLifecycle$lifecycle_common().addObserver(LifecycleCoroutineScopeImpl.this);
            } else {
                ar arVar = (ar) wVar.getCoroutineContext().get(t.f551c);
                if (arVar != null) {
                    ay ayVar = (ay) arVar;
                    ayVar.e(new as(ayVar.g(), null, ayVar));
                }
            }
            return g.i.f423a;
        }

        @Override // p.p
        public final Object invoke(w wVar, i.e eVar) {
            return ((AnonymousClass1) create(wVar, eVar)).invokeSuspend(g.i.f423a);
        }
    }

    public LifecycleCoroutineScopeImpl(Lifecycle lifecycle, j coroutineContext) {
        ar arVar;
        k.g(lifecycle, "lifecycle");
        k.g(coroutineContext, "coroutineContext");
        this.lifecycle = lifecycle;
        this.coroutineContext = coroutineContext;
        if (getLifecycle$lifecycle_common().getCurrentState() != Lifecycle.State.DESTROYED || (arVar = (ar) getCoroutineContext().get(t.f551c)) == null) {
            return;
        }
        ay ayVar = (ay) arVar;
        ayVar.e(new as(ayVar.g(), null, ayVar));
    }

    @Override // androidx.lifecycle.LifecycleCoroutineScope, w.w
    public j getCoroutineContext() {
        return this.coroutineContext;
    }

    @Override // androidx.lifecycle.LifecycleCoroutineScope
    public Lifecycle getLifecycle$lifecycle_common() {
        return this.lifecycle;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        k.g(source, "source");
        k.g(event, "event");
        if (getLifecycle$lifecycle_common().getCurrentState().compareTo(Lifecycle.State.DESTROYED) <= 0) {
            getLifecycle$lifecycle_common().removeObserver(this);
            ar arVar = (ar) getCoroutineContext().get(t.f551c);
            if (arVar != null) {
                ay ayVar = (ay) arVar;
                ayVar.e(new as(ayVar.g(), null, ayVar));
            }
        }
    }

    public final void register() {
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        k.m(this, ((x.c) l.f459a).f562e, new AnonymousClass1(null), 2);
    }
}
