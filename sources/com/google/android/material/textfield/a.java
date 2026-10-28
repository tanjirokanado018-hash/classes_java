package com.google.android.material.textfield;

import android.view.View;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f138a;
    public final /* synthetic */ EndIconDelegate b;

    public /* synthetic */ a(EndIconDelegate endIconDelegate, int i2) {
        this.f138a = i2;
        this.b = endIconDelegate;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f138a;
        EndIconDelegate endIconDelegate = this.b;
        switch (i2) {
            case 0:
                ((ClearTextEndIconDelegate) endIconDelegate).lambda$new$0(view);
                break;
            case 1:
                ((DropdownMenuEndIconDelegate) endIconDelegate).lambda$new$0(view);
                break;
            default:
                ((PasswordToggleEndIconDelegate) endIconDelegate).lambda$new$0(view);
                break;
        }
    }
}
