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

package edu.nctu.minuku_2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import edu.nctu.minuku.event.DecrementLoadingProcessCountEvent;
import edu.nctu.minuku.event.IncrementLoadingProcessCountEvent;
import edu.nctu.minuku.logger.Log;
import edu.nctu.minuku_2.controller.home;
import edu.nctu.minuku_2.controller.report;
import edu.nctu.minuku_2.manager.InstanceManager;
import edu.nctu.minuku_2.service.BackgroundService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //private TextView compensationMessage;
    private AtomicInteger loadingProcessCount = new AtomicInteger(0);
    private ProgressDialog loadingProgressDialog;

    private int mYear, mMonth, mDay;

    public static String task="PART"; //default is PART
    ArrayList viewList;
    public final int REQUEST_ID_MULTIPLE_PERMISSIONS=1;
    public static View timerview,recordview,deviceIdview;

    public static android.support.design.widget.TabLayout mTabs;
    public static ViewPager mViewPager;

    private TextView device_id;

    private String projName = "mobilecrowdsourcing";

    private int requestCode_setting = 1;


    //private UserSubmissionStats mUserSubmissionStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Creating Main activity");

        //compensationMessage = (TextView) findViewById(R.id.compensation_message);

        //initializeActionList();


        Log.e(TAG,"start");

        setContentView(R.layout.activity_main);

        final LayoutInflater mInflater = getLayoutInflater().from(this);
        timerview = mInflater.inflate(R.layout.home, null);
        recordview = mInflater.inflate(R.layout.record, null);

        initViewPager(timerview,recordview);

        SettingViewPager();

        //** Please set your project name. **//
//        whichView(projName);

        startService(new Intent(getBaseContext(), BackgroundService.class));
        //startService(new Intent(getBaseContext(), MinukuNotificationManager.class));
        //startService(new Intent(getBaseContext(), DiaryNotificationService.class)); /* might be useless for us */

        //UUID dummyUUID = UUID.randomUUID();
        EventBus.getDefault().register(this);

