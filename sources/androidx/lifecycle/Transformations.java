package androidx.lifecycle;

import androidx.annotation.CheckResult;
import androidx.annotation.MainThread;
import androidx.arch.core.util.Function;
import g.i;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.o;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class Transformations {

    /* JADX INFO: renamed from: androidx.lifecycle.Transformations$distinctUntilChanged$1, reason: invalid class name */
    public static final class AnonymousClass1 extends l implements p.l {
        final /* synthetic */ o $firstTime;
        final /* synthetic */ MediatorLiveData<X> $outputLiveData;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MediatorLiveData<X> mediatorLiveData, o oVar) {
            super(1);
            this.$outputLiveData = mediatorLiveData;
            this.$firstTime = oVar;
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            m12invoke(obj);
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
        public final void m12invoke(X x2) {
            Object value = this.$outputLiveData.getValue();
            if (this.$firstTime.b || ((value == null && x2 != 0) || !(value == null || k.a(value, x2)))) {
                this.$firstTime.b = false;
                this.$outputLiveData.setValue(x2);
            }
        }
    }

    /* JADX INFO: renamed from: androidx.lifecycle.Transformations$map$1, reason: invalid class name and case insensitive filesystem */
    public static final class C00181 extends l implements p.l {
        final /* synthetic */ MediatorLiveData<Y> $result;
        final /* synthetic */ p.l $transform;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C00181(MediatorLiveData<Y> mediatorLiveData, p.l lVar) {
            super(1);
            this.$result = mediatorLiveData;
            this.$transform = lVar;
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            m13invoke(obj);
            return i.f423a;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m13invoke(X x2) {
            this.$result.setValue((Y) this.$transform.invoke(x2));
        }
    }

    /* JADX INFO: renamed from: androidx.lifecycle.Transformations$map$2, reason: invalid class name */
    public static final class AnonymousClass2 extends l implements p.l {
        final /* synthetic */ Function $mapFunction;
        final /* synthetic */ MediatorLiveData $result;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MediatorLiveData mediatorLiveData, Function function) {
            super(1);
            this.$result = mediatorLiveData;
            this.$mapFunction = function;
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            m14invoke(obj);
            return i.f423a;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m14invoke(Object obj) {
            this.$result.setValue(this.$mapFunction.apply(obj));
        }
    }

    @CheckResult
    @MainThread
    public static final <X> LiveData<X> distinctUntilChanged(LiveData<X> liveData) {
        k.g(liveData, "<this>");
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        o oVar = new o();
        oVar.b = true;
        if (liveData.isInitialized()) {
            mediatorLiveData.setValue(liveData.getValue());
            oVar.b = false;
        }
        mediatorLiveData.addSource(liveData, new Transformations$sam$androidx_lifecycle_Observer$0(new AnonymousClass1(mediatorLiveData, oVar)));
        return mediatorLiveData;
    }

    @CheckResult
    @MainThread
    public static final /* synthetic */ LiveData map(LiveData liveData, Function mapFunction) {
        k.g(liveData, "<this>");
        k.g(mapFunction, "mapFunction");
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Transformations$sam$androidx_lifecycle_Observer$0(new AnonymousClass2(mediatorLiveData, mapFunction)));
        return mediatorLiveData;
    }

    @CheckResult
    @MainThread
    public static final /* synthetic */ LiveData switchMap(LiveData liveData, final Function switchMapFunction) {
        k.g(liveData, "<this>");
        k.g(switchMapFunction, "switchMapFunction");
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer() { // from class: androidx.lifecycle.Transformations.switchMap.2
            private LiveData liveData;

            public final LiveData getLiveData() {
                return this.liveData;
            }

            @Override // androidx.lifecycle.Observer
            public void onChanged(Object obj) {
                LiveData liveData2 = (LiveData) switchMapFunction.apply(obj);
                LiveData liveData3 = this.liveData;
                if (liveData3 == liveData2) {
                    return;
                }
                if (liveData3 != null) {
                    MediatorLiveData mediatorLiveData2 = mediatorLiveData;
                    k.d(liveData3);
                    mediatorLiveData2.removeSource(liveData3);
                }
                this.liveData = liveData2;
                if (liveData2 != null) {
                    MediatorLiveData mediatorLiveData3 = mediatorLiveData;
                    k.d(liveData2);
                    mediatorLiveData3.addSource(liveData2, new Transformations$sam$androidx_lifecycle_Observer$0(new Transformations$switchMap$2$onChanged$1(mediatorLiveData)));
                }
            }

            public final void setLiveData(LiveData liveData2) {
                this.liveData = liveData2;
            }
        });
        return mediatorLiveData;
    }

    @CheckResult
    @MainThread
    public static final <X, Y> LiveData<Y> map(LiveData<X> liveData, p.l transform) {
        k.g(liveData, "<this>");
        k.g(transform, "transform");
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Transformations$sam$androidx_lifecycle_Observer$0(new C00181(mediatorLiveData, transform)));
        return mediatorLiveData;
    }

    @CheckResult
    @MainThread
    public static final <X, Y> LiveData<Y> switchMap(LiveData<X> liveData, final p.l transform) {
        k.g(liveData, "<this>");
        k.g(transform, "transform");
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>() { // from class: androidx.lifecycle.Transformations.switchMap.1
            private LiveData<Y> liveData;

            public final LiveData<Y> getLiveData() {
                return this.liveData;
            }

            /* JADX WARN: Multi-variable type inference failed */
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
            @Override // androidx.lifecycle.Observer
            public void onChanged(X x2) {
                LiveData<Y> liveData2 = (LiveData) transform.invoke(x2);
                Object obj = this.liveData;
                if (obj == liveData2) {
                    return;
                }
                if (obj != null) {
                    MediatorLiveData<Y> mediatorLiveData2 = mediatorLiveData;
                    k.d(obj);
                    mediatorLiveData2.removeSource(obj);
                }
                this.liveData = liveData2;
                if (liveData2 != 0) {
                    MediatorLiveData<Y> mediatorLiveData3 = mediatorLiveData;
                    k.d(liveData2);
                    mediatorLiveData3.addSource(liveData2, new Transformations$sam$androidx_lifecycle_Observer$0(new Transformations$switchMap$1$onChanged$1(mediatorLiveData)));
                }
            }

            public final void setLiveData(LiveData<Y> liveData2) {
                this.liveData = liveData2;
            }
        });
        return mediatorLiveData;
    }
}
