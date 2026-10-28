package com.google.android.material.textfield;

import android.view.View;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements View.OnFocusChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f139a;
    public final /* synthetic */ EndIconDelegate b;

    public /* synthetic */ b(EndIconDelegate endIconDelegate, int i2) {
        this.f139a = i2;
        this.b = endIconDelegate;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z2) {
        int i2 = this.f139a;
        EndIconDelegate endIconDelegate = this.b;
        switch (i2) {
            case 0:
                ((ClearTextEndIconDelegate) endIconDelegate).lambda$new$1(view, z2);
                break;
            default:
                ((DropdownMenuEndIconDelegate) endIconDelegate).lambda$new$1(view, z2);
                break;
        }
    }
}
