package androidx.appcompat.widget;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class RtlSpacingHelper {
    public static final int UNDEFINED = Integer.MIN_VALUE;
    private int mLeft = 0;
    private int mRight = 0;
    private int mStart = Integer.MIN_VALUE;
    private int mEnd = Integer.MIN_VALUE;
    private int mExplicitLeft = 0;
    private int mExplicitRight = 0;
    private boolean mIsRtl = false;
    private boolean mIsRelative = false;

    public int getEnd() {
        return this.mIsRtl ? this.mLeft : this.mRight;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getStart() {
        return this.mIsRtl ? this.mRight : this.mLeft;
    }

    public void setAbsolute(int i2, int i3) {
        this.mIsRelative = false;
        if (i2 != Integer.MIN_VALUE) {
            this.mExplicitLeft = i2;
            this.mLeft = i2;
        }
        if (i3 != Integer.MIN_VALUE) {
            this.mExplicitRight = i3;
            this.mRight = i3;
        }
    }

    public void setDirection(boolean z2) {
        int i2;
        if (z2 == this.mIsRtl) {
            return;
        }
        this.mIsRtl = z2;
        if (this.mIsRelative) {
            if (z2) {
                int i3 = this.mEnd;
                if (i3 == Integer.MIN_VALUE) {
                    i3 = this.mExplicitLeft;
                }
                this.mLeft = i3;
                i2 = this.mStart;
                if (i2 == Integer.MIN_VALUE) {
                }
            } else {
                int i4 = this.mStart;
                if (i4 == Integer.MIN_VALUE) {
                    i4 = this.mExplicitLeft;
                }
                this.mLeft = i4;
                i2 = this.mEnd;
                if (i2 == Integer.MIN_VALUE) {
                }
            }
            this.mRight = i2;
        }
        this.mLeft = this.mExplicitLeft;
        i2 = this.mExplicitRight;
        this.mRight = i2;
    }

    public void setRelative(int i2, int i3) {
        this.mStart = i2;
        this.mEnd = i3;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if (i3 != Integer.MIN_VALUE) {
                this.mLeft = i3;
            }
            if (i2 != Integer.MIN_VALUE) {
                this.mRight = i2;
                return;
            }
            return;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mLeft = i2;
        }
        if (i3 != Integer.MIN_VALUE) {
            this.mRight = i3;
        }
    }
}
