package com.unibz.cockpit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.GraphicalView;

import java.io.File;
import java.io.FileOutputStream;

public class Utils {
	
	//Email Validation pattern
	public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

	//Fragments Tags
	public static final String Login_Fragment = "ProjectFragment";
	public static final String SignUp_Fragment = "SignUpFragment";
	public static final String Project_Fragment = "ProjectFragment";
	public static final String ForgotPassword_Fragment = "ForgotPasswordFragment";
	public static final String Dashboard_Fragment = "DashboardFragment";


	// Base URL of API
	public static final String BASE_URL = "http://ccpm.inf.unibz.it/api/";
	//public static final String BASE_URL = "http://10.7.162.136:8080/api/";
	//public static final String BASE_URL = "http://192.168.43.125:8080/api/";

	public static void sendEmail(FragmentActivity activity,String subject, View view){

		if(checkPermissionREAD_EXTERNAL_STORAGE(activity,activity)) {
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			Bitmap mBitmap = view.getDrawingCache();

			String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), mBitmap, "Image Description", null);
			Uri uri = Uri.parse(path);

			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/jpeg");

			intent.putExtra(Intent.EXTRA_STREAM, uri);
			activity.startActivity(Intent.createChooser(intent, "Share Via"));
		}

	}


	private static Bitmap generateBitmap(View view)
	{
		//Provide it with a layout params. It should necessarily be wrapping the
		//content as we not really going to have a parent for it.
		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		//Pre-measure the view so that height and width don't remain null.
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

		//Assign a size and position to the view and all of its descendants
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

		//Create the bitmap
		Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
				view.getMeasuredHeight(),
				Bitmap.Config.ARGB_8888);
		//Create a canvas with the specified bitmap to draw into
		Canvas c = new Canvas(bitmap);

		//Render this view (and all of its children) to the given Canvas
		view.draw(c);
		return bitmap;
	}

	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

	public static boolean checkPermissionREAD_EXTERNAL_STORAGE(FragmentActivity activity,final Context context) {
		int currentAPIVersion = Build.VERSION.SDK_INT;
		if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
			if (ContextCompat.checkSelfPermission(context,
					Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(
						(Activity) context,
						Manifest.permission.READ_EXTERNAL_STORAGE)) {

				} else {
					ActivityCompat
							.requestPermissions(
									(Activity) context,
									new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
									MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
				}
				return false;
			} else {
				return true;
			}

		} else {
			return true;
		}
	}
}
