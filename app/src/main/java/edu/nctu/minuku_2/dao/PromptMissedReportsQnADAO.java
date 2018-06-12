package edu.nctu.minuku_2.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.config.UserPreferences;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku_2.model.PromptMissedReportsQnADataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by shriti on 11/9/16.
 */

public class PromptMissedReportsQnADAO implements DAO<PromptMissedReportsQnADataRecord> {

    private String TAG = "PromptMissedReportsQnADAO";
    private String myUserEmail;
    private UUID uuID;

    public PromptMissedReportsQnADAO() {
        myUserEmail = UserPreferences.getInstance().getPreference(Constants.KEY_ENCODED_EMAIL);

    }
    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(PromptMissedReportsQnADataRecord entity) throws DAOException {
        Log.d(TAG, "Adding missed report prompt question answer record.");
        /*
        Firebase dataRecordListRef = new Firebase(Constants.FIREBASE_URL_MISSED_REPORT_PROMPT_QNA)
                .child(myUserEmail)
                .child(new SimpleDateFormat("MMddyyyy").format(new Date()).toString());
        dataRecordListRef.push().setValue(entity);
        */
    }

    @Override
    public void delete(PromptMissedReportsQnADataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<PromptMissedReportsQnADataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<PromptMissedReportsQnADataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(PromptMissedReportsQnADataRecord oldEntity, PromptMissedReportsQnADataRecord newEntity) throws DAOException {

    }
}
