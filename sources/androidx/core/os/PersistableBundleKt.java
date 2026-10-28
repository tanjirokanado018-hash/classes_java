package androidx.core.os;

import android.os.PersistableBundle;
import androidx.annotation.RequiresApi;
import g.d;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PersistableBundleKt {
    @RequiresApi(21)
    public static final PersistableBundle persistableBundleOf() {
        return PersistableBundleApi21ImplKt.createPersistableBundle(0);
    }

    @RequiresApi(21)
    public static final PersistableBundle toPersistableBundle(Map<String, ? extends Object> map) {
        PersistableBundle persistableBundleCreatePersistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(map.size());
        for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
            PersistableBundleApi21ImplKt.putValue(persistableBundleCreatePersistableBundle, entry.getKey(), entry.getValue());
        }
        return persistableBundleCreatePersistableBundle;
    }

    @RequiresApi(21)
    public static final PersistableBundle persistableBundleOf(d... dVarArr) {
        PersistableBundle persistableBundleCreatePersistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(dVarArr.length);
        for (d dVar : dVarArr) {
            PersistableBundleApi21ImplKt.putValue(persistableBundleCreatePersistableBundle, (String) dVar.b, dVar.f420c);
        }
        return persistableBundleCreatePersistableBundle;
    }
}
