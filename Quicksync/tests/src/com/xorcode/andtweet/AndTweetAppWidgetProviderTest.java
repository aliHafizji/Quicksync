/*
 * Copyright (C) 2010 yvolk (Yuri Volkov), http://yurivolkov.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xorcode.andtweet;

import android.app.AlarmManager;
import android.content.Intent;
import android.app.PendingIntent;
import android.text.format.Time;
import android.util.Log;
import com.xorcode.andtweet.appwidget.AndTweetAppWidgetProvider;

import static com.xorcode.andtweet.AndTweetService.*;

import android.content.*;
import android.text.format.DateUtils;
import android.test.ActivityTestCase;

import java.util.Calendar;

/**
 * Runs various tests...
 * @author yvolk (Yuri Volkov), http://yurivolkov.com
 */
public class AndTweetAppWidgetProviderTest extends ActivityTestCase {
    static final String TAG = AndTweetAppWidgetProviderTest.class.getSimpleName();

    public AndTweetAppWidgetProviderTest() {
    	initializeDateTests();
    }
    
    public void test001WidgetTime() throws Exception {
    	Log.i(TAG,"testWidgetTime started");
    	Context targetContext = getInstrumentation().getTargetContext();
    	
    	AndTweetAppWidgetProvider widget = new AndTweetAppWidgetProvider();
    	Log.i(TAG,"AndTweetAppWidgetProvider created");

    	/*
    	long startMillis = 1267968833922l;
    	long endMillis = 1267968834922l;
    	
    	widgetTime = "3/7/10";
        assertEquals("Widget time is not equal for " + widgetTime, widgetTime, widget.formatWidgetTime(targetContext, startMillis, endMillis));

        Time time = new Time();
    	time.set(1, 1, 10, 8, 3, 2010);
    	startMillis = time.toMillis(false);
    	time.setToNow();
    	endMillis = time.toMillis(false);
    	widgetTime = "4/8/10 - 4:01 PM";
        assertEquals("Widget time is not equal for " + widgetTime, widgetTime, widget.formatWidgetTime(targetContext, startMillis, endMillis));
        */
    	
        int len = dateTests.length;
        for (int index = 0; index < len; index++) {
            DateTest dateTest = dateTests[index];
            if (dateTest == null) { 
            	break; 
            }
            long startMillis = dateTest.date1.toMillis(false /* use isDst */);
            long endMillis = dateTest.date2.toMillis(false /* use isDst */);
            int flags = dateTest.flags;
            String output = DateUtils.formatDateRange(targetContext, startMillis, endMillis, flags);
            /*
            if (!dateTest.expectedOutput.equals(output)) {
                Log.i("FormatDateRangeTest", "index " + index
                        + " expected: " + dateTest.expectedOutput
                        + " actual: " + output);
            } */
            
            String output2 = widget.formatWidgetTime(targetContext, startMillis, endMillis);
        	Log.i(TAG,"\"" + output + "\"; \"" + output2 + "\"");
            
            //assertEquals(dateTest.expectedOutput, output);
        }         
    }   

    DateTest[] dateTests = new DateTest[101];
    
    static private class DateTest {
        public Time date1;
        public Time date2;
        public int flags;
        // Is not used yet...
        public String expectedOutput;
        
        public DateTest(long startMillis, long endMillis) {
        	date1 = new Time();
        	date1.set(startMillis);
        	date2 = new Time();
        	date2.set(endMillis);
        	expectedOutput = "";
        	flags = DateUtils.FORMAT_24HOUR | DateUtils.FORMAT_SHOW_DATE 
        	| DateUtils.FORMAT_SHOW_TIME;
        }
    }
    
    private void initializeDateTests() {
    	// Initialize dateTests
    	int ind = 0;
    	Calendar cal1 = Calendar.getInstance();
    	Calendar cal2 = Calendar.getInstance();

    	cal1.setTimeInMillis(System.currentTimeMillis());
    	cal2.setTimeInMillis(System.currentTimeMillis());
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());
    	
