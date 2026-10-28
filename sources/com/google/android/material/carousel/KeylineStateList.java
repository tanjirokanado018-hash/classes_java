package com.google.android.material.carousel;

import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class KeylineStateList {
    private static final int NO_INDEX = -1;
    private final KeylineState defaultState;
    private final float endShiftRange;
    private final List<KeylineState> endStateSteps;
    private final float[] endStateStepsInterpolationPoints;
    private final float startShiftRange;
    private final List<KeylineState> startStateSteps;
    private final float[] startStateStepsInterpolationPoints;

    private KeylineStateList(@NonNull KeylineState keylineState, List<KeylineState> list, List<KeylineState> list2) {
        this.defaultState = keylineState;
        this.startStateSteps = Collections.unmodifiableList(list);
        this.endStateSteps = Collections.unmodifiableList(list2);
        float f2 = list.get(list.size() - 1).getFirstKeyline().loc - keylineState.getFirstKeyline().loc;
        this.startShiftRange = f2;
        float f3 = keylineState.getLastKeyline().loc - list2.get(list2.size() - 1).getLastKeyline().loc;
        this.endShiftRange = f3;
        this.startStateStepsInterpolationPoints = getStateStepInterpolationPoints(f2, list, true);
        this.endStateStepsInterpolationPoints = getStateStepInterpolationPoints(f3, list2, false);
    }

    private KeylineState closestStateStepFromInterpolation(List<KeylineState> list, float f2, float[] fArr) {
        float[] stateStepsRange = getStateStepsRange(list, f2, fArr);
        return list.get((int) (stateStepsRange[0] >= 0.5f ? stateStepsRange[2] : stateStepsRange[1]));
    }

    private static int findFirstIndexAfterLastFocalKeylineWithMask(KeylineState keylineState, float f2) {
        for (int lastFocalKeylineIndex = keylineState.getLastFocalKeylineIndex(); lastFocalKeylineIndex < keylineState.getKeylines().size(); lastFocalKeylineIndex++) {
            if (f2 == keylineState.getKeylines().get(lastFocalKeylineIndex).mask) {
                return lastFocalKeylineIndex;
            }
        }
        return keylineState.getKeylines().size() - 1;
    }

    private static int findFirstNonAnchorKeylineIndex(KeylineState keylineState) {
        for (int i2 = 0; i2 < keylineState.getKeylines().size(); i2++) {
            if (!keylineState.getKeylines().get(i2).isAnchor) {
                return i2;
            }
        }
        return -1;
    }

    private static int findLastIndexBeforeFirstFocalKeylineWithMask(KeylineState keylineState, float f2) {
        for (int firstFocalKeylineIndex = keylineState.getFirstFocalKeylineIndex() - 1; firstFocalKeylineIndex >= 0; firstFocalKeylineIndex--) {
            if (f2 == keylineState.getKeylines().get(firstFocalKeylineIndex).mask) {
                return firstFocalKeylineIndex;
            }
        }
        return 0;
    }

    private static int findLastNonAnchorKeylineIndex(KeylineState keylineState) {
        for (int size = keylineState.getKeylines().size() - 1; size >= 0; size--) {
            if (!keylineState.getKeylines().get(size).isAnchor) {
                return size;
            }
        }
        return -1;
    }

    public static KeylineStateList from(Carousel carousel, KeylineState keylineState, float f2, float f3, float f4) {
        return new KeylineStateList(keylineState, getStateStepsStart(carousel, keylineState, f2, f3), getStateStepsEnd(carousel, keylineState, f2, f4));
    }

    private static float[] getStateStepInterpolationPoints(float f2, List<KeylineState> list, boolean z2) {
        int size = list.size();
        float[] fArr = new float[size];
        int i2 = 1;
        while (i2 < size) {
            int i3 = i2 - 1;
            KeylineState keylineState = list.get(i3);
            KeylineState keylineState2 = list.get(i2);
            fArr[i2] = i2 == size + (-1) ? 1.0f : fArr[i3] + ((z2 ? keylineState2.getFirstKeyline().loc - keylineState.getFirstKeyline().loc : keylineState.getLastKeyline().loc - keylineState2.getLastKeyline().loc) / f2);
            i2++;
        }
        return fArr;
    }

    private static List<KeylineState> getStateStepsEnd(Carousel carousel, KeylineState keylineState, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(keylineState);
        int iFindLastNonAnchorKeylineIndex = findLastNonAnchorKeylineIndex(keylineState);
        float containerWidth = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        if (isLastFocalItemVisibleAtRightOfContainer(carousel, keylineState) || iFindLastNonAnchorKeylineIndex == -1) {
            if (f3 > 0.0f) {
                arrayList.add(shiftKeylineStateForPadding(keylineState, f3, containerWidth, false, f2));
            }
            return arrayList;
        }
        int lastFocalKeylineIndex = iFindLastNonAnchorKeylineIndex - keylineState.getLastFocalKeylineIndex();
        float f4 = keylineState.getFirstKeyline().locOffset - (keylineState.getFirstKeyline().maskedItemSize / 2.0f);
        if (lastFocalKeylineIndex <= 0 && keylineState.getLastFocalKeyline().cutoff > 0.0f) {
            arrayList.add(shiftKeylinesAndCreateKeylineState(keylineState, f4 - keylineState.getLastFocalKeyline().cutoff, containerWidth));
            return arrayList;
        }
        float f5 = 0.0f;
        int i2 = 0;
        while (i2 < lastFocalKeylineIndex) {
            KeylineState keylineState2 = (KeylineState) arrayList.get(arrayList.size() - 1);
            int i3 = iFindLastNonAnchorKeylineIndex - i2;
            float f6 = f5 + keylineState.getKeylines().get(i3).cutoff;
            int i4 = i3 + 1;
            int i5 = i2;
            KeylineState keylineStateMoveKeylineAndCreateKeylineState = moveKeylineAndCreateKeylineState(keylineState2, iFindLastNonAnchorKeylineIndex, i4 < keylineState.getKeylines().size() ? findLastIndexBeforeFirstFocalKeylineWithMask(keylineState2, keylineState.getKeylines().get(i4).mask) + 1 : 0, f4 - f6, keylineState.getFirstFocalKeylineIndex() + i2 + 1, keylineState.getLastFocalKeylineIndex() + i2 + 1, containerWidth);
            if (i5 == lastFocalKeylineIndex - 1 && f3 > 0.0f) {
                keylineStateMoveKeylineAndCreateKeylineState = shiftKeylineStateForPadding(keylineStateMoveKeylineAndCreateKeylineState, f3, containerWidth, false, f2);
            }
            arrayList.add(keylineStateMoveKeylineAndCreateKeylineState);
            i2 = i5 + 1;
            f5 = f6;
        }
        return arrayList;
    }

    private static float[] getStateStepsRange(List<KeylineState> list, float f2, float[] fArr) {
        int size = list.size();
        float f3 = fArr[0];
        int i2 = 1;
        while (i2 < size) {
            float f4 = fArr[i2];
            if (f2 <= f4) {
                return new float[]{AnimationUtils.lerp(0.0f, 1.0f, f3, f4, f2), i2 - 1, i2};
            }
            i2++;
            f3 = f4;
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    private static List<KeylineState> getStateStepsStart(Carousel carousel, KeylineState keylineState, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(keylineState);
        int iFindFirstNonAnchorKeylineIndex = findFirstNonAnchorKeylineIndex(keylineState);
        float containerWidth = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        int i2 = 1;
        if (isFirstFocalItemAtLeftOfContainer(keylineState) || iFindFirstNonAnchorKeylineIndex == -1) {
            if (f3 > 0.0f) {
                arrayList.add(shiftKeylineStateForPadding(keylineState, f3, containerWidth, true, f2));
            }
            return arrayList;
        }
        int firstFocalKeylineIndex = keylineState.getFirstFocalKeylineIndex() - iFindFirstNonAnchorKeylineIndex;
        float f4 = keylineState.getFirstKeyline().locOffset - (keylineState.getFirstKeyline().maskedItemSize / 2.0f);
        if (firstFocalKeylineIndex <= 0 && keylineState.getFirstFocalKeyline().cutoff > 0.0f) {
            arrayList.add(shiftKeylinesAndCreateKeylineState(keylineState, f4 + keylineState.getFirstFocalKeyline().cutoff, containerWidth));
            return arrayList;
        }
        float f5 = 0.0f;
        int i3 = 0;
        while (i3 < firstFocalKeylineIndex) {
            KeylineState keylineState2 = (KeylineState) arrayList.get(arrayList.size() - i2);
            int i4 = iFindFirstNonAnchorKeylineIndex + i3;
            int size = keylineState.getKeylines().size() - i2;
            float f6 = f5 + keylineState.getKeylines().get(i4).cutoff;
            int i5 = i4 - i2;
            int iFindFirstIndexAfterLastFocalKeylineWithMask = i5 >= 0 ? findFirstIndexAfterLastFocalKeylineWithMask(keylineState2, keylineState.getKeylines().get(i5).mask) - i2 : size;
            int i6 = i3;
            KeylineState keylineStateMoveKeylineAndCreateKeylineState = moveKeylineAndCreateKeylineState(keylineState2, iFindFirstNonAnchorKeylineIndex, iFindFirstIndexAfterLastFocalKeylineWithMask, f4 + f6, (keylineState.getFirstFocalKeylineIndex() - i3) - 1, (keylineState.getLastFocalKeylineIndex() - i3) - 1, containerWidth);
            if (i6 == firstFocalKeylineIndex - 1 && f3 > 0.0f) {
                keylineStateMoveKeylineAndCreateKeylineState = shiftKeylineStateForPadding(keylineStateMoveKeylineAndCreateKeylineState, f3, containerWidth, true, f2);
            }
            arrayList.add(keylineStateMoveKeylineAndCreateKeylineState);
            i3 = i6 + 1;
            f5 = f6;
            i2 = 1;
        }
        return arrayList;
    }

    private static boolean isFirstFocalItemAtLeftOfContainer(KeylineState keylineState) {
        return keylineState.getFirstFocalKeyline().locOffset - (keylineState.getFirstFocalKeyline().maskedItemSize / 2.0f) >= 0.0f && keylineState.getFirstFocalKeyline() == keylineState.getFirstNonAnchorKeyline();
    }

    private static boolean isLastFocalItemVisibleAtRightOfContainer(Carousel carousel, KeylineState keylineState) {
        int containerHeight = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
        }
        return (keylineState.getLastFocalKeyline().maskedItemSize / 2.0f) + keylineState.getLastFocalKeyline().locOffset <= ((float) containerHeight) && keylineState.getLastFocalKeyline() == keylineState.getLastNonAnchorKeyline();
    }

    private static KeylineState lerp(List<KeylineState> list, float f2, float[] fArr) {
        float[] stateStepsRange = getStateStepsRange(list, f2, fArr);
        return KeylineState.lerp(list.get((int) stateStepsRange[1]), list.get((int) stateStepsRange[2]), stateStepsRange[0]);
    }

    private static KeylineState moveKeylineAndCreateKeylineState(KeylineState keylineState, int i2, int i3, float f2, int i4, int i5, float f3) {
        ArrayList arrayList = new ArrayList(keylineState.getKeylines());
        arrayList.add(i3, (KeylineState.Keyline) arrayList.remove(i2));
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), f3);
        int i6 = 0;
        while (i6 < arrayList.size()) {
            KeylineState.Keyline keyline = (KeylineState.Keyline) arrayList.get(i6);
            float f4 = keyline.maskedItemSize;
            builder.addKeyline((f4 / 2.0f) + f2, keyline.mask, f4, i6 >= i4 && i6 <= i5, keyline.isAnchor, keyline.cutoff);
            f2 += keyline.maskedItemSize;
            i6++;
        }
        return builder.build();
    }

    private static KeylineState shiftKeylineStateForPadding(KeylineState keylineState, float f2, float f3, boolean z2, float f4) {
        ArrayList arrayList = new ArrayList(keylineState.getKeylines());
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), f3);
        float numberOfNonAnchorKeylines = f2 / keylineState.getNumberOfNonAnchorKeylines();
        float f5 = z2 ? f2 : 0.0f;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            KeylineState.Keyline keyline = (KeylineState.Keyline) arrayList.get(i2);
            if (keyline.isAnchor) {
                builder.addKeyline(keyline.locOffset, keyline.mask, keyline.maskedItemSize, false, true, keyline.cutoff);
            } else {
                boolean z3 = i2 >= keylineState.getFirstFocalKeylineIndex() && i2 <= keylineState.getLastFocalKeylineIndex();
                float f6 = keyline.maskedItemSize - numberOfNonAnchorKeylines;
                float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(f6, keylineState.getItemSize(), f4);
                float f7 = (f6 / 2.0f) + f5;
                float f8 = f7 - keyline.locOffset;
                builder.addKeyline(f7, childMaskPercentage, f6, z3, false, keyline.cutoff, z2 ? f8 : 0.0f, z2 ? 0.0f : f8);
                f5 += f6;
            }
            i2++;
        }
        return builder.build();
    }

    private static KeylineState shiftKeylinesAndCreateKeylineState(KeylineState keylineState, float f2, float f3) {
        return moveKeylineAndCreateKeylineState(keylineState, 0, 0, f2, keylineState.getFirstFocalKeylineIndex(), keylineState.getLastFocalKeylineIndex(), f3);
    }

    public KeylineState getDefaultState() {
        return this.defaultState;
    }

    public KeylineState getEndState() {
        List<KeylineState> list = this.endStateSteps;
        return list.get(list.size() - 1);
    }

    public Map<Integer, KeylineState> getKeylineStateForPositionMap(int i2, int i3, int i4, boolean z2) {
        float itemSize = this.defaultState.getItemSize();
        HashMap map = new HashMap();
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (i5 >= i2) {
                break;
            }
            int i7 = z2 ? (i2 - i5) - 1 : i5;
            if (i7 * itemSize * (z2 ? -1 : 1) > i4 - this.endShiftRange || i5 >= i2 - this.endStateSteps.size()) {
                Integer numValueOf = Integer.valueOf(i7);
                List<KeylineState> list = this.endStateSteps;
                map.put(numValueOf, list.get(MathUtils.clamp(i6, 0, list.size() - 1)));
                i6++;
            }
            i5++;
        }
        int i8 = 0;
        for (int i9 = i2 - 1; i9 >= 0; i9--) {
            int i10 = z2 ? (i2 - i9) - 1 : i9;
            if (i10 * itemSize * (z2 ? -1 : 1) < i3 + this.startShiftRange || i9 < this.startStateSteps.size()) {
                Integer numValueOf2 = Integer.valueOf(i10);
                List<KeylineState> list2 = this.startStateSteps;
                map.put(numValueOf2, list2.get(MathUtils.clamp(i8, 0, list2.size() - 1)));
                i8++;
            }
        }
        return map;
    }

    public KeylineState getShiftedState(float f2, float f3, float f4) {
        return getShiftedState(f2, f3, f4, false);
    }

    public KeylineState getStartState() {
        List<KeylineState> list = this.startStateSteps;
        return list.get(list.size() - 1);
    }

    public KeylineState getShiftedState(float f2, float f3, float f4, boolean z2) {
        float fLerp;
        List<KeylineState> list;
        float[] fArr;
        float f5 = this.startShiftRange + f3;
        float f6 = f4 - this.endShiftRange;
        float f7 = getStartState().getFirstFocalKeyline().leftOrTopPaddingShift;
        float f8 = getEndState().getLastFocalKeyline().rightOrBottomPaddingShift;
        if (this.startShiftRange == f7) {
            f5 += f7;
        }
        if (this.endShiftRange == f8) {
            f6 -= f8;
        }
        if (f2 < f5) {
            fLerp = AnimationUtils.lerp(1.0f, 0.0f, f3, f5, f2);
            list = this.startStateSteps;
            fArr = this.startStateStepsInterpolationPoints;
        } else {
            if (f2 <= f6) {
                return this.defaultState;
            }
            fLerp = AnimationUtils.lerp(0.0f, 1.0f, f6, f4, f2);
            list = this.endStateSteps;
            fArr = this.endStateStepsInterpolationPoints;
        }
        return z2 ? closestStateStepFromInterpolation(list, fLerp, fArr) : lerp(list, fLerp, fArr);
    }
}
