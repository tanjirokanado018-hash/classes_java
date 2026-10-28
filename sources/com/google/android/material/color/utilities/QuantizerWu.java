package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class QuantizerWu implements Quantizer {
    private static final int INDEX_BITS = 5;
    private static final int INDEX_COUNT = 33;
    private static final int TOTAL_SIZE = 35937;
    Box[] cubes;
    double[] moments;
    int[] momentsB;
    int[] momentsG;
    int[] momentsR;
    int[] weights;

    /* JADX INFO: renamed from: com.google.android.material.color.utilities.QuantizerWu$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction;

        static {
            int[] iArr = new int[Direction.values().length];
            $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction = iArr;
            try {
                iArr[Direction.RED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[Direction.GREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[Direction.BLUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static final class Box {
        int b0;
        int b1;
        int g0;
        int g1;
        int r0;
        int r1;
        int vol;

        private Box() {
            this.r0 = 0;
            this.r1 = 0;
            this.g0 = 0;
            this.g1 = 0;
            this.b0 = 0;
            this.b1 = 0;
            this.vol = 0;
        }

        public /* synthetic */ Box(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public static final class CreateBoxesResult {
        int resultCount;

        public CreateBoxesResult(int i2, int i3) {
            this.resultCount = i3;
        }
    }

    public enum Direction {
        RED,
        GREEN,
        BLUE
    }

    public static final class MaximizeResult {
        int cutLocation;
        double maximum;

        public MaximizeResult(int i2, double d) {
            this.cutLocation = i2;
            this.maximum = d;
        }
    }

    public static int bottom(Box box, Direction direction, int[] iArr) {
        int i2;
        int i3;
        int i4 = AnonymousClass1.$SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[direction.ordinal()];
        if (i4 == 1) {
            i2 = (-iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)] + iArr[getIndex(box.r0, box.g0, box.b1)];
            i3 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else if (i4 == 2) {
            i2 = (-iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)] + iArr[getIndex(box.r0, box.g0, box.b1)];
            i3 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else {
            if (i4 != 3) {
                throw new IllegalArgumentException("unexpected direction " + direction);
            }
            i2 = (-iArr[getIndex(box.r1, box.g1, box.b0)]) + iArr[getIndex(box.r1, box.g0, box.b0)] + iArr[getIndex(box.r0, box.g1, box.b0)];
            i3 = iArr[getIndex(box.r0, box.g0, box.b0)];
        }
        return i2 - i3;
    }

    public static int getIndex(int i2, int i3, int i4) {
        return (i2 << 10) + (i2 << 6) + i2 + (i3 << 5) + i3 + i4;
    }

    public static int top(Box box, Direction direction, int i2, int[] iArr) {
        int i3;
        int i4;
        int i5 = AnonymousClass1.$SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[direction.ordinal()];
        if (i5 == 1) {
            i3 = (iArr[getIndex(i2, box.g1, box.b1)] - iArr[getIndex(i2, box.g1, box.b0)]) - iArr[getIndex(i2, box.g0, box.b1)];
            i4 = iArr[getIndex(i2, box.g0, box.b0)];
        } else if (i5 == 2) {
            i3 = (iArr[getIndex(box.r1, i2, box.b1)] - iArr[getIndex(box.r1, i2, box.b0)]) - iArr[getIndex(box.r0, i2, box.b1)];
            i4 = iArr[getIndex(box.r0, i2, box.b0)];
        } else {
            if (i5 != 3) {
                throw new IllegalArgumentException("unexpected direction " + direction);
            }
            i3 = (iArr[getIndex(box.r1, box.g1, i2)] - iArr[getIndex(box.r1, box.g0, i2)]) - iArr[getIndex(box.r0, box.g1, i2)];
            i4 = iArr[getIndex(box.r0, box.g0, i2)];
        }
        return i3 + i4;
    }

    public static int volume(Box box, int[] iArr) {
        return ((((((iArr[getIndex(box.r1, box.g1, box.b1)] - iArr[getIndex(box.r1, box.g1, box.b0)]) - iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)]) - iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)]) + iArr[getIndex(box.r0, box.g0, box.b1)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
    }

    public void constructHistogram(Map<Integer, Integer> map) {
        this.weights = new int[TOTAL_SIZE];
        this.momentsR = new int[TOTAL_SIZE];
        this.momentsG = new int[TOTAL_SIZE];
        this.momentsB = new int[TOTAL_SIZE];
        this.moments = new double[TOTAL_SIZE];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            int iIntValue2 = entry.getValue().intValue();
            int iRedFromArgb = ColorUtils.redFromArgb(iIntValue);
            int iGreenFromArgb = ColorUtils.greenFromArgb(iIntValue);
            int iBlueFromArgb = ColorUtils.blueFromArgb(iIntValue);
            int index = getIndex((iRedFromArgb >> 3) + 1, (iGreenFromArgb >> 3) + 1, (iBlueFromArgb >> 3) + 1);
            int[] iArr = this.weights;
            iArr[index] = iArr[index] + iIntValue2;
            int[] iArr2 = this.momentsR;
            iArr2[index] = (iRedFromArgb * iIntValue2) + iArr2[index];
            int[] iArr3 = this.momentsG;
            iArr3[index] = (iGreenFromArgb * iIntValue2) + iArr3[index];
            int[] iArr4 = this.momentsB;
            iArr4[index] = (iBlueFromArgb * iIntValue2) + iArr4[index];
            double[] dArr = this.moments;
            int i2 = iBlueFromArgb * iBlueFromArgb;
            dArr[index] = dArr[index] + ((double) ((i2 + (iGreenFromArgb * iGreenFromArgb) + (iRedFromArgb * iRedFromArgb)) * iIntValue2));
        }
    }

    public CreateBoxesResult createBoxes(int i2) {
        int i3;
        this.cubes = new Box[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            this.cubes[i4] = new Box(null);
        }
        double[] dArr = new double[i2];
        Box box = this.cubes[0];
        box.r1 = 32;
        box.g1 = 32;
        box.b1 = 32;
        int i5 = 1;
        int i6 = 0;
        while (i5 < i2) {
            Box[] boxArr = this.cubes;
            if (cut(boxArr[i6], boxArr[i5]).booleanValue()) {
                Box box2 = this.cubes[i6];
                dArr[i6] = box2.vol > 1 ? variance(box2) : 0.0d;
                Box box3 = this.cubes[i5];
                dArr[i5] = box3.vol > 1 ? variance(box3) : 0.0d;
            } else {
                dArr[i6] = 0.0d;
                i5--;
            }
            double d = dArr[0];
            int i7 = 0;
            for (int i8 = 1; i8 <= i5; i8++) {
                double d2 = dArr[i8];
                if (d2 > d) {
                    i7 = i8;
                    d = d2;
                }
            }
            if (d <= 0.0d) {
                i3 = i5 + 1;
                return new CreateBoxesResult(i2, i3);
            }
            i5++;
            i6 = i7;
        }
        i3 = i2;
        return new CreateBoxesResult(i2, i3);
    }

    public void createMoments() {
        int i2 = 1;
        while (true) {
            int i3 = 33;
            if (i2 >= 33) {
                return;
            }
            int[] iArr = new int[33];
            int[] iArr2 = new int[33];
            int[] iArr3 = new int[33];
            int[] iArr4 = new int[33];
            double[] dArr = new double[33];
            int i4 = 1;
            while (i4 < i3) {
                int i5 = 0;
                double d = 0.0d;
                int i6 = 1;
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (i6 < i3) {
                    int index = getIndex(i2, i4, i6);
                    int i10 = i5 + this.weights[index];
                    i7 += this.momentsR[index];
                    i8 += this.momentsG[index];
                    i9 += this.momentsB[index];
                    d += this.moments[index];
                    iArr[i6] = iArr[i6] + i10;
                    iArr2[i6] = iArr2[i6] + i7;
                    iArr3[i6] = iArr3[i6] + i8;
                    iArr4[i6] = iArr4[i6] + i9;
                    dArr[i6] = dArr[i6] + d;
                    int index2 = getIndex(i2 - 1, i4, i6);
                    int[] iArr5 = this.weights;
                    iArr5[index] = iArr5[index2] + iArr[i6];
                    int[] iArr6 = this.momentsR;
                    iArr6[index] = iArr6[index2] + iArr2[i6];
                    int[] iArr7 = this.momentsG;
                    iArr7[index] = iArr7[index2] + iArr3[i6];
                    int[] iArr8 = this.momentsB;
                    iArr8[index] = iArr8[index2] + iArr4[i6];
                    double[] dArr2 = this.moments;
                    dArr2[index] = dArr2[index2] + dArr[i6];
                    i6++;
                    i5 = i10;
                    i3 = 33;
                }
                i4++;
                i3 = 33;
            }
            i2++;
        }
    }

    public List<Integer> createResult(int i2) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < i2; i3++) {
            Box box = this.cubes[i3];
            int iVolume = volume(box, this.weights);
            if (iVolume > 0) {
                int iVolume2 = volume(box, this.momentsR) / iVolume;
                int iVolume3 = volume(box, this.momentsG) / iVolume;
                arrayList.add(Integer.valueOf(((volume(box, this.momentsB) / iVolume) & 255) | ((iVolume2 & 255) << 16) | ViewCompat.MEASURED_STATE_MASK | ((iVolume3 & 255) << 8)));
            }
        }
        return arrayList;
    }

    public Boolean cut(Box box, Box box2) {
        int i2;
        int i3;
        int iVolume = volume(box, this.momentsR);
        int iVolume2 = volume(box, this.momentsG);
        int iVolume3 = volume(box, this.momentsB);
        int iVolume4 = volume(box, this.weights);
        Direction direction = Direction.RED;
        MaximizeResult maximizeResultMaximize = maximize(box, direction, box.r0 + 1, box.r1, iVolume, iVolume2, iVolume3, iVolume4);
        Direction direction2 = Direction.GREEN;
        MaximizeResult maximizeResultMaximize2 = maximize(box, direction2, box.g0 + 1, box.g1, iVolume, iVolume2, iVolume3, iVolume4);
        Direction direction3 = Direction.BLUE;
        MaximizeResult maximizeResultMaximize3 = maximize(box, direction3, box.b0 + 1, box.b1, iVolume, iVolume2, iVolume3, iVolume4);
        double d = maximizeResultMaximize.maximum;
        double d2 = maximizeResultMaximize2.maximum;
        double d3 = maximizeResultMaximize3.maximum;
        if (d < d2 || d < d3) {
            direction = (d2 < d || d2 < d3) ? direction3 : direction2;
        } else if (maximizeResultMaximize.cutLocation < 0) {
            return Boolean.FALSE;
        }
        box2.r1 = box.r1;
        box2.g1 = box.g1;
        box2.b1 = box.b1;
        int i4 = AnonymousClass1.$SwitchMap$com$google$android$material$color$utilities$QuantizerWu$Direction[direction.ordinal()];
        if (i4 != 1) {
            if (i4 == 2) {
                i2 = maximizeResultMaximize2.cutLocation;
                box.g1 = i2;
                box2.r0 = box.r0;
            } else if (i4 == 3) {
                i3 = maximizeResultMaximize3.cutLocation;
                box.b1 = i3;
                box2.r0 = box.r0;
                box2.g0 = box.g0;
                box2.b0 = i3;
            }
            box.vol = (box.b1 - box.b0) * (box.g1 - box.g0) * (box.r1 - box.r0);
            box2.vol = (box2.b1 - box2.b0) * (box2.g1 - box2.g0) * (box2.r1 - box2.r0);
            return Boolean.TRUE;
        }
        int i5 = maximizeResultMaximize.cutLocation;
        box.r1 = i5;
        box2.r0 = i5;
        i2 = box.g0;
        box2.g0 = i2;
        i3 = box.b0;
        box2.b0 = i3;
        box.vol = (box.b1 - box.b0) * (box.g1 - box.g0) * (box.r1 - box.r0);
        box2.vol = (box2.b1 - box2.b0) * (box2.g1 - box2.g0) * (box2.r1 - box2.r0);
        return Boolean.TRUE;
    }

    public MaximizeResult maximize(Box box, Direction direction, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8;
        QuantizerWu quantizerWu = this;
        Box box2 = box;
        Direction direction2 = direction;
        int iBottom = bottom(box2, direction2, quantizerWu.momentsR);
        int iBottom2 = bottom(box2, direction2, quantizerWu.momentsG);
        int iBottom3 = bottom(box2, direction2, quantizerWu.momentsB);
        int iBottom4 = bottom(box2, direction2, quantizerWu.weights);
        double d = 0.0d;
        int i9 = -1;
        int i10 = i2;
        while (i10 < i3) {
            int pVar = top(box2, direction2, i10, quantizerWu.momentsR) + iBottom;
            int pVar2 = top(box2, direction2, i10, quantizerWu.momentsG) + iBottom2;
            int pVar3 = top(box2, direction2, i10, quantizerWu.momentsB) + iBottom3;
            int pVar4 = top(box2, direction2, i10, quantizerWu.weights) + iBottom4;
            if (pVar4 == 0) {
                i8 = iBottom;
            } else {
                i8 = iBottom;
                double d2 = ((double) ((pVar3 * pVar3) + ((pVar2 * pVar2) + (pVar * pVar)))) / ((double) pVar4);
                int i11 = i4 - pVar;
                int i12 = i5 - pVar2;
                int i13 = i6 - pVar3;
                int i14 = i7 - pVar4;
                if (i14 != 0) {
                    double d3 = (((double) ((i13 * i13) + ((i12 * i12) + (i11 * i11)))) / ((double) i14)) + d2;
                    if (d3 > d) {
                        d = d3;
                        i9 = i10;
                    }
                }
            }
            i10++;
            quantizerWu = this;
            box2 = box;
            direction2 = direction;
            iBottom = i8;
        }
        return new MaximizeResult(i9, d);
    }

    @Override // com.google.android.material.color.utilities.Quantizer
    public QuantizerResult quantize(int[] iArr, int i2) {
        constructHistogram(new QuantizerMap().quantize(iArr, i2).colorToCount);
        createMoments();
        List<Integer> listCreateResult = createResult(createBoxes(i2).resultCount);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Integer num : listCreateResult) {
            num.intValue();
            linkedHashMap.put(num, 0);
        }
        return new QuantizerResult(linkedHashMap);
    }

    public double variance(Box box) {
        int iVolume = volume(box, this.momentsR);
        int iVolume2 = volume(box, this.momentsG);
        int iVolume3 = volume(box, this.momentsB);
        int i2 = iVolume3 * iVolume3;
        return (((((((this.moments[getIndex(box.r1, box.g1, box.b1)] - this.moments[getIndex(box.r1, box.g1, box.b0)]) - this.moments[getIndex(box.r1, box.g0, box.b1)]) + this.moments[getIndex(box.r1, box.g0, box.b0)]) - this.moments[getIndex(box.r0, box.g1, box.b1)]) + this.moments[getIndex(box.r0, box.g1, box.b0)]) + this.moments[getIndex(box.r0, box.g0, box.b1)]) - this.moments[getIndex(box.r0, box.g0, box.b0)]) - (((double) (i2 + ((iVolume2 * iVolume2) + (iVolume * iVolume)))) / ((double) volume(box, this.weights)));
    }
}
