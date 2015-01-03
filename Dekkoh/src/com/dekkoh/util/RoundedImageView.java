package com.dekkoh.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;


public class RoundedImageView {
		
	public static void setCircledLinearLayoutBackground(LinearLayout linearLayout, int drawableObject, Resources resources){
		Bitmap bMap = BitmapFactory.decodeResource(resources, drawableObject);
		bMap = circledimage(bMap);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bMap);
		linearLayout.setBackgroundDrawable(bitmapDrawable);
	}
	
	
	public static Bitmap circledimage(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
 
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        float radius = (source.getHeight()>source.getWidth())? source.getWidth() : source.getHeight();
        canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);
 
        if (source != output) {
            source.recycle();
        }
 
        return output;
    }
    
}