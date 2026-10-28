package m;

import java.lang.reflect.InvocationTargetException;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class b extends l.b {
    @Override // l.b
    public final void a(Throwable cause, Throwable exception) throws IllegalAccessException, InvocationTargetException {
        k.g(cause, "cause");
        k.g(exception, "exception");
        Integer num = a.f496a;
        if (num == null || num.intValue() >= 19) {
            cause.addSuppressed(exception);
        } else {
            super.a(cause, exception);
        }
    }
}
