package kotlin.jvm.internal;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class q {
    public static String a(h hVar) {
        String string = hVar.getClass().getGenericInterfaces()[0].toString();
        return string.startsWith("kotlin.jvm.functions.") ? string.substring(21) : string;
    }
}
