package de.uniulm.bagception.calendar.service;


import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class CalendarService extends IntentService {

	private ResultReceiver resultReceiver;

	public CalendarService() {
		super("CalendarService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		resultReceiver = intent.getParcelableExtra("receiverTag");
		log("request received!");
		
		String[] calendarNames = null;
		int[] calendarIDs = null;
		
		if(intent.hasExtra("calendarNames")){
			calendarNames = intent.getStringArrayExtra("calendarNames");
		}
		if(intent.hasExtra("calendarIDs")){
			calendarIDs = intent.getIntArrayExtra("calendarIDs");
		}
	
		CalendarReader reader = new CalendarReader();
		ArrayList<String> calendarEvents = reader.readCalendarEvent(this, calendarNames, calendarIDs);
		
		Bundle b = new Bundle();
		
		b.putStringArrayList("calendarEvents", calendarEvents);
		resultReceiver.send(0, b);
		log("sending answer...");
	}

	private void log(String s){
		Log.d("Service", s);
	}
}
