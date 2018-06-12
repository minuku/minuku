package edu.nctu.minuku_2.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.config.UserPreferences;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku_2.model.EODQuestionDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by shriti on 10/12/16.
 */

public class EODQuestionAnswerDAO implements DAO<EODQuestionDataRecord> {

    private String TAG = "EODQuestionAnswerDAO";
    private String myUserEmail;
    private UUID uuID;

    public EODQuestionAnswerDAO() {
        myUserEmail = UserPreferences.getInstance().getPreference(Constants.KEY_ENCODED_EMAIL);

    }
    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(EODQuestionDataRecord entity) throws DAOException {
        Log.d(TAG, "Adding EOD question answer record.");
        /*
        Firebase dataRecordListRef = new Firebase(Constants.FIREBASE_URL_EOD_QUESTION_ANSWER)
                .child(myUserEmail)
                .child(new SimpleDateFormat("MMddyyyy").format(new Date()).toString());
        dataRecordListRef.push().setValue(entity);
        */
    }

    @Override
    public void delete(EODQuestionDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<EODQuestionDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<EODQuestionDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(EODQuestionDataRecord oldEntity, EODQuestionDataRecord newEntity) throws DAOException {

    }
}

