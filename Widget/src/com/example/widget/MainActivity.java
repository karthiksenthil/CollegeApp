package com.example.widget;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;


public class MainActivity extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Random r = new Random();
		int randomint = r.nextInt(100000);
		String rand = String.valueOf(randomint);
		
		final int n = appWidgetIds.length;
		for(int i=0;i<n;i++)
		{
			int awid = appWidgetIds[i];
			RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.activity_main);
			//rv.setTextViewText(R.id.tvwidgetupdate, rand);
			appWidgetManager.updateAppWidget(awid, rv);
		}
		}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "Bye-Bye!!", Toast.LENGTH_LONG).show();
		
	}

	

	
}
