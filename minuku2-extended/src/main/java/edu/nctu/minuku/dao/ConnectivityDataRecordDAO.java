package edu.nctu.minuku.dao;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.DBHelper.DBHelper;
import edu.nctu.minuku.model.DataRecord.ConnectivityDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by Lawrence on 2017/8/22.
 */

public class ConnectivityDataRecordDAO implements DAO<ConnectivityDataRecord>{

    private final String TAG = "ConnectivityDataRecordDAO";

    private DBHelper dBHelper;
    private Context mContext;

    public ConnectivityDataRecordDAO(){
       /* this.mContext = applicationContext;

        dBHelper = DBHelper.getInstance(applicationContext);*/
    }

    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(ConnectivityDataRecord entity) throws DAOException {
        Log.d(TAG, "Adding Connectivity data record.");
/*
        ContentValues values = new ContentValues();

        try {
            SQLiteDatabase db = DBManager.getInstance().openDatabase();

            values.put(DBHelper.TIME, entity.getCreationTime());
//            values.put(DBHelper.TaskDayCount, entity.getTaskDayCount());
//            values.put(DBHelper.HOUR, entity.getHour());
            values.put(DBHelper.NetworkType_col, entity.getNetworkType());
            values.put(DBHelper.IsNetworkAvailable_col, entity.getIsNetworkAvailable());
            values.put(DBHelper.IsConnected_col, entity.getIsConnected());
            values.put(DBHelper.IsWifiAvailable_col, entity.getIsWifiAvailable());
            values.put(DBHelper.IsMobileAvailable_col, entity.getIsMobileAvailable());
            values.put(DBHelper.IsWifiConnected_col, entity.getIsWifiConnected());
            values.put(DBHelper.IsMobileConnected_col, entity.getIsMobileConnected());

            db.insert(DBHelper.connectivity_table, null, values);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        finally {
            values.clear();
            DBManager.getInstance().closeDatabase(); // Closing database connection
        }*/
    }

    @Override
    public void delete(ConnectivityDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<ConnectivityDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<ConnectivityDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(ConnectivityDataRecord oldEntity, ConnectivityDataRecord newEntity) throws DAOException {

    }
}

