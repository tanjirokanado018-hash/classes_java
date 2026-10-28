package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class VerticalWidgetRun extends WidgetRun {
    public DependencyNode baseline;
    DimensionDependency baselineDimension;

    /* JADX INFO: renamed from: androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public VerticalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        DependencyNode dependencyNode = new DependencyNode(this);
        this.baseline = dependencyNode;
        this.baselineDimension = null;
        this.start.type = DependencyNode.Type.TOP;
        this.end.type = DependencyNode.Type.BOTTOM;
        dependencyNode.type = DependencyNode.Type.BASELINE;
        this.orientation = 1;
    }

    /* JADX WARN: Code duplicated, block: B:151:0x03d5 A[PHI: r0
      0x03d5: PHI (r0v32 androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun) = 
      (r0v23 androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun)
      (r0v47 androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun)
     binds: [B:150:0x03d3, B:124:0x032e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x02d9, code lost:
    
        if (r10.widget.hasBaseline() != false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02db, code lost:
    
        r0 = r10.baseline;
        r1 = r10.start;
        r2 = r10.baselineDimension;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x035e, code lost:
    
        if (r10.widget.hasBaseline() != false) goto L110;
     */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void apply() {
        /*
            Method dump skipped, instruction units count: 1022
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun.apply():void");
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.widget.setY(dependencyNode.value);
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.baseline.clear();
        this.baseline.resolved = false;
        this.dimension.resolved = false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean supportsWrapComputation() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultHeight == 0;
    }

    public String toString() {
        return "VerticalRun " + this.widget.getDebugName();
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0056  */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        float f2;
        float dimensionRatio;
        int dimensionRatio2;
        int i2 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
        if (i2 == 1) {
            updateRunStart(dependency);
        } else if (i2 == 2) {
            updateRunEnd(dependency);
        } else if (i2 == 3) {
            ConstraintWidget constraintWidget = this.widget;
            updateRunCenter(dependency, constraintWidget.mTop, constraintWidget.mBottom, 1);
            return;
        }
        DimensionDependency dimensionDependency = this.dimension;
        if (dimensionDependency.readyToSolve && !dimensionDependency.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.widget;
            int i3 = constraintWidget2.mMatchConstraintDefaultHeight;
            if (i3 == 2) {
                ConstraintWidget parent = constraintWidget2.getParent();
                if (parent != null) {
                    DimensionDependency dimensionDependency2 = parent.verticalRun.dimension;
                    if (dimensionDependency2.resolved) {
                        f2 = this.widget.mMatchConstraintPercentHeight;
                        dimensionRatio = dimensionDependency2.value;
                        dimensionRatio2 = (int) ((dimensionRatio * f2) + 0.5f);
                        this.dimension.resolve(dimensionRatio2);
                    }
                }
            } else if (i3 == 3 && constraintWidget2.horizontalRun.dimension.resolved) {
                int dimensionRatioSide = constraintWidget2.getDimensionRatioSide();
                if (dimensionRatioSide == -1) {
                    ConstraintWidget constraintWidget3 = this.widget;
                    dimensionRatio2 = (int) ((constraintWidget3.horizontalRun.dimension.value / constraintWidget3.getDimensionRatio()) + 0.5f);
                } else if (dimensionRatioSide == 0) {
                    ConstraintWidget constraintWidget4 = this.widget;
                    f2 = constraintWidget4.horizontalRun.dimension.value;
                    dimensionRatio = constraintWidget4.getDimensionRatio();
                    dimensionRatio2 = (int) ((dimensionRatio * f2) + 0.5f);
                } else if (dimensionRatioSide != 1) {
                    dimensionRatio2 = 0;
                } else {
                    ConstraintWidget constraintWidget5 = this.widget;
                    dimensionRatio2 = (int) ((constraintWidget5.horizontalRun.dimension.value / constraintWidget5.getDimensionRatio()) + 0.5f);
                }
                this.dimension.resolve(dimensionRatio2);
            }
        }
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.readyToSolve) {
                if (dependencyNode.resolved && dependencyNode2.resolved && this.dimension.resolved) {
                    return;
                }
                if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    ConstraintWidget constraintWidget6 = this.widget;
                    if (constraintWidget6.mMatchConstraintDefaultWidth == 0 && !constraintWidget6.isInVerticalChain()) {
                        DependencyNode dependencyNode3 = this.start.targets.get(0);
                        DependencyNode dependencyNode4 = this.end.targets.get(0);
                        int i4 = dependencyNode3.value;
                        DependencyNode dependencyNode5 = this.start;
                        int i5 = i4 + dependencyNode5.margin;
                        int i6 = dependencyNode4.value + this.end.margin;
                        dependencyNode5.resolve(i5);
                        this.end.resolve(i6);
                        this.dimension.resolve(i6 - i5);
                        return;
                    }
                }
                if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                    DependencyNode dependencyNode6 = this.start.targets.get(0);
                    int i7 = (this.end.targets.get(0).value + this.end.margin) - (dependencyNode6.value + this.start.margin);
                    DimensionDependency dimensionDependency3 = this.dimension;
                    int i8 = dimensionDependency3.wrapValue;
                    if (i7 < i8) {
                        dimensionDependency3.resolve(i7);
                    } else {
                        dimensionDependency3.resolve(i8);
                    }
                }
                if (this.dimension.resolved && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                    DependencyNode dependencyNode7 = this.start.targets.get(0);
                    DependencyNode dependencyNode8 = this.end.targets.get(0);
                    int i9 = dependencyNode7.value + this.start.margin;
                    int i10 = dependencyNode8.value + this.end.margin;
                    float verticalBiasPercent = this.widget.getVerticalBiasPercent();
                    if (dependencyNode7 == dependencyNode8) {
                        i9 = dependencyNode7.value;
                        i10 = dependencyNode8.value;
                        verticalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) ((((i10 - i9) - this.dimension.value) * verticalBiasPercent) + i9 + 0.5f));
                    this.end.resolve(this.start.value + this.dimension.value);
                }
            }
        }
    }
}
