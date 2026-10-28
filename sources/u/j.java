package u;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class j extends m {
    public static Object aa(d dVar) {
        c cVar = new c(dVar);
        if (cVar.hasNext()) {
            return cVar.next();
        }
        return null;
    }

    public static d ab(g gVar, p.l transform) {
        kotlin.jvm.internal.k.g(transform, "transform");
        return new d(new f(gVar, transform));
    }

    public static List ac(g gVar) {
        ArrayList arrayList = new ArrayList();
        Iterator it = gVar.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        int size = arrayList.size();
        if (size == 0) {
            return h.g.b;
        }
        if (size != 1) {
            return arrayList;
        }
        List listSingletonList = Collections.singletonList(arrayList.get(0));
        kotlin.jvm.internal.k.f(listSingletonList, "singletonList(element)");
        return listSingletonList;
    }
}
