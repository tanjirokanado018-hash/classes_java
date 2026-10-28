package w;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class x {
    public static /* synthetic */ Iterator a() {
        try {
            return Arrays.asList(new x.b()).iterator();
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }

    public static /* synthetic */ void b(i.h hVar) {
        if (hVar != null) {
            throw new ClassCastException();
        }
    }

    public static /* synthetic */ int c(int i2) {
        if (i2 != 0) {
            return i2 - 1;
        }
        throw null;
    }
}
