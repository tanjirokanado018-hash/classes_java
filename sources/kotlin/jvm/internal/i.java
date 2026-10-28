package kotlin.jvm.internal;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class i extends c implements h, t.e {
    private final int arity;
    private final int flags;

    public i(int i2, Object obj, Class cls, String str, String str2) {
        super(obj, cls, str, str2, false);
        this.arity = i2;
        this.flags = 0;
    }

    @Override // kotlin.jvm.internal.c
    public t.b computeReflected() {
        p.f437a.getClass();
        return this;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof i) {
            i iVar = (i) obj;
            return getName().equals(iVar.getName()) && getSignature().equals(iVar.getSignature()) && this.flags == iVar.flags && this.arity == iVar.arity && k.a(getBoundReceiver(), iVar.getBoundReceiver()) && k.a(getOwner(), iVar.getOwner());
        }
        if (obj instanceof t.e) {
            return obj.equals(compute());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.h
    public int getArity() {
        return this.arity;
    }

    public int hashCode() {
        return getSignature().hashCode() + ((getName().hashCode() + (getOwner() == null ? 0 : getOwner().hashCode() * 31)) * 31);
    }

    @Override // t.e
    public boolean isExternal() {
        return getReflected().isExternal();
    }

    @Override // t.e
    public boolean isInfix() {
        return getReflected().isInfix();
    }

    @Override // t.e
    public boolean isInline() {
        return getReflected().isInline();
    }

    @Override // t.e
    public boolean isOperator() {
        return getReflected().isOperator();
    }

    @Override // t.e
    public boolean isSuspend() {
        return getReflected().isSuspend();
    }

    public String toString() {
        t.b bVarCompute = compute();
        if (bVarCompute != this) {
            return bVarCompute.toString();
        }
        if ("<init>".equals(getName())) {
            return "constructor (Kotlin reflection is not available)";
        }
        return "function " + getName() + " (Kotlin reflection is not available)";
    }

    @Override // kotlin.jvm.internal.c
    public t.e getReflected() {
        t.b bVarCompute = compute();
        if (bVarCompute != this) {
            return (t.e) bVarCompute;
        }
        throw new o.a("Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath");
    }
}
