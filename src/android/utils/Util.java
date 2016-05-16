package com.hpe.android.plugin.backgroundservice.utils;

import java.io.File;
import java.io.FileWriter;
import android.os.Environment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.IOException;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import com.ionicframework.sismobile287465.R;
import com.ionicframework.sismobile287465.MainActivity;
import android.content.Intent;
import android.app.PendingIntent;
import org.json.JSONObject;



public class Util {

	public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
	public static final String LOG_DIR ="HybridSisMobile";
	public static final String LOG_FILE ="Log.txt";
	private static File file = null;


	public static void createLogFile(){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String dir = Environment.getExternalStorageDirectory() + File.separator + LOG_DIR;
			//create folder
			File folder = new File(dir);
			folder.mkdirs();

			//create file
			if (file == null){
				file = new File(dir, LOG_FILE);
			}
		}
	}
	public static void appendLog(String text)
	{
		if(file==null)
			return;
		try
		{
			BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
			buf.append(simpleDateFormat.format(new Date()) + ": " +text);
			buf.newLine();
			buf.flush();
			buf.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void addNotification(Context context, String title, String msg, JSONObject values)
	{
		// prepare intent which is triggered if the
		// notification is selected

		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("extrasData",	values.toString());

		// use System.currentTimeMillis() to have a unique ID for the pending intent
		PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification noti = new Notification.Builder(context)
				.setContentTitle(title)
				//.setContentText(msg)
				.setSmallIcon(R.drawable.icon)
				.setContentIntent(pIntent)
				.setStyle(new Notification.BigTextStyle().bigText(msg))
				.setAutoCancel(true).build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		notificationManager.notify(12345, noti);
		appendLog(" in addNotification .... ");
/*
		Notification n  = new Notification.Builder(this)
				.setContentTitle("New mail from " + "test@gmail.com")
				.setContentText("Subject")
				.setSmallIcon(R.drawable.icon)
				.setContentIntent(pIntent)
				.setAutoCancel(true)
				.addAction(R.drawable.icon, "Call", pIntent)
				.addAction(R.drawable.icon, "More", pIntent)
				.addAction(R.drawable.icon, "And more", pIntent).build();


		NotificationManager notificationManager =
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		notificationManager.notify(0, n);

*/

	}

}
