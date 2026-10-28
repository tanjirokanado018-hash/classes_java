package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Consumer f33c;
    public final /* synthetic */ Location d;

    public /* synthetic */ f(Consumer consumer, Location location, int i2) {
        this.b = i2;
        this.f33c = consumer;
        this.d = location;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Location location = this.d;
        Consumer consumer = this.f33c;
        switch (i2) {
            case 0:
                consumer.accept(location);
                break;
            default:
                consumer.accept(location);
                break;
        }
    }
}
