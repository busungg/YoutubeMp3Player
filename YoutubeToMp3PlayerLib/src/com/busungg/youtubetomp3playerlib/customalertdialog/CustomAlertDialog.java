package com.busungg.youtubetomp3playerlib.customalertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomAlertDialog {
	
	public static void show(Context context, String title, String message)
	{
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(context);
		alertDlg.setTitle(title);
	    alertDlg.setMessage(message);
	    alertDlg.setNegativeButton("»Æ¿Œ", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
	    });
	    
	    if ((context != null) && (!((Activity)context).isFinishing()))
	    {
	      alertDlg.show();
	    }
	}
}
