package com.google.android.material.timepicker;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f146c;

    public /* synthetic */ b(int i2, Object obj) {
        this.b = i2;
        this.f146c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f146c;
        switch (i2) {
            case 0:
                ((RadialViewGroup) obj).updateLayoutParams();
                break;
            default:
                ((MaterialTimePicker) obj).lambda$onViewCreated$0();
                break;
        }
    }
}
