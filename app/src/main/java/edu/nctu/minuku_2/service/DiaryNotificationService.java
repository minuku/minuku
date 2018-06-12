package edu.nctu.minuku_2.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashMap;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.config.UserPreferences;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku_2.R;
import edu.nctu.minuku_2.preferences.ApplicationConstants;
import edu.nctu.minuku_2.preferences.TimePreference;
import edu.nctu.minukucore.event.ShowNotificationEvent;
import edu.nctu.minukucore.event.ShowNotificationEventBuilder;

/**
 * Created by shriti on 11/5/16.
 */

public class DiaryNotificationService extends Service {

    private static final String TAG = "DiaryNotificationService";

    //private boolean wasDiaryAdministered;

    public DiaryNotificationService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + Constants.DIARY_NOTIFICATION_SERVICE_REPEAT_MILLISECONDS,
                PendingIntent.getService(this, 0, new Intent(this, DiaryNotificationService.class), 0)
        );

        //trigger prompt for timeline
        Log.d(TAG, "checking time to trigger diary prompt");
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        Log.d(TAG, "current hour " + hour);

        String endTime = UserPreferences.getInstance().getPreference("endTime");
        if(endTime == null || endTime.isEmpty()) {
            endTime = "21:00";
        }
        int endTimeHour = TimePreference.getHour(endTime);
        Log.d(TAG, "end time hour: " + (endTimeHour));
        Log.d(TAG, "end time hour - 1: " + (endTimeHour - 1));

        //Map<String, ?> preferences = UserPreferences.getInstance().getSharedPreferences().getAll();
        /*if(preferences.entrySet().contains("STATUS_OF_DIARY"))
            wasDiaryAdministered = true;
        else
            wasDiaryAdministered = false;*/

        /*Log.d(TAG, "value of diary status before: " + wasDiaryAdministered);

        for (Map.Entry<String, ?> entry : preferences.entrySet()) {
            if(entry.getKey() == "STATUS_OF_DIARY") {
                wasDiaryAdministered = true;
                break;
            }
            else
                wasDiaryAdministered = false;
        }

        Log.d(TAG, "value of diary status after: " + wasDiaryAdministered);*/
        //TODO: This will not work if end time is beyond 12 am. Need to fix this. Maybe keep the logic as seconds passed
        if(hour == (endTimeHour-2)) {
            EventBus.getDefault().post(
                    new ShowNotificationEventBuilder()
                            .setExpirationAction(ShowNotificationEvent.ExpirationAction.DISMISS)
                            .setExpirationTimeSeconds(Constants.DIARY_NOTIFICATION_EXPIRATION_TIME)
                            //.setViewToShow(EODQuestionsActivity.class)
                            .setIconID(R.drawable.self_reflection)
                            .setTitle(Constants.EOD_DIARY_PROMPT_TITLE)
                            .setCategory(ApplicationConstants.NOTIFICATION_CATEGORY_EOD_DIARY)
                            .setParams(new HashMap<String, String>())
                            .setMessage(Constants.EOD_DIARY_PROMPT_MESSAGE)
                            .createShowNotificationEvent());
            Log.d(TAG, "notification posted");
            //UserPreferences.getInstance().writePreference("STATUS_OF_DIARY", "true");
        }

        //Log.d(TAG, "Writte preference value: " + UserPreferences.getInstance().getPreference("STATUS_OF_DIARY").toString());

        //what is the role of this code? No pending intent here? and how is notification builder different?
        Notification note  = new Notification.Builder(getBaseContext())
                .setContentTitle(Constants.APP_NAME)
                .setContentText(Constants.RUNNING_APP_DECLARATION)
                .setSmallIcon(R.drawable.self_reflection)
                .setAutoCancel(false)
                .build();
        note.flags |= Notification.FLAG_NO_CLEAR;
        startForeground( 42, note );

        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroying service. Your state might be lost!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
