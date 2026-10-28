package l;

import java.lang.reflect.Method;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Method f494a;

    static {
        Method method;
        Method[] throwableMethods = Throwable.class.getMethods();
        k.f(throwableMethods, "throwableMethods");
        int length = throwableMethods.length;
        int i2 = 0;
        while (true) {
            method = null;
            if (i2 >= length) {
                break;
            }
            Method method2 = throwableMethods[i2];
            if (k.a(method2.getName(), "addSuppressed")) {
                Class<?>[] parameterTypes = method2.getParameterTypes();
                k.f(parameterTypes, "it.parameterTypes");
                if (k.a(parameterTypes.length == 1 ? parameterTypes[0] : null, Throwable.class)) {
                    method = method2;
                    break;
                }
            }
            i2++;
        }
        f494a = method;
        int length2 = throwableMethods.length;
        for (int i3 = 0; i3 < length2 && !k.a(throwableMethods[i3].getName(), "getSuppressed"); i3++) {
        }
    }
}
