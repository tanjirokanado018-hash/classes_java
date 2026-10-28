package f;

import android.content.DialogInterface;
import android.view.View;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ap implements DialogInterface.OnShowListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BottomSheetDialog f342a;

    public ap(BottomSheetDialog bottomSheetDialog) {
        this.f342a = bottomSheetDialog;
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        View viewFindViewById = this.f342a.findViewById(R.id.design_bottom_sheet);
        if (viewFindViewById != null) {
            viewFindViewById.setBackgroundColor(0);
            BottomSheetBehavior bottomSheetBehaviorFrom = BottomSheetBehavior.from(viewFindViewById);
            bottomSheetBehaviorFrom.setState(3);
            bottomSheetBehaviorFrom.setSkipCollapsed(true);
        }
    }
}
