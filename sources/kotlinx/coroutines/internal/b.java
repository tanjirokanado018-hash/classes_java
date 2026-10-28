package kotlinx.coroutines.internal;

import java.util.concurrent.CancellationException;
import w.G;
import w.H;
import w.J;
import w.ak;
import w.ar;
import w.ay;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final q f440a = new q("NO_DECISION");
    public static final q b = new q("UNDEFINED");

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final q f441c = new q("REUSABLE_CLAIMED");
    public static final q d = new q("CONDITION_FALSE");

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final q f442e = new q("NO_THREAD_ELEMENTS");

    public static final void c(i.j jVar, Object obj) {
        if (obj == f442e) {
            return;
        }
        if (!(obj instanceof t)) {
            Object objFold = jVar.fold(null, s.d);
            if (objFold == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
            }
            androidx.appcompat.graphics.drawable.a.j(objFold);
            throw null;
        }
        t tVar = (t) obj;
        G[] gArr = tVar.b;
        int length = gArr.length - 1;
        if (length < 0) {
            return;
        }
        G g2 = gArr[length];
        kotlin.jvm.internal.k.d(null);
        Object obj2 = tVar.f465a[length];
        throw null;
    }

    public static final void d(i.e eVar, Object obj, p.l lVar) {
        Object mVar;
        if (!(eVar instanceof d)) {
            eVar.resumeWith(obj);
            return;
        }
        d dVar = (d) eVar;
        Throwable thA = g.f.a(obj);
        if (thA == null) {
            mVar = lVar != null ? new w.n(obj, lVar) : obj;
        } else {
            mVar = new w.m(false, thA);
        }
        i.e eVar2 = dVar.f446f;
        i.j context = eVar2.getContext();
        w.s sVar = dVar.f445e;
        if (sVar.isDispatchNeeded(context)) {
            dVar.f447g = mVar;
            dVar.d = 1;
            sVar.dispatch(eVar2.getContext(), dVar);
            return;
        }
        ak akVarA = H.a();
        if (akVarA.b >= 4294967296L) {
            dVar.f447g = mVar;
            dVar.d = 1;
            akVarA.c(dVar);
            return;
        }
        akVarA.e(true);
        try {
            ar arVar = (ar) eVar2.getContext().get(w.t.f551c);
            if (arVar == null || arVar.a()) {
                Object obj2 = dVar.f448h;
                i.j context2 = eVar2.getContext();
                Object objE = e(context2, obj2);
                J jX = objE != f442e ? kotlin.jvm.internal.k.x(eVar2, context2, objE) : null;
                try {
                    eVar2.resumeWith(obj);
                    if (jX == null || jX.ae()) {
                        c(context2, objE);
                    }
                } catch (Throwable th) {
                    if (jX == null || jX.ae()) {
                        c(context2, objE);
                    }
                    throw th;
                }
            } else {
                CancellationException cancellationExceptionL = ((ay) arVar).l();
                dVar.a(mVar, cancellationExceptionL);
                dVar.resumeWith(y.f(cancellationExceptionL));
            }
            while (akVarA.f()) {
            }
        } catch (Throwable th2) {
            try {
                dVar.e(th2, null);
            } finally {
                akVarA.b();
            }
        }
    }

    public static final Object e(i.j jVar, Object obj) {
        if (obj == null) {
            obj = jVar.fold(0, s.f463c);
            kotlin.jvm.internal.k.d(obj);
        }
        if (obj == 0) {
            return f442e;
        }
        if (obj instanceof Integer) {
            return jVar.fold(new t(jVar, ((Number) obj).intValue()), s.f464e);
        }
        androidx.appcompat.graphics.drawable.a.j(obj);
        throw null;
    }

    public abstract boolean a();

    public abstract void b();
}