//        Context mContext = this;

        if(!InstanceManager.isInitialized()) {
            InstanceManager.getInstance(getApplicationContext());

            //TODO do we need it?
            //loadingProgressDialog = ProgressDialog.show(MainActivity.this, "Loading data", "Fetching information",true);
        }

        int sdk_int = Build.VERSION.SDK_INT;
        if(sdk_int>=23) {
            checkAndRequestPermissions();
        }else{
            startServiceWork();
        }

    }

    private void whichView(String projName){
        if(projName.equals("mobilecrowdsourcing")){
            setContentView(R.layout.activity_main);

            final LayoutInflater mInflater = getLayoutInflater().from(this);
            timerview = mInflater.inflate(R.layout.home, null);
            recordview = mInflater.inflate(R.layout.record, null);

            initViewPager(timerview,recordview);

            SettingViewPager();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //public for update
    public void initViewPager(View timerview, View recordview){
        mTabs = (android.support.design.widget.TabLayout) findViewById(R.id.tablayout);
        mTabs.addTab(mTabs.newTab().setText("計時"));
        mTabs.addTab(mTabs.newTab().setText("紀錄"));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        timerview.setTag(Constant.home_tag);


    }

    protected void showToast(String aText) {
        Toast.makeText(this, aText, Toast.LENGTH_SHORT).show();
    }

    public void improveMenu(boolean bool){
        Constant.tabpos = bool;
        ActivityCompat.invalidateOptionsMenu(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.findItem(R.id.action_selectdate).setVisible(false);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(projName.equals("Ohio")) //f***ing hardcode
            menu.findItem(R.id.action_report).setVisible(false);

        if(Constant.tabpos)
            menu.findItem(R.id.action_selectdate).setVisible(true);
        else
            menu.findItem(R.id.action_selectdate).setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_report:
                startActivity(new Intent(MainActivity.this, report.class));
                return true;
            case R.id.action_selectdate:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        //startdate.setText(format);
                    }

                }, mYear,mMonth, mDay).show();
                return true;
        }
        return true;
    }

    private void checkAndRequestPermissions() {

        Log.e(TAG,"checkingAndRequestingPermissions");

        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteExternalStorage = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permissionFineLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarseLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionStatus= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();


        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        }else{
            startServiceWork();
        }

    }


    public void getDeviceid(){

        TelephonyManager mngr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        int permissionStatus= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        if(permissionStatus==PackageManager.PERMISSION_GRANTED){
            Constant.DEVICE_ID = mngr.getDeviceId();

            Log.e(TAG,"DEVICE_ID"+Constant.DEVICE_ID+" : "+mngr.getDeviceId());
            if(projName.equals("Ohio")) {
               device_id=(TextView)findViewById(R.id.deviceid);
               device_id.setText("ID = " + Constant.DEVICE_ID);

            }

        }
    }

    public void startServiceWork(){

        getDeviceid();


        //Use service to catch user's log, GPS, activity;
        //TODO Bootcomplete 復原
        //** remember to check this is for what?
/*
        if (!CheckFamiliarOrNotService.isServiceRunning()){
            android.util.Log.d("MainActivity", "[test service running]  going start the probe service isServiceRunning:" + CheckFamiliarOrNotService.isServiceRunning());
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CheckFamiliarOrNotService.class);

            startService(intent);
        }
*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();

                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                //perms.put(Manifest.permission.SYSTEM_ALERT_WINDOW, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.BODY_SENSORS, PackageManager.PERMISSION_GRANTED);



                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED){
                        android.util.Log.d("permission", "[permission test]all permission granted");
                        //permission_ok=1;
                        startServiceWork();
                    } else {
                        Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "/"
                + String.valueOf(monthOfYear + 1) + "/"
                + String.valueOf(dayOfMonth);
    }

    public void SettingViewPager() {
        viewList = new ArrayList<View>();
        viewList.add(timerview);
        viewList.add(recordview);

        mViewPager.setAdapter(new TimerOrRecordPagerAdapter(viewList, this));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        //TODO date button now can show on menu when switch to recordview, but need to determine where to place the date textview(default is today's date).
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(!Constant.tabpos)
                    //show date on menu
                    Constant.tabpos = true;
                else
                    //hide date on menu
                    Constant.tabpos = false;
                invalidateOptionsMenu();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(MainActivity.mViewPager));
    }
/*
    private void showSettingsScreen() {
        //showToast("Clicked settings");
        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
    }
*/
    @Override
    protected void onStart() {
        super.onStart();
    }

/*
    @Subscribe
    public void assertEligibilityAndPopulateCompensationMessage(
            UserSubmissionStats userSubmissionStats) {
        Log.d(TAG, "Attempting to update compesnation message");
        if(userSubmissionStats != null && isEligibleForReward(userSubmissionStats)) {
            Log.d(TAG, "populating the compensation message");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    compensationMessage.setText("You are now eligible for today's reward!");
                    compensationMessage.setVisibility(View.VISIBLE);
                    compensationMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onCheckCreditPressed(v);
                        }
                    });

                }});
        } else {
                compensationMessage.setText("");
                compensationMessage.setVisibility(View.INVISIBLE);
        }
    }
*/

    @Subscribe
    public void incrementLoadingProcessCount(IncrementLoadingProcessCountEvent event) {
        Integer loadingCount = loadingProcessCount.incrementAndGet();
        Log.d(TAG, "Incrementing loading processes count: " + loadingCount);
    }

    @Subscribe
    public void decrementLoadingProcessCountEvent(DecrementLoadingProcessCountEvent event) {
        Integer loadingCount = loadingProcessCount.decrementAndGet();
        Log.d(TAG, "Decrementing loading processes count: " + loadingCount);
        //maybeRemoveProgressDialog(loadingCount);
    }
    // because of loadingProgressDialog
/*
    private void maybeRemoveProgressDialog(Integer loadingCount) {
        if(loadingCount <= 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingProgressDialog.hide();
                }
            });
        }
    }
*/
    /*
    @Subscribe
    public boolean isEligibleForReward(UserSubmissionStats userSubmissionStats) {
        return getRewardRelevantSubmissionCount(userSubmissionStats) >= ApplicationConstants.MIN_REPORTS_TO_GET_REWARD;
    }

    public void onCheckCreditPressed(View view) {
        Intent displayCreditIntent = new Intent(MainActivity.this, DisplayCreditActivity.class);
        startActivity(displayCreditIntent);
    }*/


    public class TimerOrRecordPagerAdapter extends PagerAdapter {
        private List<View> mListViews;
        private Context mContext;

        public TimerOrRecordPagerAdapter(){};

        public TimerOrRecordPagerAdapter(List<View> mListViews,Context mContext) {
            this.mListViews = mListViews;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mListViews.get(position);
            switch (position){
                case 0: //timer
                    home mhome = new home(mContext);
                    mhome.inithome(timerview);

                    break;
                case 1: //report

                    break;
            }


            container.addView(view);

            return view;
        }

        /*
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }
}
