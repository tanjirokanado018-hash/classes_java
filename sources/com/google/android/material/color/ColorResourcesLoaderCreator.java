package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import android.content.res.loader.ResourcesProvider;
import android.os.ParcelFileDescriptor;
import android.system.Os;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.p;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
final class ColorResourcesLoaderCreator {
    private static final String TAG = "ColorResLoaderCreator";

    private ColorResourcesLoaderCreator() {
    }

    @Nullable
    public static ResourcesLoader create(@NonNull Context context, @NonNull Map<Integer, Integer> map) throws Throwable {
        FileDescriptor fileDescriptorMemfd_create;
        try {
            byte[] bArrCreate = ColorResourcesTableCreator.create(context, map);
            int length = bArrCreate.length;
            if (bArrCreate.length == 0) {
                return null;
            }
            try {
                fileDescriptorMemfd_create = Os.memfd_create("temp.arsc", 0);
                if (fileDescriptorMemfd_create == null) {
                    if (fileDescriptorMemfd_create != null) {
                        Os.close(fileDescriptorMemfd_create);
                    }
                    return null;
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptorMemfd_create);
                    try {
                        fileOutputStream.write(bArrCreate);
                        ParcelFileDescriptor parcelFileDescriptorDup = ParcelFileDescriptor.dup(fileDescriptorMemfd_create);
                        try {
                            p.i();
                            ResourcesLoader resourcesLoaderC = p.c();
                            resourcesLoaderC.addProvider(ResourcesProvider.loadFromTable(parcelFileDescriptorDup, null));
                            if (parcelFileDescriptorDup != null) {
                                parcelFileDescriptorDup.close();
                            }
                            fileOutputStream.close();
                            Os.close(fileDescriptorMemfd_create);
                            return resourcesLoaderC;
                        } catch (Throwable th) {
                            if (parcelFileDescriptorDup != null) {
                                try {
                                    parcelFileDescriptorDup.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    if (fileDescriptorMemfd_create != null) {
                        Os.close(fileDescriptorMemfd_create);
                    }
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                fileDescriptorMemfd_create = null;
            }
        } catch (Exception unused) {
            return null;
        }
    }
}
