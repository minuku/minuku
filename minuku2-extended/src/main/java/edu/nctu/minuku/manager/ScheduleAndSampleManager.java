package edu.nctu.minuku.manager;

import java.text.SimpleDateFormat;

import edu.nctu.minuku.config.Constants;

/**
 * Created by Lawrence on 2017/9/19.
 */

public class ScheduleAndSampleManager {

    public ScheduleAndSampleManager(){

    }

    public static String getTimeString(long time){
        SimpleDateFormat sdf_now = new SimpleDateFormat(Constants.DATE_FORMAT_NOW);
        String currentTimeString = sdf_now.format(time);

        return currentTimeString;
    }

}
