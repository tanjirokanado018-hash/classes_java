package w;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class v {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final List f552a;

    static {
        Iterator itA = x.a();
        kotlin.jvm.internal.k.g(itA, "<this>");
        u.k kVar = new u.k(1, itA);
        boolean z2 = kVar instanceof u.a;
        u.g aVar = kVar;
        if (!z2) {
            aVar = new u.a(kVar);
        }
        f552a = u.j.ac(aVar);
    }

    public static final void a(i.j jVar, Throwable th) {
        Throwable runtimeException;
        Iterator it = f552a.iterator();
        while (it.hasNext()) {
            try {
                ((x.b) ((u) it.next())).b(jVar, th);
            } catch (Throwable th2) {
                Thread threadCurrentThread = Thread.currentThread();
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = threadCurrentThread.getUncaughtExceptionHandler();
                if (th == th2) {
                    runtimeException = th;
                } else {
                    runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                    y.a(runtimeException, th);
                }
                uncaughtExceptionHandler.uncaughtException(threadCurrentThread, runtimeException);
            }
        }
        Thread threadCurrentThread2 = Thread.currentThread();
        try {
            y.a(th, new ac(jVar));
        } catch (Throwable th3) {
            y.f(th3);
        }
        threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th);
    }
}
