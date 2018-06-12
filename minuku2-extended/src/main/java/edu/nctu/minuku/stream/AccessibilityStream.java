package edu.nctu.minuku.stream;

import java.util.ArrayList;
import java.util.List;

import edu.nctu.minuku.model.DataRecord.AccessibilityDataRecord;
import edu.nctu.minukucore.model.DataRecord;
import edu.nctu.minukucore.stream.AbstractStreamFromDevice;

/**
 * Created by Lawrence on 2017/9/6.
 */

public class AccessibilityStream extends AbstractStreamFromDevice<AccessibilityDataRecord> {
    public AccessibilityStream(int maxSize) {
        super(maxSize);
    }

    @Override
    public List<Class<? extends DataRecord>> dependsOnDataRecordType() {
        return new ArrayList<>();
    }
}
