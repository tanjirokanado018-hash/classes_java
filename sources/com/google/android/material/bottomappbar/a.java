package com.google.android.material.bottomappbar;

import android.view.View;
import com.google.android.material.internal.ViewUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ View f87c;

    public /* synthetic */ a(View view, int i2) {
        this.b = i2;
        this.f87c = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        View view = this.f87c;
        switch (i2) {
            case 0:
                view.requestLayout();
                break;
            default:
                ViewUtils.requestFocusAndShowKeyboard(view, false);
                break;
        }
    }
}
