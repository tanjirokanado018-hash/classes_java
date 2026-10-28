package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ShapeAppearancePathProvider {
    private final ShapePath[] cornerPaths = new ShapePath[4];
    private final Matrix[] cornerTransforms = new Matrix[4];
    private final Matrix[] edgeTransforms = new Matrix[4];
    private final PointF pointF = new PointF();
    private final Path overlappedEdgePath = new Path();
    private final Path boundsPath = new Path();
    private final ShapePath shapePath = new ShapePath();
    private final float[] scratch = new float[2];
    private final float[] scratch2 = new float[2];
    private final Path edgePath = new Path();
    private final Path cornerPath = new Path();
    private boolean edgeIntersectionCheckEnabled = true;

    public static class Lazy {
        static final ShapeAppearancePathProvider INSTANCE = new ShapeAppearancePathProvider();

        private Lazy() {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface PathListener {
        void onCornerPathCreated(ShapePath shapePath, Matrix matrix, int i2);

        void onEdgePathCreated(ShapePath shapePath, Matrix matrix, int i2);
    }

    public static final class ShapeAppearancePathSpec {

        @NonNull
        public final RectF bounds;
        public final float interpolation;

        @NonNull
        public final Path path;

        @Nullable
        public final PathListener pathListener;

        @NonNull
        public final ShapeAppearanceModel shapeAppearanceModel;

        public ShapeAppearancePathSpec(@NonNull ShapeAppearanceModel shapeAppearanceModel, float f2, RectF rectF, @Nullable PathListener pathListener, Path path) {
            this.pathListener = pathListener;
            this.shapeAppearanceModel = shapeAppearanceModel;
            this.interpolation = f2;
            this.bounds = rectF;
            this.path = path;
        }
    }

    public ShapeAppearancePathProvider() {
        for (int i2 = 0; i2 < 4; i2++) {
            this.cornerPaths[i2] = new ShapePath();
            this.cornerTransforms[i2] = new Matrix();
            this.edgeTransforms[i2] = new Matrix();
        }
    }

    private float angleOfEdge(int i2) {
        return ((i2 + 1) % 4) * 90;
    }

    private void appendCornerPath(@NonNull ShapeAppearancePathSpec shapeAppearancePathSpec, int i2) {
        this.scratch[0] = this.cornerPaths[i2].getStartX();
        this.scratch[1] = this.cornerPaths[i2].getStartY();
        this.cornerTransforms[i2].mapPoints(this.scratch);
        Path path = shapeAppearancePathSpec.path;
        float[] fArr = this.scratch;
        if (i2 == 0) {
            path.moveTo(fArr[0], fArr[1]);
        } else {
            path.lineTo(fArr[0], fArr[1]);
        }
        this.cornerPaths[i2].applyToPath(this.cornerTransforms[i2], shapeAppearancePathSpec.path);
        PathListener pathListener = shapeAppearancePathSpec.pathListener;
        if (pathListener != null) {
            pathListener.onCornerPathCreated(this.cornerPaths[i2], this.cornerTransforms[i2], i2);
        }
    }

    private void appendEdgePath(@NonNull ShapeAppearancePathSpec shapeAppearancePathSpec, int i2) {
        ShapePath shapePath;
        Matrix matrix;
        Path path;
        int i3 = (i2 + 1) % 4;
        this.scratch[0] = this.cornerPaths[i2].getEndX();
        this.scratch[1] = this.cornerPaths[i2].getEndY();
        this.cornerTransforms[i2].mapPoints(this.scratch);
        this.scratch2[0] = this.cornerPaths[i3].getStartX();
        this.scratch2[1] = this.cornerPaths[i3].getStartY();
        this.cornerTransforms[i3].mapPoints(this.scratch2);
        float[] fArr = this.scratch;
        float f2 = fArr[0];
        float[] fArr2 = this.scratch2;
        float fMax = Math.max(((float) Math.hypot(f2 - fArr2[0], fArr[1] - fArr2[1])) - 0.001f, 0.0f);
        float edgeCenterForIndex = getEdgeCenterForIndex(shapeAppearancePathSpec.bounds, i2);
        this.shapePath.reset(0.0f, 0.0f);
        EdgeTreatment edgeTreatmentForIndex = getEdgeTreatmentForIndex(i2, shapeAppearancePathSpec.shapeAppearanceModel);
        edgeTreatmentForIndex.getEdgePath(fMax, edgeCenterForIndex, shapeAppearancePathSpec.interpolation, this.shapePath);
        this.edgePath.reset();
        this.shapePath.applyToPath(this.edgeTransforms[i2], this.edgePath);
        if (this.edgeIntersectionCheckEnabled && (edgeTreatmentForIndex.forceIntersection() || pathOverlapsCorner(this.edgePath, i2) || pathOverlapsCorner(this.edgePath, i3))) {
            Path path2 = this.edgePath;
            path2.op(path2, this.boundsPath, Path.Op.DIFFERENCE);
            this.scratch[0] = this.shapePath.getStartX();
            this.scratch[1] = this.shapePath.getStartY();
            this.edgeTransforms[i2].mapPoints(this.scratch);
            Path path3 = this.overlappedEdgePath;
            float[] fArr3 = this.scratch;
            path3.moveTo(fArr3[0], fArr3[1]);
            shapePath = this.shapePath;
            matrix = this.edgeTransforms[i2];
            path = this.overlappedEdgePath;
        } else {
            shapePath = this.shapePath;
            matrix = this.edgeTransforms[i2];
            path = shapeAppearancePathSpec.path;
        }
        shapePath.applyToPath(matrix, path);
        PathListener pathListener = shapeAppearancePathSpec.pathListener;
        if (pathListener != null) {
            pathListener.onEdgePathCreated(this.shapePath, this.edgeTransforms[i2], i2);
        }
    }

    private void getCoordinatesOfCorner(int i2, @NonNull RectF rectF, @NonNull PointF pointF) {
        float f2;
        float f3;
        if (i2 != 1) {
            if (i2 != 2) {
                f2 = i2 != 3 ? rectF.right : rectF.left;
                f3 = rectF.top;
            } else {
                f2 = rectF.left;
            }
            pointF.set(f2, f3);
        }
        f2 = rectF.right;
        f3 = rectF.bottom;
        pointF.set(f2, f3);
    }

    private CornerSize getCornerSizeForIndex(int i2, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (i2 == 1) {
            return shapeAppearanceModel.getBottomRightCornerSize();
        }
        if (i2 != 2) {
            return i2 != 3 ? shapeAppearanceModel.getTopRightCornerSize() : shapeAppearanceModel.getTopLeftCornerSize();
        }
        return shapeAppearanceModel.getBottomLeftCornerSize();
    }

    private CornerTreatment getCornerTreatmentForIndex(int i2, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (i2 == 1) {
            return shapeAppearanceModel.getBottomRightCorner();
        }
        if (i2 != 2) {
            return i2 != 3 ? shapeAppearanceModel.getTopRightCorner() : shapeAppearanceModel.getTopLeftCorner();
        }
        return shapeAppearanceModel.getBottomLeftCorner();
    }

    private float getEdgeCenterForIndex(@NonNull RectF rectF, int i2) {
        float fCenterX;
        float f2;
        float[] fArr = this.scratch;
        ShapePath shapePath = this.cornerPaths[i2];
        fArr[0] = shapePath.endX;
        fArr[1] = shapePath.endY;
        this.cornerTransforms[i2].mapPoints(fArr);
        if (i2 == 1 || i2 == 3) {
            fCenterX = rectF.centerX();
            f2 = this.scratch[0];
        } else {
            fCenterX = rectF.centerY();
            f2 = this.scratch[1];
        }
        return Math.abs(fCenterX - f2);
    }

    private EdgeTreatment getEdgeTreatmentForIndex(int i2, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (i2 == 1) {
            return shapeAppearanceModel.getBottomEdge();
        }
        if (i2 != 2) {
            return i2 != 3 ? shapeAppearanceModel.getRightEdge() : shapeAppearanceModel.getTopEdge();
        }
        return shapeAppearanceModel.getLeftEdge();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @UiThread
    public static ShapeAppearancePathProvider getInstance() {
        return Lazy.INSTANCE;
    }

    @RequiresApi(19)
    private boolean pathOverlapsCorner(Path path, int i2) {
        this.cornerPath.reset();
        this.cornerPaths[i2].applyToPath(this.cornerTransforms[i2], this.cornerPath);
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        this.cornerPath.computeBounds(rectF, true);
        path.op(this.cornerPath, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        if (rectF.isEmpty()) {
            return rectF.width() > 1.0f && rectF.height() > 1.0f;
        }
        return true;
    }

    private void setCornerPathAndTransform(@NonNull ShapeAppearancePathSpec shapeAppearancePathSpec, int i2) {
        getCornerTreatmentForIndex(i2, shapeAppearancePathSpec.shapeAppearanceModel).getCornerPath(this.cornerPaths[i2], 90.0f, shapeAppearancePathSpec.interpolation, shapeAppearancePathSpec.bounds, getCornerSizeForIndex(i2, shapeAppearancePathSpec.shapeAppearanceModel));
        float fAngleOfEdge = angleOfEdge(i2);
        this.cornerTransforms[i2].reset();
        getCoordinatesOfCorner(i2, shapeAppearancePathSpec.bounds, this.pointF);
        Matrix matrix = this.cornerTransforms[i2];
        PointF pointF = this.pointF;
        matrix.setTranslate(pointF.x, pointF.y);
        this.cornerTransforms[i2].preRotate(fAngleOfEdge);
    }

    private void setEdgePathAndTransform(int i2) {
        this.scratch[0] = this.cornerPaths[i2].getEndX();
        this.scratch[1] = this.cornerPaths[i2].getEndY();
        this.cornerTransforms[i2].mapPoints(this.scratch);
        float fAngleOfEdge = angleOfEdge(i2);
        this.edgeTransforms[i2].reset();
        Matrix matrix = this.edgeTransforms[i2];
        float[] fArr = this.scratch;
        matrix.setTranslate(fArr[0], fArr[1]);
        this.edgeTransforms[i2].preRotate(fAngleOfEdge);
    }

    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f2, RectF rectF, @NonNull Path path) {
        calculatePath(shapeAppearanceModel, f2, rectF, null, path);
    }

    public void setEdgeIntersectionCheckEnable(boolean z2) {
        this.edgeIntersectionCheckEnabled = z2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f2, RectF rectF, PathListener pathListener, @NonNull Path path) {
        path.rewind();
        this.overlappedEdgePath.rewind();
        this.boundsPath.rewind();
        this.boundsPath.addRect(rectF, Path.Direction.CW);
        ShapeAppearancePathSpec shapeAppearancePathSpec = new ShapeAppearancePathSpec(shapeAppearanceModel, f2, rectF, pathListener, path);
        for (int i2 = 0; i2 < 4; i2++) {
            setCornerPathAndTransform(shapeAppearancePathSpec, i2);
            setEdgePathAndTransform(i2);
        }
        for (int i3 = 0; i3 < 4; i3++) {
            appendCornerPath(shapeAppearancePathSpec, i3);
            appendEdgePath(shapeAppearancePathSpec, i3);
        }
        path.close();
        this.overlappedEdgePath.close();
        if (this.overlappedEdgePath.isEmpty()) {
            return;
        }
        path.op(this.overlappedEdgePath, Path.Op.UNION);
    }
}
