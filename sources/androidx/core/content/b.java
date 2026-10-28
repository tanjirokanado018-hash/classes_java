package androidx.core.content;

import androidx.core.util.Consumer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements Consumer {
    public final /* synthetic */ int b;

    public /* synthetic */ b(int i2) {
        this.b = i2;
    }

    @Override // androidx.core.util.Consumer
    public final void accept(Object obj) {
        String str = (String) obj;
        switch (this.b) {
            case 0:
                IntentSanitizer.lambda$sanitizeByThrowing$1(str);
                break;
            default:
                IntentSanitizer.lambda$sanitizeByFiltering$0(str);
                break;
        }
    }
}
