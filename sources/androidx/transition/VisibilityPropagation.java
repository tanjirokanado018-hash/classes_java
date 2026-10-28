package androidx.transition;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class VisibilityPropagation extends TransitionPropagation {
    private static final String PROPNAME_VISIBILITY = "android:visibilityPropagation:visibility";
    private static final String PROPNAME_VIEW_CENTER = "android:visibilityPropagation:center";
    private static final String[] VISIBILITY_PROPAGATION_VALUES = {PROPNAME_VISIBILITY, PROPNAME_VIEW_CENTER};

    private static int getViewCoordinate(@Nullable TransitionValues transitionValues, int i2) {
        int[] iArr;
        if (transitionValues == null || (iArr = (int[]) transitionValues.values.get(PROPNAME_VIEW_CENTER)) == null) {
            return -1;
        }
        return iArr[i2];
    }

    @Override // androidx.transition.TransitionPropagation
    public void captureValues(@NonNull TransitionValues transitionValues) {
        View view = transitionValues.view;
        Integer numValueOf = (Integer) transitionValues.values.get("android:visibility:visibility");
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(view.getVisibility());
        }
        transitionValues.values.put(PROPNAME_VISIBILITY, numValueOf);
        int[] iArr = {iRound, 0};
        view.getLocationOnScreen(iArr);
        int iRound = Math.round(view.getTranslationX()) + iArr[0];
        iArr[0] = (view.getWidth() / 2) + iRound;
        int iRound2 = Math.round(view.getTranslationY()) + iArr[1];
        iArr[1] = iRound2;
        iArr[1] = (view.getHeight() / 2) + iRound2;
        transitionValues.values.put(PROPNAME_VIEW_CENTER, iArr);
    }

    @Override // androidx.transition.TransitionPropagation
    @Nullable
    public String[] getPropagationProperties() {
        return VISIBILITY_PROPAGATION_VALUES;
    }

    public int getViewVisibility(@Nullable TransitionValues transitionValues) {
        Integer num;
        if (transitionValues == null || (num = (Integer) transitionValues.values.get(PROPNAME_VISIBILITY)) == null) {
            return 8;
        }
        return num.intValue();
    }

    public int getViewX(@Nullable TransitionValues transitionValues) {
        return getViewCoordinate(transitionValues, 0);
    }

    public int getViewY(@Nullable TransitionValues transitionValues) {
        return getViewCoordinate(transitionValues, 1);
    }
}
