package androidx.constraintlayout.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.graphics.drawable.a;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ConstraintProperties {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int END = 7;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int WRAP_CONTENT = -2;
    ConstraintLayout.LayoutParams mParams;
    View mView;

    public ConstraintProperties(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ConstraintLayout.LayoutParams)) {
            throw new RuntimeException("Only children of ConstraintLayout.LayoutParams supported");
        }
        this.mParams = (ConstraintLayout.LayoutParams) layoutParams;
        this.mView = view;
    }

    private String sideToString(int i2) {
        switch (i2) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return "top";
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return "end";
            default:
                return "undefined";
        }
    }

    public ConstraintProperties addToHorizontalChain(int i2, int i3) {
        connect(1, i2, i2 == 0 ? 1 : 2, 0);
        connect(2, i3, i3 == 0 ? 2 : 1, 0);
        if (i2 != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i2)).connect(2, this.mView.getId(), 1, 0);
        }
        if (i3 != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i3)).connect(1, this.mView.getId(), 2, 0);
        }
        return this;
    }

    public ConstraintProperties addToHorizontalChainRTL(int i2, int i3) {
        connect(6, i2, i2 == 0 ? 6 : 7, 0);
        connect(7, i3, i3 == 0 ? 7 : 6, 0);
        if (i2 != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i2)).connect(7, this.mView.getId(), 6, 0);
        }
        if (i3 != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i3)).connect(6, this.mView.getId(), 7, 0);
        }
        return this;
    }

    public ConstraintProperties addToVerticalChain(int i2, int i3) {
        connect(3, i2, i2 == 0 ? 3 : 4, 0);
        connect(4, i3, i3 == 0 ? 4 : 3, 0);
        if (i2 != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i2)).connect(4, this.mView.getId(), 3, 0);
        }
        if (i3 != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i3)).connect(3, this.mView.getId(), 4, 0);
        }
        return this;
    }

    public ConstraintProperties alpha(float f2) {
        this.mView.setAlpha(f2);
        return this;
    }

    public void apply() {
    }

    /* JADX WARN: Code duplicated, block: B:18:0x002c A[PHI: r0 r1
      0x002c: PHI (r0v6 int) = (r0v5 int), (r0v5 int), (r0v7 int), (r0v7 int) binds: [B:10:0x0013, B:11:0x0015, B:14:0x001a, B:15:0x001c] A[DONT_GENERATE, DONT_INLINE]
      0x002c: PHI (r1v1 int) = (r1v0 int), (r1v0 int), (r1v2 int), (r1v2 int) binds: [B:10:0x0013, B:11:0x0015, B:14:0x001a, B:15:0x001c] A[DONT_GENERATE, DONT_INLINE]] */
    public ConstraintProperties center(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        if (i4 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (i7 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (f2 <= 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        }
        int i8 = 2;
        int i9 = 1;
        if (i3 == 1 || i3 == 2) {
            connect(i9, i2, i3, i4);
            connect(i8, i5, i6, i7);
            this.mParams.horizontalBias = f2;
        } else {
            i8 = 7;
            i9 = 6;
            if (i3 == 6 || i3 == 7) {
                connect(i9, i2, i3, i4);
                connect(i8, i5, i6, i7);
                this.mParams.horizontalBias = f2;
            } else {
                connect(3, i2, i3, i4);
                connect(4, i5, i6, i7);
                this.mParams.verticalBias = f2;
            }
        }
        return this;
    }

    public ConstraintProperties centerHorizontally(int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (i2 == 0) {
            i6 = 0;
            i3 = 1;
            i4 = 0;
            i7 = 0;
            i5 = 2;
        } else {
            i3 = 2;
            i4 = 0;
            i5 = 1;
            i6 = i2;
            i7 = i2;
        }
        center(i6, i3, i4, i7, i5, 0, 0.5f);
        return this;
    }

    public ConstraintProperties centerHorizontallyRtl(int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (i2 == 0) {
            i6 = 0;
            i3 = 6;
            i4 = 0;
            i7 = 0;
            i5 = 7;
        } else {
            i3 = 7;
            i4 = 0;
            i5 = 6;
            i6 = i2;
            i7 = i2;
        }
        center(i6, i3, i4, i7, i5, 0, 0.5f);
        return this;
    }

    public ConstraintProperties centerVertically(int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (i2 == 0) {
            i6 = 0;
            i3 = 3;
            i4 = 0;
            i7 = 0;
            i5 = 4;
        } else {
            i3 = 4;
            i4 = 0;
            i5 = 3;
            i6 = i2;
            i7 = i2;
        }
        center(i6, i3, i4, i7, i5, 0, 0.5f);
        return this;
    }

    public ConstraintProperties connect(int i2, int i3, int i4, int i5) {
        ConstraintLayout.LayoutParams layoutParams;
        ConstraintLayout.LayoutParams layoutParams2;
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    ConstraintLayout.LayoutParams layoutParams3 = this.mParams;
                    layoutParams3.leftToLeft = i3;
                    layoutParams3.leftToRight = -1;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException(a.g(new StringBuilder("Left to "), sideToString(i4), " undefined"));
                    }
                    ConstraintLayout.LayoutParams layoutParams4 = this.mParams;
                    layoutParams4.leftToRight = i3;
                    layoutParams4.leftToLeft = -1;
                }
                ((ViewGroup.MarginLayoutParams) this.mParams).leftMargin = i5;
                return this;
            case 2:
                if (i4 == 1) {
                    ConstraintLayout.LayoutParams layoutParams5 = this.mParams;
                    layoutParams5.rightToLeft = i3;
                    layoutParams5.rightToRight = -1;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException(a.g(new StringBuilder("right to "), sideToString(i4), " undefined"));
                    }
                    ConstraintLayout.LayoutParams layoutParams6 = this.mParams;
                    layoutParams6.rightToRight = i3;
                    layoutParams6.rightToLeft = -1;
                }
                ((ViewGroup.MarginLayoutParams) this.mParams).rightMargin = i5;
                return this;
            case 3:
                if (i4 == 3) {
                    layoutParams = this.mParams;
                    layoutParams.topToTop = i3;
                    layoutParams.topToBottom = -1;
                } else {
                    if (i4 != 4) {
                        throw new IllegalArgumentException(a.g(new StringBuilder("right to "), sideToString(i4), " undefined"));
                    }
                    layoutParams = this.mParams;
                    layoutParams.topToBottom = i3;
                    layoutParams.topToTop = -1;
                }
                layoutParams.baselineToBaseline = -1;
                ((ViewGroup.MarginLayoutParams) this.mParams).topMargin = i5;
                return this;
            case 4:
                if (i4 == 4) {
                    layoutParams2 = this.mParams;
                    layoutParams2.bottomToBottom = i3;
                    layoutParams2.bottomToTop = -1;
                } else {
                    if (i4 != 3) {
                        throw new IllegalArgumentException(a.g(new StringBuilder("right to "), sideToString(i4), " undefined"));
                    }
                    layoutParams2 = this.mParams;
                    layoutParams2.bottomToTop = i3;
                    layoutParams2.bottomToBottom = -1;
                }
                layoutParams2.baselineToBaseline = -1;
                ((ViewGroup.MarginLayoutParams) this.mParams).bottomMargin = i5;
                return this;
            case 5:
                if (i4 != 5) {
                    throw new IllegalArgumentException(a.g(new StringBuilder("right to "), sideToString(i4), " undefined"));
                }
                ConstraintLayout.LayoutParams layoutParams7 = this.mParams;
                layoutParams7.baselineToBaseline = i3;
                layoutParams7.bottomToBottom = -1;
                layoutParams7.bottomToTop = -1;
                layoutParams7.topToTop = -1;
                layoutParams7.topToBottom = -1;
                return this;
            case 6:
                if (i4 == 6) {
                    ConstraintLayout.LayoutParams layoutParams8 = this.mParams;
                    layoutParams8.startToStart = i3;
                    layoutParams8.startToEnd = -1;
                } else {
                    if (i4 != 7) {
                        throw new IllegalArgumentException(a.g(new StringBuilder("right to "), sideToString(i4), " undefined"));
                    }
                    ConstraintLayout.LayoutParams layoutParams9 = this.mParams;
                    layoutParams9.startToEnd = i3;
                    layoutParams9.startToStart = -1;
                }
                this.mParams.setMarginStart(i5);
                return this;
            case 7:
                if (i4 == 7) {
                    ConstraintLayout.LayoutParams layoutParams10 = this.mParams;
                    layoutParams10.endToEnd = i3;
                    layoutParams10.endToStart = -1;
                } else {
                    if (i4 != 6) {
                        throw new IllegalArgumentException(a.g(new StringBuilder("right to "), sideToString(i4), " undefined"));
                    }
                    ConstraintLayout.LayoutParams layoutParams11 = this.mParams;
                    layoutParams11.endToStart = i3;
                    layoutParams11.endToEnd = -1;
                }
                this.mParams.setMarginEnd(i5);
                return this;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append(sideToString(i2));
                sb.append(" to ");
                throw new IllegalArgumentException(a.g(sb, sideToString(i4), " unknown"));
        }
    }

    public ConstraintProperties constrainDefaultHeight(int i2) {
        this.mParams.matchConstraintDefaultHeight = i2;
        return this;
    }

    public ConstraintProperties constrainDefaultWidth(int i2) {
        this.mParams.matchConstraintDefaultWidth = i2;
        return this;
    }

    public ConstraintProperties constrainHeight(int i2) {
        ((ViewGroup.MarginLayoutParams) this.mParams).height = i2;
        return this;
    }

    public ConstraintProperties constrainMaxHeight(int i2) {
        this.mParams.matchConstraintMaxHeight = i2;
        return this;
    }

    public ConstraintProperties constrainMaxWidth(int i2) {
        this.mParams.matchConstraintMaxWidth = i2;
        return this;
    }

    public ConstraintProperties constrainMinHeight(int i2) {
        this.mParams.matchConstraintMinHeight = i2;
        return this;
    }

    public ConstraintProperties constrainMinWidth(int i2) {
        this.mParams.matchConstraintMinWidth = i2;
        return this;
    }

    public ConstraintProperties constrainWidth(int i2) {
        ((ViewGroup.MarginLayoutParams) this.mParams).width = i2;
        return this;
    }

    public ConstraintProperties dimensionRatio(String str) {
        this.mParams.dimensionRatio = str;
        return this;
    }

    public ConstraintProperties elevation(float f2) {
        this.mView.setElevation(f2);
        return this;
    }

    public ConstraintProperties goneMargin(int i2, int i3) {
        switch (i2) {
            case 1:
                this.mParams.goneLeftMargin = i3;
                return this;
            case 2:
                this.mParams.goneRightMargin = i3;
                return this;
            case 3:
                this.mParams.goneTopMargin = i3;
                return this;
            case 4:
                this.mParams.goneBottomMargin = i3;
                return this;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                this.mParams.goneStartMargin = i3;
                return this;
            case 7:
                this.mParams.goneEndMargin = i3;
                return this;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public ConstraintProperties horizontalBias(float f2) {
        this.mParams.horizontalBias = f2;
        return this;
    }

    public ConstraintProperties horizontalChainStyle(int i2) {
        this.mParams.horizontalChainStyle = i2;
        return this;
    }

    public ConstraintProperties horizontalWeight(float f2) {
        this.mParams.horizontalWeight = f2;
        return this;
    }

    public ConstraintProperties margin(int i2, int i3) {
        switch (i2) {
            case 1:
                ((ViewGroup.MarginLayoutParams) this.mParams).leftMargin = i3;
                return this;
            case 2:
                ((ViewGroup.MarginLayoutParams) this.mParams).rightMargin = i3;
                return this;
            case 3:
                ((ViewGroup.MarginLayoutParams) this.mParams).topMargin = i3;
                return this;
            case 4:
                ((ViewGroup.MarginLayoutParams) this.mParams).bottomMargin = i3;
                return this;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                this.mParams.setMarginStart(i3);
                return this;
            case 7:
                this.mParams.setMarginEnd(i3);
                return this;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public ConstraintProperties removeConstraints(int i2) {
        switch (i2) {
            case 1:
                ConstraintLayout.LayoutParams layoutParams = this.mParams;
                layoutParams.leftToRight = -1;
                layoutParams.leftToLeft = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = -1;
                layoutParams.goneLeftMargin = -1;
                return this;
            case 2:
                ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
                layoutParams2.rightToRight = -1;
                layoutParams2.rightToLeft = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = -1;
                layoutParams2.goneRightMargin = -1;
                return this;
            case 3:
                ConstraintLayout.LayoutParams layoutParams3 = this.mParams;
                layoutParams3.topToBottom = -1;
                layoutParams3.topToTop = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = -1;
                layoutParams3.goneTopMargin = -1;
                return this;
            case 4:
                ConstraintLayout.LayoutParams layoutParams4 = this.mParams;
                layoutParams4.bottomToTop = -1;
                layoutParams4.bottomToBottom = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams4).bottomMargin = -1;
                layoutParams4.goneBottomMargin = -1;
                return this;
            case 5:
                this.mParams.baselineToBaseline = -1;
                return this;
            case 6:
                ConstraintLayout.LayoutParams layoutParams5 = this.mParams;
                layoutParams5.startToEnd = -1;
                layoutParams5.startToStart = -1;
                layoutParams5.setMarginStart(-1);
                this.mParams.goneStartMargin = -1;
                return this;
            case 7:
                ConstraintLayout.LayoutParams layoutParams6 = this.mParams;
                layoutParams6.endToStart = -1;
                layoutParams6.endToEnd = -1;
                layoutParams6.setMarginEnd(-1);
                this.mParams.goneEndMargin = -1;
                return this;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public ConstraintProperties removeFromHorizontalChain() {
        ConstraintLayout.LayoutParams layoutParams = this.mParams;
        int i2 = layoutParams.leftToRight;
        int i3 = layoutParams.rightToLeft;
        if (i2 == -1 && i3 == -1) {
            int i4 = layoutParams.startToEnd;
            int i5 = layoutParams.endToStart;
            if (i4 != -1 || i5 != -1) {
                ConstraintProperties constraintProperties = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i4));
                ConstraintProperties constraintProperties2 = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i5));
                ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
                if (i4 != -1 && i5 != -1) {
                    constraintProperties.connect(7, i5, 6, 0);
                    constraintProperties2.connect(6, i2, 7, 0);
                } else if (i2 != -1 || i5 != -1) {
                    int i6 = layoutParams2.rightToRight;
                    if (i6 != -1) {
                        constraintProperties.connect(7, i6, 7, 0);
                    } else {
                        int i7 = layoutParams2.leftToLeft;
                        if (i7 != -1) {
                            constraintProperties2.connect(6, i7, 6, 0);
                        }
                    }
                }
            }
            removeConstraints(6);
            removeConstraints(7);
        } else {
            ConstraintProperties constraintProperties3 = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i2));
            ConstraintProperties constraintProperties4 = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i3));
            ConstraintLayout.LayoutParams layoutParams3 = this.mParams;
            if (i2 != -1 && i3 != -1) {
                constraintProperties3.connect(2, i3, 1, 0);
                constraintProperties4.connect(1, i2, 2, 0);
            } else if (i2 != -1 || i3 != -1) {
                int i8 = layoutParams3.rightToRight;
                if (i8 != -1) {
                    constraintProperties3.connect(2, i8, 2, 0);
                } else {
                    int i9 = layoutParams3.leftToLeft;
                    if (i9 != -1) {
                        constraintProperties4.connect(1, i9, 1, 0);
                    }
                }
            }
            removeConstraints(1);
            removeConstraints(2);
        }
        return this;
    }

    public ConstraintProperties removeFromVerticalChain() {
        ConstraintLayout.LayoutParams layoutParams = this.mParams;
        int i2 = layoutParams.topToBottom;
        int i3 = layoutParams.bottomToTop;
        if (i2 != -1 || i3 != -1) {
            ConstraintProperties constraintProperties = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i2));
            ConstraintProperties constraintProperties2 = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(i3));
            ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
            if (i2 != -1 && i3 != -1) {
                constraintProperties.connect(4, i3, 3, 0);
                constraintProperties2.connect(3, i2, 4, 0);
            } else if (i2 != -1 || i3 != -1) {
                int i4 = layoutParams2.bottomToBottom;
                if (i4 != -1) {
                    constraintProperties.connect(4, i4, 4, 0);
                } else {
                    int i5 = layoutParams2.topToTop;
                    if (i5 != -1) {
                        constraintProperties2.connect(3, i5, 3, 0);
                    }
                }
            }
        }
        removeConstraints(3);
        removeConstraints(4);
        return this;
    }

    public ConstraintProperties rotation(float f2) {
        this.mView.setRotation(f2);
        return this;
    }

    public ConstraintProperties rotationX(float f2) {
        this.mView.setRotationX(f2);
        return this;
    }

    public ConstraintProperties rotationY(float f2) {
        this.mView.setRotationY(f2);
        return this;
    }

    public ConstraintProperties scaleX(float f2) {
        this.mView.setScaleY(f2);
        return this;
    }

    public ConstraintProperties scaleY(float f2) {
        return this;
    }

    public ConstraintProperties transformPivot(float f2, float f3) {
        this.mView.setPivotX(f2);
        this.mView.setPivotY(f3);
        return this;
    }

    public ConstraintProperties transformPivotX(float f2) {
        this.mView.setPivotX(f2);
        return this;
    }

    public ConstraintProperties transformPivotY(float f2) {
        this.mView.setPivotY(f2);
        return this;
    }

    public ConstraintProperties translation(float f2, float f3) {
        this.mView.setTranslationX(f2);
        this.mView.setTranslationY(f3);
        return this;
    }

    public ConstraintProperties translationX(float f2) {
        this.mView.setTranslationX(f2);
        return this;
    }

    public ConstraintProperties translationY(float f2) {
        this.mView.setTranslationY(f2);
        return this;
    }

    public ConstraintProperties translationZ(float f2) {
        this.mView.setTranslationZ(f2);
        return this;
    }

    public ConstraintProperties verticalBias(float f2) {
        this.mParams.verticalBias = f2;
        return this;
    }

    public ConstraintProperties verticalChainStyle(int i2) {
        this.mParams.verticalChainStyle = i2;
        return this;
    }

    public ConstraintProperties verticalWeight(float f2) {
        this.mParams.verticalWeight = f2;
        return this;
    }

    public ConstraintProperties visibility(int i2) {
        this.mView.setVisibility(i2);
        return this;
    }

    public ConstraintProperties centerHorizontally(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(1, i2, i3, i4);
        connect(2, i5, i6, i7);
        this.mParams.horizontalBias = f2;
        return this;
    }

    public ConstraintProperties centerHorizontallyRtl(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(6, i2, i3, i4);
        connect(7, i5, i6, i7);
        this.mParams.horizontalBias = f2;
        return this;
    }

    public ConstraintProperties centerVertically(int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(3, i2, i3, i4);
        connect(4, i5, i6, i7);
        this.mParams.verticalBias = f2;
        return this;
    }
}
