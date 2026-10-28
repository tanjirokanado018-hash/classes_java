package kotlin.jvm.internal;

import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class n extends c implements t.f, p.a {
    public final boolean b;

    public n(Object obj) {
        super(obj, y.class, "classSimpleName", "getClassSimpleName(Ljava/lang/Object;)Ljava/lang/String;", true);
        this.b = false;
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof n) {
            n nVar = (n) obj;
            return getOwner().equals(nVar.getOwner()) && getName().equals(nVar.getName()) && getSignature().equals(nVar.getSignature()) && k.a(getBoundReceiver(), nVar.getBoundReceiver());
        }
        if (obj instanceof t.f) {
            return obj.equals(compute());
        }
        return false;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final int hashCode() {
        return getSignature().hashCode() + ((getName().hashCode() + (getOwner().hashCode() * 31)) * 31);
    }

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final String toString() {
        t.b bVarCompute = compute();
        if (bVarCompute != this) {
            return bVarCompute.toString();
        }
        return "property " + getName() + " (Kotlin reflection is not available)";
    }

    @Override // kotlin.jvm.internal.c
    public final t.b compute() {
        return this.b ? this : super.compute();
    }

    @Override // kotlin.jvm.internal.c
    public final t.b computeReflected() {
        p.f437a.getClass();
        return this;
    }

    @Override // kotlin.jvm.internal.c
    public final t.b getReflected() {
        if (this.b) {
            throw new UnsupportedOperationException("Kotlin reflection is not yet supported for synthetic Java properties");
        }
        t.b bVarCompute = compute();
        if (bVarCompute != this) {
            return (t.f) bVarCompute;
        }
        throw new o.a("Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath");
    }

    @Override // p.a
    public final Object invoke() {
        return ((kotlinx.coroutines.internal.f) this).d();
    }
}
