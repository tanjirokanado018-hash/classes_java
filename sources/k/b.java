package k;

import i.j;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class b implements i.e {
    public static final b b = new b();

    @Override // i.e
    public final j getContext() {
        throw new IllegalStateException("This continuation is already complete".toString());
    }

    @Override // i.e
    public final void resumeWith(Object obj) {
        throw new IllegalStateException("This continuation is already complete".toString());
    }

    public final String toString() {
        return "This continuation is already complete";
    }
}
