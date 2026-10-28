package i;

import kotlin.jvm.internal.l;
import p.p;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends l implements p {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final c f427c = new c(0);
    public static final c d = new c(1);
    public final /* synthetic */ int b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(int i2) {
        super(2);
        this.b = i2;
    }

    @Override // p.p
    public final Object invoke(Object obj, Object obj2) {
        d dVar;
        switch (this.b) {
            case 0:
                String acc = (String) obj;
                h element = (h) obj2;
                kotlin.jvm.internal.k.g(acc, "acc");
                kotlin.jvm.internal.k.g(element, "element");
                if (acc.length() == 0) {
                    return element.toString();
                }
                return acc + ", " + element;
            default:
                j acc2 = (j) obj;
                h element2 = (h) obj2;
                kotlin.jvm.internal.k.g(acc2, "acc");
                kotlin.jvm.internal.k.g(element2, "element");
                j jVarMinusKey = acc2.minusKey(element2.getKey());
                k kVar = k.b;
                if (jVarMinusKey == kVar) {
                    return element2;
                }
                f fVar = f.b;
                g gVar = (g) jVarMinusKey.get(fVar);
                if (gVar == null) {
                    dVar = new d(element2, jVarMinusKey);
                } else {
                    j jVarMinusKey2 = jVarMinusKey.minusKey(fVar);
                    if (jVarMinusKey2 == kVar) {
                        return new d(gVar, element2);
                    }
                    dVar = new d(gVar, new d(element2, jVarMinusKey2));
                }
                return dVar;
        }
    }
}
