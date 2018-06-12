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

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku_2.R;
import edu.nctu.minuku_2.event.MoodDataExpectedActionEvent;
import edu.nctu.minuku_2.preferences.ApplicationConstants;
import edu.nctu.minukucore.event.ShowNotificationEvent;
import edu.nctu.minukucore.event.ShowNotificationEventBuilder;

/**
 * Created by neerajkumar on 7/30/16.
 */
public class MoodDataExpectedAction {
    private static final String TAG = "MoodDataExpectedAction";

    public MoodDataExpectedAction() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void handleMoodDataExpectedEvent(MoodDataExpectedActionEvent expectedActionEvent) {
        Log.d(TAG, "Handling mood data expected event");
        EventBus.getDefault().post(
                new ShowNotificationEventBuilder()
                        .setExpirationAction(ShowNotificationEvent.ExpirationAction.DISMISS)
                        .setExpirationTimeSeconds(Constants.MOOD_NOTIFICATION_EXPIRATION_TIME)
                        //.setViewToShow(MoodDataRecordActivity.class)
                        .setIconID(R.drawable.self_reflection)
                        .setTitle(Constants.MOOD_REMINDER_TITLE)
                        .setMessage(Constants.MOOD_REMINDER_MESSAGE)
                        .setCategory(ApplicationConstants.NOTIFICATION_CATEGORY_MOOD_REPORT)
                        .setParams(new HashMap<String, String>())
                        .createShowNotificationEvent());
    }
}
