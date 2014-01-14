package de.uniulm.bagception.calendar.service;


import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class CalendarService extends IntentService {

	private ResultReceiver resultReceiver;
	private CalendarReader reader;
	
	public CalendarService() {
		super("CalendarService");
		reader = new CalendarReader();

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		resultReceiver = intent.getParcelableExtra("receiverTag");
		log("calendar request received!");
		
		
		if(intent.hasExtra("requestType")){
			if(intent.getStringExtra("requestType").equals("calendarEvents")){
				// getting intent extras
				String[] calendarNames = intent.getStringArrayExtra("calendarNames");
				int[] calendarIDs = intent.getIntArrayExtra("calendarIDs");
				int numberOfEvents = intent.getIntExtra("numberOfEvents", -1);
				
				// getting and sending calendar events
				Bundle b = new Bundle();
				b.putStringArrayList("calendarEvents", reader.readCalendarEvent(this, calendarNames, calendarIDs, numberOfEvents));
				resultReceiver.send(0, b);
				log("sending calendar events...");
			}
			if(intent.getStringExtra("requestType").equals("calendarNames")){
				Bundle b = new Bundle();
				b.putStringArrayList("calendarNames", reader.getCalendarNames(this));
				resultReceiver.send(0, b);
				log("sending calendar names...");
			}
		}
		
	}

	private void log(String s){
		Log.d("CalendarService", s);
	}
}
