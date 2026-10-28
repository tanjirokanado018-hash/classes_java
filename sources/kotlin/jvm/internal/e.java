package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import p.r;
import p.s;
import p.t;
import p.u;
import p.v;
import p.w;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class e implements t.c, d {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final Map f435c;
    public final Class b;

    static {
        int i2 = 0;
        List listAsList = Arrays.asList(p.a.class, p.l.class, p.p.class, p.q.class, r.class, s.class, t.class, u.class, v.class, w.class, p.b.class, p.c.class, p.d.class, p.e.class, p.f.class, p.g.class, p.h.class, p.i.class, p.j.class, p.k.class, p.m.class, p.n.class, p.o.class);
        k.f(listAsList, "asList(this)");
        ArrayList arrayList = new ArrayList(listAsList.size());
        for (Object obj : listAsList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                throw new ArithmeticException("Index overflow has happened.");
            }
            arrayList.add(new g.d((Class) obj, Integer.valueOf(i2)));
            i2 = i3;
        }
        f435c = h.d.ar(arrayList);
        HashMap map = new HashMap();
        map.put("boolean", "kotlin.Boolean");
        map.put("char", "kotlin.Char");
        map.put("byte", "kotlin.Byte");
        map.put("short", "kotlin.Short");
        map.put("int", "kotlin.Int");
        map.put("float", "kotlin.Float");
        map.put("long", "kotlin.Long");
        map.put("double", "kotlin.Double");
        HashMap map2 = new HashMap();
        map2.put("java.lang.Boolean", "kotlin.Boolean");
        map2.put("java.lang.Character", "kotlin.Char");
        map2.put("java.lang.Byte", "kotlin.Byte");
        map2.put("java.lang.Short", "kotlin.Short");
        map2.put("java.lang.Integer", "kotlin.Int");
        map2.put("java.lang.Float", "kotlin.Float");
        map2.put("java.lang.Long", "kotlin.Long");
        map2.put("java.lang.Double", "kotlin.Double");
        HashMap map3 = new HashMap();
        map3.put("java.lang.Object", "kotlin.Any");
        map3.put("java.lang.String", "kotlin.String");
        map3.put("java.lang.CharSequence", "kotlin.CharSequence");
        map3.put("java.lang.Throwable", "kotlin.Throwable");
        map3.put("java.lang.Cloneable", "kotlin.Cloneable");
        map3.put("java.lang.Number", "kotlin.Number");
        map3.put("java.lang.Comparable", "kotlin.Comparable");
        map3.put("java.lang.Enum", "kotlin.Enum");
        map3.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        map3.put("java.lang.Iterable", "kotlin.collections.Iterable");
        map3.put("java.util.Iterator", "kotlin.collections.Iterator");
        map3.put("java.util.Collection", "kotlin.collections.Collection");
        map3.put("java.util.List", "kotlin.collections.List");
        map3.put("java.util.Set", "kotlin.collections.Set");
        map3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        map3.put("java.util.Map", "kotlin.collections.Map");
        map3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        map3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        map3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        map3.putAll(map);
        map3.putAll(map2);
        Collection<String> collectionValues = map.values();
        k.f(collectionValues, "primitiveFqNames.values");
        for (String kotlinName : collectionValues) {
            StringBuilder sb = new StringBuilder("kotlin.jvm.internal.");
            k.f(kotlinName, "kotlinName");
            sb.append(v.d.ab(kotlinName));
            sb.append("CompanionObject");
            map3.put(sb.toString(), kotlinName.concat(".Companion"));
        }
        for (Map.Entry entry : f435c.entrySet()) {
            Class cls = (Class) entry.getKey();
            int iIntValue = ((Number) entry.getValue()).intValue();
            map3.put(cls.getName(), "kotlin.Function" + iIntValue);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(y.x(map3.size()));
        for (Map.Entry entry2 : map3.entrySet()) {
            linkedHashMap.put(entry2.getKey(), v.d.ab((String) entry2.getValue()));
        }
    }

    public e(Class jClass) {
        k.g(jClass, "jClass");
        this.b = jClass;
    }

    @Override // kotlin.jvm.internal.d
    public final Class a() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof e) && k.a(y.o(this), y.o((t.c) obj));
    }

    public final int hashCode() {
        return y.o(this).hashCode();
    }

    public final String toString() {
        return this.b.toString() + " (Kotlin reflection is not available)";
    }
}
