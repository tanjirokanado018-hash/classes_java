package l;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import q.e;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class b {
    public void a(Throwable cause, Throwable exception) throws IllegalAccessException, InvocationTargetException {
        k.g(cause, "cause");
        k.g(exception, "exception");
        Method method = a.f494a;
        if (method != null) {
            method.invoke(cause, exception);
        }
    }

    public e b() {
        return new q.c();
    }
}
