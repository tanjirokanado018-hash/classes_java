package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class QuantizerMap implements Quantizer {
    Map<Integer, Integer> colorToCount;

    public Map<Integer, Integer> getColorToCount() {
        return this.colorToCount;
    }

    @Override // com.google.android.material.color.utilities.Quantizer
    public QuantizerResult quantize(int[] iArr, int i2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i3 : iArr) {
            Integer num = (Integer) linkedHashMap.get(Integer.valueOf(i3));
            int iIntValue = 1;
            if (num != null) {
                iIntValue = 1 + num.intValue();
            }
            linkedHashMap.put(Integer.valueOf(i3), Integer.valueOf(iIntValue));
        }
        this.colorToCount = linkedHashMap;
        return new QuantizerResult(linkedHashMap);
    }
}
