package k;

import f.q;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a implements i.e, d, Serializable {
    private final i.e completion;

    public a(i.e eVar) {
        this.completion = eVar;
    }

    public i.e create(i.e completion) {
        k.g(completion, "completion");
        throw new UnsupportedOperationException("create(Continuation) has not been overridden");
    }

    @Override // k.d
    public d getCallerFrame() {
        i.e eVar = this.completion;
        if (eVar instanceof d) {
            return (d) eVar;
        }
        return null;
    }

    public final i.e getCompletion() {
        return this.completion;
    }

    public StackTraceElement getStackTraceElement() {
        int iIntValue;
        String strC;
        e eVar = (e) getClass().getAnnotation(e.class);
        String str = null;
        if (eVar == null) {
            return null;
        }
        int iV = eVar.v();
        if (iV > 1) {
            throw new IllegalStateException(("Debug metadata version mismatch. Expected: 1, got " + iV + ". Please update the Kotlin standard library.").toString());
        }
        try {
            Field declaredField = getClass().getDeclaredField("label");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this);
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            iIntValue = (num != null ? num.intValue() : 0) - 1;
        } catch (Exception unused) {
            iIntValue = -1;
        }
        int i2 = iIntValue >= 0 ? eVar.l()[iIntValue] : -1;
        q qVar = f.b;
        q qVar2 = f.f433a;
        if (qVar == null) {
            try {
                q qVar3 = new q(Class.class.getDeclaredMethod("getModule", new Class[0]), getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", new Class[0]), getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", new Class[0]));
                f.b = qVar3;
                qVar = qVar3;
            } catch (Exception unused2) {
                f.b = qVar2;
                qVar = qVar2;
            }
        }
        if (qVar != qVar2) {
            Method method = (Method) qVar.f383a;
            Object objInvoke = method != null ? method.invoke(getClass(), new Object[0]) : null;
            if (objInvoke != null) {
                Method method2 = (Method) qVar.b;
                Object objInvoke2 = method2 != null ? method2.invoke(objInvoke, new Object[0]) : null;
                if (objInvoke2 != null) {
                    Method method3 = (Method) qVar.f384c;
                    Object objInvoke3 = method3 != null ? method3.invoke(objInvoke2, new Object[0]) : null;
                    if (objInvoke3 instanceof String) {
                        str = (String) objInvoke3;
                    }
                }
            }
        }
        if (str == null) {
            strC = eVar.c();
        } else {
            strC = str + '/' + eVar.c();
        }
        return new StackTraceElement(strC, eVar.m(), eVar.f(), i2);
    }

    public abstract Object invokeSuspend(Object obj);

    @Override // i.e
    public final void resumeWith(Object obj) {
        i.e eVar = this;
        while (true) {
            a aVar = (a) eVar;
            i.e eVar2 = aVar.completion;
            k.d(eVar2);
            try {
                obj = aVar.invokeSuspend(obj);
                if (obj == j.a.b) {
                    return;
                }
            } catch (Throwable th) {
                obj = y.f(th);
            }
            aVar.releaseIntercepted();
            if (!(eVar2 instanceof a)) {
                eVar2.resumeWith(obj);
                return;
            }
            eVar = eVar2;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Continuation at ");
        Object stackTraceElement = getStackTraceElement();
        if (stackTraceElement == null) {
            stackTraceElement = getClass().getName();
        }
        sb.append(stackTraceElement);
        return sb.toString();
    }

    public i.e create(Object obj, i.e completion) {
        k.g(completion, "completion");
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    public void releaseIntercepted() {
    }
}
