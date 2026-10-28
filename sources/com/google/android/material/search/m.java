package com.google.android.material.search;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class m implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f128c;

    public /* synthetic */ m(int i2, Object obj) {
        this.b = i2;
        this.f128c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f128c;
        switch (i2) {
            case 0:
                ((SearchViewAnimationHelper) obj).lambda$startShowAnimationExpand$0();
                break;
            case 1:
                ((SearchViewAnimationHelper) obj).lambda$startShowAnimationTranslate$1();
                break;
            default:
                ((SearchBar) obj).lambda$startOnLoadAnimation$1();
                break;
        }
    }
}
