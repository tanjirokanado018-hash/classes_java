package androidx.lifecycle;

import g.i;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class Transformations$switchMap$2$onChanged$1 extends l implements p.l {
    final /* synthetic */ MediatorLiveData $result;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Transformations$switchMap$2$onChanged$1(MediatorLiveData mediatorLiveData) {
        super(1);
        this.$result = mediatorLiveData;
    }

    @Override // p.l
    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        m16invoke(obj);
        return i.f423a;
    }

    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m16invoke(Object obj) {
        this.$result.setValue(obj);
    }
}
