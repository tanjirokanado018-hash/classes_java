package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ChainRun extends WidgetRun {
    private int chainStyle;
    ArrayList<WidgetRun> widgets;

    public ChainRun(ConstraintWidget constraintWidget, int i2) {
        super(constraintWidget);
        this.widgets = new ArrayList<>();
        this.orientation = i2;
        build();
    }

    private void build() {
        ConstraintWidget constraintWidget;
        ConstraintWidget previousChainMember = this.widget;
        do {
            constraintWidget = previousChainMember;
            previousChainMember = previousChainMember.getPreviousChainMember(this.orientation);
        } while (previousChainMember != null);
        this.widget = constraintWidget;
        this.widgets.add(constraintWidget.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.widgets.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        for (WidgetRun widgetRun : this.widgets) {
            int i2 = this.orientation;
            if (i2 == 0) {
                widgetRun.widget.horizontalChainRun = this;
            } else if (i2 == 1) {
                widgetRun.widget.verticalChainRun = this;
            }
        }
        if (this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.getParent()).isRtl() && this.widgets.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.widgets;
            this.widget = arrayList.get(arrayList.size() - 1).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i2 = 0; i2 < this.widgets.size(); i2++) {
            WidgetRun widgetRun = this.widgets.get(i2);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int size = this.widgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.widgets.get(size);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00a5 A[PHI: r0 r1
      0x00a5: PHI (r0v13 int) = (r0v10 int), (r0v19 int) binds: [B:29:0x00a3, B:19:0x006d] A[DONT_GENERATE, DONT_INLINE]
      0x00a5: PHI (r1v3 androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) = 
      (r1v2 androidx.constraintlayout.solver.widgets.analyzer.DependencyNode)
      (r1v7 androidx.constraintlayout.solver.widgets.analyzer.DependencyNode)
     binds: [B:29:0x00a3, B:19:0x006d] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void apply() {
        DependencyNode target;
        int margin;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int size = this.widgets.size();
        if (size < 1) {
            return;
        }
        ConstraintWidget constraintWidget = this.widgets.get(0).widget;
        ConstraintWidget constraintWidget2 = this.widgets.get(size - 1).widget;
        if (this.orientation == 0) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
            DependencyNode target2 = getTarget(constraintAnchor, 0);
            int margin2 = constraintAnchor.getMargin();
            ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
            if (firstVisibleWidget != null) {
                margin2 = firstVisibleWidget.mLeft.getMargin();
            }
            if (target2 != null) {
                addTarget(this.start, target2, margin2);
            }
            target = getTarget(constraintAnchor2, 0);
            margin = constraintAnchor2.getMargin();
            ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
            if (lastVisibleWidget != null) {
                margin = lastVisibleWidget.mRight.getMargin();
            }
            if (target != null) {
                addTarget(this.end, target, -margin);
            }
        } else {
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
            DependencyNode target3 = getTarget(constraintAnchor3, 1);
            int margin3 = constraintAnchor3.getMargin();
            ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
            if (firstVisibleWidget2 != null) {
                margin3 = firstVisibleWidget2.mTop.getMargin();
            }
            if (target3 != null) {
                addTarget(this.start, target3, margin3);
            }
            target = getTarget(constraintAnchor4, 1);
            margin = constraintAnchor4.getMargin();
            ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
            if (lastVisibleWidget2 != null) {
                margin = lastVisibleWidget2.mBottom.getMargin();
            }
            if (target != null) {
                addTarget(this.end, target, -margin);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        for (int i2 = 0; i2 < this.widgets.size(); i2++) {
            this.widgets.get(i2).applyToWidget();
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public long getWrapDimension() {
        int size = this.widgets.size();
        long wrapDimension = 0;
        for (int i2 = 0; i2 < size; i2++) {
            WidgetRun widgetRun = this.widgets.get(i2);
            wrapDimension = ((long) widgetRun.end.margin) + widgetRun.getWrapDimension() + wrapDimension + ((long) widgetRun.start.margin);
        }
        return wrapDimension;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean supportsWrapComputation() {
        int size = this.widgets.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!this.widgets.get(i2).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String strConcat = "ChainRun ".concat(this.orientation == 0 ? "horizontal : " : "vertical : ");
        for (WidgetRun widgetRun : this.widgets) {
            strConcat = a.e(a.e(strConcat, "<") + widgetRun, "> ");
        }
        return strConcat;
    }

    /* JADX WARN: Code duplicated, block: B:120:0x01d0 A[PHI: r1 r22 r23 r24
      0x01d0: PHI (r1v51 int) = (r1v49 int), (r1v57 int) binds: [B:119:0x01ce, B:110:0x01a8] A[DONT_GENERATE, DONT_INLINE]
      0x01d0: PHI (r22v1 float) = (r22v0 float), (r22v3 float) binds: [B:119:0x01ce, B:110:0x01a8] A[DONT_GENERATE, DONT_INLINE]
      0x01d0: PHI (r23v4 boolean) = (r23v3 boolean), (r23v6 boolean) binds: [B:119:0x01ce, B:110:0x01a8] A[DONT_GENERATE, DONT_INLINE]
      0x01d0: PHI (r24v4 int) = (r24v3 int), (r24v6 int) binds: [B:119:0x01ce, B:110:0x01a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:311:0x00f6 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:66:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:67:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:69:0x00ee A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:92:0x014f  */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f2;
        boolean z2;
        int i7;
        int i8;
        int i9;
        boolean z3;
        int i10;
        float f3;
        int iMax;
        int i11;
        int i12;
        float f4;
        if (this.start.resolved && this.end.resolved) {
            ConstraintWidget parent = this.widget.getParent();
            boolean zIsRtl = (parent == null || !(parent instanceof ConstraintWidgetContainer)) ? false : ((ConstraintWidgetContainer) parent).isRtl();
            int i13 = this.end.value - this.start.value;
            int size = this.widgets.size();
            int i14 = 0;
            while (true) {
                i2 = -1;
                i3 = 8;
                if (i14 >= size) {
                    i14 = -1;
                    break;
                } else if (this.widgets.get(i14).widget.getVisibility() != 8) {
                    break;
                } else {
                    i14++;
                }
            }
            int i15 = size - 1;
            for (int i16 = i15; i16 >= 0; i16--) {
                if (this.widgets.get(i16).widget.getVisibility() != 8) {
                    i2 = i16;
                    break;
                }
            }
            int i17 = 0;
            while (true) {
                if (i17 >= 2) {
                    i4 = 0;
                    i5 = 0;
                    i6 = 0;
                    f2 = 0.0f;
                    break;
                }
                int i18 = 0;
                i5 = 0;
                i6 = 0;
                int i19 = 0;
                f2 = 0.0f;
                while (i18 < size) {
                    WidgetRun widgetRun = this.widgets.get(i18);
                    if (widgetRun.widget.getVisibility() != i3) {
                        i19++;
                        if (i18 > 0 && i18 >= i14) {
                            i5 += widgetRun.start.margin;
                        }
                        DimensionDependency dimensionDependency = widgetRun.dimension;
                        int i20 = dimensionDependency.value;
                        boolean z4 = widgetRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        if (z4) {
                            int i21 = this.orientation;
                            if (i21 == 0 && !widgetRun.widget.horizontalRun.dimension.resolved) {
                                return;
                            }
                            if (i21 == 1 && !widgetRun.widget.verticalRun.dimension.resolved) {
                                return;
                            } else {
                                i11 = i20;
                            }
                        } else {
                            i11 = i20;
                            if (widgetRun.matchConstraintsType == 1 && i17 == 0) {
                                i12 = dimensionDependency.wrapValue;
                                i6++;
                            } else {
                                if (dimensionDependency.resolved) {
                                    i12 = i11;
                                }
                                if (z4) {
                                    i5 += i12;
                                } else {
                                    i6++;
                                    f4 = widgetRun.widget.mWeight[this.orientation];
                                    if (f4 >= 0.0f) {
                                        f2 += f4;
                                    }
                                }
                                if (i18 >= i15 && i18 < i2) {
                                    i5 += -widgetRun.end.margin;
                                }
                            }
                            z4 = true;
                            if (z4) {
                                i6++;
                                f4 = widgetRun.widget.mWeight[this.orientation];
                                if (f4 >= 0.0f) {
                                    f2 += f4;
                                }
                            } else {
                                i5 += i12;
                            }
                            if (i18 >= i15) {
                            }
                        }
                        i12 = i11;
                        if (z4) {
                            i6++;
                            f4 = widgetRun.widget.mWeight[this.orientation];
                            if (f4 >= 0.0f) {
                                f2 += f4;
                            }
                        } else {
                            i5 += i12;
                        }
                        if (i18 >= i15) {
                        }
                    }
                    i18++;
                    i3 = 8;
                }
                if (i5 < i13 || i6 == 0) {
                    i4 = i19;
                    break;
                } else {
                    i17++;
                    i3 = 8;
                }
            }
            int i22 = this.start.value;
            if (zIsRtl) {
                i22 = this.end.value;
            }
            if (i5 > i13) {
                int i23 = (int) (((i5 - i13) / 2.0f) + 0.5f);
                i22 = zIsRtl ? i22 + i23 : i22 - i23;
            }
            if (i6 > 0) {
                float f5 = i13 - i5;
                int i24 = (int) ((f5 / i6) + 0.5f);
                int i25 = 0;
                int i26 = 0;
                while (i25 < size) {
                    WidgetRun widgetRun2 = this.widgets.get(i25);
                    int i27 = i24;
                    int i28 = i5;
                    if (widgetRun2.widget.getVisibility() != 8 && widgetRun2.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        DimensionDependency dimensionDependency2 = widgetRun2.dimension;
                        if (dimensionDependency2.resolved) {
                            z3 = zIsRtl;
                            i10 = i4;
                            i22 = i22;
                            f3 = f5;
                        } else {
                            int i29 = f2 > 0.0f ? (int) (((widgetRun2.widget.mWeight[this.orientation] * f5) / f2) + 0.5f) : i27;
                            if (this.orientation == 0) {
                                ConstraintWidget constraintWidget = widgetRun2.widget;
                                f3 = f5;
                                int i30 = constraintWidget.mMatchConstraintMaxWidth;
                                z3 = zIsRtl;
                                i10 = i4;
                                iMax = Math.max(constraintWidget.mMatchConstraintMinWidth, widgetRun2.matchConstraintsType == 1 ? Math.min(i29, dimensionDependency2.wrapValue) : i29);
                                if (i30 > 0) {
                                    iMax = Math.min(i30, iMax);
                                }
                                if (iMax != i29) {
                                    i26++;
                                    i29 = iMax;
                                }
                            } else {
                                z3 = zIsRtl;
                                i10 = i4;
                                f3 = f5;
                                ConstraintWidget constraintWidget2 = widgetRun2.widget;
                                int i31 = constraintWidget2.mMatchConstraintMaxHeight;
                                iMax = Math.max(constraintWidget2.mMatchConstraintMinHeight, widgetRun2.matchConstraintsType == 1 ? Math.min(i29, dimensionDependency2.wrapValue) : i29);
                                if (i31 > 0) {
                                    iMax = Math.min(i31, iMax);
                                }
                                if (iMax != i29) {
                                    i26++;
                                    i29 = iMax;
                                }
                            }
                            widgetRun2.dimension.resolve(i29);
                        }
                    } else {
                        z3 = zIsRtl;
                        i10 = i4;
                        i22 = i22;
                        f3 = f5;
                    }
                    i25++;
                    i24 = i27;
                    i5 = i28;
                    i22 = i22;
                    f5 = f3;
                    zIsRtl = z3;
                    i4 = i10;
                }
                z2 = zIsRtl;
                i7 = i4;
                i8 = i22;
                int i32 = i5;
                if (i26 > 0) {
                    i6 -= i26;
                    i5 = 0;
                    for (int i33 = 0; i33 < size; i33++) {
                        WidgetRun widgetRun3 = this.widgets.get(i33);
                        if (widgetRun3.widget.getVisibility() != 8) {
                            if (i33 > 0 && i33 >= i14) {
                                i5 += widgetRun3.start.margin;
                            }
                            i5 += widgetRun3.dimension.value;
                            if (i33 < i15 && i33 < i2) {
                                i5 += -widgetRun3.end.margin;
                            }
                        }
                    }
                } else {
                    i5 = i32;
                }
                i9 = 2;
                if (this.chainStyle == 2 && i26 == 0) {
                    this.chainStyle = 0;
                }
            } else {
                z2 = zIsRtl;
                i7 = i4;
                i8 = i22;
                i9 = 2;
            }
            if (i5 > i13) {
                this.chainStyle = i9;
            }
            if (i7 > 0 && i6 == 0 && i14 == i2) {
                this.chainStyle = i9;
            }
            int i34 = this.chainStyle;
            int i35 = i7;
            if (i34 == 1) {
                int i36 = i35 > 1 ? (i13 - i5) / (i35 - 1) : i35 == 1 ? (i13 - i5) / 2 : 0;
                if (i6 > 0) {
                    i36 = 0;
                }
                int i37 = i8;
                for (int i38 = 0; i38 < size; i38++) {
                    WidgetRun widgetRun4 = this.widgets.get(z2 ? size - (i38 + 1) : i38);
                    if (widgetRun4.widget.getVisibility() == 8) {
                        widgetRun4.start.resolve(i37);
                        widgetRun4.end.resolve(i37);
                    } else {
                        if (i38 > 0) {
                            i37 = z2 ? i37 - i36 : i37 + i36;
                        }
                        if (i38 > 0 && i38 >= i14) {
                            int i39 = widgetRun4.start.margin;
                            i37 = z2 ? i37 - i39 : i37 + i39;
                        }
                        (z2 ? widgetRun4.end : widgetRun4.start).resolve(i37);
                        DimensionDependency dimensionDependency3 = widgetRun4.dimension;
                        int i40 = dimensionDependency3.value;
                        if (widgetRun4.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun4.matchConstraintsType == 1) {
                            i40 = dimensionDependency3.wrapValue;
                        }
                        i37 = z2 ? i37 - i40 : i37 + i40;
                        (z2 ? widgetRun4.start : widgetRun4.end).resolve(i37);
                        widgetRun4.resolved = true;
                        if (i38 < i15 && i38 < i2) {
                            int i41 = -widgetRun4.end.margin;
                            i37 = z2 ? i37 - i41 : i37 + i41;
                        }
                    }
                }
                return;
            }
            if (i34 == 0) {
                int i42 = (i13 - i5) / (i35 + 1);
                if (i6 > 0) {
                    i42 = 0;
                }
                int i43 = i8;
                for (int i44 = 0; i44 < size; i44++) {
                    WidgetRun widgetRun5 = this.widgets.get(z2 ? size - (i44 + 1) : i44);
                    if (widgetRun5.widget.getVisibility() == 8) {
                        widgetRun5.start.resolve(i43);
                        widgetRun5.end.resolve(i43);
                    } else {
                        int i45 = z2 ? i43 - i42 : i43 + i42;
                        if (i44 > 0 && i44 >= i14) {
                            int i46 = widgetRun5.start.margin;
                            i45 = z2 ? i45 - i46 : i45 + i46;
                        }
                        (z2 ? widgetRun5.end : widgetRun5.start).resolve(i45);
                        DimensionDependency dimensionDependency4 = widgetRun5.dimension;
                        int iMin = dimensionDependency4.value;
                        if (widgetRun5.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun5.matchConstraintsType == 1) {
                            iMin = Math.min(iMin, dimensionDependency4.wrapValue);
                        }
                        i43 = z2 ? i45 - iMin : i45 + iMin;
                        (z2 ? widgetRun5.start : widgetRun5.end).resolve(i43);
                        if (i44 < i15 && i44 < i2) {
                            int i47 = -widgetRun5.end.margin;
                            i43 = z2 ? i43 - i47 : i43 + i47;
                        }
                    }
                }
                return;
            }
            if (i34 == 2) {
                float horizontalBiasPercent = this.orientation == 0 ? this.widget.getHorizontalBiasPercent() : this.widget.getVerticalBiasPercent();
                if (z2) {
                    horizontalBiasPercent = 1.0f - horizontalBiasPercent;
                }
                int i48 = (int) (((i13 - i5) * horizontalBiasPercent) + 0.5f);
                if (i48 < 0 || i6 > 0) {
                    i48 = 0;
                }
                int i49 = z2 ? i8 - i48 : i8 + i48;
                for (int i50 = 0; i50 < size; i50++) {
                    WidgetRun widgetRun6 = this.widgets.get(z2 ? size - (i50 + 1) : i50);
                    if (widgetRun6.widget.getVisibility() == 8) {
                        widgetRun6.start.resolve(i49);
                        widgetRun6.end.resolve(i49);
                    } else {
                        if (i50 > 0 && i50 >= i14) {
                            int i51 = widgetRun6.start.margin;
                            i49 = z2 ? i49 - i51 : i49 + i51;
                        }
                        (z2 ? widgetRun6.end : widgetRun6.start).resolve(i49);
                        DimensionDependency dimensionDependency5 = widgetRun6.dimension;
                        int i52 = dimensionDependency5.value;
                        if (widgetRun6.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun6.matchConstraintsType == 1) {
                            i52 = dimensionDependency5.wrapValue;
                        }
                        i49 = z2 ? i49 - i52 : i49 + i52;
                        (z2 ? widgetRun6.start : widgetRun6.end).resolve(i49);
                        if (i50 < i15 && i50 < i2) {
                            int i53 = -widgetRun6.end.margin;
                            i49 = z2 ? i49 - i53 : i49 + i53;
                        }
                    }
                }
            }
        }
    }
}
