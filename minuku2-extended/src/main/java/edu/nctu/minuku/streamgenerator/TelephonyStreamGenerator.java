package edu.nctu.minuku.streamgenerator;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import edu.nctu.minuku.config.Constants;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku.model.DataRecord.TelephonyDataRecord;
import edu.nctu.minuku.stream.TelephonyStream;
import edu.nctu.minukucore.exception.StreamAlreadyExistsException;
import edu.nctu.minukucore.exception.StreamNotFoundException;
import edu.nctu.minukucore.stream.Stream;

import static edu.nctu.minuku.manager.MinukuStreamManager.getInstance;
/**
 * Created by Lucy on 2017/9/6.
 */

public class TelephonyStreamGenerator extends AndroidStreamGenerator<TelephonyDataRecord> {

    private String TAG = "TelephonyStreamGenerator";
    private Stream mStream;
    private TelephonyManager telephonyManager;
    private String carrierName;
    private int LTESignalStrength_dbm;
    private int LTESignalStrength_asu;
    private int GsmSignalStrength;
    private int CdmaSignalStrenth;
    private int CdmaSignalStrenthLevel; // 1, 2, 3, 4
    private int GeneralSignalStrength;
    private boolean isGSM = false;

    public TelephonyStreamGenerator (Context applicationContext) {
        super(applicationContext);
        this.mStream = new TelephonyStream(Constants.DEFAULT_QUEUE_SIZE);
        this.register();
        LTESignalStrength_dbm = -9999;
        LTESignalStrength_asu = -9999;
        GsmSignalStrength = -9999;
        CdmaSignalStrenth = -9999;
        CdmaSignalStrenthLevel = -9999;
        GeneralSignalStrength = -9999;
        isGSM = false;
    }
    @Override
    public void register() {
        Log.d(TAG, "Registring with StreamManage");

        try {
            getInstance().register(mStream, TelephonyDataRecord.class, this);
        } catch (StreamNotFoundException streamNotFoundException) {
            Log.e(TAG, "One of the streams on which" +
                    "RingerDataRecord/RingerStream depends in not found.");
        } catch (StreamAlreadyExistsException streamAlreadyExsistsException) {
            Log.e(TAG, "Another stream which provides" +
                    " TelephonyDataRecord/TelephonyStream is already registered.");
        }
    }

    @Override
    public Stream<TelephonyDataRecord> generateNewStream() {
        return mStream;
    }

    @Override
    public boolean updateStream() {
        return true;
    }

    @Override
    public long getUpdateFrequency() {
        return 1;
    }

    @Override
    public void sendStateChangeEvent() {

    }

