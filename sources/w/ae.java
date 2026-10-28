package w;

import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class ae extends kotlinx.coroutines.scheduling.i {
    public int d;

    public ae(int i2) {
        super(0L, kotlinx.coroutines.scheduling.l.f489f);
        this.d = i2;
    }

    public abstract void a(Object obj, CancellationException cancellationException);

    public abstract i.e b();

    public Throwable c(Object obj) {
        m mVar = obj instanceof m ? (m) obj : null;
        if (mVar != null) {
            return mVar.f547a;
        }
        return null;
    }

    public final void e(Throwable th, Throwable th2) {
        if (th == null && th2 == null) {
            return;
        }
        if (th != null && th2 != null) {
            y.a(th, th2);
        }
        if (th == null) {
            th = th2;
        }
        kotlin.jvm.internal.k.d(th);
        kotlin.jvm.internal.k.j(b().getContext(), new o.a("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th));
    }

    public abstract Object f();

    @Override // java.lang.Runnable
    public final void run() {
        Object objF;
        Object objF2 = g.i.f423a;
        kotlinx.coroutines.scheduling.j jVar = this.f484c;
        try {
            kotlinx.coroutines.internal.d dVar = (kotlinx.coroutines.internal.d) b();
            i.e eVar = dVar.f446f;
            Object obj = dVar.f448h;
            i.j context = eVar.getContext();
            Object objE = kotlinx.coroutines.internal.b.e(context, obj);
            J jX = objE != kotlinx.coroutines.internal.b.f442e ? kotlin.jvm.internal.k.x(eVar, context, objE) : null;
            try {
                i.j context2 = eVar.getContext();
                Object objF3 = f();
                Throwable thC = c(objF3);
                ar arVar = (thC == null && kotlin.jvm.internal.k.l(this.d)) ? (ar) context2.get(t.f551c) : null;
                if (arVar == null || arVar.a()) {
                    objF = thC != null ? y.f(thC) : d(objF3);
                } else {
                    CancellationException cancellationExceptionL = ((ay) arVar).l();
                    a(objF3, cancellationExceptionL);
                    objF = y.f(cancellationExceptionL);
                }
                eVar.resumeWith(objF);
                if (jX == null || jX.ae()) {
                    kotlinx.coroutines.internal.b.c(context, objE);
                }
                try {
                    jVar.getClass();
                } catch (Throwable th) {
                    objF2 = y.f(th);
                }
                e(null, g.f.a(objF2));
            } catch (Throwable th2) {
                if (jX == null || jX.ae()) {
                    kotlinx.coroutines.internal.b.c(context, objE);
                }
                throw th2;
            }
        } catch (Throwable th3) {
            try {
                jVar.getClass();
            } catch (Throwable th4) {
                objF2 = y.f(th4);
            }
            e(th3, g.f.a(objF2));
        }
    }

    public Object d(Object obj) {
        return obj;
    }
}
