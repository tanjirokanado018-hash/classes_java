package androidx.constraintlayout.motion.widget;

import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class KeyCache {
    HashMap<Object, HashMap<String, float[]>> map = new HashMap<>();

    public float getFloatValue(Object obj, String str, int i2) {
        if (!this.map.containsKey(obj)) {
            return Float.NaN;
        }
        HashMap<String, float[]> map = this.map.get(obj);
        if (!map.containsKey(str)) {
            return Float.NaN;
        }
        float[] fArr = map.get(str);
        if (fArr.length > i2) {
            return fArr[i2];
        }
        return Float.NaN;
    }

    public void setFloatValue(Object obj, String str, int i2, float f2) {
        HashMap<String, float[]> map;
        if (this.map.containsKey(obj)) {
            map = this.map.get(obj);
            if (map.containsKey(str)) {
                float[] fArrCopyOf = map.get(str);
                if (fArrCopyOf.length <= i2) {
                    fArrCopyOf = Arrays.copyOf(fArrCopyOf, i2 + 1);
                }
                fArrCopyOf[i2] = f2;
                map.put(str, fArrCopyOf);
                return;
            }
            float[] fArr = new float[i2 + 1];
            fArr[i2] = f2;
            map.put(str, fArr);
        } else {
            map = new HashMap<>();
            float[] fArr2 = new float[i2 + 1];
            fArr2[i2] = f2;
            map.put(str, fArr2);
        }
        this.map.put(obj, map);
    }
}
