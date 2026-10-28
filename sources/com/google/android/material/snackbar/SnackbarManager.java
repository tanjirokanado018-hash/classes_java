package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class SnackbarManager {
    private static final int LONG_DURATION_MS = 2750;
    static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static SnackbarManager snackbarManager;

    @Nullable
    private SnackbarRecord currentSnackbar;

    @Nullable
    private SnackbarRecord nextSnackbar;

    @NonNull
    private final Object lock = new Object();

    @NonNull
    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.SnackbarManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(@NonNull Message message) {
            if (message.what != 0) {
                return false;
            }
            SnackbarManager.this.handleTimeout((SnackbarRecord) message.obj);
            return true;
        }
    });

    public interface Callback {
        void dismiss(int i2);

        void show();
    }

    public static class SnackbarRecord {

        @NonNull
        final WeakReference<Callback> callback;
        int duration;
        boolean paused;

        public SnackbarRecord(int i2, Callback callback) {
            this.callback = new WeakReference<>(callback);
            this.duration = i2;
        }

        public boolean isSnackbar(@Nullable Callback callback) {
            return callback != null && this.callback.get() == callback;
        }
    }

    private SnackbarManager() {
    }

    private boolean cancelSnackbarLocked(@NonNull SnackbarRecord snackbarRecord, int i2) {
        Callback callback = snackbarRecord.callback.get();
        if (callback == null) {
            return false;
        }
        this.handler.removeCallbacksAndMessages(snackbarRecord);
        callback.dismiss(i2);
        return true;
    }

    public static SnackbarManager getInstance() {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager();
        }
        return snackbarManager;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.currentSnackbar;
        return snackbarRecord != null && snackbarRecord.isSnackbar(callback);
    }

    private boolean isNextSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        return snackbarRecord != null && snackbarRecord.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(@NonNull SnackbarRecord snackbarRecord) {
        int i2 = snackbarRecord.duration;
        if (i2 == -2) {
            return;
        }
        if (i2 <= 0) {
            i2 = i2 == -1 ? SHORT_DURATION_MS : LONG_DURATION_MS;
        }
        this.handler.removeCallbacksAndMessages(snackbarRecord);
        Handler handler = this.handler;
        handler.sendMessageDelayed(Message.obtain(handler, 0, snackbarRecord), i2);
    }

    private void showNextSnackbarLocked() {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        if (snackbarRecord != null) {
            this.currentSnackbar = snackbarRecord;
            this.nextSnackbar = null;
            Callback callback = snackbarRecord.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                this.currentSnackbar = null;
            }
        }
    }

    public void dismiss(Callback callback, int i2) {
        SnackbarRecord snackbarRecord;
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    snackbarRecord = this.currentSnackbar;
                } else if (isNextSnackbarLocked(callback)) {
                    snackbarRecord = this.nextSnackbar;
                }
                cancelSnackbarLocked(snackbarRecord, i2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void handleTimeout(@NonNull SnackbarRecord snackbarRecord) {
        synchronized (this.lock) {
            try {
                if (this.currentSnackbar == snackbarRecord || this.nextSnackbar == snackbarRecord) {
                    cancelSnackbarLocked(snackbarRecord, 2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isCurrent(Callback callback) {
        boolean zIsCurrentSnackbarLocked;
        synchronized (this.lock) {
            zIsCurrentSnackbarLocked = isCurrentSnackbarLocked(callback);
        }
        return zIsCurrentSnackbarLocked;
    }

    public boolean isCurrentOrNext(Callback callback) {
        boolean z2;
        synchronized (this.lock) {
            try {
                z2 = isCurrentSnackbarLocked(callback) || isNextSnackbarLocked(callback);
            } catch (Throwable th) {
                throw th;
            }
        }
        return z2;
    }

    public void onDismissed(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    this.currentSnackbar = null;
                    if (this.nextSnackbar != null) {
                        showNextSnackbarLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onShown(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    scheduleTimeoutLocked(this.currentSnackbar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void pauseTimeout(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    SnackbarRecord snackbarRecord = this.currentSnackbar;
                    if (!snackbarRecord.paused) {
                        snackbarRecord.paused = true;
                        this.handler.removeCallbacksAndMessages(snackbarRecord);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void restoreTimeoutIfPaused(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    SnackbarRecord snackbarRecord = this.currentSnackbar;
                    if (snackbarRecord.paused) {
                        snackbarRecord.paused = false;
                        scheduleTimeoutLocked(snackbarRecord);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void show(int i2, Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    SnackbarRecord snackbarRecord = this.currentSnackbar;
                    snackbarRecord.duration = i2;
                    this.handler.removeCallbacksAndMessages(snackbarRecord);
                    scheduleTimeoutLocked(this.currentSnackbar);
                    return;
                }
                if (isNextSnackbarLocked(callback)) {
                    this.nextSnackbar.duration = i2;
                } else {
                    this.nextSnackbar = new SnackbarRecord(i2, callback);
                }
                SnackbarRecord snackbarRecord2 = this.currentSnackbar;
                if (snackbarRecord2 == null || !cancelSnackbarLocked(snackbarRecord2, 4)) {
                    this.currentSnackbar = null;
                    showNextSnackbarLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
