package androidx.lifecycle;

import g.i;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class Transformations$switchMap$1$onChanged$1 extends l implements p.l {
    final /* synthetic */ MediatorLiveData<Y> $result;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Transformations$switchMap$1$onChanged$1(MediatorLiveData<Y> mediatorLiveData) {
        super(1);
        this.$result = mediatorLiveData;
    }

    @Override // p.l
    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        m15invoke(obj);
        return i.f423a;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m15invoke(Y y2) {
        this.$result.setValue(y2);
    }
}
