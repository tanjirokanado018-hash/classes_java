package androidx.appcompat.graphics.drawable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class a {
    public static float a(float f2, float f3, float f4, float f5) {
        return ((f2 - f3) * f4) + f5;
    }

    public static String b(RecyclerView recyclerView, StringBuilder sb) {
        sb.append(recyclerView.exceptionLabel());
        return sb.toString();
    }

    public static String c(String str, int i2) {
        return str + i2;
    }

    public static String d(String str, Fragment fragment, String str2) {
        return str + fragment + str2;
    }

    public static String e(String str, String str2) {
        return str + str2;
    }

    public static String f(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String g(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static StringBuilder h(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static /* synthetic */ Iterator i() {
        try {
            return Arrays.asList(new x.a()).iterator();
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }

    public static /* synthetic */ void j(Object obj) {
        if (obj != null) {
            throw new ClassCastException();
        }
    }

    public static String k(String str, String str2) {
        return str + str2;
    }
}
