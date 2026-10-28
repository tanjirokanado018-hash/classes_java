package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class ArcCurveFit extends CurveFit {
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;
    Arc[] mArcs;
    private final double[] mTime;

    public static class Arc {
        private static final double EPSILON = 0.001d;
        private static final String TAG = "Arc";
        private static double[] ourPercent = new double[91];
        boolean linear;
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        public Arc(int i2, double d, double d2, double d3, double d4, double d5, double d6) {
            this.linear = false;
            this.mVertical = i2 == 1;
            this.mTime1 = d;
            this.mTime2 = d2;
            this.mOneOverDeltaTime = 1.0d / (d2 - d);
            if (3 == i2) {
                this.linear = true;
            }
            double d7 = d5 - d3;
            double d8 = d6 - d4;
            if (!this.linear && Math.abs(d7) >= EPSILON && Math.abs(d8) >= EPSILON) {
                this.mLut = new double[101];
                boolean z2 = this.mVertical;
                this.mEllipseA = d7 * ((double) (z2 ? -1 : 1));
                this.mEllipseB = d8 * ((double) (z2 ? 1 : -1));
                this.mEllipseCenterX = z2 ? d5 : d3;
                this.mEllipseCenterY = z2 ? d4 : d6;
                buildTable(d3, d4, d5, d6);
                this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                return;
            }
            this.linear = true;
            this.mX1 = d3;
            this.mX2 = d5;
            this.mY1 = d4;
            this.mY2 = d6;
            double dHypot = Math.hypot(d8, d7);
            this.mArcDistance = dHypot;
            this.mArcVelocity = dHypot * this.mOneOverDeltaTime;
            double d9 = this.mTime2;
            double d10 = this.mTime1;
            this.mEllipseCenterX = d7 / (d9 - d10);
            this.mEllipseCenterY = d8 / (d9 - d10);
        }

        private void buildTable(double d, double d2, double d3, double d4) {
            double dHypot;
            double d5 = d3 - d;
            double d6 = d2 - d4;
            int i2 = 0;
            double d7 = 0.0d;
            double d8 = 0.0d;
            double d9 = 0.0d;
            while (true) {
                double[] dArr = ourPercent;
                if (i2 >= dArr.length) {
                    break;
                }
                double d10 = d7;
                double radians = Math.toRadians((((double) i2) * 90.0d) / ((double) (dArr.length - 1)));
                double dSin = Math.sin(radians) * d5;
                double dCos = Math.cos(radians) * d6;
                if (i2 > 0) {
                    dHypot = Math.hypot(dSin - d8, dCos - d9) + d10;
                    ourPercent[i2] = dHypot;
                } else {
                    dHypot = d10;
                }
                i2++;
                d9 = dCos;
                d7 = dHypot;
                d8 = dSin;
            }
            double d11 = d7;
            this.mArcDistance = d11;
            int i3 = 0;
            while (true) {
                double[] dArr2 = ourPercent;
                if (i3 >= dArr2.length) {
                    break;
                }
                dArr2[i3] = dArr2[i3] / d11;
                i3++;
            }
            int i4 = 0;
            while (true) {
                double[] dArr3 = this.mLut;
                if (i4 >= dArr3.length) {
                    return;
                }
                double length = ((double) i4) / ((double) (dArr3.length - 1));
                int iBinarySearch = Arrays.binarySearch(ourPercent, length);
                if (iBinarySearch >= 0) {
                    this.mLut[i4] = iBinarySearch / (ourPercent.length - 1);
                } else if (iBinarySearch == -1) {
                    this.mLut[i4] = 0.0d;
                } else {
                    int i5 = -iBinarySearch;
                    int i6 = i5 - 2;
                    double[] dArr4 = ourPercent;
                    double d12 = dArr4[i6];
                    this.mLut[i4] = (((length - d12) / (dArr4[i5 - 1] - d12)) + ((double) i6)) / ((double) (dArr4.length - 1));
                }
                i4++;
            }
        }

        public double getDX() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double dHypot = this.mArcVelocity / Math.hypot(d, (-this.mEllipseB) * this.mTmpSinAngle);
            if (this.mVertical) {
                d = -d;
            }
            return d * dHypot;
        }

        public double getDY() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double d2 = (-this.mEllipseB) * this.mTmpSinAngle;
            double dHypot = this.mArcVelocity / Math.hypot(d, d2);
            return this.mVertical ? (-d2) * dHypot : d2 * dHypot;
        }

        public double getLinearDX(double d) {
            return this.mEllipseCenterX;
        }

        public double getLinearDY(double d) {
            return this.mEllipseCenterY;
        }

        public double getLinearX(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mX1;
            return ((this.mX2 - d3) * d2) + d3;
        }

        public double getLinearY(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mY1;
            return ((this.mY2 - d3) * d2) + d3;
        }

        public double getX() {
            return (this.mEllipseA * this.mTmpSinAngle) + this.mEllipseCenterX;
        }

        public double getY() {
            return (this.mEllipseB * this.mTmpCosAngle) + this.mEllipseCenterY;
        }

        public double lookup(double d) {
            if (d <= 0.0d) {
                return 0.0d;
            }
            if (d >= 1.0d) {
                return 1.0d;
            }
            double[] dArr = this.mLut;
            double length = d * ((double) (dArr.length - 1));
            int i2 = (int) length;
            double d2 = length - ((double) i2);
            double d3 = dArr[i2];
            return ((dArr[i2 + 1] - d3) * d2) + d3;
        }

        public void setPoint(double d) {
            double dLookup = lookup((this.mVertical ? this.mTime2 - d : d - this.mTime1) * this.mOneOverDeltaTime) * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(dLookup);
            this.mTmpCosAngle = Math.cos(dLookup);
        }
    }

    public ArcCurveFit(int[] iArr, double[] dArr, double[][] dArr2) {
        this.mTime = dArr;
        this.mArcs = new Arc[dArr.length - 1];
        int i2 = 0;
        int i3 = 1;
        int i4 = 1;
        while (true) {
            Arc[] arcArr = this.mArcs;
            if (i2 >= arcArr.length) {
                return;
            }
            int i5 = iArr[i2];
            if (i5 == 0) {
                i4 = 3;
            } else if (i5 == 1) {
                i3 = 1;
                i4 = 1;
            } else if (i5 == 2) {
                i3 = 2;
                i4 = 2;
            } else if (i5 == 3) {
                i3 = i3 == 1 ? 2 : 1;
                i4 = i3;
            }
            double d = dArr[i2];
            int i6 = i2 + 1;
            double d2 = dArr[i6];
            double[] dArr3 = dArr2[i2];
            double d3 = dArr3[0];
            double d4 = dArr3[1];
            double[] dArr4 = dArr2[i6];
            arcArr[i2] = new Arc(i4, d, d2, d3, d4, dArr4[0], dArr4[1]);
            i2 = i6;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double getPos(double d, int i2) {
        Arc[] arcArr = this.mArcs;
        int i3 = 0;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i3 >= arcArr2.length) {
                return Double.NaN;
            }
            Arc arc = arcArr2[i3];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    return i2 == 0 ? arc.getLinearX(d) : arc.getLinearY(d);
                }
                arc.setPoint(d);
                Arc[] arcArr3 = this.mArcs;
                return i2 == 0 ? arcArr3[i3].getX() : arcArr3[i3].getY();
            }
            i3++;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double getSlope(double d, int i2) {
        Arc[] arcArr = this.mArcs;
        int i3 = 0;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i3 >= arcArr2.length) {
                return Double.NaN;
            }
            Arc arc = arcArr2[i3];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    return i2 == 0 ? arc.getLinearDX(d) : arc.getLinearDY(d);
                }
                arc.setPoint(d);
                Arc[] arcArr3 = this.mArcs;
                return i2 == 0 ? arcArr3[i3].getDX() : arcArr3[i3].getDY();
            }
            i3++;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mTime;
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getPos(double d, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i2 >= arcArr2.length) {
                return;
            }
            Arc arc = arcArr2[i2];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    dArr[0] = arc.getLinearX(d);
                    dArr[1] = this.mArcs[i2].getLinearY(d);
                    return;
                } else {
                    arc.setPoint(d);
                    dArr[0] = this.mArcs[i2].getX();
                    dArr[1] = this.mArcs[i2].getY();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getSlope(double d, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i2 >= arcArr2.length) {
                return;
            }
            Arc arc = arcArr2[i2];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    dArr[0] = arc.getLinearDX(d);
                    dArr[1] = this.mArcs[i2].getLinearDY(d);
                    return;
                } else {
                    arc.setPoint(d);
                    dArr[0] = this.mArcs[i2].getDX();
                    dArr[1] = this.mArcs[i2].getDY();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getPos(double d, float[] fArr) {
        Arc[] arcArr = this.mArcs;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i2 >= arcArr2.length) {
                return;
            }
            Arc arc = arcArr2[i2];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    fArr[0] = (float) arc.getLinearX(d);
                    fArr[1] = (float) this.mArcs[i2].getLinearY(d);
                    return;
                } else {
                    arc.setPoint(d);
                    fArr[0] = (float) this.mArcs[i2].getX();
                    fArr[1] = (float) this.mArcs[i2].getY();
                    return;
                }
            }
            i2++;
        }
    }
}
