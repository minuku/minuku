package edu.nctu.minuku.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.model.DataRecord.TelephonyDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by Lawrence on 2017/9/19.
 */

public class TelephonyDataRecordDAO implements DAO<TelephonyDataRecord> {
    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(TelephonyDataRecord entity) throws DAOException {

    }

    @Override
    public void delete(TelephonyDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<TelephonyDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<TelephonyDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(TelephonyDataRecord oldEntity, TelephonyDataRecord newEntity) throws DAOException {

    }
}
