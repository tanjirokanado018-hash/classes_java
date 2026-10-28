package x;

import android.os.Looper;
import java.util.List;
import kotlinx.coroutines.internal.k;
import w.az;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a implements k {
    @Override // kotlinx.coroutines.internal.k
    public int a() {
        return 1073741823;
    }

    @Override // kotlinx.coroutines.internal.k
    public String b() {
        return "For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used";
    }

    @Override // kotlinx.coroutines.internal.k
    public az c(List list) {
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            return new c(d.a(mainLooper));
        }
        throw new IllegalStateException("The main looper is not available");
    }
}
