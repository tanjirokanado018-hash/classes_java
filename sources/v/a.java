package v;

import java.nio.charset.Charset;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Charset f515a;

    static {
        Charset charsetForName = Charset.forName("UTF-8");
        k.f(charsetForName, "forName(\"UTF-8\")");
        f515a = charsetForName;
        k.f(Charset.forName("UTF-16"), "forName(\"UTF-16\")");
        k.f(Charset.forName("UTF-16BE"), "forName(\"UTF-16BE\")");
        k.f(Charset.forName("UTF-16LE"), "forName(\"UTF-16LE\")");
        k.f(Charset.forName("US-ASCII"), "forName(\"US-ASCII\")");
        k.f(Charset.forName("ISO-8859-1"), "forName(\"ISO-8859-1\")");
    }
}
