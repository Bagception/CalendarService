package de.uniulm.bagception.calendar.service;


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
		Bundle b = new Bundle();
		b.putString("payload", "itworks");
		resultReceiver.send(0, b);
		log("sending answer...");
	}

	private void log(String s){
		Log.d("Service", s);
	}
}
