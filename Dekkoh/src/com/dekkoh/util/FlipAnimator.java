package com.dekkoh.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FlipAnimator extends Animation {
    private Camera camera;

    private View fromView;

    private View toView;

    private float centerX;

    private float centerY;

    private boolean forward = true;

    private boolean visibilitySwapped;

    /**
     * 
     * @param fromView First view in the transition.
     * @param toView Second view in the transition.
     * @param centerX The center of the views in the x-axis.
     * @param centerY The center of the views in the y-axis.
     * @param forward The direction of the animation.
     */
    public FlipAnimator(View fromView, View toView, int centerX, int centerY) {
        this.fromView = fromView;
        this.toView = toView;
        this.centerX = centerX;
        this.centerY = centerY;

        setDuration(500);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void reverse() {
        forward = false;
        View temp = toView;
        toView = fromView;
        fromView = temp;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final double radians = Math.PI * interpolatedTime;
        float degrees = (float) (180.0 * radians / Math.PI);

        if (interpolatedTime >= 0.5f) {
            degrees -= 180.f;

            if (!visibilitySwapped) {
                fromView.setVisibility(View.GONE);
                toView.setVisibility(View.VISIBLE);

                visibilitySwapped = true;
            }
        }

        if (forward)
            degrees = -degrees;

        final Matrix matrix = t.getMatrix();

        camera.save();
        camera.translate(0.0f, 0.0f, (float) (150.0 * Math.sin(radians)));
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
