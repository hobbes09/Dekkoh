
package com.dekkoh.custom.view;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

public class CustomScaleAnimation extends ScaleAnimation {

    private float fromWidth;
    private float toWidth;
    private float fromHeight;
    private float toHeight;
    private View viewToScale;

    public CustomScaleAnimation(View viewToScale, float fromX, float toX, float fromY, float toY) {
        super(fromX, toX, fromY, toY);
        init(viewToScale, fromX, toX, fromY, toY);
    }

    public CustomScaleAnimation(View viewToScale, float fromX, float toX, float fromY, float toY,
            float pivotX, float pivotY) {
        super(fromX, toX, fromY, toY, pivotX, pivotY);
        init(viewToScale, fromX, toX, fromY, toY);
    }

    public CustomScaleAnimation(View viewToScale, float fromX, float toX, float fromY, float toY,
            int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        super(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
        init(viewToScale, fromX, toX, fromY, toY);
    }

    private void init(View viewToScale, float fromX, float toX, float fromY, float toY) {

        this.viewToScale = viewToScale;
        viewToScale.measure(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        fromHeight = viewToScale.getMeasuredHeight() * fromY;
        toHeight = viewToScale.getMeasuredHeight() * toY;
        fromWidth = viewToScale.getMeasuredWidth() * fromX;
        toWidth = viewToScale.getMeasuredWidth() * toX;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        final int newHeight = (int) (fromHeight * (1 - interpolatedTime) + toHeight
                * interpolatedTime);
        final int newWidth = (int) (fromWidth * (1 - interpolatedTime) + toWidth * interpolatedTime);

        viewToScale.getLayoutParams().height = newHeight;
        viewToScale.getLayoutParams().width = newWidth;
        viewToScale.requestLayout();
    }

}
