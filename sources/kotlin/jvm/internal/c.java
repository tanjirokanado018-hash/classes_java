package kotlin.jvm.internal;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class c implements t.b, Serializable {
    public static final Object NO_RECEIVER = b.b;
    private final boolean isTopLevel;
    private final String name;
    private final Class owner;
    protected final Object receiver;
    private transient t.b reflected;
    private final String signature;

    public c(Object obj, Class cls, String str, String str2, boolean z2) {
        this.receiver = obj;
        this.owner = cls;
        this.name = str;
        this.signature = str2;
        this.isTopLevel = z2;
    }

    @Override // t.b
    public Object call(Object... objArr) {
        return getReflected().call(objArr);
    }

    @Override // t.b
    public Object callBy(Map map) {
        return getReflected().callBy(map);
    }

    public t.b compute() {
        t.b bVar = this.reflected;
        if (bVar != null) {
            return bVar;
        }
        t.b bVarComputeReflected = computeReflected();
        this.reflected = bVarComputeReflected;
        return bVarComputeReflected;
    }

    public abstract t.b computeReflected();

    @Override // t.a
    public List<Annotation> getAnnotations() {
        return getReflected().getAnnotations();
    }

    public Object getBoundReceiver() {
        return this.receiver;
    }

    public String getName() {
        return this.name;
    }

    public t.d getOwner() {
        t.d eVar;
        Class cls = this.owner;
        if (cls == null) {
            return null;
        }
        if (this.isTopLevel) {
            p.f437a.getClass();
            eVar = new m(cls);
        } else {
            p.f437a.getClass();
            eVar = new e(cls);
        }
        return eVar;
    }

    @Override // t.b
    public List<Object> getParameters() {
        return getReflected().getParameters();
    }

    public abstract t.b getReflected();

    @Override // t.b
    public t.g getReturnType() {
        getReflected().getReturnType();
        return null;
    }

    public String getSignature() {
        return this.signature;
    }

    @Override // t.b
    public List<Object> getTypeParameters() {
        return getReflected().getTypeParameters();
    }

    @Override // t.b
    public t.h getVisibility() {
        return getReflected().getVisibility();
    }

    @Override // t.b
    public boolean isAbstract() {
        return getReflected().isAbstract();
    }

    @Override // t.b
    public boolean isFinal() {
        return getReflected().isFinal();
    }

    @Override // t.b
    public boolean isOpen() {
        return getReflected().isOpen();
    }
}
