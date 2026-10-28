package androidx.core.view.contentcapture;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.a;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewStructureCompat;
import androidx.core.view.autofill.AutofillIdCompat;
import androidx.core.view.o;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ContentCaptureSessionCompat {
    private static final String KEY_VIEW_TREE_APPEARED = "TREAT_AS_VIEW_TREE_APPEARED";
    private static final String KEY_VIEW_TREE_APPEARING = "TREAT_AS_VIEW_TREE_APPEARING";
    private final View mView;
    private final Object mWrappedObj;

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static Bundle getExtras(ViewStructure viewStructure) {
            return viewStructure.getExtras();
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static AutofillId newAutofillId(ContentCaptureSession contentCaptureSession, AutofillId autofillId, long j2) {
            return contentCaptureSession.newAutofillId(autofillId, j2);
        }

        @DoNotInline
        public static ViewStructure newViewStructure(ContentCaptureSession contentCaptureSession, View view) {
            return contentCaptureSession.newViewStructure(view);
        }

        @DoNotInline
        public static ViewStructure newVirtualViewStructure(ContentCaptureSession contentCaptureSession, AutofillId autofillId, long j2) {
            return contentCaptureSession.newVirtualViewStructure(autofillId, j2);
        }

        @DoNotInline
        public static void notifyViewAppeared(ContentCaptureSession contentCaptureSession, ViewStructure viewStructure) {
            contentCaptureSession.notifyViewAppeared(viewStructure);
        }

        @DoNotInline
        public static void notifyViewTextChanged(ContentCaptureSession contentCaptureSession, AutofillId autofillId, CharSequence charSequence) {
            contentCaptureSession.notifyViewTextChanged(autofillId, charSequence);
        }

        @DoNotInline
        public static void notifyViewsDisappeared(ContentCaptureSession contentCaptureSession, AutofillId autofillId, long[] jArr) {
            contentCaptureSession.notifyViewsDisappeared(autofillId, jArr);
        }
    }

    @RequiresApi(34)
    public static class Api34Impl {
        private Api34Impl() {
        }

        @DoNotInline
        public static void notifyViewsAppeared(ContentCaptureSession contentCaptureSession, List<ViewStructure> list) {
            contentCaptureSession.notifyViewsAppeared(list);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    private ContentCaptureSessionCompat(@NonNull ContentCaptureSession contentCaptureSession, @NonNull View view) {
        this.mWrappedObj = contentCaptureSession;
        this.mView = view;
    }

    @NonNull
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static ContentCaptureSessionCompat toContentCaptureSessionCompat(@NonNull ContentCaptureSession contentCaptureSession, @NonNull View view) {
        return new ContentCaptureSessionCompat(contentCaptureSession, view);
    }

    @Nullable
    public AutofillId newAutofillId(long j2) {
        if (Build.VERSION.SDK_INT < 29) {
            return null;
        }
        ContentCaptureSession contentCaptureSessionL = o.l(this.mWrappedObj);
        AutofillIdCompat autofillId = ViewCompat.getAutofillId(this.mView);
        Objects.requireNonNull(autofillId);
        return Api29Impl.newAutofillId(contentCaptureSessionL, autofillId.toAutofillId(), j2);
    }

    @Nullable
    public ViewStructureCompat newVirtualViewStructure(@NonNull AutofillId autofillId, long j2) {
        if (Build.VERSION.SDK_INT >= 29) {
            return ViewStructureCompat.toViewStructureCompat(Api29Impl.newVirtualViewStructure(o.l(this.mWrappedObj), autofillId, j2));
        }
        return null;
    }

    public void notifyViewTextChanged(@NonNull AutofillId autofillId, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.notifyViewTextChanged(o.l(this.mWrappedObj), autofillId, charSequence);
        }
    }

    public void notifyViewsAppeared(@NonNull List<ViewStructure> list) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 34) {
            Api34Impl.notifyViewsAppeared(o.l(this.mWrappedObj), list);
            return;
        }
        if (i2 >= 29) {
            ViewStructure viewStructureNewViewStructure = Api29Impl.newViewStructure(o.l(this.mWrappedObj), this.mView);
            Api23Impl.getExtras(viewStructureNewViewStructure).putBoolean(KEY_VIEW_TREE_APPEARING, true);
            Api29Impl.notifyViewAppeared(o.l(this.mWrappedObj), viewStructureNewViewStructure);
            for (int i3 = 0; i3 < list.size(); i3++) {
                Api29Impl.notifyViewAppeared(o.l(this.mWrappedObj), a.g(list.get(i3)));
            }
            ViewStructure viewStructureNewViewStructure2 = Api29Impl.newViewStructure(o.l(this.mWrappedObj), this.mView);
            Api23Impl.getExtras(viewStructureNewViewStructure2).putBoolean(KEY_VIEW_TREE_APPEARED, true);
            Api29Impl.notifyViewAppeared(o.l(this.mWrappedObj), viewStructureNewViewStructure2);
        }
    }

    public void notifyViewsDisappeared(@NonNull long[] jArr) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 34) {
            ContentCaptureSession contentCaptureSessionL = o.l(this.mWrappedObj);
            AutofillIdCompat autofillId = ViewCompat.getAutofillId(this.mView);
            Objects.requireNonNull(autofillId);
            Api29Impl.notifyViewsDisappeared(contentCaptureSessionL, autofillId.toAutofillId(), jArr);
            return;
        }
        if (i2 >= 29) {
            ViewStructure viewStructureNewViewStructure = Api29Impl.newViewStructure(o.l(this.mWrappedObj), this.mView);
            Api23Impl.getExtras(viewStructureNewViewStructure).putBoolean(KEY_VIEW_TREE_APPEARING, true);
            Api29Impl.notifyViewAppeared(o.l(this.mWrappedObj), viewStructureNewViewStructure);
            ContentCaptureSession contentCaptureSessionL2 = o.l(this.mWrappedObj);
            AutofillIdCompat autofillId2 = ViewCompat.getAutofillId(this.mView);
            Objects.requireNonNull(autofillId2);
            Api29Impl.notifyViewsDisappeared(contentCaptureSessionL2, autofillId2.toAutofillId(), jArr);
            ViewStructure viewStructureNewViewStructure2 = Api29Impl.newViewStructure(o.l(this.mWrappedObj), this.mView);
            Api23Impl.getExtras(viewStructureNewViewStructure2).putBoolean(KEY_VIEW_TREE_APPEARED, true);
            Api29Impl.notifyViewAppeared(o.l(this.mWrappedObj), viewStructureNewViewStructure2);
        }
    }

    @NonNull
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public ContentCaptureSession toContentCaptureSession() {
        return o.l(this.mWrappedObj);
    }
}
