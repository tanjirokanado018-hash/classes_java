package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DependencyGraph {
    private static final boolean USE_GROUPS = true;
    private ConstraintWidgetContainer container;
    private ConstraintWidgetContainer mContainer;
    private boolean mNeedBuildGraph = USE_GROUPS;
    private boolean mNeedRedoMeasures = USE_GROUPS;
    private ArrayList<WidgetRun> mRuns = new ArrayList<>();
    private ArrayList<RunGroup> runGroups = new ArrayList<>();
    private BasicMeasure.Measurer mMeasurer = null;
    private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    ArrayList<RunGroup> mGroups = new ArrayList<>();

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        this.container = constraintWidgetContainer;
        this.mContainer = constraintWidgetContainer;
    }

    private void applyGroup(DependencyNode dependencyNode, int i2, int i3, DependencyNode dependencyNode2, ArrayList<RunGroup> arrayList, RunGroup runGroup) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun.runGroup == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            if (widgetRun == constraintWidgetContainer.horizontalRun || widgetRun == constraintWidgetContainer.verticalRun) {
                return;
            }
            if (runGroup == null) {
                runGroup = new RunGroup(widgetRun, i3);
                arrayList.add(runGroup);
            }
            widgetRun.runGroup = runGroup;
            runGroup.add(widgetRun);
            for (Dependency dependency : widgetRun.start.dependencies) {
                if (dependency instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency, i2, 0, dependencyNode2, arrayList, runGroup);
                }
            }
            for (Dependency dependency2 : widgetRun.end.dependencies) {
                if (dependency2 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency2, i2, 1, dependencyNode2, arrayList, runGroup);
                }
            }
            if (i2 == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                for (Dependency dependency3 : ((VerticalWidgetRun) widgetRun).baseline.dependencies) {
                    if (dependency3 instanceof DependencyNode) {
                        applyGroup((DependencyNode) dependency3, i2, 2, dependencyNode2, arrayList, runGroup);
                    }
                }
            }
            for (DependencyNode dependencyNode3 : widgetRun.start.targets) {
                if (dependencyNode3 == dependencyNode2) {
                    runGroup.dual = USE_GROUPS;
                }
                applyGroup(dependencyNode3, i2, 0, dependencyNode2, arrayList, runGroup);
            }
            for (DependencyNode dependencyNode4 : widgetRun.end.targets) {
                if (dependencyNode4 == dependencyNode2) {
                    runGroup.dual = USE_GROUPS;
                }
                applyGroup(dependencyNode4, i2, 1, dependencyNode2, arrayList, runGroup);
            }
            if (i2 == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                Iterator<DependencyNode> it = ((VerticalWidgetRun) widgetRun).baseline.targets.iterator();
                while (it.hasNext()) {
                    applyGroup(it.next(), i2, 2, dependencyNode2, arrayList, runGroup);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x015d  */
    /* JADX WARN: Code duplicated, block: B:125:0x01db A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:171:0x01dd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:182:0x0008 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x0065  */
    /* JADX WARN: Code duplicated, block: B:99:0x015b  */
    private boolean basicMeasureWidgets(ConstraintWidgetContainer constraintWidgetContainer) {
        int width;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        int height;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
        DimensionDependency dimensionDependency;
        int height2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5;
        for (ConstraintWidget constraintWidget : constraintWidgetContainer.mChildren) {
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidget.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = dimensionBehaviourArr2[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = dimensionBehaviourArr2[1];
            if (constraintWidget.getVisibility() != 8) {
                if (constraintWidget.mMatchConstraintPercentWidth < 1.0f && dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultWidth = 2;
                }
                if (constraintWidget.mMatchConstraintPercentHeight < 1.0f && dimensionBehaviour7 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultHeight = 2;
                }
                if (constraintWidget.getDimensionRatio() > 0.0f) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour6 == dimensionBehaviour8 && (dimensionBehaviour7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour7 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultWidth = 3;
                    } else if (dimensionBehaviour7 == dimensionBehaviour8 && (dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultHeight = 3;
                    } else if (dimensionBehaviour6 == dimensionBehaviour8 && dimensionBehaviour7 == dimensionBehaviour8) {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            constraintWidget.mMatchConstraintDefaultWidth = 3;
                        }
                        if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                            constraintWidget.mMatchConstraintDefaultHeight = 3;
                        }
                    }
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour6 == dimensionBehaviour9 && constraintWidget.mMatchConstraintDefaultWidth == 1 && (constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                    dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour10 = dimensionBehaviour6;
                if (dimensionBehaviour7 == dimensionBehaviour9 && constraintWidget.mMatchConstraintDefaultHeight == 1 && (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null)) {
                    dimensionBehaviour7 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour11 = dimensionBehaviour7;
                HorizontalWidgetRun horizontalWidgetRun = constraintWidget.horizontalRun;
                horizontalWidgetRun.dimensionBehavior = dimensionBehaviour10;
                int i2 = constraintWidget.mMatchConstraintDefaultWidth;
                horizontalWidgetRun.matchConstraintsType = i2;
                VerticalWidgetRun verticalWidgetRun = constraintWidget.verticalRun;
                verticalWidgetRun.dimensionBehavior = dimensionBehaviour11;
                int i3 = constraintWidget.mMatchConstraintDefaultHeight;
                verticalWidgetRun.matchConstraintsType = i3;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour12 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if ((dimensionBehaviour10 == dimensionBehaviour12 || dimensionBehaviour10 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour10 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (dimensionBehaviour11 == dimensionBehaviour12 || dimensionBehaviour11 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour11 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
                    int width2 = constraintWidget.getWidth();
                    if (dimensionBehaviour10 == dimensionBehaviour12) {
                        width = (constraintWidgetContainer.getWidth() - constraintWidget.mLeft.mMargin) - constraintWidget.mRight.mMargin;
                        dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    } else {
                        width = width2;
                        dimensionBehaviour = dimensionBehaviour10;
                    }
                    int height3 = constraintWidget.getHeight();
                    if (dimensionBehaviour11 == dimensionBehaviour12) {
                        height = (constraintWidgetContainer.getHeight() - constraintWidget.mTop.mMargin) - constraintWidget.mBottom.mMargin;
                        dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
                    } else {
                        height = height3;
                        dimensionBehaviour2 = dimensionBehaviour11;
                    }
                    this = this;
                    constraintWidget = constraintWidget;
                    this.measure(constraintWidget, dimensionBehaviour, width, dimensionBehaviour2, height);
                    constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                    constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                } else if (dimensionBehaviour10 == dimensionBehaviour9 && (dimensionBehaviour11 == (dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour11 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                    if (i2 == 3) {
                        if (dimensionBehaviour11 == dimensionBehaviour5) {
                            measure(constraintWidget, dimensionBehaviour5, 0, dimensionBehaviour5, 0);
                        }
                        height = constraintWidget.getHeight();
                        width = (int) ((height * constraintWidget.mDimensionRatio) + 0.5f);
                    } else {
                        if (i2 == 1) {
                            measure(constraintWidget, dimensionBehaviour5, 0, dimensionBehaviour11, 0);
                            dimensionDependency = constraintWidget.horizontalRun.dimension;
                            height2 = constraintWidget.getWidth();
                        } else if (i2 == 2) {
                            ConstraintWidget.DimensionBehaviour dimensionBehaviour13 = constraintWidgetContainer.mListDimensionBehaviors[0];
                            dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.FIXED;
                            if (dimensionBehaviour13 == dimensionBehaviour5 || dimensionBehaviour13 == dimensionBehaviour12) {
                                width = (int) ((constraintWidget.mMatchConstraintPercentWidth * constraintWidgetContainer.getWidth()) + 0.5f);
                                height = constraintWidget.getHeight();
                                dimensionBehaviour = dimensionBehaviour5;
                                dimensionBehaviour2 = dimensionBehaviour11;
                                this.measure(constraintWidget, dimensionBehaviour, width, dimensionBehaviour2, height);
                                constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                                constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                            } else if (dimensionBehaviour11 != dimensionBehaviour9) {
                                if (dimensionBehaviour10 != dimensionBehaviour9) {
                                }
                            } else if (dimensionBehaviour10 != dimensionBehaviour9) {
                            }
                        } else {
                            ConstraintAnchor[] constraintAnchorArr = constraintWidget.mListAnchors;
                            if (constraintAnchorArr[0].mTarget == null || constraintAnchorArr[1].mTarget == null) {
                                width = 0;
                                height = 0;
                                dimensionBehaviour = dimensionBehaviour5;
                                dimensionBehaviour2 = dimensionBehaviour11;
                                this.measure(constraintWidget, dimensionBehaviour, width, dimensionBehaviour2, height);
                                constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                                constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                            } else if (dimensionBehaviour11 != dimensionBehaviour9) {
                                if (dimensionBehaviour10 != dimensionBehaviour9) {
                                }
                            } else if (dimensionBehaviour10 != dimensionBehaviour9) {
                            }
                        }
                        dimensionDependency.wrapValue = height2;
                    }
                    dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
                    this = this;
                    constraintWidget = constraintWidget;
                    dimensionBehaviour = dimensionBehaviour2;
                    this.measure(constraintWidget, dimensionBehaviour, width, dimensionBehaviour2, height);
                    constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                    constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                } else if (dimensionBehaviour11 != dimensionBehaviour9 && (dimensionBehaviour10 == (dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour10 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                    if (i3 == 3) {
                        if (dimensionBehaviour10 == dimensionBehaviour4) {
                            measure(constraintWidget, dimensionBehaviour4, 0, dimensionBehaviour4, 0);
                        }
                        width = constraintWidget.getWidth();
                        float f2 = constraintWidget.mDimensionRatio;
                        if (constraintWidget.getDimensionRatioSide() == -1) {
                            f2 = 1.0f / f2;
                        }
                        height = (int) ((width * f2) + 0.5f);
                        dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
                        this = this;
                        constraintWidget = constraintWidget;
                        dimensionBehaviour = dimensionBehaviour2;
                    } else {
                        if (i3 == 1) {
                            measure(constraintWidget, dimensionBehaviour10, 0, dimensionBehaviour4, 0);
                        } else {
                            if (i3 == 2) {
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour14 = constraintWidgetContainer.mListDimensionBehaviors[1];
                                ConstraintWidget.DimensionBehaviour dimensionBehaviour15 = ConstraintWidget.DimensionBehaviour.FIXED;
                                if (dimensionBehaviour14 == dimensionBehaviour15 || dimensionBehaviour14 == dimensionBehaviour12) {
                                    float f3 = constraintWidget.mMatchConstraintPercentHeight;
                                    width = constraintWidget.getWidth();
                                    this = this;
                                    constraintWidget = constraintWidget;
                                    dimensionBehaviour = dimensionBehaviour10;
                                    dimensionBehaviour2 = dimensionBehaviour15;
                                    height = (int) ((f3 * constraintWidgetContainer.getHeight()) + 0.5f);
                                }
                            } else {
                                ConstraintAnchor[] constraintAnchorArr2 = constraintWidget.mListAnchors;
                                if (constraintAnchorArr2[2].mTarget == null || constraintAnchorArr2[3].mTarget == null) {
                                    width = 0;
                                    height = 0;
                                    dimensionBehaviour = dimensionBehaviour4;
                                    dimensionBehaviour2 = dimensionBehaviour11;
                                }
                            }
                            if (dimensionBehaviour10 != dimensionBehaviour9) {
                            }
                        }
                        dimensionDependency = constraintWidget.verticalRun.dimension;
                        height2 = constraintWidget.getHeight();
                        dimensionDependency.wrapValue = height2;
                    }
                    this.measure(constraintWidget, dimensionBehaviour, width, dimensionBehaviour2, height);
                    constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                    constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                } else if (dimensionBehaviour10 != dimensionBehaviour9 && dimensionBehaviour11 == dimensionBehaviour9) {
                    if (i2 == 1 || i3 == 1) {
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour16 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        measure(constraintWidget, dimensionBehaviour16, 0, dimensionBehaviour16, 0);
                        constraintWidget.horizontalRun.dimension.wrapValue = constraintWidget.getWidth();
                        dimensionDependency = constraintWidget.verticalRun.dimension;
                        height2 = constraintWidget.getHeight();
                        dimensionDependency.wrapValue = height2;
                    } else if (i3 == 2 && i2 == 2 && ((dimensionBehaviour3 = (dimensionBehaviourArr = constraintWidgetContainer.mListDimensionBehaviors)[0]) == (dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED) || dimensionBehaviour3 == dimensionBehaviour2)) {
                        ConstraintWidget.DimensionBehaviour dimensionBehaviour17 = dimensionBehaviourArr[1];
                        if (dimensionBehaviour17 == dimensionBehaviour2 || dimensionBehaviour17 == dimensionBehaviour2) {
                            float f4 = constraintWidget.mMatchConstraintPercentWidth;
                            float f5 = constraintWidget.mMatchConstraintPercentHeight;
                            width = (int) ((f4 * constraintWidgetContainer.getWidth()) + 0.5f);
                            height = (int) ((f5 * constraintWidgetContainer.getHeight()) + 0.5f);
                            this = this;
                            constraintWidget = constraintWidget;
                            dimensionBehaviour = dimensionBehaviour2;
                            this.measure(constraintWidget, dimensionBehaviour, width, dimensionBehaviour2, height);
                            constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                        }
                    }
                }
            }
            constraintWidget.measured = USE_GROUPS;
        }
        return false;
    }

    private int computeWrap(ConstraintWidgetContainer constraintWidgetContainer, int i2) {
        int size = this.mGroups.size();
        long jMax = 0;
        for (int i3 = 0; i3 < size; i3++) {
            jMax = Math.max(jMax, this.mGroups.get(i3).computeWrapSize(constraintWidgetContainer, i2));
        }
        return (int) jMax;
    }

    private void displayGraph() {
        Iterator<WidgetRun> it = this.mRuns.iterator();
        String strGenerateDisplayGraph = "digraph {\n";
        while (it.hasNext()) {
            strGenerateDisplayGraph = generateDisplayGraph(it.next(), strGenerateDisplayGraph);
        }
        String strE = a.e(strGenerateDisplayGraph, "\n}\n");
        System.out.println("content:<<\n" + strE + "\n>>");
    }

    private void findGroup(WidgetRun widgetRun, int i2, ArrayList<RunGroup> arrayList) {
        for (Dependency dependency : widgetRun.start.dependencies) {
            if (dependency instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency, i2, 0, widgetRun.end, arrayList, null);
            } else if (dependency instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency).start, i2, 0, widgetRun.end, arrayList, null);
            }
        }
        for (Dependency dependency2 : widgetRun.end.dependencies) {
            if (dependency2 instanceof DependencyNode) {
                applyGroup((DependencyNode) dependency2, i2, 1, widgetRun.start, arrayList, null);
            } else if (dependency2 instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependency2).end, i2, 1, widgetRun.start, arrayList, null);
            }
        }
        if (i2 == 1) {
            for (Dependency dependency3 : ((VerticalWidgetRun) widgetRun).baseline.dependencies) {
                if (dependency3 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependency3, i2, 2, null, arrayList, null);
                }
            }
        }
    }

    private String generateChainDisplayGraph(ChainRun chainRun, String str) {
        int i2 = chainRun.orientation;
        String strF = a.f("subgraph ", a.e("cluster_" + chainRun.widget.getDebugName(), i2 == 0 ? "_h" : "_v"), " {\n");
        String strGenerateDisplayGraph = "";
        for (WidgetRun widgetRun : chainRun.widgets) {
            strF = strF + a.e(widgetRun.widget.getDebugName(), i2 == 0 ? "_HORIZONTAL" : "_VERTICAL") + ";\n";
            strGenerateDisplayGraph = generateDisplayGraph(widgetRun, strGenerateDisplayGraph);
        }
        return str + strGenerateDisplayGraph + a.e(strF, "}\n");
    }

    /* JADX WARN: Code duplicated, block: B:34:0x008f  */
    private String generateDisplayGraph(WidgetRun widgetRun, String str) {
        StringBuilder sb;
        String strName;
        boolean z2;
        DependencyNode dependencyNode = widgetRun.start;
        DependencyNode dependencyNode2 = widgetRun.end;
        if (!(widgetRun instanceof HelperReferences) && dependencyNode.dependencies.isEmpty() && (dependencyNode2.dependencies.isEmpty() && dependencyNode.targets.isEmpty()) && dependencyNode2.targets.isEmpty()) {
            return str;
        }
        StringBuilder sbH = a.h(str);
        sbH.append(nodeDefinition(widgetRun));
        String string = sbH.toString();
        boolean zIsCenteredConnection = isCenteredConnection(dependencyNode, dependencyNode2);
        String strGenerateDisplayNode = generateDisplayNode(dependencyNode2, zIsCenteredConnection, generateDisplayNode(dependencyNode, zIsCenteredConnection, string));
        boolean z3 = widgetRun instanceof VerticalWidgetRun;
        if (z3) {
            strGenerateDisplayNode = generateDisplayNode(((VerticalWidgetRun) widgetRun).baseline, zIsCenteredConnection, strGenerateDisplayNode);
        }
        if ((widgetRun instanceof HorizontalWidgetRun) || (((z2 = widgetRun instanceof ChainRun)) && ((ChainRun) widgetRun).orientation == 0)) {
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = widgetRun.widget.getHorizontalDimensionBehaviour();
            if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                if (!dependencyNode.targets.isEmpty() && dependencyNode2.targets.isEmpty()) {
                    sb = new StringBuilder("\n");
                    sb.append(dependencyNode2.name());
                    sb.append(" -> ");
                    strName = dependencyNode.name();
                    sb.append(strName);
                    sb.append("\n");
                    strGenerateDisplayNode = a.e(strGenerateDisplayNode, sb.toString());
                } else if (dependencyNode.targets.isEmpty() && !dependencyNode2.targets.isEmpty()) {
                    sb = new StringBuilder("\n");
                    sb.append(dependencyNode.name());
                    sb.append(" -> ");
                    strName = dependencyNode2.name();
                    sb.append(strName);
                    sb.append("\n");
                    strGenerateDisplayNode = a.e(strGenerateDisplayNode, sb.toString());
                }
            } else if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun.widget.getDimensionRatio() > 0.0f) {
                widgetRun.widget.getDebugName();
            }
        } else if (z3 || (z2 && ((ChainRun) widgetRun).orientation == 1)) {
            ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = widgetRun.widget.getVerticalDimensionBehaviour();
            if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                if (!dependencyNode.targets.isEmpty() && dependencyNode2.targets.isEmpty()) {
                    sb = new StringBuilder("\n");
                    sb.append(dependencyNode2.name());
                    sb.append(" -> ");
                    strName = dependencyNode.name();
                    sb.append(strName);
                    sb.append("\n");
                    strGenerateDisplayNode = a.e(strGenerateDisplayNode, sb.toString());
                } else if (dependencyNode.targets.isEmpty() && !dependencyNode2.targets.isEmpty()) {
                    sb = new StringBuilder("\n");
                    sb.append(dependencyNode.name());
                    sb.append(" -> ");
                    strName = dependencyNode2.name();
                    sb.append(strName);
                    sb.append("\n");
                    strGenerateDisplayNode = a.e(strGenerateDisplayNode, sb.toString());
                }
            } else if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widgetRun.widget.getDimensionRatio() > 0.0f) {
                widgetRun.widget.getDebugName();
            }
        }
        return widgetRun instanceof ChainRun ? generateChainDisplayGraph((ChainRun) widgetRun, strGenerateDisplayNode) : strGenerateDisplayNode;
    }

    private String generateDisplayNode(DependencyNode dependencyNode, boolean z2, String str) {
        for (DependencyNode dependencyNode2 : dependencyNode.targets) {
            String strE = ("\n" + dependencyNode.name()) + " -> " + dependencyNode2.name();
            if (dependencyNode.margin > 0 || z2 || (dependencyNode.run instanceof HelperReferences)) {
                String strE2 = a.e(strE, "[");
                if (dependencyNode.margin > 0) {
                    strE2 = strE2 + "label=\"" + dependencyNode.margin + "\"";
                    if (z2) {
                        strE2 = a.e(strE2, ",");
                    }
                }
                if (z2) {
                    strE2 = a.e(strE2, " style=dashed ");
                }
                if (dependencyNode.run instanceof HelperReferences) {
                    strE2 = a.e(strE2, " style=bold,color=gray ");
                }
                strE = a.e(strE2, "]");
            }
            str = a.e(str, a.e(strE, "\n"));
        }
        return str;
    }

    private boolean isCenteredConnection(DependencyNode dependencyNode, DependencyNode dependencyNode2) {
        Iterator<DependencyNode> it = dependencyNode.targets.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next() != dependencyNode2) {
                i2++;
            }
        }
        Iterator<DependencyNode> it2 = dependencyNode2.targets.iterator();
        int i3 = 0;
        while (it2.hasNext()) {
            if (it2.next() != dependencyNode) {
                i3++;
            }
        }
        if (i2 <= 0 || i3 <= 0) {
            return false;
        }
        return USE_GROUPS;
    }

    private void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i2, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i3) {
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i2;
        measure.verticalDimension = i3;
        this.mMeasurer.measure(constraintWidget, measure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0094  */
    /* JADX WARN: Code duplicated, block: B:40:0x009c  */
    /* JADX WARN: Code duplicated, block: B:41:0x00be  */
    /* JADX WARN: Code duplicated, block: B:44:0x00de  */
    /* JADX WARN: Code duplicated, block: B:46:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:49:0x00f3  */
    /* JADX WARN: Code duplicated, block: B:56:0x0118  */
    /* JADX WARN: Instruction removed from duplicated block: B:40:0x009c, please report this as an issue */
    private String nodeDefinition(WidgetRun widgetRun) {
        String strE;
        String str;
        String str2;
        String str3;
        String str4;
        String strE2;
        String strE3;
        String str5;
        boolean z2 = widgetRun instanceof VerticalWidgetRun;
        String debugName = widgetRun.widget.getDebugName();
        ConstraintWidget constraintWidget = widgetRun.widget;
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = !z2 ? constraintWidget.getHorizontalDimensionBehaviour() : constraintWidget.getVerticalDimensionBehaviour();
        RunGroup runGroup = widgetRun.runGroup;
        String strE4 = a.e(a.e(a.e(a.e(debugName, !z2 ? "_HORIZONTAL" : "_VERTICAL"), " [shape=none, label=<"), "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">"), "  <TR>");
        if (z2) {
            strE = a.e(strE4, "    <TD ");
            if (widgetRun.start.resolved) {
                strE = a.e(strE, " BGCOLOR=\"green\"");
            }
            str = " PORT=\"TOP\" BORDER=\"1\">T</TD>";
        } else {
            strE = a.e(strE4, "    <TD ");
            if (widgetRun.start.resolved) {
                strE = a.e(strE, " BGCOLOR=\"green\"");
            }
            str = " PORT=\"LEFT\" BORDER=\"1\">L</TD>";
        }
        String strE5 = a.e(a.e(strE, str), "    <TD BORDER=\"1\" ");
        boolean z3 = widgetRun.dimension.resolved;
        if (z3 && !widgetRun.widget.measured) {
            str2 = " BGCOLOR=\"green\" ";
        } else {
            if (!z3 || !widgetRun.widget.measured) {
                if (!z3 && widgetRun.widget.measured) {
                    str2 = " BGCOLOR=\"yellow\" ";
                }
                if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    strE5 = a.e(strE5, "style=\"dashed\"");
                }
                if (runGroup != null) {
                    str3 = " [" + (runGroup.groupIndex + 1) + "/" + RunGroup.index + "]";
                } else {
                    str3 = "";
                }
                str4 = strE5 + ">" + debugName + str3 + " </TD>";
                if (z2) {
                    strE2 = a.e(str4, "    <TD ");
                    if ((widgetRun instanceof VerticalWidgetRun) && ((VerticalWidgetRun) widgetRun).baseline.resolved) {
                        strE2 = a.e(strE2, " BGCOLOR=\"green\"");
                    }
                    strE3 = a.e(a.e(strE2, " PORT=\"BASELINE\" BORDER=\"1\">b</TD>"), "    <TD ");
                    if (widgetRun.end.resolved) {
                        strE3 = a.e(strE3, " BGCOLOR=\"green\"");
                    }
                    str5 = " PORT=\"BOTTOM\" BORDER=\"1\">B</TD>";
                } else {
                    strE3 = a.e(str4, "    <TD ");
                    if (widgetRun.end.resolved) {
                        strE3 = a.e(strE3, " BGCOLOR=\"green\"");
                    }
                    str5 = " PORT=\"RIGHT\" BORDER=\"1\">R</TD>";
                }
                return a.e(a.e(a.e(strE3, str5), "  </TR></TABLE>"), ">];\n");
            }
            str2 = " BGCOLOR=\"lightgray\" ";
        }
        strE5 = a.e(strE5, str2);
        if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            strE5 = a.e(strE5, "style=\"dashed\"");
        }
        if (runGroup != null) {
            str3 = " [" + (runGroup.groupIndex + 1) + "/" + RunGroup.index + "]";
        } else {
            str3 = "";
        }
        str4 = strE5 + ">" + debugName + str3 + " </TD>";
        if (z2) {
            strE3 = a.e(str4, "    <TD ");
            if (widgetRun.end.resolved) {
                strE3 = a.e(strE3, " BGCOLOR=\"green\"");
            }
            str5 = " PORT=\"RIGHT\" BORDER=\"1\">R</TD>";
        } else {
            strE2 = a.e(str4, "    <TD ");
            if (widgetRun instanceof VerticalWidgetRun) {
                strE2 = a.e(strE2, " BGCOLOR=\"green\"");
            }
            strE3 = a.e(a.e(strE2, " PORT=\"BASELINE\" BORDER=\"1\">b</TD>"), "    <TD ");
            if (widgetRun.end.resolved) {
                strE3 = a.e(strE3, " BGCOLOR=\"green\"");
            }
            str5 = " PORT=\"BOTTOM\" BORDER=\"1\">B</TD>";
        }
        return a.e(a.e(a.e(strE3, str5), "  </TR></TABLE>"), ">];\n");
    }

    public void buildGraph() {
        buildGraph(this.mRuns);
        this.mGroups.clear();
        RunGroup.index = 0;
        findGroup(this.container.horizontalRun, 0, this.mGroups);
        findGroup(this.container.verticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2) {
        if (this.mNeedBuildGraph) {
            buildGraph();
            boolean z2 = false;
            for (ConstraintWidget constraintWidget : this.container.mChildren) {
                boolean[] zArr = constraintWidget.isTerminalWidget;
                zArr[0] = USE_GROUPS;
                zArr[1] = USE_GROUPS;
                if (constraintWidget instanceof Barrier) {
                    z2 = USE_GROUPS;
                }
            }
            if (z2) {
                return;
            }
            for (RunGroup runGroup : this.mGroups) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                runGroup.defineTerminalWidgets(dimensionBehaviour == dimensionBehaviour3 ? USE_GROUPS : false, dimensionBehaviour2 == dimensionBehaviour3 ? USE_GROUPS : false);
            }
        }
    }

    public boolean directMeasure(boolean z2) {
        boolean z3;
        boolean z4 = USE_GROUPS;
        boolean z5 = z2 & USE_GROUPS;
        if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
            for (ConstraintWidget constraintWidget : this.container.mChildren) {
                constraintWidget.measured = false;
                constraintWidget.horizontalRun.reset();
                constraintWidget.verticalRun.reset();
            }
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            constraintWidgetContainer.measured = false;
            constraintWidgetContainer.horizontalRun.reset();
            this.container.verticalRun.reset();
            this.mNeedRedoMeasures = false;
        }
        if (basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.container.setX(0);
        this.container.setY(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.container.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.container.getDimensionBehaviour(1);
        if (this.mNeedBuildGraph) {
            buildGraph();
        }
        int x2 = this.container.getX();
        int y2 = this.container.getY();
        this.container.horizontalRun.start.resolve(x2);
        this.container.verticalRun.start.resolve(y2);
        measureWidgets();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour == dimensionBehaviour3 || dimensionBehaviour2 == dimensionBehaviour3) {
            if (z5) {
                Iterator<WidgetRun> it = this.mRuns.iterator();
                while (it.hasNext()) {
                    if (!it.next().supportsWrapComputation()) {
                        z5 = false;
                        break;
                    }
                }
            }
            if (z5 && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.container;
                constraintWidgetContainer2.setWidth(computeWrap(constraintWidgetContainer2, 0));
                ConstraintWidgetContainer constraintWidgetContainer3 = this.container;
                constraintWidgetContainer3.horizontalRun.dimension.resolve(constraintWidgetContainer3.getWidth());
            }
            if (z5 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer4 = this.container;
                constraintWidgetContainer4.setHeight(computeWrap(constraintWidgetContainer4, 1));
                ConstraintWidgetContainer constraintWidgetContainer5 = this.container;
                constraintWidgetContainer5.verticalRun.dimension.resolve(constraintWidgetContainer5.getHeight());
            }
        }
        ConstraintWidgetContainer constraintWidgetContainer6 = this.container;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = constraintWidgetContainer6.mListDimensionBehaviors[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (dimensionBehaviour4 == dimensionBehaviour5 || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int width = constraintWidgetContainer6.getWidth() + x2;
            this.container.horizontalRun.end.resolve(width);
            this.container.horizontalRun.dimension.resolve(width - x2);
            measureWidgets();
            ConstraintWidgetContainer constraintWidgetContainer7 = this.container;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = constraintWidgetContainer7.mListDimensionBehaviors[1];
            if (dimensionBehaviour6 == dimensionBehaviour5 || dimensionBehaviour6 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height = constraintWidgetContainer7.getHeight() + y2;
                this.container.verticalRun.end.resolve(height);
                this.container.verticalRun.dimension.resolve(height - y2);
            }
            measureWidgets();
            z3 = USE_GROUPS;
        } else {
            z3 = false;
        }
        for (WidgetRun widgetRun : this.mRuns) {
            if (widgetRun.widget != this.container || widgetRun.resolved) {
                widgetRun.applyToWidget();
            }
        }
        for (WidgetRun widgetRun2 : this.mRuns) {
            if (z3 || widgetRun2.widget != this.container) {
                if (!widgetRun2.start.resolved || ((!widgetRun2.end.resolved && !(widgetRun2 instanceof GuidelineReference)) || (!widgetRun2.dimension.resolved && !(widgetRun2 instanceof ChainRun) && !(widgetRun2 instanceof GuidelineReference)))) {
                    z4 = false;
                    break;
                }
            }
        }
        this.container.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.container.setVerticalDimensionBehaviour(dimensionBehaviour2);
        return z4;
    }

    public boolean directMeasureSetup(boolean z2) {
        if (this.mNeedBuildGraph) {
            for (ConstraintWidget constraintWidget : this.container.mChildren) {
                constraintWidget.measured = false;
                HorizontalWidgetRun horizontalWidgetRun = constraintWidget.horizontalRun;
                horizontalWidgetRun.dimension.resolved = false;
                horizontalWidgetRun.resolved = false;
                horizontalWidgetRun.reset();
                VerticalWidgetRun verticalWidgetRun = constraintWidget.verticalRun;
                verticalWidgetRun.dimension.resolved = false;
                verticalWidgetRun.resolved = false;
                verticalWidgetRun.reset();
            }
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            constraintWidgetContainer.measured = false;
            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidgetContainer.horizontalRun;
            horizontalWidgetRun2.dimension.resolved = false;
            horizontalWidgetRun2.resolved = false;
            horizontalWidgetRun2.reset();
            VerticalWidgetRun verticalWidgetRun2 = this.container.verticalRun;
            verticalWidgetRun2.dimension.resolved = false;
            verticalWidgetRun2.resolved = false;
            verticalWidgetRun2.reset();
            buildGraph();
        }
        if (basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.container.setX(0);
        this.container.setY(0);
        this.container.horizontalRun.start.resolve(0);
        this.container.verticalRun.start.resolve(0);
        return USE_GROUPS;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00be  */
    public boolean directMeasureWithOrientation(boolean z2, int i2) {
        boolean z3;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        DimensionDependency dimensionDependency;
        int height;
        boolean z4 = USE_GROUPS;
        boolean z5 = z2 & USE_GROUPS;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.container.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = this.container.getDimensionBehaviour(1);
        int x2 = this.container.getX();
        int y2 = this.container.getY();
        if (z5 && (dimensionBehaviour2 == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || dimensionBehaviour3 == dimensionBehaviour)) {
            for (WidgetRun widgetRun : this.mRuns) {
                if (widgetRun.orientation == i2 && !widgetRun.supportsWrapComputation()) {
                    z5 = false;
                    break;
                }
            }
            if (i2 == 0) {
                if (z5 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    ConstraintWidgetContainer constraintWidgetContainer = this.container;
                    constraintWidgetContainer.setWidth(computeWrap(constraintWidgetContainer, 0));
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.container;
                    dimensionDependency = constraintWidgetContainer2.horizontalRun.dimension;
                    height = constraintWidgetContainer2.getWidth();
                    dimensionDependency.resolve(height);
                }
            } else if (z5 && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer3 = this.container;
                constraintWidgetContainer3.setHeight(computeWrap(constraintWidgetContainer3, 1));
                ConstraintWidgetContainer constraintWidgetContainer4 = this.container;
                dimensionDependency = constraintWidgetContainer4.verticalRun.dimension;
                height = constraintWidgetContainer4.getHeight();
                dimensionDependency.resolve(height);
            }
        }
        ConstraintWidgetContainer constraintWidgetContainer5 = this.container;
        if (i2 == 0) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = constraintWidgetContainer5.mListDimensionBehaviors[0];
            if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int width = constraintWidgetContainer5.getWidth() + x2;
                this.container.horizontalRun.end.resolve(width);
                this.container.horizontalRun.dimension.resolve(width - x2);
                z3 = USE_GROUPS;
            } else {
                z3 = false;
            }
        } else {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidgetContainer5.mListDimensionBehaviors[1];
            if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height2 = constraintWidgetContainer5.getHeight() + y2;
                this.container.verticalRun.end.resolve(height2);
                this.container.verticalRun.dimension.resolve(height2 - y2);
                z3 = USE_GROUPS;
            } else {
                z3 = false;
            }
        }
        measureWidgets();
        for (WidgetRun widgetRun2 : this.mRuns) {
            if (widgetRun2.orientation == i2 && (widgetRun2.widget != this.container || widgetRun2.resolved)) {
                widgetRun2.applyToWidget();
            }
        }
        for (WidgetRun widgetRun3 : this.mRuns) {
            if (widgetRun3.orientation == i2 && (z3 || widgetRun3.widget != this.container)) {
                if (!widgetRun3.start.resolved || !widgetRun3.end.resolved || (!(widgetRun3 instanceof ChainRun) && !widgetRun3.dimension.resolved)) {
                    z4 = false;
                    break;
                }
            }
        }
        this.container.setHorizontalDimensionBehaviour(dimensionBehaviour2);
        this.container.setVerticalDimensionBehaviour(dimensionBehaviour3);
        return z4;
    }

    public void invalidateGraph() {
        this.mNeedBuildGraph = USE_GROUPS;
    }

    public void invalidateMeasures() {
        this.mNeedRedoMeasures = USE_GROUPS;
    }

    public void measureWidgets() {
        DimensionDependency dimensionDependency;
        int width;
        DimensionDependency dimensionDependency2;
        int width2;
        DimensionDependency dimensionDependency3;
        for (ConstraintWidget constraintWidget : this.container.mChildren) {
            if (!constraintWidget.measured) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                boolean z2 = false;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                int i2 = constraintWidget.mMatchConstraintDefaultWidth;
                int i3 = constraintWidget.mMatchConstraintDefaultHeight;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean z3 = (dimensionBehaviour == dimensionBehaviour3 || (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i2 == 1)) ? USE_GROUPS : false;
                if (dimensionBehaviour2 == dimensionBehaviour3 || (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i3 == 1)) {
                    z2 = USE_GROUPS;
                }
                DimensionDependency dimensionDependency4 = constraintWidget.horizontalRun.dimension;
                boolean z4 = dimensionDependency4.resolved;
                DimensionDependency dimensionDependency5 = constraintWidget.verticalRun.dimension;
                boolean z5 = dimensionDependency5.resolved;
                if (z4 && z5) {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
                    measure(constraintWidget, dimensionBehaviour4, dimensionDependency4.value, dimensionBehaviour4, dimensionDependency5.value);
                } else {
                    if (z4 && z2) {
                        measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, dimensionDependency4.value, dimensionBehaviour3, dimensionDependency5.value);
                        if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            dimensionDependency2 = constraintWidget.verticalRun.dimension;
                            width2 = constraintWidget.getHeight();
                            dimensionDependency2.wrapValue = width2;
                        } else {
                            dimensionDependency = constraintWidget.verticalRun.dimension;
                            width = constraintWidget.getHeight();
                            dimensionDependency.resolve(width);
                        }
                    } else if (z5 && z3) {
                        measure(constraintWidget, dimensionBehaviour3, dimensionDependency4.value, ConstraintWidget.DimensionBehaviour.FIXED, dimensionDependency5.value);
                        if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            dimensionDependency2 = constraintWidget.horizontalRun.dimension;
                            width2 = constraintWidget.getWidth();
                            dimensionDependency2.wrapValue = width2;
                        } else {
                            dimensionDependency = constraintWidget.horizontalRun.dimension;
                            width = constraintWidget.getWidth();
                            dimensionDependency.resolve(width);
                        }
                    }
                    if (!constraintWidget.measured && (dimensionDependency3 = constraintWidget.verticalRun.baselineDimension) != null) {
                        dimensionDependency3.resolve(constraintWidget.getBaselineDistance());
                    }
                }
                constraintWidget.measured = USE_GROUPS;
                if (!constraintWidget.measured) {
                }
            }
        }
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
    }

    public void buildGraph(ArrayList<WidgetRun> arrayList) {
        WidgetRun guidelineReference;
        arrayList.clear();
        this.mContainer.horizontalRun.clear();
        this.mContainer.verticalRun.clear();
        arrayList.add(this.mContainer.horizontalRun);
        arrayList.add(this.mContainer.verticalRun);
        HashSet hashSet = null;
        for (ConstraintWidget constraintWidget : this.mContainer.mChildren) {
            if (constraintWidget instanceof Guideline) {
                guidelineReference = new GuidelineReference(constraintWidget);
            } else {
                if (constraintWidget.isInHorizontalChain()) {
                    if (constraintWidget.horizontalChainRun == null) {
                        constraintWidget.horizontalChainRun = new ChainRun(constraintWidget, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.horizontalChainRun);
                } else {
                    arrayList.add(constraintWidget.horizontalRun);
                }
                if (constraintWidget.isInVerticalChain()) {
                    if (constraintWidget.verticalChainRun == null) {
                        constraintWidget.verticalChainRun = new ChainRun(constraintWidget, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.verticalChainRun);
                } else {
                    arrayList.add(constraintWidget.verticalRun);
                }
                if (constraintWidget instanceof HelperWidget) {
                    guidelineReference = new HelperReferences(constraintWidget);
                }
            }
            arrayList.add(guidelineReference);
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        Iterator<WidgetRun> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        for (WidgetRun widgetRun : arrayList) {
            if (widgetRun.widget != this.mContainer) {
                widgetRun.apply();
            }
        }
    }
}
