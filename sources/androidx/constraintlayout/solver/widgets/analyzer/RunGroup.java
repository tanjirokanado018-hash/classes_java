package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    int direction;
    WidgetRun firstRun;
    int groupIndex;
    WidgetRun lastRun;
    public int position = 0;
    public boolean dual = false;
    ArrayList<WidgetRun> runs = new ArrayList<>();

    public RunGroup(WidgetRun widgetRun, int i2) {
        this.firstRun = null;
        this.lastRun = null;
        int i3 = index;
        this.groupIndex = i3;
        index = i3 + 1;
        this.firstRun = widgetRun;
        this.lastRun = widgetRun;
        this.direction = i2;
    }

    private boolean defineTerminalWidget(WidgetRun widgetRun, int i2) {
        DependencyNode dependencyNode;
        WidgetRun widgetRun2;
        DependencyNode dependencyNode2;
        WidgetRun widgetRun3;
        if (!widgetRun.widget.isTerminalWidget[i2]) {
            return false;
        }
        for (Dependency dependency : widgetRun.start.dependencies) {
            if ((dependency instanceof DependencyNode) && (widgetRun3 = (dependencyNode2 = (DependencyNode) dependency).run) != widgetRun && dependencyNode2 == widgetRun3.start) {
                if (widgetRun instanceof ChainRun) {
                    Iterator<WidgetRun> it = ((ChainRun) widgetRun).widgets.iterator();
                    while (it.hasNext()) {
                        defineTerminalWidget(it.next(), i2);
                    }
                } else if (!(widgetRun instanceof HelperReferences)) {
                    widgetRun.widget.isTerminalWidget[i2] = false;
                }
                defineTerminalWidget(dependencyNode2.run, i2);
            }
        }
        for (Dependency dependency2 : widgetRun.end.dependencies) {
            if ((dependency2 instanceof DependencyNode) && (widgetRun2 = (dependencyNode = (DependencyNode) dependency2).run) != widgetRun && dependencyNode == widgetRun2.start) {
                if (widgetRun instanceof ChainRun) {
                    Iterator<WidgetRun> it2 = ((ChainRun) widgetRun).widgets.iterator();
                    while (it2.hasNext()) {
                        defineTerminalWidget(it2.next(), i2);
                    }
                } else if (!(widgetRun instanceof HelperReferences)) {
                    widgetRun.widget.isTerminalWidget[i2] = false;
                }
                defineTerminalWidget(dependencyNode.run, i2);
            }
        }
        return false;
    }

    private long traverseEnd(DependencyNode dependencyNode, long j2) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return j2;
        }
        int size = dependencyNode.dependencies.size();
        long jMin = j2;
        for (int i2 = 0; i2 < size; i2++) {
            Dependency dependency = dependencyNode.dependencies.get(i2);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.run != widgetRun) {
                    jMin = Math.min(jMin, traverseEnd(dependencyNode2, ((long) dependencyNode2.margin) + j2));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return jMin;
        }
        long wrapDimension = j2 - widgetRun.getWrapDimension();
        return Math.min(Math.min(jMin, traverseEnd(widgetRun.start, wrapDimension)), wrapDimension - ((long) widgetRun.start.margin));
    }

    private long traverseStart(DependencyNode dependencyNode, long j2) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return j2;
        }
        int size = dependencyNode.dependencies.size();
        long jMax = j2;
        for (int i2 = 0; i2 < size; i2++) {
            Dependency dependency = dependencyNode.dependencies.get(i2);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.run != widgetRun) {
                    jMax = Math.max(jMax, traverseStart(dependencyNode2, ((long) dependencyNode2.margin) + j2));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return jMax;
        }
        long wrapDimension = j2 + widgetRun.getWrapDimension();
        return Math.max(Math.max(jMax, traverseStart(widgetRun.end, wrapDimension)), wrapDimension - ((long) widgetRun.end.margin));
    }

    public void add(WidgetRun widgetRun) {
        this.runs.add(widgetRun);
        this.lastRun = widgetRun;
    }

    public long computeWrapSize(ConstraintWidgetContainer constraintWidgetContainer, int i2) {
        long j2;
        long jTraverseStart;
        WidgetRun widgetRun = this.firstRun;
        long j3 = 0;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i2) {
                return 0L;
            }
        } else if (i2 == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = (i2 == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).start;
        DependencyNode dependencyNode2 = (i2 == 0 ? constraintWidgetContainer.horizontalRun : constraintWidgetContainer.verticalRun).end;
        boolean zContains = widgetRun.start.targets.contains(dependencyNode);
        boolean zContains2 = this.firstRun.end.targets.contains(dependencyNode2);
        long wrapDimension = this.firstRun.getWrapDimension();
        if (!zContains || !zContains2) {
            if (zContains) {
                DependencyNode dependencyNode3 = this.firstRun.start;
                jTraverseStart = traverseStart(dependencyNode3, dependencyNode3.margin);
                j2 = ((long) this.firstRun.start.margin) + wrapDimension;
            } else {
                if (!zContains2) {
                    WidgetRun widgetRun2 = this.firstRun;
                    return (widgetRun2.getWrapDimension() + ((long) widgetRun2.start.margin)) - ((long) this.firstRun.end.margin);
                }
                DependencyNode dependencyNode4 = this.firstRun.end;
                long jTraverseEnd = traverseEnd(dependencyNode4, dependencyNode4.margin);
                j2 = ((long) (-this.firstRun.end.margin)) + wrapDimension;
                jTraverseStart = -jTraverseEnd;
            }
            return Math.max(jTraverseStart, j2);
        }
        long jTraverseStart2 = traverseStart(this.firstRun.start, 0L);
        long jTraverseEnd2 = traverseEnd(this.firstRun.end, 0L);
        long j4 = jTraverseStart2 - wrapDimension;
        WidgetRun widgetRun3 = this.firstRun;
        int i3 = widgetRun3.end.margin;
        if (j4 >= (-i3)) {
            j4 += (long) i3;
        }
        int i4 = widgetRun3.start.margin;
        long j5 = ((-jTraverseEnd2) - wrapDimension) - ((long) i4);
        if (j5 >= i4) {
            j5 -= (long) i4;
        }
        float biasPercent = widgetRun3.widget.getBiasPercent(i2);
        if (biasPercent > 0.0f) {
            j3 = (long) ((j4 / (1.0f - biasPercent)) + (j5 / biasPercent));
        }
        float f2 = j3;
        long jA = ((long) ((f2 * biasPercent) + 0.5f)) + wrapDimension + ((long) a.a(1.0f, biasPercent, f2, 0.5f));
        WidgetRun widgetRun4 = this.firstRun;
        return (((long) widgetRun4.start.margin) + jA) - ((long) widgetRun4.end.margin);
    }

    public void defineTerminalWidgets(boolean z2, boolean z3) {
        if (z2) {
            WidgetRun widgetRun = this.firstRun;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (z3) {
            WidgetRun widgetRun2 = this.firstRun;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
