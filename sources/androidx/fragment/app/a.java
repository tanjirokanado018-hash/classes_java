package androidx.fragment.app;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements SavedStateRegistry.SavedStateProvider {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f64a;
    public final /* synthetic */ Object b;

    public /* synthetic */ a(int i2, Object obj) {
        this.f64a = i2;
        this.b = obj;
    }

    @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
    public final Bundle saveState() {
        int i2 = this.f64a;
        Object obj = this.b;
        switch (i2) {
            case 0:
                return ((FragmentActivity) obj).lambda$init$0();
            default:
                return ((FragmentManager) obj).lambda$attachController$4();
        }
    }
}
