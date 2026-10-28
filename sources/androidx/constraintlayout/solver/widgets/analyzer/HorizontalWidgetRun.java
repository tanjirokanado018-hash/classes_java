package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    /* JADX INFO: renamed from: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun$1, reason: invalid class name */
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

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    private void computeInsetRatio(int[] iArr, int i2, int i3, int i4, int i5, float f2, int i6) {
        int i7 = i3 - i2;
        int i8 = i5 - i4;
        if (i6 != -1) {
            if (i6 == 0) {
                iArr[0] = (int) ((i8 * f2) + 0.5f);
                iArr[1] = i8;
                return;
            } else {
                if (i6 != 1) {
                    return;
                }
                iArr[0] = i7;
                iArr[1] = (int) ((i7 * f2) + 0.5f);
                return;
            }
        }
        int i9 = (int) ((i8 * f2) + 0.5f);
        int i10 = (int) ((i7 / f2) + 0.5f);
        if (i9 <= i7) {
            iArr[0] = i9;
            iArr[1] = i8;
        } else if (i10 <= i8) {
            iArr[0] = i7;
            iArr[1] = i10;
        }
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0333  */
    /* JADX WARN: Code duplicated, block: B:104:0x0339  */
    /* JADX WARN: Code duplicated, block: B:105:0x0346  */
    /* JADX WARN: Code duplicated, block: B:107:0x034c  */
    /* JADX WARN: Code duplicated, block: B:109:0x0352  */
    /* JADX WARN: Code duplicated, block: B:110:0x036d  */
    /* JADX WARN: Code duplicated, block: B:126:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:127:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x01c1  */
    /* JADX WARN: Code duplicated, block: B:71:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:73:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:76:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:78:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:80:0x01f1  */
    /* JADX WARN: Code duplicated, block: B:83:0x0245  */
    /* JADX WARN: Code duplicated, block: B:85:0x024d  */
    /* JADX WARN: Code duplicated, block: B:86:0x0265  */
    /* JADX WARN: Code duplicated, block: B:88:0x026e  */
    /* JADX WARN: Code duplicated, block: B:89:0x02b8  */
    /* JADX WARN: Code duplicated, block: B:92:0x02bf  */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget constraintWidget;
        ConstraintAnchor[] constraintAnchorArr;
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        DependencyNode target;
        DependencyNode dependencyNode;
        int x2;
        DependencyNode target2;
        DependencyNode dependencyNode2;
        ConstraintAnchor constraintAnchor4;
        ConstraintWidget constraintWidget2;
        int i2;
        ConstraintWidget parent2;
        List<Dependency> list;
        Dependency dependency;
        DependencyNode dependencyNode3;
        DependencyNode target3;
        DependencyNode dependencyNode4;
        int x3;
        DependencyNode dependencyNode5;
        DependencyNode dependencyNode6;
        int i3;
        ConstraintWidget parent3;
        ConstraintWidget constraintWidget3 = this.widget;
        if (constraintWidget3.measured) {
            this.dimension.resolve(constraintWidget3.getWidth());
        }
        if (this.dimension.resolved) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.dimensionBehavior;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
            if (dimensionBehaviour == dimensionBehaviour2 && (((parent = this.widget.getParent()) != null && parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent.getHorizontalDimensionBehaviour() == dimensionBehaviour2)) {
                addTarget(this.start, parent.horizontalRun.start, this.widget.mLeft.getMargin());
                addTarget(this.end, parent.horizontalRun.end, -this.widget.mRight.getMargin());
                return;
            }
        } else {
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = this.widget.getHorizontalDimensionBehaviour();
            this.dimensionBehavior = horizontalDimensionBehaviour;
            if (horizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if (horizontalDimensionBehaviour == dimensionBehaviour3 && (((parent3 = this.widget.getParent()) != null && parent3.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent3.getHorizontalDimensionBehaviour() == dimensionBehaviour3)) {
                    int width = (parent3.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    addTarget(this.start, parent3.horizontalRun.start, this.widget.mLeft.getMargin());
                    addTarget(this.end, parent3.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(width);
                    return;
                }
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        }
        DimensionDependency dimensionDependency = this.dimension;
        if (!dimensionDependency.resolved) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                constraintWidget2 = this.widget;
                i2 = constraintWidget2.mMatchConstraintDefaultWidth;
                if (i2 != 2) {
                    parent2 = constraintWidget2.getParent();
                    if (parent2 != null) {
                        DimensionDependency dimensionDependency2 = parent2.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency2);
                        dimensionDependency2.dependencies.add(this.dimension);
                        DimensionDependency dimensionDependency3 = this.dimension;
                        dimensionDependency3.delegateToWidgetRun = true;
                        dimensionDependency3.dependencies.add(this.start);
                        list = this.dimension.dependencies;
                        dependency = this.end;
                        list.add(dependency);
                    }
                } else if (i2 == 3) {
                    if (constraintWidget2.mMatchConstraintDefaultHeight == 3) {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        VerticalWidgetRun verticalWidgetRun = constraintWidget2.verticalRun;
                        verticalWidgetRun.start.updateDelegate = this;
                        verticalWidgetRun.end.updateDelegate = this;
                        dimensionDependency.updateDelegate = this;
                        if (constraintWidget2.isInVerticalChain()) {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            VerticalWidgetRun verticalWidgetRun2 = this.widget.verticalRun;
                            verticalWidgetRun2.dimension.updateDelegate = this;
                            this.dimension.targets.add(verticalWidgetRun2.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            list = this.widget.verticalRun.end.dependencies;
                            dependency = this.dimension;
                        } else if (this.widget.isInHorizontalChain()) {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                            list = this.dimension.dependencies;
                            dependency = this.widget.verticalRun.dimension;
                        } else {
                            dependencyNode3 = this.widget.verticalRun.dimension;
                        }
                        list.add(dependency);
                    } else {
                        DimensionDependency dimensionDependency4 = constraintWidget2.verticalRun.dimension;
                        dimensionDependency.targets.add(dimensionDependency4);
                        dimensionDependency4.dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        DimensionDependency dimensionDependency5 = this.dimension;
                        dimensionDependency5.delegateToWidgetRun = true;
                        dimensionDependency5.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        dependencyNode3 = this.end;
                    }
                    list = dependencyNode3.targets;
                    dependency = this.dimension;
                    list.add(dependency);
                }
            }
            constraintWidget = this.widget;
            constraintAnchorArr = constraintWidget.mListAnchors;
            constraintAnchor = constraintAnchorArr[0];
            constraintAnchor2 = constraintAnchor.mTarget;
            if (constraintAnchor2 != null) {
            }
            if (constraintAnchor2 == null) {
                constraintAnchor3 = constraintAnchorArr[1];
                if (constraintAnchor3.mTarget == null) {
                    if (constraintWidget instanceof Helper) {
                        return;
                    } else {
                        return;
                    }
                }
                target2 = getTarget(constraintAnchor3);
                if (target2 != null) {
                    addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -1, this.dimension);
                    return;
                }
                return;
            }
            target = getTarget(constraintAnchor);
            if (target != null) {
                return;
            }
            dependencyNode = this.start;
            x2 = this.widget.mListAnchors[0].getMargin();
            addTarget(dependencyNode, target, x2);
            addTarget(this.end, this.start, 1, this.dimension);
            return;
        }
        ConstraintWidget constraintWidget4 = this.widget;
        if (constraintWidget4.measured) {
            ConstraintAnchor[] constraintAnchorArr2 = constraintWidget4.mListAnchors;
            ConstraintAnchor constraintAnchor5 = constraintAnchorArr2[0];
            ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
            if (constraintAnchor6 == null || constraintAnchorArr2[1].mTarget == null) {
                if (constraintAnchor6 == null) {
                    ConstraintAnchor constraintAnchor7 = constraintAnchorArr2[1];
                    if (constraintAnchor7.mTarget != null) {
                        DependencyNode target4 = getTarget(constraintAnchor7);
                        if (target4 == null) {
                            return;
                        }
                        addTarget(this.end, target4, -this.widget.mListAnchors[1].getMargin());
                        dependencyNode5 = this.start;
                        dependencyNode6 = this.end;
                        i3 = -this.dimension.value;
                    } else {
                        if ((constraintWidget4 instanceof Helper) || constraintWidget4.getParent() == null || this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                            return;
                        }
                        target3 = this.widget.getParent().horizontalRun.start;
                        dependencyNode4 = this.start;
                        x3 = this.widget.getX();
                    }
                    addTarget(dependencyNode5, dependencyNode6, i3);
                    return;
                }
                target3 = getTarget(constraintAnchor5);
                if (target3 == null) {
                    return;
                }
                dependencyNode4 = this.start;
                x3 = this.widget.mListAnchors[0].getMargin();
                addTarget(dependencyNode4, target3, x3);
                dependencyNode5 = this.end;
                dependencyNode6 = this.start;
                i3 = this.dimension.value;
                addTarget(dependencyNode5, dependencyNode6, i3);
                return;
            }
            if (!constraintWidget4.isInHorizontalChain()) {
                DependencyNode target5 = getTarget(this.widget.mListAnchors[0]);
                if (target5 != null) {
                    addTarget(this.start, target5, this.widget.mListAnchors[0].getMargin());
                }
                DependencyNode target6 = getTarget(this.widget.mListAnchors[1]);
                if (target6 != null) {
                    addTarget(this.end, target6, -this.widget.mListAnchors[1].getMargin());
                }
                this.start.delegateToWidgetRun = true;
                this.end.delegateToWidgetRun = true;
                return;
            }
            this.start.margin = this.widget.mListAnchors[0].getMargin();
            dependencyNode2 = this.end;
            constraintAnchor4 = this.widget.mListAnchors[1];
        } else {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                constraintWidget2 = this.widget;
                i2 = constraintWidget2.mMatchConstraintDefaultWidth;
                if (i2 != 2) {
                    parent2 = constraintWidget2.getParent();
                    if (parent2 != null) {
                        DimensionDependency dimensionDependency6 = parent2.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency6);
                        dimensionDependency6.dependencies.add(this.dimension);
                        DimensionDependency dimensionDependency7 = this.dimension;
                        dimensionDependency7.delegateToWidgetRun = true;
                        dimensionDependency7.dependencies.add(this.start);
                        list = this.dimension.dependencies;
                        dependency = this.end;
                        list.add(dependency);
                    }
                } else if (i2 == 3) {
                    if (constraintWidget2.mMatchConstraintDefaultHeight == 3) {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        VerticalWidgetRun verticalWidgetRun3 = constraintWidget2.verticalRun;
                        verticalWidgetRun3.start.updateDelegate = this;
                        verticalWidgetRun3.end.updateDelegate = this;
                        dimensionDependency.updateDelegate = this;
                        if (constraintWidget2.isInVerticalChain()) {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            VerticalWidgetRun verticalWidgetRun4 = this.widget.verticalRun;
                            verticalWidgetRun4.dimension.updateDelegate = this;
                            this.dimension.targets.add(verticalWidgetRun4.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            list = this.widget.verticalRun.end.dependencies;
                            dependency = this.dimension;
                        } else if (this.widget.isInHorizontalChain()) {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                            list = this.dimension.dependencies;
                            dependency = this.widget.verticalRun.dimension;
                        } else {
                            dependencyNode3 = this.widget.verticalRun.dimension;
                        }
                        list.add(dependency);
                    } else {
                        DimensionDependency dimensionDependency8 = constraintWidget2.verticalRun.dimension;
                        dimensionDependency.targets.add(dimensionDependency8);
                        dimensionDependency8.dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        DimensionDependency dimensionDependency9 = this.dimension;
                        dimensionDependency9.delegateToWidgetRun = true;
                        dimensionDependency9.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        dependencyNode3 = this.end;
                    }
                    list = dependencyNode3.targets;
                    dependency = this.dimension;
                    list.add(dependency);
                }
            }
            constraintWidget = this.widget;
            constraintAnchorArr = constraintWidget.mListAnchors;
            constraintAnchor = constraintAnchorArr[0];
            constraintAnchor2 = constraintAnchor.mTarget;
            if (constraintAnchor2 != null || constraintAnchorArr[1].mTarget == null) {
                if (constraintAnchor2 == null) {
                    target = getTarget(constraintAnchor);
                    if (target != null) {
                        return;
                    }
                    dependencyNode = this.start;
                    x2 = this.widget.mListAnchors[0].getMargin();
                } else {
                    constraintAnchor3 = constraintAnchorArr[1];
                    if (constraintAnchor3.mTarget == null) {
                        target2 = getTarget(constraintAnchor3);
                        if (target2 != null) {
                            addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                            addTarget(this.start, this.end, -1, this.dimension);
                            return;
                        }
                        return;
                    }
                    if ((constraintWidget instanceof Helper) || constraintWidget.getParent() == null) {
                        return;
                    }
                    target = this.widget.getParent().horizontalRun.start;
                    dependencyNode = this.start;
                    x2 = this.widget.getX();
                }
                addTarget(dependencyNode, target, x2);
                addTarget(this.end, this.start, 1, this.dimension);
                return;
            }
            if (!constraintWidget.isInHorizontalChain()) {
                DependencyNode target7 = getTarget(this.widget.mListAnchors[0]);
                DependencyNode target8 = getTarget(this.widget.mListAnchors[1]);
                target7.addDependency(this);
                target8.addDependency(this);
                this.mRunType = WidgetRun.RunType.CENTER;
                return;
            }
            this.start.margin = this.widget.mListAnchors[0].getMargin();
            dependencyNode2 = this.end;
            constraintAnchor4 = this.widget.mListAnchors[1];
        }
        dependencyNode2.margin = -constraintAnchor4.getMargin();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.widget.setX(dependencyNode.value);
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
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
        this.dimension.resolved = false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean supportsWrapComputation() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0;
    }

    public String toString() {
        return "HorizontalRun " + this.widget.getDebugName();
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x029b, code lost:
    
        if (r14 != 1) goto L134;
     */
    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void update(androidx.constraintlayout.solver.widgets.analyzer.Dependency r17) {
        /*
            Method dump skipped, instruction units count: 1041
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun.update(androidx.constraintlayout.solver.widgets.analyzer.Dependency):void");
    }
}
