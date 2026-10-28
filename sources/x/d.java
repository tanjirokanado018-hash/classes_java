package x;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import g.e;
import java.lang.reflect.InvocationTargetException;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class d {
    private static volatile Choreographer choreographer;

    static {
        Object objF;
        try {
            objF = new c(a(Looper.getMainLooper()));
        } catch (Throwable th) {
            objF = y.f(th);
        }
        if (objF instanceof e) {
            objF = null;
        }
    }

    public static final Handler a(Looper looper) throws IllegalAccessException, InvocationTargetException {
        if (Build.VERSION.SDK_INT < 28) {
            try {
                return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
            } catch (NoSuchMethodException unused) {
                return new Handler(looper);
            }
        }
        Object objInvoke = Handler.class.getDeclaredMethod("createAsync", Looper.class).invoke(null, looper);
        if (objInvoke != null) {
            return (Handler) objInvoke;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.os.Handler");
    }
}
