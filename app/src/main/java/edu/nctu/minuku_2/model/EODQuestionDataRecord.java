package edu.nctu.minuku_2.model;

import java.util.Date;
import java.util.HashMap;

import edu.nctu.minuku_2.preferences.ApplicationConstants;
import edu.nctu.minukucore.model.DataRecord;

/**
 * Created by shriti on 10/12/16.
 */

public class EODQuestionDataRecord implements DataRecord {

    public long creationTime;

    public static final String EODQuestionOne = ApplicationConstants.EOD_QUESTION_ONE_MISSED_DATA;
    private String EODAnswerOne = null;

    public static final String EODQuestionTwo = ApplicationConstants.EOD_QUESTION_TWO_LIFE_EVENTS;
    private String EODAnswerTwo = null;

    public static final String EODQuestionThree = ApplicationConstants.EOD_QUESTION_THREE_DIABETES_EVENTS;
    private String EODAnswerThree = null;

    public static final String EODQuestionFour = ApplicationConstants.EOD_QUESTION_FOUR_DIABETES_EVENTS;
    private String EODAnswerFour = null;


    public EODQuestionDataRecord() {

        this.creationTime = new Date().getTime();
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getEODAnswerOne() {
        return EODAnswerOne;
    }

    public String getEODAnswerTwo() {
        return EODAnswerTwo;
    }

    public String getEODAnswerThree() {
        return EODAnswerThree;
    }

    public String getEODAnswerFour() {
        return EODAnswerFour;
    }

    public void setEODAnswerOne(String EODAnswerOne) {
        this.EODAnswerOne = EODAnswerOne;
    }

    public void setEODAnswerTwo(String EODAnswerTwo) {
        this.EODAnswerTwo = EODAnswerTwo;
    }

    public void setEODAnswerThree(String EODAnswerThree) {
        this.EODAnswerThree = EODAnswerThree;
    }

    public void setEODAnswerFour(String EODAnswerFour) {
        this.EODAnswerFour = EODAnswerFour;
    }
}
