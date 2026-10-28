package androidx.core.content.res;

import com.google.android.material.sidesheet.SideSheetBehavior;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f30c;
    public final /* synthetic */ Object d;

    public /* synthetic */ b(Object obj, int i2, int i3) {
        this.b = i3;
        this.d = obj;
        this.f30c = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        int i3 = this.f30c;
        Object obj = this.d;
        switch (i2) {
            case 0:
                ((ResourcesCompat.FontCallback) obj).lambda$callbackFailAsync$1(i3);
                break;
            default:
                ((SideSheetBehavior) obj).lambda$setState$0(i3);
                break;
        }
    }
}
