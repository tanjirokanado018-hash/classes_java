package androidx.arch.core.executor;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Executor {
    public final /* synthetic */ int b;

    public /* synthetic */ a(int i2) {
        this.b = i2;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.b) {
            case 0:
                ArchTaskExecutor.lambda$static$0(runnable);
                break;
            case 1:
                ArchTaskExecutor.lambda$static$1(runnable);
                break;
            case 2:
                runnable.run();
                break;
            case 3:
                runnable.run();
                break;
            case 4:
                runnable.run();
                break;
            default:
                runnable.run();
                break;
        }
    }
}
