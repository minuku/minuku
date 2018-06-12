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

package edu.nctu.minuku.streamgenerator;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.common.util.concurrent.AtomicDouble;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.dao.LocationDataRecordDAO;
import edu.nctu.minuku.event.DecrementLoadingProcessCountEvent;
import edu.nctu.minuku.event.IncrementLoadingProcessCountEvent;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.manager.MinukuDAOManager;
import edu.nctu.minuku.manager.MinukuStreamManager;
import edu.nctu.minuku.model.DataRecord.LocationDataRecord;
import edu.nctu.minuku.stream.LocationStream;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.exception.StreamAlreadyExistsException;
import edu.nctu.minukucore.exception.StreamNotFoundException;
import edu.nctu.minukucore.stream.Stream;

/**
 * Created by neerajkumar on 7/18/16.
 */
public class LocationStreamGenerator extends AndroidStreamGenerator<LocationDataRecord> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private LocationStream mStream;
    private String TAG = "LocationStreamGenerator";

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    /**Properties for Record**/
    public static final String RECORD_DATA_PROPERTY_LATITUDE = "Latitude";
    public static final String RECORD_DATA_PROPERTY_LONGITUDE = "Longitude";
    public static final String RECORD_DATA_PROPERTY_ACCURACY = "Accuracy";
    public static final String RECORD_DATA_PROPERTY_ALTITUDE = "Altitude";
    public static final String RECORD_DATA_PROPERTY_PROVIDER = "Provider";
    public static final String RECORD_DATA_PROPERTY_SPEED = "Speed";
    public static final String RECORD_DATA_PROPERTY_BEARING = "Bearing";
    public static final String RECORD_DATA_PROPERTY_EXTRAS = "Extras";

    private AtomicDouble latitude;
    private AtomicDouble longitude;

    private Location location;

    LocationDataRecordDAO mDAO;

    public static LocationDataRecord toCheckFamiliarOrNotLocationDataRecord;

    public LocationStreamGenerator(Context applicationContext) {
        super(applicationContext);
        this.mStream = new LocationStream(Constants.LOCATION_QUEUE_SIZE);
        this.mDAO = MinukuDAOManager.getInstance().getDaoFor(LocationDataRecord.class);
        this.latitude = new AtomicDouble();
        this.longitude = new AtomicDouble();

        this.latitude.set(-999.0);
        this.longitude.set(-999.0);

        this.register();
    }


    @Override
    public void onStreamRegistration() {
        // do nothing.
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(mApplicationContext)
                == ConnectionResult.SUCCESS) {
            mGoogleApiClient = new GoogleApiClient.Builder(mApplicationContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else {
            Log.e(TAG, "Error occurred while attempting to access Google play.");
        }

        Log.d(TAG, "Stream " + TAG + " registered successfully");

        EventBus.getDefault().post(new IncrementLoadingProcessCountEvent());

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Log.d(TAG, "Stream " + TAG + "initialized from previous state");
                    Future<List<LocationDataRecord>> listFuture =
                            mDAO.getLast(Constants.LOCATION_QUEUE_SIZE);
                    while(!listFuture.isDone()) {
                        Thread.sleep(1000);
                    }
                    Log.d(TAG, "Received data from Future for " + TAG);
                    mStream.addAll(new LinkedList<>(listFuture.get()));
                } catch (DAOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    EventBus.getDefault().post(new DecrementLoadingProcessCountEvent());
                }
            }
        });



    }

    @Override
    public void register() {
        Log.d(TAG, "Registering with StreamManager.");
        try {
            MinukuStreamManager.getInstance().register(mStream, LocationDataRecord.class, this);
        } catch (StreamNotFoundException streamNotFoundException) {
            Log.e(TAG, "One of the streams on which LocationDataRecord depends in not found.");
        } catch (StreamAlreadyExistsException streamAlreadyExistsException) {
            Log.e(TAG, "Another stream which provides LocationDataRecord is already registered.");
        }
    }

    @Override
    public Stream<LocationDataRecord> generateNewStream() {
        return mStream;
    }

    @Override
    public boolean updateStream() {
        Log.d(TAG, "Update stream called.");
        LocationDataRecord locationDataRecord = new LocationDataRecord(
                (float)latitude.get(),
                (float)longitude.get());
        Log.e(TAG,"latitude : "+latitude.get()+" longitude : "+longitude.get());

        /*
        JSONObject data = new JSONObject();

        //add location to data
        try {
            data.put(RECORD_DATA_PROPERTY_LATITUDE, location.getLatitude());
            data.put(RECORD_DATA_PROPERTY_LONGITUDE, location.getLongitude());
            data.put(RECORD_DATA_PROPERTY_ALTITUDE, location.getAltitude());
            data.put(RECORD_DATA_PROPERTY_ACCURACY, location.getAccuracy());
            data.put(RECORD_DATA_PROPERTY_SPEED, location.getSpeed());
            data.put(RECORD_DATA_PROPERTY_BEARING, location.getBearing());
            data.put(RECORD_DATA_PROPERTY_PROVIDER, location.getProvider());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        LocationDataRecord jsonlocationDataRecord = new LocationDataRecord(data);
*/

        if(location!=null) {
            LocationDataRecord newlocationDataRecord = new LocationDataRecord(
                    (float) latitude.get(),
                    (float) longitude.get(),
                    location.getAccuracy(),
                    (float) location.getAltitude(),
                    location.getSpeed(),
                    location.getBearing(),
                    location.getProvider());


            MinukuStreamManager.getInstance().setLocationDataRecord(newlocationDataRecord);
            toCheckFamiliarOrNotLocationDataRecord = newlocationDataRecord;

            mStream.add(newlocationDataRecord);
            Log.d(TAG, "Location to be sent to event bus" + newlocationDataRecord);

            // also post an event.
            EventBus.getDefault().post(newlocationDataRecord);
            try {

//            mDAO.add(locationDataRecord);
                mDAO.add(newlocationDataRecord);

                mDAO.query_counting();

            } catch (DAOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public long getUpdateFrequency() {
        return 1; // 1 minutes
    }

    @Override
    public void sendStateChangeEvent() {

    }

    @Override
    public void offer(LocationDataRecord dataRecord) {
        Log.e(TAG, "Offer for location data record does nothing!");
    }

    /**
     * Location Listerner events start here.
     */

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d(TAG, "GPS: "
                    + location.getLatitude() + ", "
                    + location.getLongitude() + ", "
                    + "accuracy: " + location.getAccuracy());

            // If the location is accurate to 30 meters, it's good enough for us.
            // Post an update event and exit.
            if (location.getAccuracy() < 100.0f) {
                if(!this.latitude.equals(location.getLatitude())
                        || !this.longitude.equals(location.getLongitude())) {
                    Log.d(TAG, "Location is accurate upto 50 meters");
                    this.latitude.set(location.getLatitude());
                    this.longitude.set(location.getLongitude());

                    //**** additional
                    this.location = location;

                    updateStream();
                }
            } else {
                Log.d(TAG, "Location is not accurate");
            }

        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(Constants.INTERNAL_LOCATION_UPDATE_FREQUENCY);
        mLocationRequest.setFastestInterval(Constants.INTERNAL_LOCATION_UPDATE_FREQUENCY);
        //mLocationRequest.setSmallestDisplacement(Constants.LOCATION_MINUMUM_DISPLACEMENT_UPDATE_THRESHOLD);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        try {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                            this);
        }catch (SecurityException e){

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Connection to Google play services failed.");
        stopCheckingForLocationUpdates();
    }

    private void stopCheckingForLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            try {
                MinukuStreamManager.getInstance().unregister(mStream, this);
                Log.e(TAG, "Unregistering location stream generator from stream manager");
            } catch (StreamNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
