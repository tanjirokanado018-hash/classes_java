package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ah implements an {
    public final boolean b;

    public ah(boolean z2) {
        this.b = z2;
    }

    @Override // w.an
    public final boolean a() {
        return this.b;
    }

    @Override // w.an
    public final A d() {
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Empty{");
        sb.append(this.b ? "Active" : "New");
        sb.append('}');
        return sb.toString();
    }
}
