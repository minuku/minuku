package edu.nctu.minuku.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import edu.nctu.minuku.manager.MinukuStreamManager;
import edu.nctu.minuku.model.DataRecord.ActivityRecognitionDataRecord;
import edu.nctu.minuku.streamgenerator.ActivityRecognitionStreamGenerator;
import edu.nctu.minukucore.exception.StreamNotFoundException;

/**
 * Created by Lawrence on 2017/5/22.
 */

public class ActivityRecognitionService extends IntentService {

    private final String TAG = "ActivityRecognitionService";

    private String Latest_mMostProbableActivitytype;
    private DetectedActivity mMostProbableActivity;
    private List<DetectedActivity> mProbableActivities;

    public static ActivityRecognitionStreamGenerator mActivityRecognitionStreamGenerator;

    //private static String detectedtime;
    private long detectedtime;

    public ActivityRecognitionService() {
        super("ActivityRecognitionService");
        Log.e(TAG,"ActivityRecognitionService is constructed!");
        //mActivityRecognitionManager = ContextManager.getActivityRecognitionManager();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /**  move to TriggerManager  **/
//TODO triggerManager situationManager, triggerManager: replace ModeWork.work. , situationManager: replace ModeWork.condition. 放transportationManager(In Minuku).
        if(ActivityRecognitionResult.hasResult(intent)) {
            try {
                mActivityRecognitionStreamGenerator = (ActivityRecognitionStreamGenerator) MinukuStreamManager.getInstance().getStreamGeneratorFor(ActivityRecognitionDataRecord.class);
            }catch (StreamNotFoundException e){

            }
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            mProbableActivities = result.getProbableActivities();
            mMostProbableActivity = result.getMostProbableActivity();
            detectedtime = new Date().getTime(); //TODO might be wrong, be aware for it!!

            Log.d(TAG, "[test ActivityRecognition]" +   mMostProbableActivity.toString());
            try {
                if (mProbableActivities != null && mMostProbableActivity != null)
                    mActivityRecognitionStreamGenerator.setActivitiesandDetectedtime(mProbableActivities, mMostProbableActivity, detectedtime);
            }catch(Exception e){

            }

        }
    }

    public long getCurrentTimeInMillis(){
        //get timzone
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(tz);
        long t = cal.getTimeInMillis();
        return t;
    }


}
