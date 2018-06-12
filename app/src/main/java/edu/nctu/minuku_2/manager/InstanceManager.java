/*
 * Copyright (c) 2016.
 *
 * DReflect and Minuku Libraries by Shriti Raj (shritir@umich.edu) and Neeraj Kumar(neerajk@uci.edu) is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/Shriti-UCI/Minuku-2.
 *
 *
 * You are free to (only if you meet the terms mentioned below) :
 *
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 *
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * Under the following terms:
 *
 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * NonCommercial — You may not use the material for commercial purposes.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 */

package edu.nctu.minuku_2.manager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.nctu.minuku.DBHelper.DBHelper;
import edu.nctu.minuku.dao.AccessibilityDataRecordDAO;
import edu.nctu.minuku.dao.ActivityRecognitionDataRecordDAO;
import edu.nctu.minuku.dao.AppUsageDataRecordDAO;
import edu.nctu.minuku.dao.BatteryDataRecordDAO;
import edu.nctu.minuku.dao.ConnectivityDataRecordDAO;
import edu.nctu.minuku.dao.LocationDataRecordDAO;
import edu.nctu.minuku.dao.RingerDataRecordDAO;
import edu.nctu.minuku.dao.SensorDataRecordDAO;
import edu.nctu.minuku.dao.TelephonyDataRecordDAO;
import edu.nctu.minuku.dao.TransportationModeDAO;
import edu.nctu.minuku.dao.UserSubmissionStatsDAO;
import edu.nctu.minuku.event.DecrementLoadingProcessCountEvent;
import edu.nctu.minuku.event.IncrementLoadingProcessCountEvent;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.manager.MinukuDAOManager;
import edu.nctu.minuku.manager.MinukuSituationManager;
import edu.nctu.minuku.model.DataRecord.AccessibilityDataRecord;
import edu.nctu.minuku.model.DataRecord.ActivityRecognitionDataRecord;
import edu.nctu.minuku.model.DataRecord.AppUsageDataRecord;
import edu.nctu.minuku.model.DataRecord.BatteryDataRecord;
import edu.nctu.minuku.model.DataRecord.ConnectivityDataRecord;
import edu.nctu.minuku.model.DataRecord.LocationDataRecord;
import edu.nctu.minuku.model.DataRecord.RingerDataRecord;
import edu.nctu.minuku.model.DataRecord.SensorDataRecord;
import edu.nctu.minuku.model.DataRecord.TelephonyDataRecord;
import edu.nctu.minuku.model.DataRecord.TransportationModeDataRecord;
import edu.nctu.minuku.model.UserSubmissionStats;
import edu.nctu.minuku.streamgenerator.AccessibilityStreamGenerator;
import edu.nctu.minuku.streamgenerator.ActivityRecognitionStreamGenerator;
import edu.nctu.minuku.streamgenerator.AppUsageStreamGenerator;
import edu.nctu.minuku.streamgenerator.BatteryStreamGenerator;
import edu.nctu.minuku.streamgenerator.ConnectivityStreamGenerator;
import edu.nctu.minuku.streamgenerator.LocationStreamGenerator;
import edu.nctu.minuku.streamgenerator.RingerStreamGenerator;
import edu.nctu.minuku.streamgenerator.SensorStreamGenerator;
import edu.nctu.minuku.streamgenerator.TelephonyStreamGenerator;
import edu.nctu.minuku.streamgenerator.TransportationModeStreamGenerator;
import edu.nctu.minuku_2.dao.CheckAndRemindDataRecordDAO;
import edu.nctu.minuku_2.model.CheckAndRemindDataRecord;
import edu.nctu.minuku_2.question.QuestionConfig;
import edu.nctu.minuku_2.streamgenerator.CheckAndRemindStreamGenerator;

/**
 * Created by neerajkumar on 8/28/16.
 */
public class InstanceManager {
    private static InstanceManager instance = null;
    private Context mApplicationContext = null;
    private static Context mContext = null;
    private static Intent mintent;
    private UserSubmissionStats mUserSubmissionStats = null;
    private String TAG = "InstanceManager";

    private InstanceManager(Context applicationContext) {
        this.mApplicationContext = applicationContext;
        initialize();
    }

