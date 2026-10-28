package x;

import android.os.Build;
import i.j;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import w.t;
import w.u;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class b extends i.a implements u {
    private volatile Object _preHandler;

    public b() {
        super(t.b);
        this._preHandler = this;
    }

    public void b(j jVar, Throwable th) {
        Method declaredMethod;
        int i2 = Build.VERSION.SDK_INT;
        if (26 > i2 || i2 >= 28) {
            return;
        }
        Object obj = this._preHandler;
        if (obj != this) {
            declaredMethod = (Method) obj;
        } else {
            try {
                declaredMethod = Thread.class.getDeclaredMethod("getUncaughtExceptionPreHandler", new Class[0]);
                if (!Modifier.isPublic(declaredMethod.getModifiers()) || !Modifier.isStatic(declaredMethod.getModifiers())) {
                    declaredMethod = null;
                }
            } catch (Throwable unused) {
            }
            this._preHandler = declaredMethod;
        }
        Object objInvoke = declaredMethod != null ? declaredMethod.invoke(null, new Object[0]) : null;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = objInvoke instanceof Thread.UncaughtExceptionHandler ? (Thread.UncaughtExceptionHandler) objInvoke : null;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
        }
    }
}
