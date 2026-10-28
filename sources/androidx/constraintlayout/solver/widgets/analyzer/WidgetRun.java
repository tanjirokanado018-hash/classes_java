package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class WidgetRun implements Dependency {
    protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public int matchConstraintsType;
    RunGroup runGroup;
    ConstraintWidget widget;
    DimensionDependency dimension = new DimensionDependency(this);
    public int orientation = 0;
    boolean resolved = false;
    public DependencyNode start = new DependencyNode(this);
    public DependencyNode end = new DependencyNode(this);
    protected RunType mRunType = RunType.NONE;

    /* JADX INFO: renamed from: androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.widget = constraintWidget;
    }

    private void resolveDimension(int i2, int i3) {
        DimensionDependency dimensionDependency;
        int limitedDimension;
        int i4 = this.matchConstraintsType;
        if (i4 == 0) {
            dimensionDependency = this.dimension;
            limitedDimension = getLimitedDimension(i3, i2);
        } else if (i4 == 1) {
            int limitedDimension2 = getLimitedDimension(this.dimension.wrapValue, i2);
            dimensionDependency = this.dimension;
            limitedDimension = Math.min(limitedDimension2, i3);
        } else {
            if (i4 != 2) {
                if (i4 != 3) {
                    return;
                }
                ConstraintWidget constraintWidget = this.widget;
                WidgetRun widgetRun = constraintWidget.horizontalRun;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = widgetRun.dimensionBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour == dimensionBehaviour2 && widgetRun.matchConstraintsType == 3) {
                    VerticalWidgetRun verticalWidgetRun = constraintWidget.verticalRun;
                    if (verticalWidgetRun.dimensionBehavior == dimensionBehaviour2 && verticalWidgetRun.matchConstraintsType == 3) {
                        return;
                    }
                }
                if (i2 == 0) {
                    widgetRun = constraintWidget.verticalRun;
                }
                if (widgetRun.dimension.resolved) {
                    float dimensionRatio = constraintWidget.getDimensionRatio();
                    this.dimension.resolve(i2 == 1 ? (int) ((widgetRun.dimension.value / dimensionRatio) + 0.5f) : (int) ((dimensionRatio * widgetRun.dimension.value) + 0.5f));
                    return;
                }
                return;
            }
            ConstraintWidget parent = this.widget.getParent();
            if (parent == null) {
                return;
            }
            DimensionDependency dimensionDependency2 = (i2 == 0 ? parent.horizontalRun : parent.verticalRun).dimension;
            if (!dimensionDependency2.resolved) {
                return;
            }
            ConstraintWidget constraintWidget2 = this.widget;
            i3 = (int) ((dimensionDependency2.value * (i2 == 0 ? constraintWidget2.mMatchConstraintPercentWidth : constraintWidget2.mMatchConstraintPercentHeight)) + 0.5f);
            dimensionDependency = this.dimension;
            limitedDimension = getLimitedDimension(i3, i2);
        }
        dimensionDependency.resolve(limitedDimension);
    }

    public final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i2) {
        dependencyNode.targets.add(dependencyNode2);
        dependencyNode.margin = i2;
        dependencyNode2.dependencies.add(dependencyNode);
    }

    public abstract void apply();

    public abstract void applyToWidget();

    public abstract void clear();

    public final int getLimitedDimension(int i2, int i3) {
        int iMax;
        if (i3 == 0) {
            ConstraintWidget constraintWidget = this.widget;
            int i4 = constraintWidget.mMatchConstraintMaxWidth;
            iMax = Math.max(constraintWidget.mMatchConstraintMinWidth, i2);
            if (i4 > 0) {
                iMax = Math.min(i4, i2);
            }
            if (iMax == i2) {
                return i2;
            }
        } else {
            ConstraintWidget constraintWidget2 = this.widget;
            int i5 = constraintWidget2.mMatchConstraintMaxHeight;
            iMax = Math.max(constraintWidget2.mMatchConstraintMinHeight, i2);
            if (i5 > 0) {
                iMax = Math.min(i5, i2);
            }
            if (iMax == i2) {
                return i2;
            }
        }
        return iMax;
    }

    public final DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        WidgetRun widgetRun;
        WidgetRun widgetRun2;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        int i2 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[constraintAnchor2.mType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                widgetRun2 = constraintWidget.horizontalRun;
            } else if (i2 == 3) {
                widgetRun = constraintWidget.verticalRun;
            } else {
                if (i2 == 4) {
                    return constraintWidget.verticalRun.baseline;
                }
                if (i2 != 5) {
                    return null;
                }
                widgetRun2 = constraintWidget.verticalRun;
            }
            return widgetRun2.end;
        }
        widgetRun = constraintWidget.horizontalRun;
        return widgetRun.start;
    }

    public long getWrapDimension() {
        DimensionDependency dimensionDependency = this.dimension;
        if (dimensionDependency.resolved) {
            return dimensionDependency.value;
        }
        return 0L;
    }

    public boolean isCenterConnection() {
        int size = this.start.targets.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (this.start.targets.get(i3).run != this) {
                i2++;
            }
        }
        int size2 = this.end.targets.size();
        for (int i4 = 0; i4 < size2; i4++) {
            if (this.end.targets.get(i4).run != this) {
                i2++;
            }
        }
        return i2 >= 2;
    }

    public boolean isDimensionResolved() {
        return this.dimension.resolved;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public abstract void reset();

    public abstract boolean supportsWrapComputation();

    @Override // androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }

    public void updateRunCenter(Dependency dependency, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2) {
        DependencyNode target = getTarget(constraintAnchor);
        DependencyNode target2 = getTarget(constraintAnchor2);
        if (target.resolved && target2.resolved) {
            int margin = constraintAnchor.getMargin() + target.value;
            int margin2 = target2.value - constraintAnchor2.getMargin();
            int i3 = margin2 - margin;
            if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                resolveDimension(i2, i3);
            }
            DimensionDependency dimensionDependency = this.dimension;
            if (dimensionDependency.resolved) {
                if (dimensionDependency.value == i3) {
                    this.start.resolve(margin);
                    this.end.resolve(margin2);
                    return;
                }
                ConstraintWidget constraintWidget = this.widget;
                float horizontalBiasPercent = i2 == 0 ? constraintWidget.getHorizontalBiasPercent() : constraintWidget.getVerticalBiasPercent();
                if (target == target2) {
                    margin = target.value;
                    margin2 = target2.value;
                    horizontalBiasPercent = 0.5f;
                }
                this.start.resolve((int) ((((margin2 - margin) - this.dimension.value) * horizontalBiasPercent) + margin + 0.5f));
                this.end.resolve(this.start.value + this.dimension.value);
            }
        }
    }

    public void updateRunEnd(Dependency dependency) {
    }

    public void updateRunStart(Dependency dependency) {
    }

    public long wrapSize(int i2) {
        int i3;
        DimensionDependency dimensionDependency = this.dimension;
        if (!dimensionDependency.resolved) {
            return 0L;
        }
        long j2 = dimensionDependency.value;
        if (isCenterConnection()) {
            i3 = this.start.margin - this.end.margin;
        } else {
            if (i2 != 0) {
                return j2 - ((long) this.end.margin);
            }
            i3 = this.start.margin;
        }
        return j2 + ((long) i3);
    }

    public final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i2, DimensionDependency dimensionDependency) {
        dependencyNode.targets.add(dependencyNode2);
        dependencyNode.targets.add(this.dimension);
        dependencyNode.marginFactor = i2;
        dependencyNode.marginDependency = dimensionDependency;
        dependencyNode2.dependencies.add(dependencyNode);
        dimensionDependency.dependencies.add(dependencyNode);
    }

    public final DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i2) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        WidgetRun widgetRun = i2 == 0 ? constraintWidget.horizontalRun : constraintWidget.verticalRun;
        int i3 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[constraintAnchor2.mType.ordinal()];
        if (i3 != 1) {
            if (i3 != 2) {
                if (i3 != 3) {
                    if (i3 != 5) {
                        return null;
                    }
                }
            }
            return widgetRun.end;
        }
        return widgetRun.start;
    }
}
