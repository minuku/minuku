package edu.nctu.minuku_2.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.config.UserPreferences;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku_2.model.TimelinePatchDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by shriti on 11/1/16.
 */

public class TimelinePatchDataRecordDAO implements DAO<TimelinePatchDataRecord> {

    private String TAG = "TimelinePatchDataRecordDAO";
    private String myUserEmail;
    private UUID uuID;

    public TimelinePatchDataRecordDAO() {
        myUserEmail = UserPreferences.getInstance().getPreference(Constants.KEY_ENCODED_EMAIL);
    }

    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(TimelinePatchDataRecord entity) throws DAOException {
        Log.d(TAG, "Adding timeline patch data record.");
        /*
        Firebase dataRecordListRef = new Firebase(Constants.FIREBASE_URL_TIMELINE_PATCH)
                .child(myUserEmail)
                .child(new SimpleDateFormat("MMddyyyy").format(new Date()).toString());
        dataRecordListRef.push().setValue(entity);
        */
    }

    @Override
    public void delete(TimelinePatchDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<TimelinePatchDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<TimelinePatchDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(TimelinePatchDataRecord oldEntity, TimelinePatchDataRecord newEntity) throws DAOException {

    }
}
