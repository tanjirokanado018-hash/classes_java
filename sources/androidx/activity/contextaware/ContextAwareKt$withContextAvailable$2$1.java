package androidx.activity.contextaware;

import g.i;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ContextAwareKt$withContextAvailable$2$1 extends l implements p.l {
    final /* synthetic */ ContextAwareKt$withContextAvailable$2$listener$1 $listener;
    final /* synthetic */ ContextAware $this_withContextAvailable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextAwareKt$withContextAvailable$2$1(ContextAware contextAware, ContextAwareKt$withContextAvailable$2$listener$1 contextAwareKt$withContextAvailable$2$listener$1) {
        super(1);
        this.$this_withContextAvailable = contextAware;
        this.$listener = contextAwareKt$withContextAvailable$2$listener$1;
    }

    @Override // p.l
    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return i.f423a;
    }

    public final void invoke(Throwable th) {
        this.$this_withContextAvailable.removeOnContextAvailableListener(this.$listener);
    }
}
