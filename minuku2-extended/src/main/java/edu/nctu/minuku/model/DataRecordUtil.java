package edu.nctu.minuku.model;

import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.manager.MinukuStreamManager;
import edu.nctu.minuku.model.DataRecord.LocationDataRecord;
import edu.nctu.minuku.model.DataRecord.SemanticLocationDataRecord;
import edu.nctu.minukucore.exception.StreamNotFoundException;

/**
 * Created by neerajkumar on 10/22/16.
 */

public class DataRecordUtil {
    private static final String TAG = "DataRecordUtil";

    public static String attemptToGetSemanticOrNormalLocation() {
        try {
            SemanticLocationDataRecord semanticLocationDataRecord = MinukuStreamManager
                    .getInstance()
                    .getStreamFor(SemanticLocationDataRecord.class)
                    .getCurrentValue();
            if(semanticLocationDataRecord != null) {
                return semanticLocationDataRecord.getSemanticLocation();
            }
        } catch (StreamNotFoundException e) {
            Log.e(TAG, "No semantic location stream found: " + e.getMessage());
        }

        try {
            LocationDataRecord locationDataRecord = MinukuStreamManager
                    .getInstance()
                    .getStreamFor(LocationDataRecord.class)
                    .getCurrentValue();
            if(locationDataRecord != null) {
                return String.valueOf(locationDataRecord.getLatitude())
                        + ","
                        + String.valueOf(locationDataRecord.getLatitude());
            }
        } catch (StreamNotFoundException e) {
            Log.e(TAG, "No location stream found: " + e.getMessage());
        }

        return "";
    }
}
