package com.operr.yelpchallenge.common.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.operr.yelpchallenge.R;
import com.operr.yelpchallenge.common.view.BaseApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

public class Utilities {
	private static Utilities _instance = null;

//	ProgressDialogView progressDialog;
	SharedPreferenceData shared = null;

	private AlertDialog alertDialog;

	public static Utilities instance() {
		if (_instance == null) _instance = new Utilities();
		return _instance;
	}

	public static String errorToString(Throwable t) {
		StringBuffer errorMessage = new StringBuffer();

		if (errorMessage.length() == 0 && t.getCause() != null && t.getCause().getLocalizedMessage() != null)
			errorMessage.append(t.getCause().getLocalizedMessage());

		if (errorMessage.length() == 0 && t.getCause() != null && t.getCause().getMessage() != null)
			errorMessage.append(t.getCause().getMessage());

		if (errorMessage.length() == 0 && t.getLocalizedMessage() != null)
			errorMessage.append(t.getLocalizedMessage());

		if (errorMessage.length() == 0 && t.getMessage() != null)
			errorMessage.append(t.getMessage());

		if (errorMessage.length() == 0)
			errorMessage.append(t.getClass().getSimpleName());

		return errorMessage.toString();
	}

	public static String md5Hash(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		// http://stackoverflow.com/questions/332079
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/*
	 * Constructor
	 */
	public Utilities() {
		shared = new SharedPreferenceData(BaseApplication.getContext());

	}



	/*
	 * Get Screen height
	 */
	public static int getScreenHeight(Activity activity) {
		int measuredHeight = 0;
		Point size = new Point();
		WindowManager w = activity.getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);
			measuredHeight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			measuredHeight = d.getHeight();
		}
		return measuredHeight;
	}

	/*
	 * Get Screen Width
	 */
	public static int getScreenWidth(Activity activity) {
		int measuredWidth = 0;
		Point size = new Point();
		WindowManager w = activity.getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);
			measuredWidth = size.x;
		} else {
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
		}
		return measuredWidth;
	}


	public static String getLanguage() {
		String language = Locale.getDefault().getLanguage();
		return language;
	}

	public static void exit() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}


	/*
	 * Check if String is Null/empty or not
	 */
	public static boolean isNullString(String str) {
		if (str != null && str.compareToIgnoreCase("null") != 0 && str.trim().length() > 0)
			return false;

		return true;
	}
	/*
	 * Check if List is Null/empty or not
	 */
	public static boolean isNullList(List<?> list) {
		if (list != null && list.size() > 0)
			return false;

		return true;
	}

	/*
	 * Display toast msg
	 */
	public static void showToastMsg(String msg, int duration) {
		Toast toastMsg = Toast.makeText(getContext(), msg, duration);
		toastMsg.setGravity(Gravity.CENTER, 0, 0);
		toastMsg.show();
	}

	public static void showToastMsg(Activity activity, final String msg, final int duration) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showToastMsg(msg, duration);
			}
		});
	}

	public static void showErrorMsg(Activity activity, final String msg, final int duration) {
		showToastMsg(activity, msg, duration);
	}

	public static boolean isValidPhoneNumber(CharSequence phoneNumber){
        String regex = "^[+]?[0-9]{10,13}$";
        return phoneNumber.toString().matches(regex);
    }



    public static boolean isValidPassword(CharSequence target){
        if(target == null) {
            return false;
        } else {
            if(target.length() < 5) return false;
            return true;
        }
    }

    public static boolean isPassMatchesConfirmPass(CharSequence t1, CharSequence t2){
        if(t1.toString().contentEquals(t2)) return true;
        return false;
    }

    public static void checkGPSAndNetwork(final Context context){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings)
                    , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                }
            });
            dialog.show();
        }
    }


	/*
	 *errorDialog
	 */
	public void showErrorDialog(final Activity activity, String title, String msg) {
		if (alertDialog != null)
			alertDialog.dismiss();

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		if (title != null)
			builder.setTitle(title);

		builder.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						alertDialog.dismiss();
					}
				});
		alertDialog = builder.create();
		alertDialog.show();

	}

	public void dismissAlertDialog() {
		if (alertDialog != null) alertDialog.dismiss();
	}




	/*
	 * Setters & Getters
	 */

	public  SharedPreferenceData getShared() {
		return shared;
	}

	public void setShared(SharedPreferenceData shared) {
		this.shared = shared;
	}

	public static Context getContext() {
		return BaseApplication.getContext();
	}





	/**
	 * Simple network connection check.
	 *
	 * @param context
	 */
	public static boolean isConnected(Context context) {
		final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
			return false;
		}

		return true;
	}





	/* * email address validation */
	public final static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

	public static boolean isGooglePlayServicesInstalled(Context context) {
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo("com.google.android.gms", 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi != null;
	}



	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && (activeNetworkInfo.isConnectedOrConnecting() ); // activeNetworkInfo.isConnected() ||
	}


	/**
	 * Check if the app has access to fine location permission. On pre-M
	 * devices this will always return true.
	 */
	public static boolean checkFineLocationPermission(Context context) {
		return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
				context, Manifest.permission.ACCESS_FINE_LOCATION);
	}

}