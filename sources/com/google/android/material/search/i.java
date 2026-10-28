package com.google.android.material.search;

import android.view.View;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class i implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f124a;
    public final /* synthetic */ SearchView b;

    public /* synthetic */ i(SearchView searchView, int i2) {
        this.f124a = i2;
        this.b = searchView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f124a;
        SearchView searchView = this.b;
        switch (i2) {
            case 0:
                searchView.lambda$setupWithSearchBar$7(view);
                break;
            case 1:
                searchView.lambda$setUpBackButton$1(view);
                break;
            default:
                searchView.lambda$setUpClearButton$2(view);
                break;
        }
    }
}
