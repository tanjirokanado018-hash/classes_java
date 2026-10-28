package androidx.constraintlayout.solver.widgets.analyzer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class DimensionDependency extends DependencyNode {
    public int wrapValue;

    public DimensionDependency(WidgetRun widgetRun) {
        super(widgetRun);
        this.type = widgetRun instanceof HorizontalWidgetRun ? DependencyNode.Type.HORIZONTAL_DIMENSION : DependencyNode.Type.VERTICAL_DIMENSION;
    }

    @Override // androidx.constraintlayout.solver.widgets.analyzer.DependencyNode
    public void resolve(int i2) {
        if (this.resolved) {
            return;
        }
        this.resolved = true;
        this.value = i2;
        for (Dependency dependency : this.dependencies) {
            dependency.update(dependency);
        }
    }
}
