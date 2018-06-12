package edu.nctu.minuku_2.streamgenerator;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.manager.MinukuStreamManager;
import edu.nctu.minuku.manager.ScheduleAndSampleManager;
import edu.nctu.minuku.streamgenerator.AndroidStreamGenerator;
import edu.nctu.minuku.streamgenerator.TransportationModeStreamGenerator;
import edu.nctu.minuku_2.R;
import edu.nctu.minuku_2.dao.CheckAndRemindDataRecordDAO;
import edu.nctu.minuku_2.model.CheckAndRemindDataRecord;
import edu.nctu.minuku_2.stream.CheckAndRemindStream;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.exception.StreamAlreadyExistsException;
import edu.nctu.minukucore.exception.StreamNotFoundException;
import edu.nctu.minukucore.stream.Stream;

/**
 * Created by Lawrence on 2017/9/19.
 */

public class CheckAndRemindStreamGenerator extends AndroidStreamGenerator<CheckAndRemindDataRecord> {

    private final String TAG = "CheckAndRemindStreamGenerator";

    private static final String PACKAGE_DIRECTORY_PATH="/Android/data/edu.nctu.minuku_2/";

    private CheckAndRemindStream mStream;
    private CheckAndRemindDataRecordDAO mDAO;
    private Context mContext;

    private ScheduledExecutorService mScheduledExecutorService;

    private int car_notifyID = 3;

    private CSVWriter csv_writer = null;

    private final String logfile = "CheckAndRemindDataRecord";

    public final int REFRESH_FREQUENCY = 10; //1s, 1000ms
    public final int BACKGROUND_RECORDING_INITIAL_DELAY = 0;

    private String lastConfirmedActivityType = "NA";

    public CheckAndRemindStreamGenerator(Context applicationContext){
        super(applicationContext);
        this.mContext = applicationContext;
        this.mStream = new CheckAndRemindStream(Constants.LOCATION_QUEUE_SIZE);
        this.mDAO = new CheckAndRemindDataRecordDAO();

        mScheduledExecutorService = Executors.newScheduledThreadPool(REFRESH_FREQUENCY);

        createCSV();

        this.register();
    }

    @Override
    public void register() {
        Log.d(TAG, "Registering with StreamManager.");
        try {
            MinukuStreamManager.getInstance().register(mStream, CheckAndRemindDataRecord.class, this);
        } catch (StreamNotFoundException streamNotFoundException) {
            Log.e(TAG, "One of the streams on which CheckAndRemindDataRecord depends in not found.");
        } catch (StreamAlreadyExistsException streamAlreadyExistsException) {
            Log.e(TAG, "Another stream which provides CheckAndRemindDataRecord is already registered.");
        }
    }

    @Override
    public Stream<CheckAndRemindDataRecord> generateNewStream() {
        return mStream;
    }

    @Override
    public boolean updateStream() {
        Log.e(TAG, "Update stream called.");

       CheckAndRemindDataRecord checkAndRemindDataRecord = 
               new CheckAndRemindDataRecord();

        if(checkAndRemindDataRecord!=null) {

            mStream.add(checkAndRemindDataRecord);

            Log.e(TAG, "CAR to be sent to event bus" + checkAndRemindDataRecord);

            EventBus.getDefault().post(checkAndRemindDataRecord);
            try {
                mDAO.add(checkAndRemindDataRecord);
            } catch (DAOException e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }

    @Override
    public long getUpdateFrequency() {
        return 1;
    }

    @Override
    public void sendStateChangeEvent() {

    }

    @Override
    public void onStreamRegistration() {

        mScheduledExecutorService.scheduleAtFixedRate(
                CARRunnable,
                BACKGROUND_RECORDING_INITIAL_DELAY,
                REFRESH_FREQUENCY,
                TimeUnit.SECONDS);

    }

    Runnable CARRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                String currentConfirmedActivityType = TransportationModeStreamGenerator.toOtherClassDataRecord.getConfirmedActivityType();
                if(!lastConfirmedActivityType.equals(currentConfirmedActivityType))
                    caRemind();

                StoreToCSV(TransportationModeStreamGenerator.toOtherClassDataRecord.getCreationTime(),
                        lastConfirmedActivityType,
                        currentConfirmedActivityType);

                lastConfirmedActivityType = currentConfirmedActivityType;

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    private void caRemind(){
        String notiText = "CheckAndRemind";

        Log.d(TAG,"caRemind");

//        Intent resultIntent = new Intent(CheckFamiliarOrNotService.this, linkListohio.class);
//        PendingIntent pending = PendingIntent.getActivity(CheckFamiliarOrNotService.this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);//Context.
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        bigTextStyle.setBigContentTitle("Minuku2");
        bigTextStyle.bigText(notiText);

        Notification note = new Notification.Builder(mContext)
                .setContentTitle(Constants.APP_NAME)
                .setContentText(notiText)
//                .setContentIntent(pending)
                .setStyle(bigTextStyle)
                .setSmallIcon(R.drawable.self_reflection)
                .setAutoCancel(true)
                .build();
        //note.flags |= Notification.FLAG_NO_CLEAR;
        //startForeground( 42, note );

        // using the same tag and Id causes the new notification to replace an existing one
        mNotificationManager.notify(car_notifyID, note); //String.valueOf(System.currentTimeMillis()),
        note.flags = Notification.FLAG_AUTO_CANCEL;
    }

    @Override
    public void offer(CheckAndRemindDataRecord dataRecord) {

    }

    public void StoreToCSV(long timestamp, String lastConfirmedActivityType, String currentConfirmedActivityType){

        Log.d(TAG,"StoreToCSV");

        String sFileName = logfile+".csv";

        try{
            File root = new File(Environment.getExternalStorageDirectory() + PACKAGE_DIRECTORY_PATH);
            if (!root.exists()) {
                root.mkdirs();
            }

            Log.d(TAG, "root : " + root);

            csv_writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory()+PACKAGE_DIRECTORY_PATH+sFileName,true));

            List<String[]> data = new ArrayList<String[]>();

//            data.add(new String[]{"timestamp","timeString","Latitude","Longitude","Accuracy"});
            String timeString = ScheduleAndSampleManager.getTimeString(timestamp);

            data.add(new String[]{timeString,lastConfirmedActivityType,currentConfirmedActivityType});

            csv_writer.writeAll(data);

            csv_writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createCSV(){
        String sFileName = logfile+".csv";

        try{
            File root = new File(Environment.getExternalStorageDirectory() + PACKAGE_DIRECTORY_PATH);
            if (!root.exists()) {
                root.mkdirs();
            }

            csv_writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory()+PACKAGE_DIRECTORY_PATH+sFileName,true));

            List<String[]> data = new ArrayList<String[]>();

            data.add(new String[]{"timestamp","timeString","Latitude","Longitude","Accuracy"});

            csv_writer.writeAll(data);

            csv_writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