    public static InstanceManager getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new InstanceManager(applicationContext);
        }
        return instance;
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    private Context getApplicationContext() {
        return mApplicationContext;
    }

    public static void setContexttoActivityRecognitionservice(Context context) {
        mContext = context;
    }

    private void initialize() {
        // Add all initialization code here.
        // DAO initialization stuff

        DBHelper dBHelper = new DBHelper(getApplicationContext());

        MinukuDAOManager daoManager = MinukuDAOManager.getInstance();


        //TODO build new DAO here.
        CheckAndRemindDataRecordDAO checkAndRemindDataRecordDAO = new CheckAndRemindDataRecordDAO();
        daoManager.registerDaoFor(CheckAndRemindDataRecord.class, checkAndRemindDataRecordDAO);

        LocationDataRecordDAO locationDataRecordDAO = new LocationDataRecordDAO(getApplicationContext());
        daoManager.registerDaoFor(LocationDataRecord.class, locationDataRecordDAO);

        ActivityRecognitionDataRecordDAO activityRecognitionDataRecordDAO = new ActivityRecognitionDataRecordDAO(getApplicationContext());
        daoManager.registerDaoFor(ActivityRecognitionDataRecord.class, activityRecognitionDataRecordDAO);

        TransportationModeDAO transportationModeDAO = new TransportationModeDAO();
        daoManager.registerDaoFor(TransportationModeDataRecord.class, transportationModeDAO);

        //Background
        BatteryDataRecordDAO batteryDataRecordDAO = new BatteryDataRecordDAO();
        daoManager.registerDaoFor(BatteryDataRecord.class, batteryDataRecordDAO);

        RingerDataRecordDAO ringerDataRecordDAO = new RingerDataRecordDAO();
        daoManager.registerDaoFor(RingerDataRecord.class, ringerDataRecordDAO);

        SensorDataRecordDAO sensorDataRecordDAO = new SensorDataRecordDAO();
        daoManager.registerDaoFor(SensorDataRecord.class, sensorDataRecordDAO);

        TelephonyDataRecordDAO telephonyDataRecordDAO = new TelephonyDataRecordDAO();
        daoManager.registerDaoFor(TelephonyDataRecord.class, telephonyDataRecordDAO);

        ConnectivityDataRecordDAO connectivityDataRecordDAO = new ConnectivityDataRecordDAO();
        daoManager.registerDaoFor(ConnectivityDataRecord.class, connectivityDataRecordDAO);

        AppUsageDataRecordDAO appUsageDataRecordDAO = new AppUsageDataRecordDAO();
        daoManager.registerDaoFor(AppUsageDataRecord.class, appUsageDataRecordDAO);

        AccessibilityDataRecordDAO accessibilityDataRecordDAO = new AccessibilityDataRecordDAO();
        daoManager.registerDaoFor(AccessibilityDataRecord.class, accessibilityDataRecordDAO);



        // Create corresponding stream generators. Only to be created once in Main Activity
        //creating a new stream registers it with the stream manager

        //TODO build new StreamGenerator here.
        CheckAndRemindStreamGenerator checkAndRemindStreamGenerator =
                new CheckAndRemindStreamGenerator(getApplicationContext());

        LocationStreamGenerator locationStreamGenerator =
                new LocationStreamGenerator(getApplicationContext());

        ActivityRecognitionStreamGenerator activityRecognitionStreamGenerator =
                new ActivityRecognitionStreamGenerator(getApplicationContext());

        TransportationModeStreamGenerator transportationModeStreamGenerator =
                new TransportationModeStreamGenerator(getApplicationContext());

        //Background
        BatteryStreamGenerator batteryStreamGenerator =
                new BatteryStreamGenerator(getApplicationContext());

        RingerStreamGenerator ringerStreamGenerator =
                new RingerStreamGenerator(getApplicationContext());

        SensorStreamGenerator sensorStreamGenerator =
                new SensorStreamGenerator(getApplicationContext());

        TelephonyStreamGenerator telephonyStreamGenerator =
                new TelephonyStreamGenerator(getApplicationContext());

        ConnectivityStreamGenerator connectivityStreamGenerator =
                new ConnectivityStreamGenerator(getApplicationContext());

        AppUsageStreamGenerator appUsageStreamGenerator =
                new AppUsageStreamGenerator(getApplicationContext());

        AccessibilityStreamGenerator accessibilityStreamGenerator =
                new AccessibilityStreamGenerator(getApplicationContext());


        //TODO All situations must be registered AFTER the stream generators are registers.
        MinukuSituationManager situationManager = MinukuSituationManager.getInstance();

        /*MoodDataExpectedSituation moodDataExpectedSituation = new MoodDataExpectedSituation();
        MoodDataExpectedAction moodDataExpectedAction = new MoodDataExpectedAction();

        MissedReportsSituation missedReportsSituation = new MissedReportsSituation(getApplicationContext());
        MissedReportsAction missedReportsAction = new MissedReportsAction();*/

        //TODO additional function
        //for testing to trigger qualtrics
        //QuestionnaireManager questionnaireManager = new QuestionnaireManager(getApplicationContext());

        //create questionnaires
        QuestionConfig.getInstance().setUpQuestions(getApplicationContext());

        // Fetch tags
//        Model tagsModel = Model.getInstance();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Future<UserSubmissionStats> submissionStatsFuture = ((UserSubmissionStatsDAO)
                        MinukuDAOManager.getInstance().getDaoFor(UserSubmissionStats.class)).get();
                EventBus.getDefault().post(new IncrementLoadingProcessCountEvent());
                while (!submissionStatsFuture.isDone()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //
                try {
                    Log.d(TAG, "initialize: getting mUserSubmissionStats from future ");
                     mUserSubmissionStats = submissionStatsFuture.get();
                    //date check - ensuring that every day we have a new instance of submission
                    // stats. Needs to be tested

                    if(!areDatesEqual((new Date().getTime()), mUserSubmissionStats.getCreationTime())
                            || mUserSubmissionStats==null) {
                        if(mUserSubmissionStats == null)
                            Log.d(TAG, "initialize: userSubmissionStats is null");
                        Log.d(TAG, "initialize: userSubmissionStats is either null or we have a new date." +
                                "Creating new userSubmissionStats object");
                        mUserSubmissionStats = new UserSubmissionStats();

                    }
                    EventBus.getDefault().post(mUserSubmissionStats);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG, "initialize: Creating mUserSubmissionStats");
                    //gotUserStatsFromDatabase(null);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Log.d(TAG, "initialize: Creating mUserSubmissionStats");
                    //gotUserStatsFromDatabase(null);
                } finally {
                    EventBus.getDefault().post(new DecrementLoadingProcessCountEvent());
                }
            }
        });

    }

    public UserSubmissionStats getUserSubmissionStats() {
            if((mUserSubmissionStats == null) || !areDatesEqual((new Date().getTime()), mUserSubmissionStats.getCreationTime())) {
                if(mUserSubmissionStats == null)
                    Log.d(TAG, "getUserSubmissionStats: userSubmissionStats is null");

                Log.d(TAG, "getUserSubmissionStats: userSubmissionStats is either null or we have a new date." +
                                "Creating new userSubmissionStats object");
            mUserSubmissionStats = new UserSubmissionStats();
        }
        return mUserSubmissionStats;
    }

    public synchronized void setUserSubmissionStats(UserSubmissionStats aUserSubmissionStats) {
        try {
            MinukuDAOManager.getInstance().getDaoFor(UserSubmissionStats.class).update(null,
                    aUserSubmissionStats);
        } catch (Exception e) {
            Log.e(TAG, "Could not upload user stats via DAO.");
        }

        mUserSubmissionStats = aUserSubmissionStats;
        EventBus.getDefault().post(mUserSubmissionStats);
    }

    protected boolean areDatesEqual(long currentTime, long previousTime) {
        Log.d(TAG, "Checking if the both dates are the same");

        Calendar currentDate = Calendar.getInstance();
        Calendar previousDate = Calendar.getInstance();

        currentDate.setTimeInMillis(currentTime);
        previousDate.setTimeInMillis(previousTime);
        Log.d(TAG, "Current Year:" + currentDate.get(Calendar.YEAR) + " Previous Year:" + previousDate.get(Calendar.YEAR));
        Log.d(TAG, "Current Day:" + currentDate.get(Calendar.DAY_OF_YEAR) + " Previous Day:" + previousDate.get(Calendar.DAY_OF_YEAR));
        Log.d(TAG, "Current Month:" + currentDate.get(Calendar.MONTH) + " Previous Month:" + previousDate.get(Calendar.MONTH));

        boolean sameDay = (currentDate.get(Calendar.YEAR) == previousDate.get(Calendar.YEAR)) &&
                (currentDate.get(Calendar.DAY_OF_YEAR) == previousDate.get(Calendar.DAY_OF_YEAR)) &&
                (currentDate.get(Calendar.MONTH) == previousDate.get(Calendar.MONTH));

        if(sameDay)
            Log.d(TAG, "it is the same day, should not create a new object");
        else
            Log.d(TAG, "it is not the same day - a new day, should create a new object");
        return sameDay;
    }
}
