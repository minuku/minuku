package edu.nctu.minuku.streamgenerator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;

import org.greenrobot.eventbus.EventBus;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.event.DecrementLoadingProcessCountEvent;
import edu.nctu.minuku.event.IncrementLoadingProcessCountEvent;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.model.DataRecord.BatteryDataRecord;
import edu.nctu.minuku.stream.BatteryStream;
import edu.nctu.minukucore.exception.StreamAlreadyExistsException;
import edu.nctu.minukucore.exception.StreamNotFoundException;
import edu.nctu.minukucore.stream.Stream;

import static edu.nctu.minuku.manager.MinukuStreamManager.getInstance;

/**
 * Created by Lucy on 8/2/17.
 */

public class BatteryStreamGenerator extends AndroidStreamGenerator<BatteryDataRecord>{

    private String TAG = "BatteryStreamGenerator";
    private Stream mStream;

    public BatteryStreamGenerator(Context applicationContext) {
        super(applicationContext);

        this.mStream = new BatteryStream(Constants.DEFAULT_QUEUE_SIZE);
        this.register();
    }

    @Override
    public void register() {
        Log.d(TAG, "Registring with StreamManage");

        try {
            getInstance().register(mStream, BatteryDataRecord.class, this);
        } catch (StreamNotFoundException streamNotFoundException) {
            Log.e(TAG, "One of the streams on which" +
                    "BatteryDataRecord/BatteryStream depends in not found.");
        } catch (StreamAlreadyExistsException streamAlreadyExsistsException) {
            Log.e(TAG, "Another stream which provides" +
                    " BatteryDataRecord/BatteryStream is already registered.");
        }
    }
    @Override
    public Stream<BatteryDataRecord> generateNewStream() {
        return mStream;
    }

    @Override
    public boolean updateStream() {


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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mApplicationContext.registerReceiver(mBroadcastReceiver, filter);

        Log.d(TAG, "Stream " + TAG + " registered successfully");

        EventBus.getDefault().post(new IncrementLoadingProcessCountEvent());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Log.d(TAG, "Stream " + TAG + "initialized from previous state");


//                    Future<List<ImageDataRecord>> listFuture =
//                           // mDAO.getLast(Constants.DEFAULT_QUEUE_SIZE);
//                    while(!listFuture.isDone()) {
//                        Thread.sleep(1000);
//                    }
//                    Log.d(TAG, "Received data from Future for " + TAG);
//                    Future<List<ImageDataRecord>> listFuture;
//                    mStream.addAll(new LinkedList<>(listFuture.get()));
//                } catch (DAOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//
//
                } finally {
                    EventBus.getDefault().post(new DecrementLoadingProcessCountEvent());
                }
            }
        });
    }

    @Override
    public void offer(BatteryDataRecord abatteryDataRecord) {
        mStream.add(abatteryDataRecord);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int status = intent.getIntExtra("status", 0);
                //int health = intent.getIntExtra("health", 0);
                //boolean present = intent.getBooleanExtra("present",false);
                //int BatteryLevel = intent.getIntExtra("BatteryLevel", 0);
                int BatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float BatteryPercentage = BatteryLevel/(float)scale;
//                int icon_small = intent.getIntExtra("icon-small", 0);
//                int plugged = intent.getIntExtra("plugged", 0);
//                int voltage = intent.getIntExtra("voltage", 0);
                int temperature = intent.getIntExtra("temperature",0);
                //String technology = intent.getStringExtra("technology");
                String statusString = "";
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        statusString = "unknown";
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        statusString = "charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        statusString = "discharging";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        statusString = "not charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        statusString = "full";
                        break;
                }
//                String healthString = "";
//                switch (health) {
//                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
//                        healthString = "unknown";
//                        break;
//                    case BatteryManager.BATTERY_HEALTH_GOOD:
//                        healthString = "good";
//                        break;
//                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
//                        healthString = "overheat";
//                        break;
//                    case BatteryManager.BATTERY_HEALTH_DEAD:
//                        healthString = "dead";
//                        break;
//                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
//                        healthString = "voltage";
//                        break;
//                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
//                        healthString = "unspecified failure";
//                        break;
//                }
//                String acString = "";
//                switch (plugged) {
//                    case BatteryManager.BATTERY_PLUGGED_AC:
//                        acString = "plugged ac";
//                        break;
//                    case BatteryManager.BATTERY_PLUGGED_USB:
//
//                        acString = "plugged usb";
//                        break;
//                }
                Log.d("Batterystatus", statusString);
                //Log.d("Batteryhealth", healthString);
                //Log.d("Batterypresent", String.valueOf(present));
                Log.d("BatteryLevel", String.valueOf(BatteryLevel));
                Log.d("BatteryScale", String.valueOf(scale));
                Log.d("BatteryPercentage", String.valueOf(BatteryPercentage));
                //Log.d("Batteryicon_small", String.valueOf(icon_small));

                //Log.d("Batteryplugged", acString);
                //Log.d("Batteryvoltage", String.valueOf(voltage));
                Log.d("Batterytemperature", String.valueOf(temperature));
                //Log.d("Batterytechnology", technology);
            }
        }
    };


}
