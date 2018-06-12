package edu.nctu.minuku.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

import edu.nctu.minuku.manager.DBManager;


/**
 * Created by Lawrence on 2017/6/5.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance = null;

    public static final String id = "_id";
    public static final String TAG = "DBHelper";
    public static final String home_col = "home";
    public static final String neighbor_col = "neighbor";
    public static final String outside_col = "outside";
    public static final String homeorfaraway = "homeorfaraway";
    public static final String staticornot = "staticornot";
    public static String DEVICE = "device_id";
    public static String TIME = "timeToSQLite";

    public static final String latitude_col = "latitude";
    public static final String longitude_col = "longitude";
    public static final String Accuracy_col = "Accuracy";
    public static final String Altitude_col = "Altitude";
    public static final String Speed_col = "Speed";
    public static final String Bearing_col = "Bearing";
    public static final String Provider_col = "Provider";

    public static final String MostProbableActivity_col = "MostProbableActivity";
    public static final String ProbableActivities_col = "ProbableActivities";

    public static JSONObject location_jsonObject;

    public static String checkFamiliarOrNot_table = "CheckFamiliarOrNot";
    public static String location_table = "Location";
    public static String activityRecognition_table = "ActivityRecognition";

    public static String DATABASE_NAME = "MySQLite.db";
    public static int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        initiateDBManager();

    }

    public static DBHelper getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new DBHelper(applicationContext);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db","oncreate");

        createARTable(db);
        createLocationTable(db);
        createCheckFamiliarOrNotTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initiateDBManager() {
        DBManager.initializeInstance(this);
    }

    public void createARTable(SQLiteDatabase db){
        Log.d(TAG,"create AR table");

        String cmd = "CREATE TABLE " +
                activityRecognition_table + "(" +
                id+" INTEGER PRIMARY KEY NOT NULL, " +
//                DEVICE+" TEXT,"+
                TIME + " TEXT NOT NULL," +
                MostProbableActivity_col+" TEXT," +
                ProbableActivities_col +" TEXT " +
                ");";

        db.execSQL(cmd);

    }

    public void createLocationTable(SQLiteDatabase db){
        Log.d(TAG,"create location table");

        String cmd = "CREATE TABLE " +
                location_table + "(" +
                id+" INTEGER PRIMARY KEY NOT NULL, " +
//                DEVICE+" TEXT,"+
                TIME + " TEXT NOT NULL," +
                latitude_col+" FLOAT,"+
                longitude_col +" FLOAT, " +
                Accuracy_col + " FLOAT, " +
                Altitude_col +" FLOAT," +
                Speed_col +" FLOAT," +
                Bearing_col +" FLOAT," +
                Provider_col +" TEXT" +
                ");";

        db.execSQL(cmd);

    }

    public void createCheckFamiliarOrNotTable(SQLiteDatabase db){
        Log.d(TAG,"create checkFamiliarOrNot table");

        String cmd = "CREATE TABLE " +
                checkFamiliarOrNot_table + "(" +
                id+" INTEGER PRIMARY KEY NOT NULL, " +
                DEVICE+" TEXT,"+
                TIME + " TEXT NOT NULL," +
                staticornot+" INTEGER,"+
                home_col +" INTEGER, " +
                neighbor_col + " INTEGER, " +
                outside_col +" INTEGER" +
                ");";

        db.execSQL(cmd);

    }

}
