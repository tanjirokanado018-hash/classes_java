package androidx.profileinstaller;

import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(19)
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class DeviceProfileWriter {

    @NonNull
    private final String mApkName;

    @NonNull
    private final AssetManager mAssetManager;

    @NonNull
    private final File mCurProfile;

    @NonNull
    private final ProfileInstaller.DiagnosticsCallback mDiagnostics;

    @NonNull
    private final Executor mExecutor;

    @Nullable
    private DexProfileData[] mProfile;

    @NonNull
    private final String mProfileMetaSourceLocation;

    @NonNull
    private final String mProfileSourceLocation;

    @Nullable
    private byte[] mTranscodedProfile;
    private boolean mDeviceSupportsAotProfile = false;

    @Nullable
    private final byte[] mDesiredVersion = desiredVersion();

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public DeviceProfileWriter(@NonNull AssetManager assetManager, @NonNull Executor executor, @NonNull ProfileInstaller.DiagnosticsCallback diagnosticsCallback, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull File file) {
        this.mAssetManager = assetManager;
        this.mExecutor = executor;
        this.mDiagnostics = diagnosticsCallback;
        this.mApkName = str;
        this.mProfileSourceLocation = str2;
        this.mProfileMetaSourceLocation = str3;
        this.mCurProfile = file;
    }

    @Nullable
    private DeviceProfileWriter addMetadata(DexProfileData[] dexProfileDataArr, byte[] bArr) {
        ProfileInstaller.DiagnosticsCallback diagnosticsCallback;
        int i2;
        try {
            InputStream inputStreamOpenStreamFromAssets = openStreamFromAssets(this.mAssetManager, this.mProfileMetaSourceLocation);
            if (inputStreamOpenStreamFromAssets == null) {
                if (inputStreamOpenStreamFromAssets != null) {
                    inputStreamOpenStreamFromAssets.close();
                }
                return null;
            }
            try {
                this.mProfile = ProfileTranscoder.readMeta(inputStreamOpenStreamFromAssets, ProfileTranscoder.readHeader(inputStreamOpenStreamFromAssets, ProfileTranscoder.MAGIC_PROFM), bArr, dexProfileDataArr);
                inputStreamOpenStreamFromAssets.close();
                return this;
            } catch (Throwable th) {
                try {
                    inputStreamOpenStreamFromAssets.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e2) {
            e = e2;
            diagnosticsCallback = this.mDiagnostics;
            i2 = 9;
            diagnosticsCallback.onResultReceived(i2, e);
        } catch (IOException e3) {
            e = e3;
            diagnosticsCallback = this.mDiagnostics;
            i2 = 7;
            diagnosticsCallback.onResultReceived(i2, e);
        } catch (IllegalStateException e4) {
            e = e4;
            this.mProfile = null;
            diagnosticsCallback = this.mDiagnostics;
            i2 = 8;
            diagnosticsCallback.onResultReceived(i2, e);
        }
    }

    private void assertDeviceAllowsProfileInstallerAotWritesCalled() {
        if (!this.mDeviceSupportsAotProfile) {
            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
        }
    }

    @Nullable
    private static byte[] desiredVersion() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 24 || i2 > 34) {
            return null;
        }
        switch (i2) {
            case 24:
            case 25:
                return ProfileVersion.V001_N;
            case 26:
                return ProfileVersion.V005_O;
            case 27:
                return ProfileVersion.V009_O_MR1;
            case 28:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS /* 30 */:
                return ProfileVersion.V010_P;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
            case 32:
            case 33:
            case 34:
                return ProfileVersion.V015_S;
            default:
                return null;
        }
    }

    @Nullable
    private InputStream getProfileInputStream(AssetManager assetManager) {
        ProfileInstaller.DiagnosticsCallback diagnosticsCallback;
        int i2;
        try {
            return openStreamFromAssets(assetManager, this.mProfileSourceLocation);
        } catch (FileNotFoundException e2) {
            e = e2;
            diagnosticsCallback = this.mDiagnostics;
            i2 = 6;
            diagnosticsCallback.onResultReceived(i2, e);
            return null;
        } catch (IOException e3) {
            e = e3;
            diagnosticsCallback = this.mDiagnostics;
            i2 = 7;
            diagnosticsCallback.onResultReceived(i2, e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$result$0(int i2, Object obj) {
        this.mDiagnostics.onResultReceived(i2, obj);
    }

    @Nullable
    private InputStream openStreamFromAssets(AssetManager assetManager, String str) {
        try {
            return assetManager.openFd(str).createInputStream();
        } catch (FileNotFoundException e2) {
            String message = e2.getMessage();
            if (message != null && message.contains("compressed")) {
                this.mDiagnostics.onDiagnosticReceived(5, null);
            }
            return null;
        }
    }

    @Nullable
    private DexProfileData[] readProfileInternal(InputStream inputStream) {
        try {
            try {
                try {
                    try {
                        DexProfileData[] profile = ProfileTranscoder.readProfile(inputStream, ProfileTranscoder.readHeader(inputStream, ProfileTranscoder.MAGIC_PROF), this.mApkName);
                        try {
                            return profile;
                        } catch (IOException e2) {
                            return profile;
                        }
                    } catch (IOException e3) {
                        this.mDiagnostics.onResultReceived(7, e3);
                        return null;
                    }
                } catch (IllegalStateException e4) {
                    this.mDiagnostics.onResultReceived(8, e4);
                    inputStream.close();
                    return null;
                }
            } catch (IOException e5) {
                this.mDiagnostics.onResultReceived(7, e5);
                inputStream.close();
                return null;
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e6) {
                this.mDiagnostics.onResultReceived(7, e6);
            }
        }
    }

    private static boolean requiresMetadata() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 24 || i2 > 34) {
            return false;
        }
        if (i2 != 24 && i2 != 25) {
            switch (i2) {
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                case 32:
                case 33:
                case 34:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private void result(int i2, @Nullable Object obj) {
        this.mExecutor.execute(new a(this, i2, obj, 2));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean deviceAllowsProfileInstallerAotWrites() {
        if (this.mDesiredVersion == null) {
            result(3, Integer.valueOf(Build.VERSION.SDK_INT));
            return false;
        }
        if (!this.mCurProfile.exists()) {
            try {
                this.mCurProfile.createNewFile();
            } catch (IOException unused) {
                result(4, null);
                return false;
            }
        } else if (!this.mCurProfile.canWrite()) {
            result(4, null);
            return false;
        }
        this.mDeviceSupportsAotProfile = true;
        return true;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public DeviceProfileWriter read() {
        DeviceProfileWriter deviceProfileWriterAddMetadata;
        assertDeviceAllowsProfileInstallerAotWritesCalled();
        if (this.mDesiredVersion == null) {
            return this;
        }
        InputStream profileInputStream = getProfileInputStream(this.mAssetManager);
        if (profileInputStream != null) {
            this.mProfile = readProfileInternal(profileInputStream);
        }
        DexProfileData[] dexProfileDataArr = this.mProfile;
        return (dexProfileDataArr == null || !requiresMetadata() || (deviceProfileWriterAddMetadata = addMetadata(dexProfileDataArr, this.mDesiredVersion)) == null) ? this : deviceProfileWriterAddMetadata;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public DeviceProfileWriter transcodeIfNeeded() {
        ProfileInstaller.DiagnosticsCallback diagnosticsCallback;
        int i2;
        DexProfileData[] dexProfileDataArr = this.mProfile;
        byte[] bArr = this.mDesiredVersion;
        if (dexProfileDataArr != null && bArr != null) {
            assertDeviceAllowsProfileInstallerAotWritesCalled();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ProfileTranscoder.writeHeader(byteArrayOutputStream, bArr);
                    if (!ProfileTranscoder.transcodeAndWriteBody(byteArrayOutputStream, bArr, dexProfileDataArr)) {
                        this.mDiagnostics.onResultReceived(5, null);
                        this.mProfile = null;
                        byteArrayOutputStream.close();
                        return this;
                    }
                    this.mTranscodedProfile = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    this.mProfile = null;
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                diagnosticsCallback = this.mDiagnostics;
                i2 = 7;
                diagnosticsCallback.onResultReceived(i2, e);
            } catch (IllegalStateException e3) {
                e = e3;
                diagnosticsCallback = this.mDiagnostics;
                i2 = 8;
                diagnosticsCallback.onResultReceived(i2, e);
            }
        }
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean write() {
        byte[] bArr = this.mTranscodedProfile;
        if (bArr == null) {
            return false;
        }
        assertDeviceAllowsProfileInstallerAotWritesCalled();
        try {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(this.mCurProfile);
                    try {
                        Encoding.writeAll(byteArrayInputStream, fileOutputStream);
                        result(1, null);
                        fileOutputStream.close();
                        byteArrayInputStream.close();
                        this.mTranscodedProfile = null;
                        this.mProfile = null;
                        return true;
                    } catch (Throwable th) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            } catch (Throwable th5) {
                this.mTranscodedProfile = null;
                this.mProfile = null;
                throw th5;
            }
        } catch (FileNotFoundException e2) {
            result(6, e2);
            this.mTranscodedProfile = null;
            this.mProfile = null;
            return false;
        } catch (IOException e3) {
            result(7, e3);
            this.mTranscodedProfile = null;
            this.mProfile = null;
            return false;
        }
    }
}
