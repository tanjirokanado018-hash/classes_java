package androidx.profileinstaller;

import androidx.annotation.NonNull;
import java.util.TreeMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class DexProfileData {

    @NonNull
    final String apkName;
    int classSetSize;

    @NonNull
    int[] classes;
    final long dexChecksum;

    @NonNull
    final String dexName;
    final int hotMethodRegionSize;
    long mTypeIdCount;

    @NonNull
    final TreeMap<Integer, Integer> methods;
    final int numMethodIds;

    public DexProfileData(@NonNull String str, @NonNull String str2, long j2, long j3, int i2, int i3, int i4, @NonNull int[] iArr, @NonNull TreeMap<Integer, Integer> treeMap) {
        this.apkName = str;
        this.dexName = str2;
        this.dexChecksum = j2;
        this.mTypeIdCount = j3;
        this.classSetSize = i2;
        this.hotMethodRegionSize = i3;
        this.numMethodIds = i4;
        this.classes = iArr;
        this.methods = treeMap;
    }
}
