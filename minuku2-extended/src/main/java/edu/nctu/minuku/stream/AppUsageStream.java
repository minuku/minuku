package edu.nctu.minuku.stream;

import java.util.ArrayList;
import java.util.List;

import edu.nctu.minuku.model.DataRecord.AppUsageDataRecord;
import edu.nctu.minukucore.model.DataRecord;
import edu.nctu.minukucore.stream.AbstractStreamFromDevice;

/**
 * Created by Jimmy on 2017/8/8.
 */

public class AppUsageStream extends AbstractStreamFromDevice<AppUsageDataRecord> {

    public AppUsageStream(int maxSize) {
        super(maxSize);
    }

    @Override
    public List<Class<? extends DataRecord>> dependsOnDataRecordType() {
        return new ArrayList<>();
    }
}