    @Override
    public void onStreamRegistration() {

        telephonyManager = (TelephonyManager) mApplicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        carrierName = telephonyManager.getNetworkOperatorName();
        int networktype = telephonyManager.getNetworkType();

        telephonyManager.listen(TelephonyStateListener,PhoneStateListener.LISTEN_CALL_STATE|PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        switch (networktype) {
            case 0: Log.d(TAG, "UNKNOWN");
            case 1: Log.d(TAG, "GPRS");
            case 2: Log.d(TAG, "EDGE");
            case 3: Log.d(TAG, "UMTS");
            case 4: Log.d(TAG, "CDMA");
            case 5: Log.d(TAG, "EVDO_0");
            case 6: Log.d(TAG, "EVDO_A");
            case 7: Log.d(TAG, "1xRTT");
            case 8: Log.d(TAG, "HSDPA");
            case 9: Log.d(TAG, "HSUPA");
            case 10: Log.d(TAG, "HSPA");
            case 11: Log.d(TAG, "IDEN");
            case 12: Log.d(TAG, "EVDO_B");
            case 13: Log.d(TAG, "LTE");
            case 14: Log.d(TAG, "EHRPD");
            case 15: Log.d(TAG, "HSPAP");
            case 16: Log.d(TAG, "GSM");
            case 17: Log.d(TAG, "TD_SCDMA");
            case 18: Log.d(TAG, "IWLAN");
        }

        Log.d(TAG, carrierName);
    }
    private final PhoneStateListener TelephonyStateListener = new PhoneStateListener() {

        public void onCallStateChanged(int state, String incomingNumber) {
            if(state==TelephonyManager.CALL_STATE_RINGING){
                Log.d(TAG, "ringing");
            }
            if(state==TelephonyManager.CALL_STATE_OFFHOOK){
                Log.d(TAG, "answering");
            }
            if(state==TelephonyManager.CALL_STATE_IDLE){
                Log.d(TAG, "idle");
            }
        }
        public void onSignalStrengthsChanged(SignalStrength sStrength) {

            String ssignal = sStrength.toString();
            String[] parts = ssignal.split(" ");

            int dbm;
            int asu;
            //Log.d("parts8", parts[8]) = -1;

            /**If LTE 4G */
            if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE){

                dbm = Integer.parseInt(parts[10]);
                asu = 140 + dbm;
                LTESignalStrength_dbm = dbm;
                LTESignalStrength_asu = asu;
                Log.d(TAG, "LTE Signal strength (dbm)" + String.valueOf(LTESignalStrength_dbm));
                Log.d(TAG, "LTE Signal strength (asu)" + String.valueOf(LTESignalStrength_asu));
            }
            /** Else GSM 3G */
            else if (sStrength.isGsm()) {

                // For GSM Signal Strength: dbm =  (2*ASU)-113.
                if (sStrength.getGsmSignalStrength() != 99) {
                    dbm = -113 + 2 * sStrength.getGsmSignalStrength();
                    GsmSignalStrength = dbm;
                    Log.d(TAG, "GSM Signal strength" + String.valueOf(GsmSignalStrength));
                } else {
                    dbm = sStrength.getGsmSignalStrength();
                    GsmSignalStrength = dbm;
                    Log.d(TAG, "GSM Signal strength"+ String.valueOf(GsmSignalStrength));
                }
            }
            /** CDMA */
            else {
                /**
                 * DBM
                 level 4 >= -75
                 level 3 >= -85
                 level 2 >= -95
                 level 1 >= -100
                 Ecio
                 level 4 >= -90
                 level 3 >= -110
                 level 2 >= -130
                 level 1 >= -150
                 level is the lowest of the two
                 actualLevel = (levelDbm < levelEcio) ? levelDbm : levelEcio;
                 */
                int snr = sStrength.getEvdoSnr();
                int cdmaDbm = sStrength.getCdmaDbm();
                int cdmaEcio = sStrength.getCdmaEcio();

                int levelDbm;
                int levelEcio;

                if (snr == -1) { //if not 3G, use cdmaDBM or cdmaEcio
                    if (cdmaDbm >= -75) levelDbm = 4;
                    else if (cdmaDbm >= -85) levelDbm = 3;
                    else if (cdmaDbm >= -95) levelDbm = 2;
                    else if (cdmaDbm >= -100) levelDbm = 1;
                    else levelDbm = 0;

                    // Ec/Io are in dB*10
                    if (cdmaEcio >= -90) levelEcio = 4;
                    else if (cdmaEcio >= -110) levelEcio = 3;
                    else if (cdmaEcio >= -130) levelEcio = 2;
                    else if (cdmaEcio >= -150) levelEcio = 1;
                    else levelEcio = 0;

                    CdmaSignalStrenthLevel = (levelDbm < levelEcio) ? levelDbm : levelEcio;
                    Log.d(TAG, "Telephony not 3G CDMA strength:" + CdmaSignalStrenthLevel);
                }
                else {  // if 3G, use SNR
                    if (snr == 7 || snr == 8) CdmaSignalStrenthLevel =4;
                    else if (snr == 5 || snr == 6 ) CdmaSignalStrenthLevel =3;
                    else if (snr == 3 || snr == 4) CdmaSignalStrenthLevel = 2;
                    else if (snr ==1 || snr ==2) CdmaSignalStrenthLevel =1;
                    Log.d(TAG, "Telephony 3G CDMA strength:" + CdmaSignalStrenthLevel);
                }
            }
        }
    };

    @Override
    public void offer(TelephonyDataRecord dataRecord) {

    }
}
