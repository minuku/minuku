package edu.nctu.minuku.stream;

import java.util.ArrayList;
import java.util.List;

import edu.nctu.minuku.model.DataRecord.ActivityRecognitionDataRecord;
import edu.nctu.minukucore.model.DataRecord;
import edu.nctu.minukucore.stream.AbstractStreamFromDevice;

/**
 * Created by Lawrence on 2017/5/22.
 */

public class SensorStream extends AbstractStreamFromDevice<ActivityRecognitionDataRecord> {

    public SensorStream(int maxSize) {
        super(maxSize);
    }

    @Override
    public List<Class<? extends DataRecord>> dependsOnDataRecordType() {
        return new ArrayList<>();
    }

}
