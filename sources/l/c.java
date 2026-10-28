package l;

import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final b f495a;

    static {
        b bVar;
        try {
            Object objNewInstance = n.b.class.newInstance();
            k.f(objNewInstance, "forName(\"kotlin.internal…entations\").newInstance()");
            try {
                try {
                    bVar = (b) objNewInstance;
                } catch (ClassCastException e2) {
                    ClassLoader classLoader = objNewInstance.getClass().getClassLoader();
                    ClassLoader classLoader2 = b.class.getClassLoader();
                    if (k.a(classLoader, classLoader2)) {
                        throw e2;
                    }
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader + ", base type classloader: " + classLoader2, e2);
                }
            } catch (ClassNotFoundException unused) {
                Object objNewInstance2 = m.b.class.newInstance();
                k.f(objNewInstance2, "forName(\"kotlin.internal…entations\").newInstance()");
                try {
                    try {
                        bVar = (b) objNewInstance2;
                    } catch (ClassNotFoundException unused2) {
                        bVar = new b();
                    }
                } catch (ClassCastException e3) {
                    ClassLoader classLoader3 = objNewInstance2.getClass().getClassLoader();
                    ClassLoader classLoader4 = b.class.getClassLoader();
                    if (k.a(classLoader3, classLoader4)) {
                        throw e3;
                    }
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader3 + ", base type classloader: " + classLoader4, e3);
                }
            }
        } catch (ClassNotFoundException unused3) {
            Object objNewInstance3 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
            k.f(objNewInstance3, "forName(\"kotlin.internal…entations\").newInstance()");
            try {
                try {
                    bVar = (b) objNewInstance3;
                } catch (ClassNotFoundException unused4) {
                    Object objNewInstance4 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                    k.f(objNewInstance4, "forName(\"kotlin.internal…entations\").newInstance()");
                    try {
                        bVar = (b) objNewInstance4;
                    } catch (ClassCastException e4) {
                        ClassLoader classLoader5 = objNewInstance4.getClass().getClassLoader();
                        ClassLoader classLoader6 = b.class.getClassLoader();
                        if (k.a(classLoader5, classLoader6)) {
                            throw e4;
                        }
                        throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader5 + ", base type classloader: " + classLoader6, e4);
                    }
                }
            } catch (ClassCastException e5) {
                ClassLoader classLoader7 = objNewInstance3.getClass().getClassLoader();
                ClassLoader classLoader8 = b.class.getClassLoader();
                if (k.a(classLoader7, classLoader8)) {
                    throw e5;
                }
                throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader7 + ", base type classloader: " + classLoader8, e5);
            }
        }
        f495a = bVar;
    }
}
