package com.google.android.material.textfield;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f141c;

    public /* synthetic */ d(int i2, Object obj) {
        this.b = i2;
        this.f141c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f141c;
        switch (i2) {
            case 0:
                ((ClearTextEndIconDelegate) obj).lambda$tearDown$2();
                break;
            case 1:
                ((DropdownMenuEndIconDelegate) obj).lambda$afterEditTextChanged$3();
                break;
            default:
                ((TextInputLayout) obj).lambda$onGlobalLayout$1();
                break;
        }
    }
}
