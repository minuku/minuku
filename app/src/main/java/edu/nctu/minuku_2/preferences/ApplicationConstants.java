/*
 * Copyright (c) 2016.
 *
 * DReflect and Minuku Libraries by Shriti Raj (shritir@umich.edu) and Neeraj Kumar(neerajk@uci.edu) is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/Shriti-UCI/Minuku-2.
 *
 *
 * You are free to (only if you meet the terms mentioned below) :
 *
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 *
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * Under the following terms:
 *
 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * NonCommercial — You may not use the material for commercial purposes.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 */

package edu.nctu.minuku_2.preferences;

import edu.nctu.minuku_2.BuildConfig;

/**
 * Created by shriti on 8/2/16.
 * Will contain all the constants specific to application
 * ToDo Clean the Constants in extended library and put all the app specific constants here.
 *
 */
public class ApplicationConstants {

    /** public static final String FIREBASE_URL_GLUCOSE_READING_IMAGES = Constants.FIREBASE_URL_IMAGES + "/glucose_readings";
    public static final String FIREBASE_URL_INSULIN_ADMIN_IMAGES = Constants.FIREBASE_URL_IMAGES + "/insulin_admin";
    public static final String FIREBASE_URL_FOOD_IMAGES = Constants.FIREBASE_URL_IMAGES + "/food";
     **/

    public static final String IMAGE_TYPE_GLUCOSE_READIMG = "GLUCOSE_READINGS";
    public static final String IMAGE_TYPE_INSULIN_SHOT = "INSULIN_SHOT";
    public static final String IMAGE_TYPE_FOOD = "FOOD";
    public static final String IMAGE_TYPE_OTHERS = "OTHER_IMAGES";
    public static final String IMAGE_TYPE_GALLERY_UPLOAD = "GALLERY_UPLOAD";


    public static final String NOTIFICATION_CATEGORY_MISSED_ACTIVITY = "MISSED_IMAGE_REPORT_NOTIF";
    public static final String NOTIFICATION_CATEGORY_MOOD_REPORT = "MOOD_REPORT_NOTIF";
    public static final String NOTIFICATION_CATEGORY_MOOD_ANNOTATION = "MOOD_ANNOTATION_NOTIF";
    public static final String NOTIFICATION_CATEGORY_EOD_DIARY = "EOD_DIARY_NOTIF";

    public static final int MIN_REPORTS_TO_GET_REWARD = 2;

    public static final String EMAIL_FROM = "dstudio.umich@gmail.com";
    public static final String EMAIL_FROM_PASSWORD = BuildConfig.DSTUDIO_MAIL_PASSWORD;
    public static final String EMAIL_TO = "shritir@uci.edu";

    public static final String EOD_QUESTION_ONE_MISSED_DATA = "Was there anything else you wanted to share? (in case there was" +
            " missing data from the day)";
    public static final String EOD_QUESTION_TWO_LIFE_EVENTS = "Did anything good or bad happen today? If yes, please describe";
    public static final String EOD_QUESTION_THREE_DIABETES_EVENTS = "Did anything good or bad happen today? If yes, please describe";
    public static final String EOD_QUESTION_FOUR_DIABETES_EVENTS = "Was there anything hard about managing diabetes today? Please describe";


    public static final String EOD_QUESTIONS_TITLE_1 = "End of Day Diary";
    public static final String EOD_QUESTIONS_TITLE_2 = "Life Events";
    public static final String EOD_QUESTIONS_TITLE_3 = "Diabetes Events";

    public static final int DIABETES_LOG_STREAM_GENERATOR_UPDATE_FREQUENCY_MINUTES = 60;
    public static final int DIABETES_LOG_STREAM_GENERATOR_UPDATE_FREQUENCY_MINUTES_DEBUG = 1;



}


