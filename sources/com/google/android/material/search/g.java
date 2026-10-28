package com.google.android.material.search;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ SearchView f122c;

    public /* synthetic */ g(SearchView searchView, int i2) {
        this.b = i2;
        this.f122c = searchView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        SearchView searchView = this.f122c;
        switch (i2) {
            case 0:
                searchView.lambda$requestFocusAndShowKeyboard$8();
                break;
            case 1:
                searchView.show();
                break;
            case 2:
                searchView.lambda$clearFocusAndHideKeyboard$9();
                break;
            default:
                searchView.requestFocusAndShowKeyboardIfNeeded();
                break;
        }
    }
}
