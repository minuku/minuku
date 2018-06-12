package edu.nctu.minuku_2.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.nctu.minuku_2.R;

/**
 * Created by Lawrence on 2017/4/20.
 */

public class home extends AppCompatActivity {

    final private String LOG_TAG = "home";

    private Context mContext;
    private LayoutInflater mInflater;



    public static TextView recentworktext; //for other class to improve it
    public static Button edituractivity
                  ,calories,distance,stepcount
                  ,move,site;

    public home(){}

    public home(Context mContext){
        this.mContext = mContext;
    }

    public home(LayoutInflater mInflater){
        this.mInflater = mInflater;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


    }

    public void inithome(View v){
        recentworktext = (TextView) v.findViewById(R.id.recentworktext);

        edituractivity = (Button) v.findViewById(R.id.edituractivity);
        calories = (Button) v.findViewById(R.id.calories);
        distance = (Button) v.findViewById(R.id.distance);
        stepcount = (Button) v.findViewById(R.id.stepcount);
        move = (Button) v.findViewById(R.id.move);
        site = (Button) v.findViewById(R.id.site);

        edituractivity.setOnClickListener(editinguractivity);
        move.setOnClickListener(moving);
        site.setOnClickListener(siting);

    }

    private Button.OnClickListener editinguractivity = new Button.OnClickListener() {
        public void onClick(View v) {
            Log.e(LOG_TAG,"edituractivity clicked");


        }
    };
    //to view timer_move
    private Button.OnClickListener moving = new Button.OnClickListener() {
        public void onClick(View v) {
            Log.e(LOG_TAG,"move clicked");

            mContext.startActivity(new Intent(mContext, timer_move.class));

        }
    };
    //to view timer_site
    private Button.OnClickListener siting = new Button.OnClickListener() {
        public void onClick(View v) {
            Log.e(LOG_TAG,"site clicked");

            mContext.startActivity(new Intent(mContext, timer_site.class));

        }
    };
}
