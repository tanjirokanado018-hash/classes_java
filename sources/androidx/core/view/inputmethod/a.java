package androidx.core.view.inputmethod;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.carousel.MaskableFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.zenoatom.app.CityRunActivity;
import f.RunnableC0028h;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements InputConnectionCompat.OnCommitContentListener, AccessibilityViewCommand, CanvasCompat.CanvasOperation, SwipeRefreshLayout.OnRefreshListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f55a;
    public final /* synthetic */ KeyEvent.Callback b;

    public /* synthetic */ a(KeyEvent.Callback callback, int i2) {
        this.f55a = i2;
        this.b = callback;
    }

    @Override // androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener
    public final boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i2, Bundle bundle) {
        return InputConnectionCompat.lambda$createOnCommitContentListenerUsingPerformReceiveContent$0((View) this.b, inputContentInfoCompat, i2, bundle);
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public final void onRefresh() {
        CityRunActivity cityRunActivity = (CityRunActivity) this.b;
        int i2 = CityRunActivity.f151p;
        cityRunActivity.getClass();
        cityRunActivity.p(new RunnableC0028h(cityRunActivity, 1));
    }

    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
    public final boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
        return ((BottomSheetDragHandleView) this.b).lambda$onBottomSheetStateChanged$0(view, commandArguments);
    }

    @Override // com.google.android.material.canvas.CanvasCompat.CanvasOperation
    public final void run(Canvas canvas) {
        int i2 = this.f55a;
        KeyEvent.Callback callback = this.b;
        switch (i2) {
            case 2:
                ((MaskableFrameLayout) callback).lambda$dispatchDraw$1(canvas);
                break;
            default:
                ((NavigationView) callback).lambda$dispatchDraw$0(canvas);
                break;
        }
    }
}
