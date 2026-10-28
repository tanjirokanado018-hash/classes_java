package f;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class an extends ViewOutlineProvider {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f340a;

    public an(int i2) {
        this.f340a = i2;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.f340a);
    }
}
