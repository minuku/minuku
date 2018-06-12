package edu.nctu.minuku.model.DataRecord;


import java.util.Date;
import edu.nctu.minukucore.model.DataRecord;

/**
 * Created by Lucy on 2017/8/11.
 */

public class BatteryDataRecord implements DataRecord {
    public String base64Data;
    public long creationTime;

    public BatteryDataRecord(String base64Data) {
        this.base64Data = base64Data;
        this.creationTime = new Date().getTime();
    }

    @Override
    public long getCreationTime() {

        return creationTime;
    }
}
