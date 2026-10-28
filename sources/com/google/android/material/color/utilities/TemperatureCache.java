package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class TemperatureCache {
    private final Hct input;
    private Hct precomputedComplement;
    private List<Hct> precomputedHctsByHue;
    private List<Hct> precomputedHctsByTemp;
    private Map<Hct, Double> precomputedTempsByHct;

    private TemperatureCache() {
        throw new UnsupportedOperationException();
    }

    private Hct getColdest() {
        return getHctsByTemp().get(0);
    }

    private List<Hct> getHctsByHue() {
        List<Hct> list = this.precomputedHctsByHue;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (double d = 0.0d; d <= 360.0d; d += 1.0d) {
            arrayList.add(Hct.from(d, this.input.getChroma(), this.input.getTone()));
        }
        List<Hct> listUnmodifiableList = Collections.unmodifiableList(arrayList);
        this.precomputedHctsByHue = listUnmodifiableList;
        return listUnmodifiableList;
    }

    private List<Hct> getHctsByTemp() {
        List<Hct> list = this.precomputedHctsByTemp;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        Collections.sort(arrayList, Comparator.comparing(new a(2, this), new i()));
        this.precomputedHctsByTemp = arrayList;
        return arrayList;
    }

    private Map<Hct, Double> getTempsByHct() {
        Map<Hct, Double> map = this.precomputedTempsByHct;
        if (map != null) {
            return map;
        }
        ArrayList<Hct> arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        HashMap map2 = new HashMap();
        for (Hct hct : arrayList) {
            map2.put(hct, Double.valueOf(rawTemperature(hct)));
        }
        this.precomputedTempsByHct = map2;
        return map2;
    }

    private Hct getWarmest() {
        return getHctsByTemp().get(getHctsByTemp().size() - 1);
    }

    private static boolean isBetween(double d, double d2, double d3) {
        if (d2 < d3) {
            return d2 <= d && d <= d3;
        }
        return d2 <= d || d <= d3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Double lambda$getHctsByTemp$0(Hct hct) {
        return getTempsByHct().get(hct);
    }

    public static double rawTemperature(Hct hct) {
        double[] dArrLabFromArgb = ColorUtils.labFromArgb(hct.toInt());
        return (Math.cos(Math.toRadians(MathUtils.sanitizeDegreesDouble(MathUtils.sanitizeDegreesDouble(Math.toDegrees(Math.atan2(dArrLabFromArgb[2], dArrLabFromArgb[1]))) - 50.0d))) * (Math.pow(Math.hypot(dArrLabFromArgb[1], dArrLabFromArgb[2]), 1.07d) * 0.02d)) - 0.5d;
    }

    public List<Hct> getAnalogousColors() {
        return getAnalogousColors(5, 12);
    }

    public Hct getComplement() {
        Hct hct = this.precomputedComplement;
        if (hct != null) {
            return hct;
        }
        double hue = getColdest().getHue();
        double dDoubleValue = getTempsByHct().get(getColdest()).doubleValue();
        double hue2 = getWarmest().getHue();
        double dDoubleValue2 = getTempsByHct().get(getWarmest()).doubleValue() - dDoubleValue;
        boolean zIsBetween = isBetween(this.input.getHue(), hue, hue2);
        double d = zIsBetween ? hue2 : hue;
        if (!zIsBetween) {
            hue = hue2;
        }
        Hct hct2 = getHctsByHue().get((int) Math.round(this.input.getHue()));
        double relativeTemperature = 1.0d - getRelativeTemperature(this.input);
        double d2 = 1000.0d;
        for (double d3 = 0.0d; d3 <= 360.0d; d3 += 1.0d) {
            double dSanitizeDegreesDouble = MathUtils.sanitizeDegreesDouble((1.0d * d3) + d);
            if (isBetween(dSanitizeDegreesDouble, d, hue)) {
                Hct hct3 = getHctsByHue().get((int) Math.round(dSanitizeDegreesDouble));
                double dAbs = Math.abs(relativeTemperature - ((getTempsByHct().get(hct3).doubleValue() - dDoubleValue) / dDoubleValue2));
                if (dAbs < d2) {
                    hct2 = hct3;
                    d2 = dAbs;
                }
            }
        }
        this.precomputedComplement = hct2;
        return hct2;
    }

    public double getRelativeTemperature(Hct hct) {
        double dDoubleValue = getTempsByHct().get(getWarmest()).doubleValue() - getTempsByHct().get(getColdest()).doubleValue();
        double dDoubleValue2 = getTempsByHct().get(hct).doubleValue() - getTempsByHct().get(getColdest()).doubleValue();
        if (dDoubleValue == 0.0d) {
            return 0.5d;
        }
        return dDoubleValue2 / dDoubleValue;
    }

    public TemperatureCache(Hct hct) {
        this.input = hct;
    }

    public List<Hct> getAnalogousColors(int i2, int i3) {
        int size;
        int size2;
        int iRound = (int) Math.round(this.input.getHue());
        Hct hct = getHctsByHue().get(iRound);
        double relativeTemperature = getRelativeTemperature(hct);
        ArrayList arrayList = new ArrayList();
        arrayList.add(hct);
        double dAbs = 0.0d;
        double dAbs2 = 0.0d;
        int i4 = 0;
        while (i4 < 360) {
            double relativeTemperature2 = getRelativeTemperature(getHctsByHue().get(MathUtils.sanitizeDegreesInt(iRound + i4)));
            dAbs2 += Math.abs(relativeTemperature2 - relativeTemperature);
            i4++;
            relativeTemperature = relativeTemperature2;
        }
        double d = dAbs2 / ((double) i3);
        double relativeTemperature3 = getRelativeTemperature(hct);
        int i5 = 1;
        while (arrayList.size() < i3) {
            Hct hct2 = getHctsByHue().get(MathUtils.sanitizeDegreesInt(iRound + i5));
            double relativeTemperature4 = getRelativeTemperature(hct2);
            dAbs += Math.abs(relativeTemperature4 - relativeTemperature3);
            boolean z2 = dAbs >= ((double) arrayList.size()) * d;
            int i6 = 1;
            while (z2 && arrayList.size() < i3) {
                arrayList.add(hct2);
                z2 = dAbs >= ((double) (arrayList.size() + i6)) * d;
                i6++;
            }
            i5++;
            if (i5 > 360) {
                while (arrayList.size() < i3) {
                    arrayList.add(hct2);
                }
                break;
            }
            relativeTemperature3 = relativeTemperature4;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.input);
        int iFloor = (int) Math.floor((((double) i2) - 1.0d) / 2.0d);
        for (int i7 = 1; i7 < iFloor + 1; i7++) {
            int size3 = 0 - i7;
            while (true) {
                size2 = arrayList.size();
                if (size3 >= 0) {
                    break;
                }
                size3 += size2;
            }
            if (size3 >= size2) {
                size3 %= arrayList.size();
            }
            arrayList2.add(0, (Hct) arrayList.get(size3));
        }
        int i8 = i2 - iFloor;
        for (int i9 = 1; i9 < i8; i9++) {
            int size4 = i9;
            while (true) {
                size = arrayList.size();
                if (size4 >= 0) {
                    break;
                }
                size4 += size;
            }
            if (size4 >= size) {
                size4 %= arrayList.size();
            }
            arrayList2.add((Hct) arrayList.get(size4));
        }
        return arrayList2;
    }
}
