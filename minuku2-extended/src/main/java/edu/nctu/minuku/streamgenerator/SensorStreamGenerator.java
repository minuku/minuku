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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.manager.MinukuStreamManager;
import edu.nctu.minuku.model.DataRecord.SensorDataRecord;
import edu.nctu.minuku.stream.SensorStream;
import edu.nctu.minukucore.exception.StreamAlreadyExistsException;
import edu.nctu.minukucore.exception.StreamNotFoundException;
import edu.nctu.minukucore.stream.Stream;

import static android.content.Context.SENSOR_SERVICE;
import static edu.nctu.minuku.config.Constants.CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
//import edu.nctu.minuku.dao.SensorDataRecordDAO;

/**
 * Created by neerajkumar on 7/18/16.
 */

public class SensorStreamGenerator extends AndroidStreamGenerator<SensorDataRecord> implements
        SensorEventListener {
    /**  variable declaration
     **/

    private SensorStream mStream;
    private String TAG = "SensorStreamGenerator";
    private Sensor sensor;
    //SensorDataRecordDAO mDAO;
    public static SensorDataRecord sensorDataRecord;
    /** Tag for logging. */
    private static final String LOG_TAG = "PhoneSensorMnger";
    public static String CONTEXT_SOURCE_PHONE_SENSOR = "PhoneSensor";
    /**Properties for Record**/
    public static final String RECORD_DATA_PROPERTY_NAME = "SensorValues";
    /**system components**/
    private static Context mContext;
    private static SensorManager mSensorManager ;
    private static List<Sensor> SensorList;



    public static final String STRING_PHONE_SENSOR_ACCELEROMETER = "Sensor-Accelerometer";
    public static final String STRING_PHONE_SENSOR_LINEAR_ACCELERATION = "Sensor-LinearAcceleration";
    public static final String STRING_PHONE_SENSOR_ROTATION_VECTOR = "Sensor-RotationVector";
    public static final String STRING_PHONE_SENSOR_GRAVITY = "Sensor-Gravity";
    public static final String STRING_PHONE_SENSOR_GYROSCOPE = "Sensor-Gyroscope";
    public static final String STRING_PHONE_SENSOR_LIGHT = "Sensor-Light";
    public static final String STRING_PHONE_SENSOR_MAGNETIC_FIELD = "Sensor-MagneticField";
    public static final String STRING_PHONE_SENSOR_PRESSURE = "Sensor-Pressure";
    public static final String STRING_PHONE_SENSOR_PROXIMITY = "Sensor-Proximity";
    public static final String STRING_PHONE_SENSOR_AMBIENT_TEMPERATURE = "Sensor-AmbientTemperature";
    public static final String STRING_PHONE_SENSOR_RELATIVE_HUMIDITY = "Sensor-RelativeHumidity";
    public static final String STRING_PHONE_SENSOR_STEP_COUNTER = "Sensor-StepCounter";
    public static final String STRING_PHONE_SENSOR_STEP_DETECTOR = "Sensor-StepDetector";
    public static final String STRING_PHONE_SENSOR_HEART_RATE = "Sensor-HeartRate";

    public static final int PHONE_SENSOR_ACCELEROMETER = 0;
    public static final int PHONE_SENSOR_LINEAR_ACCELERATION = 1;
    public static final int PHONE_SENSOR_ROTATION_VECTOR = 2;
    public static final int PHONE_SENSOR_GRAVITY = 3;
    public static final int PHONE_SENSOR_GYROSCOPE = 4;
    public static final int PHONE_SENSOR_LIGHT = 5;
    public static final int PHONE_SENSOR_MAGNETIC_FIELD = 6;
    public static final int PHONE_SENSOR_PRESSURE = 7;
    public static final int PHONE_SENSOR_PROXIMITY = 8;
    public static final int PHONE_SENSOR_AMBIENT_TEMPERATURE = 9;
    public static final int PHONE_SENSOR_RELATIVE_HUMIDITY = 10;
    public static final int PHONE_SENSOR_STEP_COUNTER = 11;
    public static final int PHONE_SENSOR_STEP_DETECTOR = 12;
    public static final int PHONE_SENSOR_HEART_RATE = 13;

    /**Motion Sensors**/
    private static float mAccele_x, mAccele_y, mAccele_z;
    private static float mGyroscope_x, mGyroscope_y, mGyroscope_z;
    private static float mGravity_x, mGravity_y, mGravity_z;
    private static float mLinearAcceleration_x, mLinearAcceleration_y, mLinearAcceleration_z;
    private static float mRotationVector_x_sin, mRotationVector_y_sin, mRotationVector_z_sin, mRotationVector_cos;
    private static float mHeartRate, mStepCount, mStepDetect;

    /**Position Sensors**/
    private static float mProximity ;
    private static float mMagneticField_x, mMagneticField_y, mMagneticField_z;

    private float mLight, mPressure, mRelativeHumidity, mAmbientTemperature ;



    /** handle stream **/
    /**sensorStreamGenerator**/
    public SensorStreamGenerator(Context applicationContext) {
        super(applicationContext);
        this.mStream = new SensorStream(Constants.SENSOR_QUEUE_SIZE);
        //this.mDAO = MinukuDAOManager.getInstance().getDaoFor(SensorDataRecord.class);

        mContext = applicationContext;
        //call sensor manager from the service
        mSensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);

        //initiate values of sensors
        mAccele_x = mAccele_y = mAccele_z = CONTEXT_SOURCE_INVALID_VALUE_FLOAT; //-9999
        mGyroscope_x = mGyroscope_y = mGyroscope_z = CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
        mGravity_x = mGravity_y = mGravity_z = CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
        mMagneticField_x = mMagneticField_y = mMagneticField_z = CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
        mLinearAcceleration_x = mLinearAcceleration_y = mLinearAcceleration_z = CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
        mRotationVector_x_sin = mRotationVector_y_sin =  mRotationVector_z_sin = mRotationVector_cos = CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
        mHeartRate = mStepCount = mStepDetect =CONTEXT_SOURCE_INVALID_VALUE_FLOAT;
        mLight = mPressure = mRelativeHumidity = mProximity = mAmbientTemperature = CONTEXT_SOURCE_INVALID_VALUE_FLOAT;

        //initiate registered sensor list
        RegisterAvailableSensors();
        this.register();  // stream
    }
    /**onStreamRegistration**/


    @Override
    public void offer(SensorDataRecord dataRecord) {

    }

    /**register**/

    @Override
    public void register() {
        Log.d(TAG, "Registering with StreamManager.");
        try {
            MinukuStreamManager.getInstance().register(mStream, SensorDataRecord.class, this);
        } catch (StreamNotFoundException streamNotFoundException) {
            Log.e(TAG, "One of the streams on which SensorDataRecord depends in not found.");
        } catch (StreamAlreadyExistsException streamAlreadyExistsException) {
            Log.e(TAG, "Another stream which provides SensorDataRecord is already registered.");
        }
    }

    @Override
    public Stream<SensorDataRecord> generateNewStream() {
        return null;
    }


    @Override
    public boolean updateStream() {
        return false;
    }



    @Override
    public long getUpdateFrequency() {
        return 1; // 1 minutes
    }

    @Override
    public void sendStateChangeEvent() {

    }

    @Override
    public void onStreamRegistration() {

    }

    /**handle different json form convertion**/



    /** handle sensor **/
    /**register sensor - Not sure**/
    protected void RegisterAvailableSensors(){
        mSensorManager=(SensorManager) mApplicationContext.getSystemService(SENSOR_SERVICE);
        SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s : SensorList){
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(s.getType()), SensorManager.SENSOR_DELAY_NORMAL);
        }
        Log.d(LOG_TAG, "in register all available sensors" );
    }
    /** get sensor values **/
    @Override
    public void onSensorChanged(SensorEvent event) {

        /**Motion Sensor**/
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            getGyroscope(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY){
            getGravity(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            getLinearAcceleration(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            getRotationVector(event);
        }

        /**Position Sensor**/
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            Log.d(LOG_TAG, "in [onSensorChange] Proximity: " +  event.values[0] );
            getProximity(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            Log.d(LOG_TAG, "in [onSensorChange] Proximity: " +  event.values[0] );
            getMagneticField(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR){
            Log.d(LOG_TAG, "in [onSensorChange] Proximity: " +  event.values[0] );
            getMagneticField(event);
        }

        /**Environment Sensor**/
        if (event.sensor.getType() == Sensor.TYPE_LIGHT){
            getLight(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            getAmbientTemperature(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE){
            getPressure(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            getRelativeHumidity(event);
        }

        /**health related**/
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE){
            getHeartRate (event);
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            getStepCounter(event);
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            getStepDetector(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * In PhoneSensorManager, all the values are float numbers
     */
    protected void saveRecordToStream (String sourceName, float[] values) {

        /** store values into a Record so that we can store them in the local database **/
        SensorDataRecord newSensorDataRecord = new SensorDataRecord( );
        newSensorDataRecord.setSource(sourceName);

        /** create data in a JSON Object. Each CotnextSource will have different formats.
         * So we need each ContextSourceMAnager to implement this part**/
        JSONObject data = new JSONObject();
        JSONArray array = new JSONArray();

        try {

            //put all values in Data JSONObject
            for (int i=0; i< values.length; i++) {
                array.put(values[i]);
            }

            data.put(RECORD_DATA_PROPERTY_NAME, array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*** Set data to SensorDataRecord **/
        newSensorDataRecord.setData(data);
        Log.d(LOG_TAG, "in SaveRecordtostream " +  newSensorDataRecord.getSource() + newSensorDataRecord.getData() );

        /** Save to stream**/
        // mLocalRecordPool.add(record);
        //updateStream(ChangeJsonForm(newSensorDataRecord));

    }

    /**get Accelerometer values**/
    private void getAccelerometer(SensorEvent event) {
        Log.d(LOG_TAG, "getting accelerometer:" + mAccele_x + " : " +  mAccele_y +  " : " + mAccele_y);

        mAccele_x = event.values[0];    // Acceleration force along the x axis (including gravity). m/s2
        mAccele_y = event.values[1];    // Acceleration force along the y axis (including gravity). m/s2
        mAccele_z = event.values[2];    // Acceleration force along the z axis (including gravity). m/s2

        saveRecordToStream(STRING_PHONE_SENSOR_ACCELEROMETER, event.values);
    }


    /**get Gyroscope values**/
    private void getGyroscope(SensorEvent event) {
        mGyroscope_x = event.values[0]; // Rate of rotation around the x axis. rad/s
        mGyroscope_y = event.values[1]; // Rate of rotation around the y axis. rad/s
        mGyroscope_z = event.values[2]; // Rate of rotation around the z axis. rad/s

        saveRecordToStream(STRING_PHONE_SENSOR_GYROSCOPE, event.values);

    }


    /**get gravity values**/
    private void getGravity(SensorEvent event) {
        mGravity_x = event.values[0];   // Force of gravity along the x axis m/s2
        mGravity_y = event.values[1];   // Force of gravity along the y axis m/s2
        mGravity_z = event.values[2];   // Force of gravity along the z axis m/s2

        saveRecordToStream(STRING_PHONE_SENSOR_GRAVITY, event.values);
    }
    /**get linear acceleration values**/
    private void getLinearAcceleration(SensorEvent event) {
        mLinearAcceleration_x = event.values[0];    //Acceleration force along the x axis (excluding gravity).  m/s2
        mLinearAcceleration_y = event.values[1];    //Acceleration force along the y axis (excluding gravity).  m/s2
        mLinearAcceleration_z = event.values[2];    //Acceleration force along the z axis (excluding gravity).  m/s2

        saveRecordToStream(STRING_PHONE_SENSOR_LINEAR_ACCELERATION, event.values);
    }

    /**get rotation vector values**/
    private void getRotationVector(SensorEvent event) {
        mRotationVector_x_sin = event.values[0];    // Rotation vector component along the x axis (x * sin(�c/2))  Unitless
        mRotationVector_y_sin = event.values[1];    // Rotation vector component along the y axis (y * sin(�c/2)). Unitless
        mRotationVector_z_sin = event.values[2];    //  Rotation vector component along the z axis (z * sin(�c/2)). Unitless
        mRotationVector_cos = event.values[3];      // Scalar component of the rotation vector ((cos(�c/2)).1 Unitless

        saveRecordToStream(STRING_PHONE_SENSOR_ROTATION_VECTOR, event.values);
    }



    /**get magnetic field values**/
    private void getMagneticField(SensorEvent event){
        mMagneticField_x = event.values[0]; // Geomagnetic field strength along the x axis.
        mMagneticField_y = event.values[1]; // Geomagnetic field strength along the y axis.
        mMagneticField_z = event.values[2]; // Geomagnetic field strength along the z axis.

        saveRecordToStream(STRING_PHONE_SENSOR_MAGNETIC_FIELD, event.values);
    }

    /**get proximity values**/
    private void getProximity(SensorEvent event){

//        Log.d(LOG_TAG, "getting proximity" + mProximity);

        mProximity = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_PROXIMITY, event.values);
    }

    private void getAmbientTemperature(SensorEvent event){
        /* Environment Sensors */
        mAmbientTemperature = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_AMBIENT_TEMPERATURE, event.values);

    }

    private void getLight(SensorEvent event){

        Log.d(LOG_TAG, "getting light" + mLight);

        mLight = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_LIGHT, event.values);
    }

    private void getPressure(SensorEvent event){
        mPressure = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_PRESSURE, event.values);
    }

    private void getRelativeHumidity(SensorEvent event){
        mRelativeHumidity = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_RELATIVE_HUMIDITY, event.values);
    }

    private void getHeartRate (SensorEvent event) {
        mHeartRate = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_HEART_RATE, event.values);
    }

    private void getStepCounter (SensorEvent event) {
        mStepCount = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_STEP_COUNTER, event.values);

    }

    private void getStepDetector (SensorEvent event) {
        mStepDetect = event.values[0];

        saveRecordToStream(STRING_PHONE_SENSOR_STEP_DETECTOR, event.values);
    }



}