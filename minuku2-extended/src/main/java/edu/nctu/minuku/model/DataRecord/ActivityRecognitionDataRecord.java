package edu.nctu.minuku.model.DataRecord;

import com.google.android.gms.location.DetectedActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minukucore.model.DataRecord;

/**
 * Created by Lawrence on 2017/5/15.
 */

public class ActivityRecognitionDataRecord implements DataRecord {

    public long creationTime;
    private static DetectedActivity MostProbableActivity;
    private List<DetectedActivity> mProbableActivities;
    private static long Detectedtime;

    //create for ActivityRecognitionDataRecord Pool
    protected long _id;
    protected JSONObject mData;
    protected long _timestamp;
    protected String mTimestring;

    public ActivityRecognitionDataRecord(){

    }

    public ActivityRecognitionDataRecord(DetectedActivity MostProbableActivity,long Detectedtime){
        this.creationTime = new Date().getTime();
        this.MostProbableActivity = MostProbableActivity;
        this.Detectedtime = Detectedtime;

    }

    public DetectedActivity getMostProbableActivity(){return MostProbableActivity;}

    public void setProbableActivities(List<DetectedActivity> probableActivities) {
        mProbableActivities = probableActivities;

    }

    public void setMostProbableActivity(DetectedActivity mostProbableActivity) {
        MostProbableActivity = mostProbableActivity;

    }

    public void setDetectedtime(long detectedtime){
        Detectedtime = detectedtime;

    }

    public void setID(long id){
        _id = id;
    }

    public long getID(){
        return _id;
    }

    public long getDetectedtime(){return Detectedtime;}

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    //create for ActivityRecognitionDataRecord Pool
    public void setTimestamp(long t){
        _timestamp = t;
    }

    public long getTimestamp(){
        return _timestamp;
    }

    public JSONObject getData() {
        return mData;
    }

    public void setData(JSONObject data) {
        this.mData = data;
    }

    public String getTimeString(){

        SimpleDateFormat sdf_now = new SimpleDateFormat(Constants.DATE_FORMAT_NOW);
        mTimestring = sdf_now.format(_timestamp);

        return mTimestring;
    }

    public List<DetectedActivity> getProbableActivities() {
        return mProbableActivities;
    }

}
