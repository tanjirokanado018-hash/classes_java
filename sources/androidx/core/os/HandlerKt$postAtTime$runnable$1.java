package androidx.core.os;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class HandlerKt$postAtTime$runnable$1 implements Runnable {
    final /* synthetic */ p.a $action;

    public HandlerKt$postAtTime$runnable$1(p.a aVar) {
        this.$action = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$action.invoke();
    }
}
