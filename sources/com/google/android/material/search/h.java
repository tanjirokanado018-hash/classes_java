package com.google.android.material.search;

import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.internal.ViewUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class h implements ViewUtils.OnApplyWindowInsetsListener, OnApplyWindowInsetsListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f123a;

    public /* synthetic */ h(SearchView searchView) {
        this.f123a = searchView;
    }

    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return this.f123a.lambda$setUpStatusBarSpacerInsetListener$5(view, windowInsetsCompat);
    }

    @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        return this.f123a.lambda$setUpToolbarInsetListener$4(view, windowInsetsCompat, relativePadding);
    }
}
