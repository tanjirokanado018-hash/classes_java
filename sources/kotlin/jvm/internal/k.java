package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import p.r;
import w.A;
import w.AbstractC0031a;
import w.B;
import w.C0040j;
import w.E;
import w.J;
import w.K;
import w.ad;
import w.ae;
import w.af;
import w.ag;
import w.ah;
import w.am;
import w.an;
import w.ao;
import w.aq;
import w.ar;
import w.as;
import w.at;
import w.au;
import w.aw;
import w.ay;
import w.s;
import w.t;
import w.u;
import w.v;
import w.w;
import w.x;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Object[] f436a = new Object[0];

    public static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static void b(Object obj) {
        if (obj != null) {
            if (obj instanceof g.a) {
                if (obj instanceof h) {
                    if (((h) obj).getArity() == 2) {
                        return;
                    }
                } else if (!(obj instanceof p.a) && !(obj instanceof p.l)) {
                    if (obj instanceof p.p) {
                        return;
                    } else {
                        boolean z2 = obj instanceof r;
                    }
                }
            }
            ClassCastException classCastException = new ClassCastException(obj.getClass().getName().concat(" cannot be cast to kotlin.jvm.functions.Function2"));
            q(k.class.getName(), classCastException);
            throw classCastException;
        }
    }

    public static void c(ar arVar) {
        ay ayVar = (ay) arVar;
        ayVar.getClass();
        ayVar.e(new as(ayVar.g(), null, ayVar));
    }

    public static void d(Object obj) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException();
        q(k.class.getName(), nullPointerException);
        throw nullPointerException;
    }

    public static void e(Object obj, String str) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException(str);
        q(k.class.getName(), nullPointerException);
        throw nullPointerException;
    }

    public static void f(Object obj, String str) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException(str.concat(" must not be null"));
        q(k.class.getName(), nullPointerException);
        throw nullPointerException;
    }

    public static void g(Object obj, String str) {
        if (obj == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String name = k.class.getName();
            int i2 = 0;
            while (!stackTrace[i2].getClassName().equals(name)) {
                i2++;
            }
            while (stackTrace[i2].getClassName().equals(name)) {
                i2++;
            }
            StackTraceElement stackTraceElement = stackTrace[i2];
            NullPointerException nullPointerException = new NullPointerException("Parameter specified as non-null is null: method " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ", parameter " + str);
            q(k.class.getName(), nullPointerException);
            throw nullPointerException;
        }
    }

    public static final void h(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.c("Expected positive parallelism level, but got ", i2).toString());
        }
    }

    public static final i.j i(i.j jVar, i.j jVar2, boolean z2) {
        Boolean bool = Boolean.FALSE;
        w.o oVar = w.o.d;
        boolean zBooleanValue = ((Boolean) jVar.fold(bool, oVar)).booleanValue();
        boolean zBooleanValue2 = ((Boolean) jVar2.fold(bool, oVar)).booleanValue();
        if (!zBooleanValue && !zBooleanValue2) {
            return jVar.plus(jVar2);
        }
        i.k kVar = i.k.b;
        i.j jVar3 = (i.j) jVar.fold(kVar, new w.p(2));
        Object objFold = jVar2;
        if (zBooleanValue2) {
            objFold = jVar2.fold(kVar, w.o.f549c);
        }
        return jVar3.plus((i.j) objFold);
    }

    public static final void j(i.j jVar, Throwable th) {
        try {
            u uVar = (u) jVar.get(t.b);
            if (uVar != null) {
                ((x.b) uVar).b(jVar, th);
            } else {
                v.a(jVar, th);
            }
        } catch (Throwable th2) {
            if (th != th2) {
                RuntimeException runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                y.a(runtimeException, th);
                th = runtimeException;
            }
            v.a(jVar, th);
        }
    }

    /* JADX WARN: Code duplicated, block: B:102:0x00bb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:103:0x00dc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:112:0x0027 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:78:0x00c2  */
    public static ag k(ar arVar, boolean z2, au auVar, int i2) {
        au aqVar;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        Throwable thC;
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        boolean z3 = (i2 & 2) != 0;
        ay ayVar = (ay) arVar;
        ayVar.getClass();
        if (z2) {
            aqVar = auVar instanceof at ? (at) auVar : null;
            if (aqVar == null) {
                aqVar = new aq(auVar);
            }
        } else {
            aqVar = auVar;
        }
        aqVar.f528e = ayVar;
        while (true) {
            Object objQ = ayVar.q();
            if (objQ instanceof ah) {
                ah ahVar = (ah) objQ;
                if (ahVar.b) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = ay.b;
                    while (!atomicReferenceFieldUpdater2.compareAndSet(ayVar, objQ, aqVar)) {
                        if (atomicReferenceFieldUpdater2.get(ayVar) != objQ) {
                        }
                    }
                    return aqVar;
                }
                A a2 = new A();
                an amVar = ahVar.b ? a2 : new am(a2);
                do {
                    atomicReferenceFieldUpdater = ay.b;
                    if (atomicReferenceFieldUpdater.compareAndSet(ayVar, ahVar, amVar)) {
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(ayVar) == ahVar);
            } else {
                if (!(objQ instanceof an)) {
                    if (z3) {
                        w.m mVar = objQ instanceof w.m ? (w.m) objQ : null;
                        auVar.invoke(mVar != null ? mVar.f547a : null);
                    }
                    return B.b;
                }
                A aD = ((an) objQ).d();
                if (aD != null) {
                    ag agVar = B.b;
                    if (z2 && (objQ instanceof aw)) {
                        synchronized (objQ) {
                            try {
                                thC = ((aw) objQ).c();
                                if (thC == null || ((auVar instanceof C0040j) && !((aw) objQ).f())) {
                                    if (ayVar.b(objQ, aD, aqVar)) {
                                        if (thC == null) {
                                            return aqVar;
                                        }
                                        agVar = aqVar;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (thC != null) {
                            if (z3) {
                                auVar.invoke(thC);
                            }
                            return agVar;
                        }
                        if (ayVar.b(objQ, aD, aqVar)) {
                            return aqVar;
                        }
                    } else {
                        thC = null;
                        if (thC != null) {
                            if (z3) {
                                auVar.invoke(thC);
                            }
                            return agVar;
                        }
                        if (ayVar.b(objQ, aD, aqVar)) {
                            return aqVar;
                        }
                    }
                } else {
                    if (objQ == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                    }
                    ayVar.ab((au) objQ);
                }
            }
        }
    }

    public static final boolean l(int i2) {
        return i2 == 1 || i2 == 2;
    }

    public static E m(w wVar, x.c cVar, p.p pVar, int i2) {
        i.j jVar = cVar;
        if ((i2 & 1) != 0) {
            jVar = i.k.b;
        }
        i.j jVarI = i(wVar.getCoroutineContext(), jVar, true);
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        if (jVarI != dVar && jVarI.get(i.f.b) == null) {
            jVarI = jVarI.plus(dVar);
        }
        E e2 = new E(jVarI, true);
        int iC = x.c(1);
        if (iC == 0) {
            r(pVar, e2, e2);
        } else if (iC != 1) {
            if (iC == 2) {
                y.u(y.e(e2, e2, pVar)).resumeWith(g.i.f423a);
            } else {
                if (iC != 3) {
                    throw new g.b();
                }
                try {
                    i.j jVar2 = e2.f519c;
                    Object objE = kotlinx.coroutines.internal.b.e(jVar2, null);
                    try {
                        b(pVar);
                        Object objInvoke = pVar.invoke(e2, e2);
                        kotlinx.coroutines.internal.b.c(jVar2, objE);
                        if (objInvoke != j.a.b) {
                            e2.resumeWith(objInvoke);
                        }
                    } catch (Throwable th) {
                        kotlinx.coroutines.internal.b.c(jVar2, objE);
                        throw th;
                    }
                } catch (Throwable th2) {
                    e2.resumeWith(y.f(th2));
                }
            }
        }
        return e2;
    }

    public static final Object n(Object obj) {
        return obj instanceof w.m ? y.f(((w.m) obj).f547a) : obj;
    }

    public static void o() {
        throw new UnsupportedOperationException("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
    }

    public static final void p(ae aeVar, i.e eVar, boolean z2) {
        Object objF = aeVar.f();
        Throwable thC = aeVar.c(objF);
        Object objF2 = thC != null ? y.f(thC) : aeVar.d(objF);
        if (!z2) {
            eVar.resumeWith(objF2);
            return;
        }
        kotlinx.coroutines.internal.d dVar = (kotlinx.coroutines.internal.d) eVar;
        i.e eVar2 = dVar.f446f;
        i.j context = eVar2.getContext();
        Object objE = kotlinx.coroutines.internal.b.e(context, dVar.f448h);
        J jX = objE != kotlinx.coroutines.internal.b.f442e ? x(eVar2, context, objE) : null;
        try {
            dVar.f446f.resumeWith(objF2);
        } finally {
            if (jX == null || jX.ae()) {
                kotlinx.coroutines.internal.b.c(context, objE);
            }
        }
    }

    public static void q(String str, RuntimeException runtimeException) {
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        int length = stackTrace.length;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            if (str.equals(stackTrace[i3].getClassName())) {
                i2 = i3;
            }
        }
        runtimeException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i2 + 1, length));
    }

    public static void r(p.p pVar, AbstractC0031a abstractC0031a, AbstractC0031a abstractC0031a2) {
        try {
            kotlinx.coroutines.internal.b.d(y.u(y.e(abstractC0031a, abstractC0031a2, pVar)), g.i.f423a, null);
        } catch (Throwable th) {
            abstractC0031a2.resumeWith(y.f(th));
            throw th;
        }
    }

    public static final Object s(kotlinx.coroutines.internal.p pVar, kotlinx.coroutines.internal.p pVar2, p.p pVar3) throws Throwable {
        Object mVar;
        Object objV;
        an anVar;
        try {
            b(pVar3);
            mVar = pVar3.invoke(pVar2, pVar);
        } catch (Throwable th) {
            mVar = new w.m(false, th);
        }
        j.a aVar = j.a.b;
        if (mVar == aVar || (objV = pVar.v(mVar)) == y.f554c) {
            return aVar;
        }
        if (objV instanceof w.m) {
            throw ((w.m) objV).f547a;
        }
        ao aoVar = objV instanceof ao ? (ao) objV : null;
        return (aoVar == null || (anVar = aoVar.f525a) == null) ? objV : anVar;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x007c A[PHI: r17
      0x007c: PHI (r17v3 long) = (r17v2 long), (r17v4 long) binds: [B:31:0x006e, B:35:0x0079] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:40:0x0087 A[LOOP:0: B:26:0x0059->B:40:0x0087, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:64:0x002c A[SYNTHETIC] */
    public static final long t(long j2, long j3, long j4, String str) {
        String property;
        String str2;
        Long lValueOf;
        long j5;
        long j6;
        long j7;
        int i2 = kotlinx.coroutines.internal.r.f462a;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null) {
            return j2;
        }
        int i3 = 1;
        if (10 > new s.d(2, 36, 1).f499c) {
            throw new IllegalArgumentException("radix 10 was not in valid range " + new s.d(2, 36, 1));
        }
        int length = property.length();
        if (length == 0) {
            str2 = property;
            lValueOf = null;
        } else {
            boolean z2 = false;
            char cCharAt = property.charAt(0);
            long j8 = -9223372036854775807L;
            if (cCharAt < '0') {
                if (length != 1) {
                    if (cCharAt == '-') {
                        j8 = Long.MIN_VALUE;
                        z2 = true;
                    } else if (cCharAt == '+') {
                    }
                }
                str2 = property;
                lValueOf = null;
            } else {
                i3 = 0;
            }
            long j9 = 0;
            long j10 = -256204778801521550L;
            while (true) {
                if (i3 >= length) {
                    long j11 = j9;
                    str2 = property;
                    lValueOf = z2 ? Long.valueOf(j11) : Long.valueOf(-j11);
                } else {
                    int iDigit = Character.digit((int) property.charAt(i3), 10);
                    if (iDigit < 0) {
                        str2 = property;
                    } else {
                        long j12 = j9;
                        str2 = property;
                        if (j12 >= j10) {
                            j5 = ((long) 10) * j12;
                            j6 = iDigit;
                            if (j5 < j8 + j6) {
                                i3++;
                                property = str2;
                                j9 = j5 - j6;
                            }
                        } else if (j10 == -256204778801521550L) {
                            j10 = j8 / ((long) 10);
                            if (j12 >= j10) {
                                j5 = ((long) 10) * j12;
                                j6 = iDigit;
                                if (j5 < j8 + j6) {
                                    i3++;
                                    property = str2;
                                    j9 = j5 - j6;
                                }
                            }
                        }
                    }
                    lValueOf = null;
                }
            }
        }
        if (lValueOf == null) {
            throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + str2 + '\'').toString());
        }
        long jLongValue = lValueOf.longValue();
        if (j3 <= jLongValue) {
            j7 = j4;
            if (jLongValue <= j7) {
                return jLongValue;
            }
        } else {
            j7 = j4;
        }
        throw new IllegalStateException(("System property '" + str + "' should be in range " + j3 + ".." + j7 + ", but is '" + jLongValue + '\'').toString());
    }

    public static int u(String str, int i2, int i3, int i4, int i5) {
        if ((i5 & 4) != 0) {
            i3 = 1;
        }
        if ((i5 & 8) != 0) {
            i4 = Integer.MAX_VALUE;
        }
        return (int) t(i2, i3, i4, str);
    }

    public static final Object[] v(Collection collection) {
        g(collection, "collection");
        int size = collection.size();
        Object[] objArr = f436a;
        if (size == 0) {
            return objArr;
        }
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            return objArr;
        }
        Object[] objArrCopyOf = new Object[size];
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            objArrCopyOf[i2] = it.next();
            if (i3 >= objArrCopyOf.length) {
                if (!it.hasNext()) {
                    return objArrCopyOf;
                }
                int i4 = ((i3 * 3) + 1) >>> 1;
                if (i4 <= i3) {
                    i4 = 2147483645;
                    if (i3 >= 2147483645) {
                        throw new OutOfMemoryError();
                    }
                }
                objArrCopyOf = Arrays.copyOf(objArrCopyOf, i4);
                f(objArrCopyOf, "copyOf(result, newSize)");
            } else if (!it.hasNext()) {
                Object[] objArrCopyOf2 = Arrays.copyOf(objArrCopyOf, i3);
                f(objArrCopyOf2, "copyOf(result, size)");
                return objArrCopyOf2;
            }
            i2 = i3;
        }
    }

    public static final Object[] w(Collection collection, Object[] objArr) {
        Object[] objArrCopyOf;
        g(collection, "collection");
        objArr.getClass();
        int size = collection.size();
        int i2 = 0;
        if (size == 0) {
            if (objArr.length <= 0) {
                return objArr;
            }
            objArr[0] = null;
            return objArr;
        }
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            if (objArr.length <= 0) {
                return objArr;
            }
            objArr[0] = null;
            return objArr;
        }
        if (size <= objArr.length) {
            objArrCopyOf = objArr;
        } else {
            Object objNewInstance = Array.newInstance(objArr.getClass().getComponentType(), size);
            e(objNewInstance, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
            objArrCopyOf = (Object[]) objNewInstance;
        }
        while (true) {
            int i3 = i2 + 1;
            objArrCopyOf[i2] = it.next();
            if (i3 >= objArrCopyOf.length) {
                if (!it.hasNext()) {
                    return objArrCopyOf;
                }
                int i4 = ((i3 * 3) + 1) >>> 1;
                if (i4 <= i3) {
                    i4 = 2147483645;
                    if (i3 >= 2147483645) {
                        throw new OutOfMemoryError();
                    }
                }
                objArrCopyOf = Arrays.copyOf(objArrCopyOf, i4);
                f(objArrCopyOf, "copyOf(result, newSize)");
            } else if (!it.hasNext()) {
                if (objArrCopyOf == objArr) {
                    objArr[i3] = null;
                    return objArr;
                }
                Object[] objArrCopyOf2 = Arrays.copyOf(objArrCopyOf, i3);
                f(objArrCopyOf2, "copyOf(result, size)");
                return objArrCopyOf2;
            }
            i2 = i3;
        }
    }

    public static final J x(i.e eVar, i.j jVar, Object obj) {
        J j2 = null;
        if (!(eVar instanceof k.d)) {
            return null;
        }
        if (jVar.get(K.b) != null) {
            k.d callerFrame = (k.d) eVar;
            while (!(callerFrame instanceof ad) && (callerFrame = callerFrame.getCallerFrame()) != null) {
                if (callerFrame instanceof J) {
                    j2 = (J) callerFrame;
                    break;
                }
            }
            if (j2 != null) {
                j2.f518e.set(new g.d(jVar, obj));
            }
        }
        return j2;
    }

    public static final Object y(s sVar, p.p pVar, i.e eVar) {
        i.j context = eVar.getContext();
        i.j jVarPlus = !((Boolean) sVar.fold(Boolean.FALSE, w.o.d)).booleanValue() ? context.plus(sVar) : i(context, sVar, false);
        ar arVar = (ar) jVarPlus.get(t.f551c);
        if (arVar != null && !arVar.a()) {
            throw ((ay) arVar).l();
        }
        if (jVarPlus == context) {
            kotlinx.coroutines.internal.p pVar2 = new kotlinx.coroutines.internal.p(eVar, jVarPlus);
            return s(pVar2, pVar2, pVar);
        }
        i.f fVar = i.f.b;
        if (!a(jVarPlus.get(fVar), context.get(fVar))) {
            ad adVar = new ad(eVar, jVarPlus);
            r(pVar, adVar, adVar);
            return adVar.ae();
        }
        K k2 = K.b;
        J j2 = new J(eVar, jVarPlus.get(k2) == null ? jVarPlus.plus(k2) : jVarPlus);
        j2.f518e = new ThreadLocal();
        if (!(eVar.getContext().get(fVar) instanceof s)) {
            Object objE = kotlinx.coroutines.internal.b.e(jVarPlus, null);
            kotlinx.coroutines.internal.b.c(jVarPlus, objE);
            j2.f518e.set(new g.d(jVarPlus, objE));
        }
        Object objE2 = kotlinx.coroutines.internal.b.e(jVarPlus, null);
        try {
            return s(j2, j2, pVar);
        } finally {
            kotlinx.coroutines.internal.b.c(jVarPlus, objE2);
        }
    }
}
