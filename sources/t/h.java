package t;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class h {
    public static final /* synthetic */ h[] b = {new h("PUBLIC", 0), new h("PROTECTED", 1), new h("INTERNAL", 2), new h("PRIVATE", 3)};

    /* JADX INFO: Fake field, exist only in values array */
    h EF8;

    public static h valueOf(String str) {
        return (h) Enum.valueOf(h.class, str);
    }

    public static h[] values() {
        return (h[]) b.clone();
    }
}
