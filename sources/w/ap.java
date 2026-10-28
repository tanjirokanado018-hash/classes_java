package w;

import androidx.activity.contextaware.ContextAwareKt$withContextAvailable$2$1;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ap extends AbstractC0034d {
    public final p.l b;

    public ap(ContextAwareKt$withContextAvailable$2$1 contextAwareKt$withContextAvailable$2$1) {
        this.b = contextAwareKt$withContextAvailable$2$1;
    }

    @Override // p.l
    public final Object invoke(Object obj) {
        this.b.invoke((Throwable) obj);
        return g.i.f423a;
    }

    public final String toString() {
        return "InvokeOnCancel[" + this.b.getClass().getSimpleName() + '@' + y.m(this) + ']';
    }
}
