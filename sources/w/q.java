package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class q extends kotlin.jvm.internal.l implements p.l {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final q f550c = new q(0);
    public final /* synthetic */ int b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ q(int i2) {
        super(1);
        this.b = i2;
    }

    @Override // p.l
    public final Object invoke(Object obj) {
        switch (this.b) {
            case 0:
                i.h hVar = (i.h) obj;
                if (hVar instanceof s) {
                    return (s) hVar;
                }
                return null;
            default:
                i.h hVar2 = (i.h) obj;
                if (hVar2 instanceof al) {
                    return (al) hVar2;
                }
                return null;
        }
    }
}
