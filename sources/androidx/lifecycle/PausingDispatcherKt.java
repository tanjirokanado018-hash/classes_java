package androidx.lifecycle;

import k.i;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.l;
import p.p;
import w.af;
import w.ar;
import w.t;
import w.w;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PausingDispatcherKt {

    /* JADX INFO: renamed from: androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2, reason: invalid class name */
    @k.e(c = "androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2", f = "PausingDispatcher.kt", l = {203}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends i implements p {
        final /* synthetic */ p $block;
        final /* synthetic */ Lifecycle.State $minState;
        final /* synthetic */ Lifecycle $this_whenStateAtLeast;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Lifecycle lifecycle, Lifecycle.State state, p pVar, i.e eVar) {
            super(eVar);
            this.$this_whenStateAtLeast = lifecycle;
            this.$minState = state;
            this.$block = pVar;
        }

        @Override // k.a
        public final i.e create(Object obj, i.e eVar) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_whenStateAtLeast, this.$minState, this.$block, eVar);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // k.a
        public final Object invokeSuspend(Object obj) throws Throwable {
            LifecycleController lifecycleController;
            j.a aVar = j.a.b;
            int i2 = this.label;
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                lifecycleController = (LifecycleController) this.L$0;
                try {
                    y.aj(obj);
                    lifecycleController.finish();
                    return obj;
                } catch (Throwable th) {
                    th = th;
                    lifecycleController.finish();
                    throw th;
                }
            }
            y.aj(obj);
            ar arVar = (ar) ((w) this.L$0).getCoroutineContext().get(t.f551c);
            if (arVar == null) {
                throw new IllegalStateException("when[State] methods should have a parent job".toString());
            }
            PausingDispatcher pausingDispatcher = new PausingDispatcher();
            LifecycleController lifecycleController2 = new LifecycleController(this.$this_whenStateAtLeast, this.$minState, pausingDispatcher.dispatchQueue, arVar);
            try {
                p pVar = this.$block;
                this.L$0 = lifecycleController2;
                this.label = 1;
                obj = k.y(pausingDispatcher, pVar, this);
                if (obj == aVar) {
                    return aVar;
                }
                lifecycleController = lifecycleController2;
                lifecycleController.finish();
                return obj;
            } catch (Throwable th2) {
                th = th2;
                lifecycleController = lifecycleController2;
                lifecycleController.finish();
                throw th;
            }
        }

        @Override // p.p
        public final Object invoke(w wVar, i.e eVar) {
            return ((AnonymousClass2) create(wVar, eVar)).invokeSuspend(g.i.f423a);
        }
    }

    public static final <T> Object whenCreated(Lifecycle lifecycle, p pVar, i.e eVar) {
        return whenStateAtLeast(lifecycle, Lifecycle.State.CREATED, pVar, eVar);
    }

    public static final <T> Object whenResumed(Lifecycle lifecycle, p pVar, i.e eVar) {
        return whenStateAtLeast(lifecycle, Lifecycle.State.RESUMED, pVar, eVar);
    }

    public static final <T> Object whenStarted(Lifecycle lifecycle, p pVar, i.e eVar) {
        return whenStateAtLeast(lifecycle, Lifecycle.State.STARTED, pVar, eVar);
    }

    public static final <T> Object whenStateAtLeast(Lifecycle lifecycle, Lifecycle.State state, p pVar, i.e eVar) {
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        return k.y(((x.c) l.f459a).f562e, new AnonymousClass2(lifecycle, state, pVar, null), eVar);
    }

    public static final <T> Object whenCreated(LifecycleOwner lifecycleOwner, p pVar, i.e eVar) {
        return whenCreated(lifecycleOwner.getLifecycle(), pVar, eVar);
    }

    public static final <T> Object whenResumed(LifecycleOwner lifecycleOwner, p pVar, i.e eVar) {
        return whenResumed(lifecycleOwner.getLifecycle(), pVar, eVar);
    }

    public static final <T> Object whenStarted(LifecycleOwner lifecycleOwner, p pVar, i.e eVar) {
        return whenStarted(lifecycleOwner.getLifecycle(), pVar, eVar);
    }
}
