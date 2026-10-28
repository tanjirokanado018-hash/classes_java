package androidx.core.graphics;

import android.graphics.Path;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.location.LocationRequestCompat;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PathParser {
    private static final String LOGTAG = "PathParser";

    public static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;
    }

    public static class PathDataNode {
        private final float[] mParams;
        private char mType;

        public PathDataNode(char c2, float[] fArr) {
            this.mType = c2;
            this.mParams = fArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static void addCommand(Path path, float[] fArr, char c2, char c3, float[] fArr2) {
            int i2;
            int i3;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            char c4 = c3;
            float f10 = fArr[0];
            float f11 = fArr[1];
            float f12 = fArr[2];
            float f13 = fArr[3];
            float f14 = fArr[4];
            float f15 = fArr[5];
            switch (c4) {
                case 'A':
                case 'a':
                    i2 = 7;
                    break;
                case 'C':
                case 'c':
                    i2 = 6;
                    break;
                case 'H':
                case 'V':
                case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
                case 'v':
                    i2 = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                case 't':
                default:
                    i2 = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i2 = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f14, f15);
                    f10 = f14;
                    f12 = f10;
                    f11 = f15;
                    f13 = f11;
                    i2 = 2;
                    break;
            }
            float f16 = f10;
            float f17 = f11;
            float f18 = f14;
            float f19 = f15;
            int i4 = 0;
            char c5 = c2;
            while (i4 < fArr2.length) {
                if (c4 != 'A') {
                    if (c4 == 'C') {
                        i3 = i4;
                        int i5 = i3 + 2;
                        int i6 = i3 + 3;
                        int i7 = i3 + 4;
                        int i8 = i3 + 5;
                        path.cubicTo(fArr2[i3], fArr2[i3 + 1], fArr2[i5], fArr2[i6], fArr2[i7], fArr2[i8]);
                        f16 = fArr2[i7];
                        float f20 = fArr2[i8];
                        float f21 = fArr2[i5];
                        float f22 = fArr2[i6];
                        f17 = f20;
                        f13 = f22;
                        f12 = f21;
                    } else if (c4 == 'H') {
                        i3 = i4;
                        path.lineTo(fArr2[i3], f17);
                        f16 = fArr2[i3];
                    } else if (c4 == 'Q') {
                        i3 = i4;
                        int i9 = i3 + 1;
                        int i10 = i3 + 2;
                        int i11 = i3 + 3;
                        path.quadTo(fArr2[i3], fArr2[i9], fArr2[i10], fArr2[i11]);
                        float f23 = fArr2[i3];
                        float f24 = fArr2[i9];
                        f16 = fArr2[i10];
                        f17 = fArr2[i11];
                        f12 = f23;
                        f13 = f24;
                    } else if (c4 == 'V') {
                        i3 = i4;
                        path.lineTo(f16, fArr2[i3]);
                        f17 = fArr2[i3];
                    } else if (c4 != 'a') {
                        if (c4 != 'c') {
                            if (c4 == 'h') {
                                path.rLineTo(fArr2[i4], 0.0f);
                                f16 += fArr2[i4];
                            } else if (c4 != 'q') {
                                if (c4 == 'v') {
                                    path.rLineTo(0.0f, fArr2[i4]);
                                    f5 = fArr2[i4];
                                } else if (c4 == 'L') {
                                    int i12 = i4 + 1;
                                    path.lineTo(fArr2[i4], fArr2[i12]);
                                    f16 = fArr2[i4];
                                    f17 = fArr2[i12];
                                } else if (c4 == 'M') {
                                    f16 = fArr2[i4];
                                    f17 = fArr2[i4 + 1];
                                    if (i4 > 0) {
                                        path.lineTo(f16, f17);
                                    } else {
                                        path.moveTo(f16, f17);
                                        i3 = i4;
                                        f19 = f17;
                                        f18 = f16;
                                    }
                                } else if (c4 == 'S') {
                                    if (c5 == 'c' || c5 == 's' || c5 == 'C' || c5 == 'S') {
                                        f16 = (f16 * 2.0f) - f12;
                                        f17 = (f17 * 2.0f) - f13;
                                    }
                                    float f25 = f17;
                                    float f26 = f16;
                                    int i13 = i4 + 1;
                                    int i14 = i4 + 2;
                                    int i15 = i4 + 3;
                                    path.cubicTo(f26, f25, fArr2[i4], fArr2[i13], fArr2[i14], fArr2[i15]);
                                    f2 = fArr2[i4];
                                    f3 = fArr2[i13];
                                    f16 = fArr2[i14];
                                    f17 = fArr2[i15];
                                    f12 = f2;
                                    f13 = f3;
                                } else if (c4 == 'T') {
                                    if (c5 == 'q' || c5 == 't' || c5 == 'Q' || c5 == 'T') {
                                        f16 = (f16 * 2.0f) - f12;
                                        f17 = (f17 * 2.0f) - f13;
                                    }
                                    int i16 = i4 + 1;
                                    path.quadTo(f16, f17, fArr2[i4], fArr2[i16]);
                                    i3 = i4;
                                    f13 = f17;
                                    f12 = f16;
                                    f16 = fArr2[i4];
                                    f17 = fArr2[i16];
                                } else if (c4 == 'l') {
                                    int i17 = i4 + 1;
                                    path.rLineTo(fArr2[i4], fArr2[i17]);
                                    f16 += fArr2[i4];
                                    f5 = fArr2[i17];
                                } else if (c4 == 'm') {
                                    float f27 = fArr2[i4];
                                    f16 += f27;
                                    float f28 = fArr2[i4 + 1];
                                    f17 += f28;
                                    if (i4 > 0) {
                                        path.rLineTo(f27, f28);
                                    } else {
                                        path.rMoveTo(f27, f28);
                                        i3 = i4;
                                        f19 = f17;
                                        f18 = f16;
                                    }
                                } else if (c4 == 's') {
                                    if (c5 == 'c' || c5 == 's' || c5 == 'C' || c5 == 'S') {
                                        float f29 = f16 - f12;
                                        f6 = f17 - f13;
                                        f7 = f29;
                                    } else {
                                        f7 = 0.0f;
                                        f6 = 0.0f;
                                    }
                                    int i18 = i4 + 1;
                                    int i19 = i4 + 2;
                                    int i20 = i4 + 3;
                                    path.rCubicTo(f7, f6, fArr2[i4], fArr2[i18], fArr2[i19], fArr2[i20]);
                                    f2 = fArr2[i4] + f16;
                                    f3 = fArr2[i18] + f17;
                                    f16 += fArr2[i19];
                                    f4 = fArr2[i20];
                                } else if (c4 == 't') {
                                    if (c5 == 'q' || c5 == 't' || c5 == 'Q' || c5 == 'T') {
                                        f8 = f16 - f12;
                                        f9 = f17 - f13;
                                    } else {
                                        f9 = 0.0f;
                                        f8 = 0.0f;
                                    }
                                    int i21 = i4 + 1;
                                    path.rQuadTo(f8, f9, fArr2[i4], fArr2[i21]);
                                    float f30 = f8 + f16;
                                    float f31 = f9 + f17;
                                    f16 += fArr2[i4];
                                    f17 += fArr2[i21];
                                    f13 = f31;
                                    f12 = f30;
                                }
                                f17 += f5;
                            } else {
                                int i22 = i4 + 1;
                                int i23 = i4 + 2;
                                int i24 = i4 + 3;
                                path.rQuadTo(fArr2[i4], fArr2[i22], fArr2[i23], fArr2[i24]);
                                f2 = fArr2[i4] + f16;
                                f3 = fArr2[i22] + f17;
                                f16 += fArr2[i23];
                                f4 = fArr2[i24];
                            }
                            i3 = i4;
                        } else {
                            int i25 = i4 + 2;
                            int i26 = i4 + 3;
                            int i27 = i4 + 4;
                            int i28 = i4 + 5;
                            path.rCubicTo(fArr2[i4], fArr2[i4 + 1], fArr2[i25], fArr2[i26], fArr2[i27], fArr2[i28]);
                            f2 = fArr2[i25] + f16;
                            f3 = fArr2[i26] + f17;
                            f16 += fArr2[i27];
                            f4 = fArr2[i28];
                        }
                        f17 += f4;
                        f12 = f2;
                        f13 = f3;
                        i3 = i4;
                    } else {
                        int i29 = i4 + 5;
                        int i30 = i4 + 6;
                        i3 = i4;
                        drawArc(path, f16, f17, fArr2[i29] + f16, fArr2[i30] + f17, fArr2[i4], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3] != 0.0f, fArr2[i4 + 4] != 0.0f);
                        f16 += fArr2[i29];
                        f17 += fArr2[i30];
                    }
                    i4 = i3 + i2;
                    c5 = c3;
                    c4 = c5;
                } else {
                    i3 = i4;
                    int i31 = i3 + 5;
                    int i32 = i3 + 6;
                    drawArc(path, f16, f17, fArr2[i31], fArr2[i32], fArr2[i3], fArr2[i3 + 1], fArr2[i3 + 2], fArr2[i3 + 3] != 0.0f, fArr2[i3 + 4] != 0.0f);
                    f16 = fArr2[i31];
                    f17 = fArr2[i32];
                }
                f13 = f17;
                f12 = f16;
                i4 = i3 + i2;
                c5 = c3;
                c4 = c5;
            }
            fArr[0] = f16;
            fArr[1] = f17;
            fArr[2] = f12;
            fArr[3] = f13;
            fArr[4] = f18;
            fArr[5] = f19;
        }

        private static void arcToBezier(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            double d10 = d3;
            int iCeil = (int) Math.ceil(Math.abs((d9 * 4.0d) / 3.141592653589793d));
            double dCos = Math.cos(d7);
            double dSin = Math.sin(d7);
            double dCos2 = Math.cos(d8);
            double dSin2 = Math.sin(d8);
            double d11 = -d10;
            double d12 = d11 * dCos;
            double d13 = d4 * dSin;
            double d14 = (d12 * dSin2) - (d13 * dCos2);
            double d15 = d11 * dSin;
            double d16 = d4 * dCos;
            double d17 = (dCos2 * d16) + (dSin2 * d15);
            double d18 = d9 / ((double) iCeil);
            double d19 = d8;
            double d20 = d17;
            double d21 = d14;
            int i2 = 0;
            double d22 = d5;
            double d23 = d6;
            while (i2 < iCeil) {
                double d24 = d19 + d18;
                double dSin3 = Math.sin(d24);
                double dCos3 = Math.cos(d24);
                double d25 = (((d10 * dCos) * dCos3) + d) - (d13 * dSin3);
                double d26 = (d16 * dSin3) + (d10 * dSin * dCos3) + d2;
                double d27 = (d12 * dSin3) - (d13 * dCos3);
                double d28 = (dCos3 * d16) + (dSin3 * d15);
                double d29 = d24 - d19;
                double dTan = Math.tan(d29 / 2.0d);
                double dSqrt = ((Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d) * Math.sin(d29)) / 3.0d;
                double d30 = dCos;
                double d31 = dSin;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) ((d21 * dSqrt) + d22), (float) ((d20 * dSqrt) + d23), (float) (d25 - (dSqrt * d27)), (float) (d26 - (dSqrt * d28)), (float) d25, (float) d26);
                i2++;
                d18 = d18;
                dSin = d31;
                d22 = d25;
                d15 = d15;
                dCos = d30;
                d19 = d24;
                d20 = d28;
                d21 = d27;
                iCeil = iCeil;
                d23 = d26;
                d10 = d3;
            }
        }

        private static void drawArc(Path path, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z2, boolean z3) {
            double d;
            double d2;
            double radians = Math.toRadians(f8);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            double d3 = f2;
            double d4 = d3 * dCos;
            double d5 = f3;
            double d6 = f6;
            double d7 = ((d5 * dSin) + d4) / d6;
            double d8 = f7;
            double d9 = ((d5 * dCos) + (((double) (-f2)) * dSin)) / d8;
            double d10 = f5;
            double d11 = ((d10 * dSin) + (((double) f4) * dCos)) / d6;
            double d12 = ((d10 * dCos) + (((double) (-f4)) * dSin)) / d8;
            double d13 = d7 - d11;
            double d14 = d9 - d12;
            double d15 = (d7 + d11) / 2.0d;
            double d16 = (d9 + d12) / 2.0d;
            double d17 = (d14 * d14) + (d13 * d13);
            if (d17 == 0.0d) {
                return;
            }
            double d18 = (1.0d / d17) - 0.25d;
            if (d18 < 0.0d) {
                float fSqrt = (float) (Math.sqrt(d17) / 1.99999d);
                drawArc(path, f2, f3, f4, f5, f6 * fSqrt, f7 * fSqrt, f8, z2, z3);
                return;
            }
            double dSqrt = Math.sqrt(d18);
            double d19 = d13 * dSqrt;
            double d20 = dSqrt * d14;
            if (z2 == z3) {
                d = d15 - d20;
                d2 = d16 + d19;
            } else {
                d = d15 + d20;
                d2 = d16 - d19;
            }
            double dAtan2 = Math.atan2(d9 - d2, d7 - d);
            double dAtan3 = Math.atan2(d12 - d2, d11 - d) - dAtan2;
            if (z3 != (dAtan3 >= 0.0d)) {
                dAtan3 = dAtan3 > 0.0d ? dAtan3 - 6.283185307179586d : dAtan3 + 6.283185307179586d;
            }
            double d21 = d * d6;
            double d22 = d2 * d8;
            arcToBezier(path, (d21 * dCos) - (d22 * dSin), (d22 * dCos) + (d21 * dSin), d6, d8, d3, d5, radians, dAtan2, dAtan3);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Deprecated
        public static void nodesToPath(@NonNull PathDataNode[] pathDataNodeArr, @NonNull Path path) {
            PathParser.nodesToPath(pathDataNodeArr, path);
        }

        @NonNull
        public float[] getParams() {
            return this.mParams;
        }

        public char getType() {
            return this.mType;
        }

        public void interpolatePathDataNode(@NonNull PathDataNode pathDataNode, @NonNull PathDataNode pathDataNode2, float f2) {
            this.mType = pathDataNode.mType;
            int i2 = 0;
            while (true) {
                float[] fArr = pathDataNode.mParams;
                if (i2 >= fArr.length) {
                    return;
                }
                this.mParams[i2] = (pathDataNode2.mParams[i2] * f2) + ((1.0f - f2) * fArr[i2]);
                i2++;
            }
        }

        public PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.copyOfRange(fArr, 0, fArr.length);
        }
    }

    private PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c2, float[] fArr) {
        arrayList.add(new PathDataNode(c2, fArr));
    }

    public static boolean canMorph(@Nullable PathDataNode[] pathDataNodeArr, @Nullable PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            if (pathDataNodeArr[i2].mType != pathDataNodeArr2[i2].mType || pathDataNodeArr[i2].mParams.length != pathDataNodeArr2[i2].mParams.length) {
                return false;
            }
        }
        return true;
    }

    public static float[] copyOfRange(float[] fArr, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (i2 < 0 || i2 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i4 = i3 - i2;
        int iMin = Math.min(i4, length - i2);
        float[] fArr2 = new float[i4];
        System.arraycopy(fArr, i2, fArr2, 0, iMin);
        return fArr2;
    }

    @NonNull
    public static PathDataNode[] createNodesFromPathData(@NonNull String str) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        int i3 = 0;
        while (i2 < str.length()) {
            int iNextStart = nextStart(str, i2);
            String strTrim = str.substring(i3, iNextStart).trim();
            if (!strTrim.isEmpty()) {
                addNode(arrayList, strTrim.charAt(0), getFloats(strTrim));
            }
            i3 = iNextStart;
            i2 = iNextStart + 1;
        }
        if (i2 - i3 == 1 && i3 < str.length()) {
            addNode(arrayList, str.charAt(i3), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[0]);
    }

    @NonNull
    public static Path createPathFromPathData(@NonNull String str) {
        Path path = new Path();
        try {
            PathDataNode.nodesToPath(createNodesFromPathData(str), path);
            return path;
        } catch (RuntimeException e2) {
            throw new RuntimeException(androidx.appcompat.graphics.drawable.a.k("Error in parsing ", str), e2);
        }
    }

    @NonNull
    public static PathDataNode[] deepCopyNodes(@NonNull PathDataNode[] pathDataNodeArr) {
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            pathDataNodeArr2[i2] = new PathDataNode(pathDataNodeArr[i2]);
        }
        return pathDataNodeArr2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:15:0x0027  */
    /* JADX WARN: Code duplicated, block: B:16:0x0029  */
    /* JADX WARN: Code duplicated, block: B:20:0x0031  */
    private static void extract(String str, int i2, ExtractFloatResult extractFloatResult) {
        extractFloatResult.mEndWithNegOrDot = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int i3 = i2; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == ' ') {
                z2 = false;
                z4 = true;
            } else if (cCharAt != 'E' && cCharAt != 'e') {
                switch (cCharAt) {
                    case ',':
                        z2 = false;
                        z4 = true;
                        break;
                    case '-':
                        if (i3 == i2 || z2) {
                            z2 = false;
                        } else {
                            extractFloatResult.mEndWithNegOrDot = true;
                            z2 = false;
                            z4 = true;
                        }
                        break;
                    case '.':
                        if (z3) {
                            extractFloatResult.mEndWithNegOrDot = true;
                            z2 = false;
                            z4 = true;
                        } else {
                            z2 = false;
                            z3 = true;
                        }
                        break;
                    default:
                        z2 = false;
                        break;
                }
            } else {
                z2 = true;
            }
            if (z4) {
                extractFloatResult.mEndPosition = i3;
            }
        }
        extractFloatResult.mEndPosition = i3;
    }

    private static float[] getFloats(String str) {
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = str.length();
            int i2 = 1;
            int i3 = 0;
            while (i2 < length) {
                extract(str, i2, extractFloatResult);
                int i4 = extractFloatResult.mEndPosition;
                if (i2 < i4) {
                    fArr[i3] = Float.parseFloat(str.substring(i2, i4));
                    i3++;
                }
                i2 = extractFloatResult.mEndWithNegOrDot ? i4 : i4 + 1;
            }
            return copyOfRange(fArr, 0, i3);
        } catch (NumberFormatException e2) {
            throw new RuntimeException(androidx.appcompat.graphics.drawable.a.f("error in parsing \"", str, "\""), e2);
        }
    }

    public static void interpolatePathDataNodes(@NonNull PathDataNode[] pathDataNodeArr, float f2, @NonNull PathDataNode[] pathDataNodeArr2, @NonNull PathDataNode[] pathDataNodeArr3) {
        if (!interpolatePathDataNodes(pathDataNodeArr, pathDataNodeArr2, pathDataNodeArr3, f2)) {
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x001a  */
    private static int nextStart(String str, int i2) {
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if ((cCharAt - 'Z') * (cCharAt - 'A') > 0) {
                if ((cCharAt - 'z') * (cCharAt - 'a') > 0) {
                    continue;
                } else if (cCharAt != 'e' && cCharAt != 'E') {
                    return i2;
                }
            } else if (cCharAt != 'e') {
                continue;
            }
            i2++;
        }
        return i2;
    }

    public static void nodesToPath(@NonNull PathDataNode[] pathDataNodeArr, @NonNull Path path) {
        float[] fArr = new float[6];
        char c2 = 'm';
        for (PathDataNode pathDataNode : pathDataNodeArr) {
            PathDataNode.addCommand(path, fArr, c2, pathDataNode.mType, pathDataNode.mParams);
            c2 = pathDataNode.mType;
        }
    }

    public static void updateNodes(@NonNull PathDataNode[] pathDataNodeArr, @NonNull PathDataNode[] pathDataNodeArr2) {
        for (int i2 = 0; i2 < pathDataNodeArr2.length; i2++) {
            pathDataNodeArr[i2].mType = pathDataNodeArr2[i2].mType;
            for (int i3 = 0; i3 < pathDataNodeArr2[i2].mParams.length; i3++) {
                pathDataNodeArr[i2].mParams[i3] = pathDataNodeArr2[i2].mParams[i3];
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public static boolean interpolatePathDataNodes(@NonNull PathDataNode[] pathDataNodeArr, @NonNull PathDataNode[] pathDataNodeArr2, @NonNull PathDataNode[] pathDataNodeArr3, float f2) {
        if (pathDataNodeArr.length != pathDataNodeArr2.length || pathDataNodeArr2.length != pathDataNodeArr3.length) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        }
        if (!canMorph(pathDataNodeArr2, pathDataNodeArr3)) {
            return false;
        }
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            pathDataNodeArr[i2].interpolatePathDataNode(pathDataNodeArr2[i2], pathDataNodeArr3[i2], f2);
        }
        return true;
    }
}
