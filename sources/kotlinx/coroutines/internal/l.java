package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import w.az;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final az f459a;

    static {
        String property;
        Object next;
        int i2 = r.f462a;
        az azVarC = null;
        try {
            property = System.getProperty("kotlinx.coroutines.fast.service.loader");
        } catch (SecurityException unused) {
            property = null;
        }
        if (property != null) {
            Boolean.parseBoolean(property);
        }
        Iterator itI = androidx.appcompat.graphics.drawable.a.i();
        kotlin.jvm.internal.k.g(itI, "<this>");
        u.k kVar = new u.k(1, itI);
        boolean z2 = kVar instanceof u.a;
        u.g aVar = kVar;
        if (!z2) {
            aVar = new u.a(kVar);
        }
        List listAc = u.j.ac(aVar);
        Iterator it = listAc.iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                int iA = ((k) next).a();
                do {
                    Object next2 = it.next();
                    int iA2 = ((k) next2).a();
                    if (iA < iA2) {
                        next = next2;
                        iA = iA2;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        k kVar2 = (k) next;
        if (kVar2 != null) {
            try {
                azVarC = kVar2.c(listAc);
            } catch (Throwable unused2) {
                kVar2.b();
            }
            if (azVarC != null) {
                f459a = azVarC;
                return;
            }
        }
        throw new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'");
    }
}
