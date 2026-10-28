package androidx.activity;

import android.content.res.Resources;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SystemBarStyle$Companion$auto$1 extends l implements p.l {
    public static final SystemBarStyle$Companion$auto$1 INSTANCE = new SystemBarStyle$Companion$auto$1();

    public SystemBarStyle$Companion$auto$1() {
        super(1);
    }

    @Override // p.l
    public final Boolean invoke(Resources resources) {
        k.g(resources, "resources");
        return Boolean.valueOf((resources.getConfiguration().uiMode & 48) == 32);
    }
}
