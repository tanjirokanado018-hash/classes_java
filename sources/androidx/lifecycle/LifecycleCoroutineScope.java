package androidx.lifecycle;

import i.j;
import k.i;
import kotlin.jvm.internal.k;
import p.p;
import w.ar;
import w.w;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class LifecycleCoroutineScope implements w {

    /* JADX INFO: renamed from: androidx.lifecycle.LifecycleCoroutineScope$launchWhenCreated$1, reason: invalid class name */
    @k.e(c = "androidx.lifecycle.LifecycleCoroutineScope$launchWhenCreated$1", f = "Lifecycle.kt", l = {337}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends i implements p {
        final /* synthetic */ p $block;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(p pVar, i.e eVar) {
            super(eVar);
            this.$block = pVar;
        }

        @Override // k.a
        public final i.e create(Object obj, i.e eVar) {
            return LifecycleCoroutineScope.this.new AnonymousClass1(this.$block, eVar);
        }

        @Override // k.a
        public final Object invokeSuspend(Object obj) throws Throwable {
            j.a aVar = j.a.b;
            int i2 = this.label;
            if (i2 == 0) {
                y.aj(obj);
                Lifecycle lifecycle$lifecycle_common = LifecycleCoroutineScope.this.getLifecycle$lifecycle_common();
                p pVar = this.$block;
                this.label = 1;
                if (PausingDispatcherKt.whenCreated(lifecycle$lifecycle_common, pVar, this) == aVar) {
                    return aVar;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                y.aj(obj);
            }
            return g.i.f423a;
        }

        @Override // p.p
        public final Object invoke(w wVar, i.e eVar) {
            return ((AnonymousClass1) create(wVar, eVar)).invokeSuspend(g.i.f423a);
        }
    }

    /* JADX INFO: renamed from: androidx.lifecycle.LifecycleCoroutineScope$launchWhenResumed$1, reason: invalid class name and case insensitive filesystem */
    @k.e(c = "androidx.lifecycle.LifecycleCoroutineScope$launchWhenResumed$1", f = "Lifecycle.kt", l = {375}, m = "invokeSuspend")
    public static final class C00161 extends i implements p {
        final /* synthetic */ p $block;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00161(p pVar, i.e eVar) {
            super(eVar);
            this.$block = pVar;
        }

        @Override // k.a
        public final i.e create(Object obj, i.e eVar) {
            return LifecycleCoroutineScope.this.new C00161(this.$block, eVar);
        }

        @Override // k.a
        public final Object invokeSuspend(Object obj) throws Throwable {
            j.a aVar = j.a.b;
            int i2 = this.label;
            if (i2 == 0) {
                y.aj(obj);
                Lifecycle lifecycle$lifecycle_common = LifecycleCoroutineScope.this.getLifecycle$lifecycle_common();
                p pVar = this.$block;
                this.label = 1;
                if (PausingDispatcherKt.whenResumed(lifecycle$lifecycle_common, pVar, this) == aVar) {
                    return aVar;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                y.aj(obj);
            }
            return g.i.f423a;
        }

        @Override // p.p
        public final Object invoke(w wVar, i.e eVar) {
            return ((C00161) create(wVar, eVar)).invokeSuspend(g.i.f423a);
        }
    }

    /* JADX INFO: renamed from: androidx.lifecycle.LifecycleCoroutineScope$launchWhenStarted$1, reason: invalid class name and case insensitive filesystem */
    @k.e(c = "androidx.lifecycle.LifecycleCoroutineScope$launchWhenStarted$1", f = "Lifecycle.kt", l = {356}, m = "invokeSuspend")
    public static final class C00171 extends i implements p {
        final /* synthetic */ p $block;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00171(p pVar, i.e eVar) {
            super(eVar);
            this.$block = pVar;
        }

        @Override // k.a
        public final i.e create(Object obj, i.e eVar) {
            return LifecycleCoroutineScope.this.new C00171(this.$block, eVar);
        }

        @Override // k.a
        public final Object invokeSuspend(Object obj) throws Throwable {
            j.a aVar = j.a.b;
            int i2 = this.label;
            if (i2 == 0) {
                y.aj(obj);
                Lifecycle lifecycle$lifecycle_common = LifecycleCoroutineScope.this.getLifecycle$lifecycle_common();
                p pVar = this.$block;
                this.label = 1;
                if (PausingDispatcherKt.whenStarted(lifecycle$lifecycle_common, pVar, this) == aVar) {
                    return aVar;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                y.aj(obj);
            }
            return g.i.f423a;
        }

        @Override // p.p
        public final Object invoke(w wVar, i.e eVar) {
            return ((C00171) create(wVar, eVar)).invokeSuspend(g.i.f423a);
        }
    }

    @Override // w.w
    public abstract /* synthetic */ j getCoroutineContext();

    public abstract Lifecycle getLifecycle$lifecycle_common();

    public final ar launchWhenCreated(p block) {
        k.g(block, "block");
        return k.m(this, null, new AnonymousClass1(block, null), 3);
    }

    public final ar launchWhenResumed(p block) {
        k.g(block, "block");
        return k.m(this, null, new C00161(block, null), 3);
    }

    public final ar launchWhenStarted(p block) {
        k.g(block, "block");
        return k.m(this, null, new C00171(block, null), 3);
    }
}
