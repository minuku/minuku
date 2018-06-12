package edu.nctu.minuku.stream;

import java.util.ArrayList;
import java.util.List;

import edu.nctu.minuku.model.DataRecord.TransportationModeDataRecord;
import edu.nctu.minukucore.model.DataRecord;
import edu.nctu.minukucore.stream.AbstractStreamFromDevice;

/**
 * Created by Lawrence on 2017/5/22.
 */

public class TransportationModeStream extends AbstractStreamFromDevice<TransportationModeDataRecord> {

    public TransportationModeStream(int maxSize) {
        super(maxSize);
    }

    @Override
    public List<Class<? extends DataRecord>> dependsOnDataRecordType() {
        return new ArrayList<>();
    }
}
