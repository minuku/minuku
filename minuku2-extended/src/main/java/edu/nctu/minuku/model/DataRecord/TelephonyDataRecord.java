package edu.nctu.minuku.model.DataRecord;

import java.util.Date;

import edu.nctu.minukucore.model.DataRecord;

/**
 * Created by Lawrence on 2017/7/22.
 */

public class TelephonyDataRecord implements DataRecord {
    public String base64Data;
    public long creationTime;

    public TelephonyDataRecord(String base64Data) {
        this.base64Data = base64Data;
        this.creationTime = new Date().getTime();
    }

    @Override
    public long getCreationTime() {

        return creationTime;
    }
}
