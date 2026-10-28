package u;

import java.util.Iterator;
import p.p;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class k implements g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f513a;
    public final /* synthetic */ Object b;

    public /* synthetic */ k(int i2, Object obj) {
        this.f513a = i2;
        this.b = obj;
    }

    @Override // u.g
    public final Iterator iterator() {
        int i2 = this.f513a;
        Object obj = this.b;
        switch (i2) {
            case 0:
                p block = (p) obj;
                kotlin.jvm.internal.k.g(block, "block");
                h hVar = new h();
                hVar.f512e = y.e(hVar, hVar, block);
                return hVar;
            default:
                return (Iterator) obj;
        }
    }
}