    	ind += 1;
    	cal1.roll(Calendar.SECOND, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal1.roll(Calendar.SECOND, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());
    	
    	ind += 1;
    	cal1.roll(Calendar.MINUTE, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal1.add(Calendar.SECOND, 5);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal1.roll(Calendar.MINUTE, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal1.roll(Calendar.HOUR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal1.roll(Calendar.HOUR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());
    	
    	ind += 1;
    	cal1.roll(Calendar.DAY_OF_YEAR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());
    	
    	ind += 1;
    	cal1.roll(Calendar.DAY_OF_YEAR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal2.roll(Calendar.MINUTE, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal2.roll(Calendar.HOUR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal2.roll(Calendar.HOUR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());

    	ind += 1;
    	cal1.roll(Calendar.YEAR, false);
    	dateTests[ind] = new DateTest(cal1.getTimeInMillis(), cal2.getTimeInMillis());
    }

    public void test100Receiver() throws Exception {
    	Log.i(TAG,"testReceiver started");

    	int numTweets;
    	int msgType;
    	
    	numTweets = 1;
    	msgType = NOTIFY_REPLIES;
    	updateWidgets(numTweets, msgType);
    	
    	numTweets = 1;
    	msgType = NOTIFY_DIRECT_MESSAGE;
    	updateWidgets(numTweets, msgType);
    	
    	numTweets = 1;
    	msgType = NOTIFY_TIMELINE;
    	updateWidgets(numTweets, msgType);
    	
    	// 15 seconds to complete updates
    	// Shorter period sometimes doesn't work (processes are being closed...)
    	Thread.sleep(25000);
    }
    
	/** 
	 * Send Update intent to AndTweet Widget(s),
	 * if there are some installed... (e.g. on the Home screen...) 
	 * @see AndTweetAppWidgetProvider
	 */
	private void updateWidgets(int numTweets, int msgType){
		try {
		updateWidgetsNow(numTweets, msgType);
		//updateWidgetsThreads(numTweets, msgType);
		//updateWidgetsPending(numTweets, msgType);
		} catch (Exception e) {
			
		}
	}

    
	/** 
	 * Send Update intent to AndTweet Widget(s),
	 * if there are some installed... (e.g. on the Home screen...) 
	 * @see AndTweetAppWidgetProvider
	 */
	private void updateWidgetsNow(int numTweets, int msgType){
    	Context context = getInstrumentation().getTargetContext();
    	//Context context = getInstrumentation().getContext();

    	Log.i(TAG,"Sending update; numTweets=" + numTweets + "; msgType=" + msgType);

    	Intent intent = new Intent(ACTION_APPWIDGET_UPDATE);
		intent.putExtra(EXTRA_NUMTWEETS, numTweets);
		intent.putExtra(EXTRA_MSGTYPE, msgType);
		context.sendBroadcast(intent);
    	
	}
	
    
	/** 
	 * Send Update intent to AndTweet Widget(s),
	 * if there are some installed... (e.g. on the Home screen...) 
	 * @see AndTweetAppWidgetProvider
	 * For some reason it sends Intents with the same "Extra" info
	 */
	private void updateWidgetsPending(int numTweets, int msgType) throws Exception {
		
		// Let's try pending intents
    	Context context = getInstrumentation().getTargetContext();
    	//Context context = getInstrumentation().getContext();
    	long triggerTime;

    	Log.i(TAG,"Sending update; numTweets=" + numTweets + "; msgType=" + msgType);

    	triggerTime = System.currentTimeMillis() + 3000;
    	Intent intent = new Intent(ACTION_APPWIDGET_UPDATE);
    	intent.addCategory("msgType" + msgType);
		intent.putExtra(EXTRA_NUMTWEETS, numTweets);
		intent.putExtra(EXTRA_MSGTYPE, msgType);
    	
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context,
        		0 /* no requestCode */,
                intent, 
                0 /* no flags */);
        
        am.cancel(pendingUpdate);
        am.set(AlarmManager.RTC, triggerTime, pendingUpdate);

        Thread.sleep(5000);
		
	}

    
	/** 
	 * Send Update intent to AndTweet Widget(s),
	 * if there are some installed... (e.g. on the Home screen...) 
	 * @see AndTweetAppWidgetProvider
	 */
	private void updateWidgetsThreads(int numTweets, int msgType) {
		IntentSender runner = new IntentSender(numTweets, msgType);
		runner.start();
	}
	
	class IntentSender extends Thread {
		int numTweets;
		int msgType;
		public IntentSender(int numTweets, int msgType) {
			this.numTweets = numTweets;
			this.msgType = msgType;
		}
		
		public void run() {
	    	Context context = getInstrumentation().getContext();

	    	Log.i(TAG,"Sending update; numTweets=" + numTweets + "; msgType=" + msgType);
	    	
	    	Intent intent = new Intent(ACTION_APPWIDGET_UPDATE);
			intent.putExtra(EXTRA_NUMTWEETS, numTweets);
			intent.putExtra(EXTRA_MSGTYPE, msgType);
			context.sendBroadcast(intent);
		}
	}
   
}