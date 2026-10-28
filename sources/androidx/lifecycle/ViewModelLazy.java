package androidx.lifecycle;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewModelLazy<VM extends ViewModel> implements g.c {
    private VM cached;
    private final p.a extrasProducer;
    private final p.a factoryProducer;
    private final p.a storeProducer;
    private final t.c viewModelClass;

    /* JADX INFO: renamed from: androidx.lifecycle.ViewModelLazy$1, reason: invalid class name */
    public static final class AnonymousClass1 extends l implements p.a {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(0);
        }

        @Override // p.a
        public final CreationExtras.Empty invoke() {
            return CreationExtras.Empty.INSTANCE;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewModelLazy(t.c viewModelClass, p.a storeProducer, p.a factoryProducer) {
        this(viewModelClass, storeProducer, factoryProducer, null, 8, null);
        k.g(viewModelClass, "viewModelClass");
        k.g(storeProducer, "storeProducer");
        k.g(factoryProducer, "factoryProducer");
    }

    @Override // g.c
    public VM getValue() {
        VM vm = this.cached;
        if (vm != null) {
            return vm;
        }
        ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStore) this.storeProducer.invoke(), (ViewModelProvider.Factory) this.factoryProducer.invoke(), (CreationExtras) this.extrasProducer.invoke());
        t.c cVar = this.viewModelClass;
        k.g(cVar, "<this>");
        Class clsA = ((kotlin.jvm.internal.d) cVar).a();
        k.e(clsA, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        VM vm2 = (VM) viewModelProvider.get(clsA);
        this.cached = vm2;
        return vm2;
    }

    public boolean isInitialized() {
        return this.cached != null;
    }

    public ViewModelLazy(t.c viewModelClass, p.a storeProducer, p.a factoryProducer, p.a extrasProducer) {
        k.g(viewModelClass, "viewModelClass");
        k.g(storeProducer, "storeProducer");
        k.g(factoryProducer, "factoryProducer");
        k.g(extrasProducer, "extrasProducer");
        this.viewModelClass = viewModelClass;
        this.storeProducer = storeProducer;
        this.factoryProducer = factoryProducer;
        this.extrasProducer = extrasProducer;
    }

    public /* synthetic */ ViewModelLazy(t.c cVar, p.a aVar, p.a aVar2, p.a aVar3, int i2, kotlin.jvm.internal.f fVar) {
        this(cVar, aVar, aVar2, (i2 & 8) != 0 ? AnonymousClass1.INSTANCE : aVar3);
    }
}
