package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class CanvasKt {
    public static final void withClip(Canvas canvas, float f2, float f3, float f4, float f5, l lVar) {
        int iSave = canvas.save();
        canvas.clipRect(f2, f3, f4, f5);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withMatrix(Canvas canvas, Matrix matrix, l lVar) {
        int iSave = canvas.save();
        canvas.concat(matrix);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static /* synthetic */ void withMatrix$default(Canvas canvas, Matrix matrix, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            matrix = new Matrix();
        }
        int iSave = canvas.save();
        canvas.concat(matrix);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withRotation(Canvas canvas, float f2, float f3, float f4, l lVar) {
        int iSave = canvas.save();
        canvas.rotate(f2, f3, f4);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static /* synthetic */ void withRotation$default(Canvas canvas, float f2, float f3, float f4, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 2) != 0) {
            f3 = 0.0f;
        }
        if ((i2 & 4) != 0) {
            f4 = 0.0f;
        }
        int iSave = canvas.save();
        canvas.rotate(f2, f3, f4);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withSave(Canvas canvas, l lVar) {
        int iSave = canvas.save();
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withScale(Canvas canvas, float f2, float f3, float f4, float f5, l lVar) {
        int iSave = canvas.save();
        canvas.scale(f2, f3, f4, f5);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static /* synthetic */ void withScale$default(Canvas canvas, float f2, float f3, float f4, float f5, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = 1.0f;
        }
        if ((i2 & 2) != 0) {
            f3 = 1.0f;
        }
        if ((i2 & 4) != 0) {
            f4 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f5 = 0.0f;
        }
        int iSave = canvas.save();
        canvas.scale(f2, f3, f4, f5);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withSkew(Canvas canvas, float f2, float f3, l lVar) {
        int iSave = canvas.save();
        canvas.skew(f2, f3);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static /* synthetic */ void withSkew$default(Canvas canvas, float f2, float f3, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 2) != 0) {
            f3 = 0.0f;
        }
        int iSave = canvas.save();
        canvas.skew(f2, f3);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withTranslation(Canvas canvas, float f2, float f3, l lVar) {
        int iSave = canvas.save();
        canvas.translate(f2, f3);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static /* synthetic */ void withTranslation$default(Canvas canvas, float f2, float f3, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 2) != 0) {
            f3 = 0.0f;
        }
        int iSave = canvas.save();
        canvas.translate(f2, f3);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withClip(Canvas canvas, int i2, int i3, int i4, int i5, l lVar) {
        int iSave = canvas.save();
        canvas.clipRect(i2, i3, i4, i5);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withClip(Canvas canvas, Path path, l lVar) {
        int iSave = canvas.save();
        canvas.clipPath(path);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withClip(Canvas canvas, Rect rect, l lVar) {
        int iSave = canvas.save();
        canvas.clipRect(rect);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    public static final void withClip(Canvas canvas, RectF rectF, l lVar) {
        int iSave = canvas.save();
        canvas.clipRect(rectF);
        try {
            lVar.invoke(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }
}
