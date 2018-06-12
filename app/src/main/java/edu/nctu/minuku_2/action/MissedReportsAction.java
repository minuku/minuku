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

package edu.nctu.minuku_2.action;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.logger.Log;
//import edu.nctu.minuku_2.PromptMissedDataQuesttionaireActivity;
//import edu.nctu.minuku_2.QuestionnaireActivity;
import edu.nctu.minuku_2.R;
import edu.nctu.minuku_2.event.MissedReportsActionEvent;
import edu.nctu.minuku_2.preferences.ApplicationConstants;
import edu.nctu.minuku_2.question.QuestionConfig;
import edu.nctu.minukucore.event.ShowNotificationEvent;
import edu.nctu.minukucore.event.ShowNotificationEventBuilder;

/**
 * Created by shriti on 8/12/16.
 */
public class MissedReportsAction {

    private String TAG = "MissedReportsAction";

    public MissedReportsAction() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void handleMissedReportsAction(MissedReportsActionEvent missedReportsEvent) {
        Log.d(TAG, "Handling no data event for diabetes log data record");
        Map<String, String> dataSentToQuestinnaireActivity = new HashMap<>();
        dataSentToQuestinnaireActivity.put(Constants.BUNDLE_KEY_FOR_QUESTIONNAIRE_ID,
                String.valueOf(QuestionConfig.missedReportQuestionnaire_2.getID()));
        dataSentToQuestinnaireActivity.put(Constants.BUNDLE_KEY_FOR_NOTIFICATION_SOURCE,
                Constants.DIABETES_LOG_NOTIFICATION_SOURCE);

        EventBus.getDefault().post(
                new ShowNotificationEventBuilder()
                        .setExpirationAction(ShowNotificationEvent.ExpirationAction.DISMISS)
                        .setExpirationTimeSeconds(Constants.MISSED_REPORT_NOTIFICATION_EXPIRATION_TIME)
                        //.setViewToShow(PromptMissedDataQuesttionaireActivity.class)
                        .setIconID(R.drawable.self_reflection)
                        .setTitle(Constants.MISSED_ACTIVITY_DATA_PROMPT_TITLE)
                        .setCategory(ApplicationConstants.NOTIFICATION_CATEGORY_MISSED_ACTIVITY)
                        .setMessage(Constants.MISSED_ACTIVITY_DATA_PROMPT_MESSAGE)
                        .setParams(dataSentToQuestinnaireActivity)
                        .createShowNotificationEvent());
    }
}