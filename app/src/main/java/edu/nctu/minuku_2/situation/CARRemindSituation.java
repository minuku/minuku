package edu.nctu.minuku_2.situation;

import java.util.List;

import edu.nctu.minukucore.event.ActionEvent;
import edu.nctu.minukucore.event.MinukuEvent;
import edu.nctu.minukucore.exception.DataRecordTypeNotFound;
import edu.nctu.minukucore.model.DataRecord;
import edu.nctu.minukucore.model.StreamSnapshot;
import edu.nctu.minukucore.situation.Situation;

/**
 * Created by Lawrence on 2017/9/19.
 */

public class CARRemindSituation implements Situation {

    @Override
    public <T extends ActionEvent> T assertSituation(StreamSnapshot snapshot, MinukuEvent minukuEvent) {
        return null;
    }

    @Override
    public List<Class<? extends DataRecord>> dependsOnDataRecordType() throws DataRecordTypeNotFound {
        return null;
    }

}
