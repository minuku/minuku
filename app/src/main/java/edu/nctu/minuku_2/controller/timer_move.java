package edu.nctu.minuku_2.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import edu.nctu.minuku_2.R;

/**
 * Created by Lawrence on 2017/4/22.
 */

public class timer_move extends AppCompatActivity {

    final private String LOG_TAG = "timer_move";

    ImageButton walk,bike,car;
    private Button site;

    public timer_move(){}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_move);

        inittimer_move();
    }

    public void inittimer_move(){

        walk = (ImageButton) findViewById(R.id.walk);
        bike = (ImageButton) findViewById(R.id.bike);
        car = (ImageButton) findViewById(R.id.car);

        site = (Button) findViewById(R.id.site);

        site.setOnClickListener(siting);
    }

    //to view timer_site
    private Button.OnClickListener siting = new Button.OnClickListener() {
        public void onClick(View v) {
            Log.e(LOG_TAG,"site clicked");

            //TODO this function will increase the screen in stack, need to be optimized.
            startActivity(new Intent(timer_move.this, timer_site.class));

        }
    };
}
