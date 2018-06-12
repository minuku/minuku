package edu.nctu.minuku.dao;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import edu.nctu.minuku.DBHelper.DBHelper;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.model.DataRecord.ActivityRecognitionDataRecord;
import edu.nctu.minukucore.dao.DAO;
import edu.nctu.minukucore.dao.DAOException;
import edu.nctu.minukucore.user.User;

/**
 * Created by Lawrence on 2017/5/22.
 */

public class ActivityRecognitionDataRecordDAO implements DAO<ActivityRecognitionDataRecord> {

    final private String TAG = "ActivityRecognitionDataRecordDAO";

    File file;
    BufferedWriter fw;
    JSONObject obj;
    private DBHelper dBHelper;


    public ActivityRecognitionDataRecordDAO(){
        //file = new File(Context.getFilesDir(), filename);
        file = new File("/output.json"); //, false
        //fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
        obj = new JSONObject();

    }

    public ActivityRecognitionDataRecordDAO(Context applicationContext){

        dBHelper = DBHelper.getInstance(applicationContext);

    }

    @Override
    public void setDevice(User user, UUID uuid) {

    }

    @Override
    public void add(ActivityRecognitionDataRecord entity) throws DAOException {

        Log.d(TAG, "Adding ActivityRecognition data record.");
        //TODO fix it please.
        //TODO store in Json file.
        //TODO (5/26) Need to store in sqllite first.

        /*ContentValues values = new ContentValues();

        try {
            SQLiteDatabase db = DBManager.getInstance().openDatabase();

            values.put(DBHelper.TIME, entity.getCreationTime());
            values.put(DBHelper.MostProbableActivity_col, entity.getMostProbableActivity().toString());
            values.put(DBHelper.ProbableActivities_col, entity.getProbableActivities().toString());

            db.insert(DBHelper.activityRecognition_table, null, values);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        finally {
            values.clear();
            DBManager.getInstance().closeDatabase(); // Closing database connection
        }
*/
    }

    public void query_counting(){
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor MostProbableActivityCursor = db.rawQuery("SELECT "+ DBHelper.MostProbableActivity_col +" FROM "+ DBHelper.activityRecognition_table, null);
        Cursor ProbableActivitiesCursor = db.rawQuery("SELECT "+ DBHelper.ProbableActivities_col +" FROM "+ DBHelper.activityRecognition_table, null);

        int MostProbableActivityrow    = MostProbableActivityCursor.getCount();
        int MostProbableActivitycol    = MostProbableActivityCursor.getColumnCount();
        int ProbableActivitiesrow= ProbableActivitiesCursor.getCount();
        int ProbableActivitiescol= ProbableActivitiesCursor.getColumnCount();

        Log.d(TAG,"MostProbableActivityrow : " + MostProbableActivityrow +" MostProbableActivitycol : " + MostProbableActivitycol+
                " ProbableActivitiesrow : " + ProbableActivitiesrow+ " ProbableActivitiescol : " + ProbableActivitiescol);
*/
    }

    @Override
    public void delete(ActivityRecognitionDataRecord entity) throws DAOException {

    }

    @Override
    public Future<List<ActivityRecognitionDataRecord>> getAll() throws DAOException {
        return null;
    }

    @Override
    public Future<List<ActivityRecognitionDataRecord>> getLast(int N) throws DAOException {
        return null;
    }

    @Override
    public void update(ActivityRecognitionDataRecord oldEntity, ActivityRecognitionDataRecord newEntity) throws DAOException {

    }
}
