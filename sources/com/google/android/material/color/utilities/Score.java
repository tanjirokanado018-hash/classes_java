package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class Score {
    private static final int BLUE_500 = -12417548;
    private static final double CUTOFF_CHROMA = 5.0d;
    private static final double CUTOFF_EXCITED_PROPORTION = 0.01d;
    private static final int MAX_COLOR_COUNT = 4;
    private static final double TARGET_CHROMA = 48.0d;
    private static final double WEIGHT_CHROMA_ABOVE = 0.3d;
    private static final double WEIGHT_CHROMA_BELOW = 0.1d;
    private static final double WEIGHT_PROPORTION = 0.7d;

    public static class ScoredComparator implements Comparator<ScoredHCT> {
        @Override // java.util.Comparator
        public int compare(ScoredHCT scoredHCT, ScoredHCT scoredHCT2) {
            return Double.compare(scoredHCT2.score, scoredHCT.score);
        }
    }

    public static class ScoredHCT {
        public final Hct hct;
        public final double score;

        public ScoredHCT(Hct hct, double d) {
            this.hct = hct;
            this.score = d;
        }
    }

    private Score() {
    }

    public static List<Integer> score(Map<Integer, Integer> map) {
        return score(map, 4, BLUE_500, true);
    }

    public static List<Integer> score(Map<Integer, Integer> map, int i2) {
        return score(map, i2, BLUE_500, true);
    }

    public static List<Integer> score(Map<Integer, Integer> map, int i2, int i3) {
        return score(map, i2, i3, true);
    }

    public static List<Integer> score(Map<Integer, Integer> map, int i2, int i3, boolean z2) {
        ArrayList<Hct> arrayList = new ArrayList();
        int[] iArr = new int[360];
        double d = 0.0d;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Hct hctFromInt = Hct.fromInt(entry.getKey().intValue());
            arrayList.add(hctFromInt);
            int iFloor = (int) Math.floor(hctFromInt.getHue());
            int iIntValue = entry.getValue().intValue();
            iArr[iFloor] = iArr[iFloor] + iIntValue;
            d += (double) iIntValue;
        }
        double[] dArr = new double[360];
        for (int i4 = 0; i4 < 360; i4++) {
            double d2 = ((double) iArr[i4]) / d;
            for (int i5 = i4 - 14; i5 < i4 + 16; i5++) {
                int iSanitizeDegreesInt = MathUtils.sanitizeDegreesInt(i5);
                dArr[iSanitizeDegreesInt] = dArr[iSanitizeDegreesInt] + d2;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Hct hct : arrayList) {
            double d3 = dArr[MathUtils.sanitizeDegreesInt((int) Math.round(hct.getHue()))];
            if (!z2 || (hct.getChroma() >= CUTOFF_CHROMA && d3 > CUTOFF_EXCITED_PROPORTION)) {
                arrayList2.add(new ScoredHCT(hct, ((hct.getChroma() - TARGET_CHROMA) * (hct.getChroma() < TARGET_CHROMA ? WEIGHT_CHROMA_BELOW : WEIGHT_CHROMA_ABOVE)) + (d3 * 100.0d * WEIGHT_PROPORTION)));
            }
        }
        Collections.sort(arrayList2, new ScoredComparator());
        ArrayList arrayList3 = new ArrayList();
        for (int i6 = 90; i6 >= 15; i6--) {
            arrayList3.clear();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                Hct hct2 = ((ScoredHCT) it.next()).hct;
                Iterator it2 = arrayList3.iterator();
                do {
                    if (!it2.hasNext()) {
                        arrayList3.add(hct2);
                        break;
                    }
                } while (MathUtils.differenceDegrees(hct2.getHue(), ((Hct) it2.next()).getHue()) >= i6);
                if (arrayList3.size() >= i2) {
                    break;
                }
            }
            if (arrayList3.size() >= i2) {
                break;
            }
        }
        ArrayList arrayList4 = new ArrayList();
        if (arrayList3.isEmpty()) {
            arrayList4.add(Integer.valueOf(i3));
            return arrayList4;
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList4.add(Integer.valueOf(((Hct) it3.next()).toInt()));
        }
        return arrayList4;
    }
}
