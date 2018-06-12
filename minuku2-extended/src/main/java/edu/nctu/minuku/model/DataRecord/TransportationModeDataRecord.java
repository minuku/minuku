package edu.nctu.minuku.model.DataRecord;

import java.util.Date;

import edu.nctu.minukucore.model.DataRecord;

/**
 * Created by Lawrence on 2017/5/22.
 */

public class TransportationModeDataRecord implements DataRecord{

    public long creationTime;
    public String ConfirmedActivityType; //

    public TransportationModeDataRecord(){}

    public TransportationModeDataRecord(String ConfirmedActivityType){
        this.creationTime = new Date().getTime();
        this.ConfirmedActivityType = ConfirmedActivityType;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    public String getConfirmedActivityType(){
        return ConfirmedActivityType;
    }

    public void setConfirmedActivityType(String ConfirmedActivityType){
        this.ConfirmedActivityType=ConfirmedActivityType;
    }
}
