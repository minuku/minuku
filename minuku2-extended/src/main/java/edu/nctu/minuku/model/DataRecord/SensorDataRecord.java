package edu.nctu.minuku.model.DataRecord;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import edu.nctu.minuku.model.Session;
import edu.nctu.minukucore.model.DataRecord;

/**
 * Created by Lawrence on 2017/7/22.
 */

public class SensorDataRecord implements DataRecord {

    protected long _id;
    protected String _source;
    protected Session _session;
    protected ArrayList<Integer> mSavedBySessionIds;
    protected boolean isCopiedToPublicPool;
    protected JSONObject mData;
    protected String mTimestring;
    protected long creationTime;

    public SensorDataRecord(){
        mSavedBySessionIds = new ArrayList<Integer>();
        this.creationTime = new Date().getTime();
    }

    public ArrayList<Integer> getSavedSessionIds() {
        return mSavedBySessionIds;
    }

    public void addSavedBySessionId(int sessionId){

        mSavedBySessionIds.add(sessionId);

    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + _id +
                ", source='" + _source + '\'' +
                ", session=" + _session +
                ", savedBySessionIds=" + mSavedBySessionIds +
                ", data=" + mData +
                ", createTime='" + creationTime + '\'' +
                '}';
    }

    public boolean isCopiedToPublicPool() {
        return isCopiedToPublicPool;
    }

    public void setIsCopiedToPublicPool(boolean isCopiedToPublicPool) {
        this.isCopiedToPublicPool = isCopiedToPublicPool;
    }

    public void setID(long id){
        _id = id;
    }

    public long getID(){
        return _id;
    }


    public JSONObject getData() {
        return mData;
    }

    public void setData(JSONObject data) {
        this.mData = data;
    }


    public String getSource(){
        return _source;
    }

    public void setSource(String source){
        _source = source;
    }

    public Session getSession(){
        return _session;
    }

    public void setSession(Session s){
        _session = s;
    }
    @Override
    public long getCreationTime() {

        return creationTime;
    }

}
