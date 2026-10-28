package androidx.lifecycle;

import kotlin.jvm.internal.k;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class Transformations$sam$androidx_lifecycle_Observer$0 implements Observer, kotlin.jvm.internal.g {
    private final /* synthetic */ l function;

    public Transformations$sam$androidx_lifecycle_Observer$0(l function) {
        k.g(function, "function");
        this.function = function;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof Observer) && (obj instanceof kotlin.jvm.internal.g)) {
            return k.a(getFunctionDelegate(), ((kotlin.jvm.internal.g) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.g
    public final g.a getFunctionDelegate() {
        return this.function;
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // androidx.lifecycle.Observer
    public final /* synthetic */ void onChanged(Object obj) {
        this.function.invoke(obj);
    }
}
