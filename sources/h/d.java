package h;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class d extends y {
    public static final void ao(Object[] objArr, Object[] destination, int i2, int i3, int i4) {
        kotlin.jvm.internal.k.g(objArr, "<this>");
        kotlin.jvm.internal.k.g(destination, "destination");
        System.arraycopy(objArr, i3, destination, i2, i4 - i3);
    }

    public static /* synthetic */ void ap(Object[] objArr, Object[] objArr2, int i2, int i3, int i4, int i5) {
        if ((i5 & 2) != 0) {
            i2 = 0;
        }
        if ((i5 & 4) != 0) {
            i3 = 0;
        }
        if ((i5 & 8) != 0) {
            i4 = objArr.length;
        }
        ao(objArr, objArr2, i2, i3, i4);
    }

    public static LinkedHashSet aq(Set set, Set elements) {
        kotlin.jvm.internal.k.g(set, "<this>");
        kotlin.jvm.internal.k.g(elements, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(y.x(set.size() + Integer.valueOf(elements.size()).intValue()));
        linkedHashSet.addAll(set);
        linkedHashSet.addAll(elements);
        return linkedHashSet;
    }

    public static Map ar(ArrayList arrayList) {
        h hVar = h.b;
        int size = arrayList.size();
        if (size == 0) {
            return hVar;
        }
        if (size != 1) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(y.x(arrayList.size()));
            as(arrayList, linkedHashMap);
            return linkedHashMap;
        }
        g.d pair = (g.d) arrayList.get(0);
        kotlin.jvm.internal.k.g(pair, "pair");
        Map mapSingletonMap = Collections.singletonMap(pair.b, pair.f420c);
        kotlin.jvm.internal.k.f(mapSingletonMap, "singletonMap(pair.first, pair.second)");
        return mapSingletonMap;
    }

    public static final void as(ArrayList arrayList, LinkedHashMap linkedHashMap) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            g.d dVar = (g.d) it.next();
            linkedHashMap.put(dVar.b, dVar.f420c);
        }
    }
}
