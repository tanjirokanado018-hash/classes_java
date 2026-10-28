package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class HelperReferences extends WidgetRun {
    public HelperReferences(ConstraintWidget constraintWidget) {
        super(constraintWidget);
    }

    private void addDependency(DependencyNode dependencyNode) {
        this.start.dependencies.add(dependencyNode);
        dependencyNode.targets.add(this.start);
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void apply() {
        WidgetRun widgetRun;
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget instanceof Barrier) {
            this.start.delegateToWidgetRun = true;
            Barrier barrier = (Barrier) constraintWidget;
            int barrierType = barrier.getBarrierType();
            boolean zAllowsGoneWidget = barrier.allowsGoneWidget();
            int i2 = 0;
            if (barrierType != 0) {
                if (barrierType != 1) {
                    if (barrierType == 2) {
                        this.start.type = DependencyNode.Type.TOP;
                        while (i2 < barrier.mWidgetsCount) {
                            ConstraintWidget constraintWidget2 = barrier.mWidgets[i2];
                            if (zAllowsGoneWidget || constraintWidget2.getVisibility() != 8) {
                                DependencyNode dependencyNode = constraintWidget2.verticalRun.start;
                                dependencyNode.dependencies.add(this.start);
                                this.start.targets.add(dependencyNode);
                            }
                            i2++;
                        }
                    } else {
                        if (barrierType != 3) {
                            return;
                        }
                        this.start.type = DependencyNode.Type.BOTTOM;
                        while (i2 < barrier.mWidgetsCount) {
                            ConstraintWidget constraintWidget3 = barrier.mWidgets[i2];
                            if (zAllowsGoneWidget || constraintWidget3.getVisibility() != 8) {
                                DependencyNode dependencyNode2 = constraintWidget3.verticalRun.end;
                                dependencyNode2.dependencies.add(this.start);
                                this.start.targets.add(dependencyNode2);
                            }
                            i2++;
                        }
                    }
                    addDependency(this.widget.verticalRun.start);
                    widgetRun = this.widget.verticalRun;
                } else {
                    this.start.type = DependencyNode.Type.RIGHT;
                    while (i2 < barrier.mWidgetsCount) {
                        ConstraintWidget constraintWidget4 = barrier.mWidgets[i2];
                        if (zAllowsGoneWidget || constraintWidget4.getVisibility() != 8) {
                            DependencyNode dependencyNode3 = constraintWidget4.horizontalRun.end;
                            dependencyNode3.dependencies.add(this.start);
                            this.start.targets.add(dependencyNode3);
                        }
                        i2++;
                    }
                }
                addDependency(widgetRun.end);
            }
            this.start.type = DependencyNode.Type.LEFT;
            while (i2 < barrier.mWidgetsCount) {
                ConstraintWidget constraintWidget5 = barrier.mWidgets[i2];
                if (zAllowsGoneWidget || constraintWidget5.getVisibility() != 8) {
                    DependencyNode dependencyNode4 = constraintWidget5.horizontalRun.start;
                    dependencyNode4.dependencies.add(this.start);
                    this.start.targets.add(dependencyNode4);
                }
                i2++;
            }
            addDependency(this.widget.horizontalRun.start);
            widgetRun = this.widget.horizontalRun;
            addDependency(widgetRun.end);
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget instanceof Barrier) {
            int barrierType = ((Barrier) constraintWidget).getBarrierType();
            if (barrierType == 0 || barrierType == 1) {
                this.widget.setX(this.start.value);
            } else {
                this.widget.setY(this.start.value);
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        this.start.clear();
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public void reset() {
        this.start.resolved = false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun
    public boolean supportsWrapComputation() {
        return false;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.WidgetRun, androidx.constraintlayout.solver.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        DependencyNode dependencyNode;
        int margin;
        Barrier barrier = (Barrier) this.widget;
        int barrierType = barrier.getBarrierType();
        Iterator<DependencyNode> it = this.start.targets.iterator();
        int i2 = 0;
        int i3 = -1;
        while (it.hasNext()) {
            int i4 = it.next().value;
            if (i3 == -1 || i4 < i3) {
                i3 = i4;
            }
            if (i2 < i4) {
                i2 = i4;
            }
        }
        if (barrierType == 0 || barrierType == 2) {
            dependencyNode = this.start;
            margin = barrier.getMargin() + i3;
        } else {
            dependencyNode = this.start;
            margin = barrier.getMargin() + i2;
        }
        dependencyNode.resolve(margin);
    }
}
