package edu.nctu.minuku_2.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku_2.model.CheckAndRemindDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by Lawrence on 2017/9/19.
 */

public class CheckAndRemindDataRecordDAO implements DAO<CheckAndRemindDataRecord> {
    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(CheckAndRemindDataRecord entity) throws DAOException {

    }

    @Override
    public void delete(CheckAndRemindDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<CheckAndRemindDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<CheckAndRemindDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(CheckAndRemindDataRecord oldEntity, CheckAndRemindDataRecord newEntity) throws DAOException {

    }
}
