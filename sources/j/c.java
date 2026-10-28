package j;

import i.e;
import i.j;
import kotlin.jvm.internal.k;
import p.p;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends k.c {
    public int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ p f432c;
    public final /* synthetic */ Object d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c(e eVar, j jVar, p pVar, Object obj) {
        super(eVar, jVar);
        this.f432c = pVar;
        this.d = obj;
        k.e(eVar, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    }

    @Override // k.a
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i2 = this.b;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("This coroutine had already completed".toString());
            }
            this.b = 2;
            y.aj(obj);
            return obj;
        }
        this.b = 1;
        y.aj(obj);
        p pVar = this.f432c;
        k.e(pVar, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1>, kotlin.Any?>");
        k.b(pVar);
        return pVar.invoke(this.d, this);
    }
}
