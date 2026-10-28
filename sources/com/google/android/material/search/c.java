package com.google.android.material.search;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class c implements SearchBarAnimationHelper.OnLoadAnimationInvocation {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f115a;

    public /* synthetic */ c(int i2) {
        this.f115a = i2;
    }

    @Override // com.google.android.material.search.SearchBarAnimationHelper.OnLoadAnimationInvocation
    public final void invoke(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback) {
        switch (this.f115a) {
            case 0:
                onLoadAnimationCallback.onAnimationStart();
                break;
            default:
                onLoadAnimationCallback.onAnimationEnd();
                break;
        }
    }
}
