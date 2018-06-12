package edu.nctu.minuku.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.model.DataRecord.TransportationModeDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by Lawrence on 2017/5/22.
 */

public class TransportationModeDAO implements DAO<TransportationModeDataRecord>{
    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(TransportationModeDataRecord entity) throws DAOException {

    }

    @Override
    public void delete(TransportationModeDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<TransportationModeDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<TransportationModeDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(TransportationModeDataRecord oldEntity, TransportationModeDataRecord newEntity) throws DAOException {

    }
}
