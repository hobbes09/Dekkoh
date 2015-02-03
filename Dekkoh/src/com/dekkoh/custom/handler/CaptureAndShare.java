package com.dekkoh.custom.handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

public class CaptureAndShare {
	 /*
     * This method is used to create the bitmap of the current activity
     * This method accepts any child view of the current view
     * You can even pass the parent container like RelativeLayout or LinearLayout as a param
     * @param : View v
     */
	public void shareIt(View view,Context ctx,String id){
	    //sharing implementation
	    List<Intent> targetedShareIntents = new ArrayList<Intent>();
	    
//	    Uri pngUri = Uri.parse("file//res/drawable/b_share.png");
	    String imagePath = Environment.getExternalStorageDirectory()+"/DekkohTemp.jpg";
	    File imageFileToShare = new File(imagePath);
	    Uri uri = Uri.fromFile(imageFileToShare);

	    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
	    sharingIntent.setType("image/*");
	   
	    String shareBody = "Dekkoh ,India's friendly search engine."+"\n"+"Get the app at "+"http://107.170.169.216:4000/questions/"+id;

	    PackageManager pm = view.getContext().getPackageManager();
	    List<ResolveInfo> activityList = pm.queryIntentActivities(sharingIntent, 0);
	
	    for(final ResolveInfo app : activityList) {

	    	String packageName = app.activityInfo.packageName;
	    	Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
	    	targetedShareIntent.setType("text/plain");
	    	targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share");

	    	if(TextUtils.equals(packageName, "com.facebook.katana")){
	    		
	    		targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT,"http://dekkoh.com");
	    	
	    	} else {
	    
	    		targetedShareIntent.putExtra(android.content.Intent.EXTRA_STREAM,uri);
	    		targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	    	}

	    	targetedShareIntent.setPackage(packageName);
	    	targetedShareIntents.add(targetedShareIntent);

	    }

	    Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share Dekkoh");

	    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
	    ctx.startActivity(chooserIntent);

	}
	public void shareItMessage(Context ctx){
	    //sharing implementation
	    List<Intent> targetedShareIntents = new ArrayList<Intent>();
	    
//	    Uri pngUri = Uri.parse("file//res/drawable/b_share.png");
	    String imagePath = Environment.getExternalStorageDirectory()+"/DekkohTemp.jpg";
	    File imageFileToShare = new File(imagePath);
	    Uri uri = Uri.fromFile(imageFileToShare);

	    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
	    sharingIntent.setType("image/*");
	   
	    String shareBody = "Dekkoh ,India's friendly search engine."+"\n"+"Get the app at http://dekkoh.com";

	    PackageManager pm = ctx.getPackageManager();
	    List<ResolveInfo> activityList = pm.queryIntentActivities(sharingIntent, 0);
	
	    for(final ResolveInfo app : activityList) {

	    	String packageName = app.activityInfo.packageName;
	    	Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
	    	targetedShareIntent.setType("text/plain");
	    	targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share");

	    	if(TextUtils.equals(packageName, "com.facebook.katana")){
	    		
	    		targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT,"http://dekkoh.com");
	    	
	    	} else {
	    
	    		targetedShareIntent.putExtra(android.content.Intent.EXTRA_STREAM,uri);
	    		targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	    	}

	    	targetedShareIntent.setPackage(packageName);
	    	targetedShareIntents.add(targetedShareIntent);

	    }

	    Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share Dekkoh");

	    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
	    ctx.startActivity(chooserIntent);

	}
	
	public Bitmap getBitmapOfView(View v)
	{
		View rootview = v.getRootView();
		rootview.setDrawingCacheEnabled(true);
		Bitmap bmp = rootview.getDrawingCache();
		return bmp;
	}

	/*
	 * This method is used to create an image file using the bitmap
	 * This method accepts an object of Bitmap class
	 * Currently we are passing the bitmap of the root view of current activity
	 * The image file will be created by the name capturedscreen.jpg
	 * @param : Bitmap bmp
	 */
	/* skip status bar in screenshot */
	
	public void createImageFromBitmap(Bitmap bmp,Window window)
	{
		Rect rectgle= new Rect();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		int StatusBarHeight= rectgle.top;

		Bitmap bit = Bitmap.createBitmap(bmp, 0, StatusBarHeight, bmp.getWidth(),bmp.getHeight() - StatusBarHeight, null, true);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		
		File file = new File( Environment.getExternalStorageDirectory() +
				"/DekkohTemp.jpg");
		try
		{
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			ostream.write(bytes.toByteArray());        
			ostream.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}    
	}
	
}
