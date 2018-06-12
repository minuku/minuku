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

public class timer_site extends AppCompatActivity {

    final private String LOG_TAG = "timer_site";

    ImageButton site_1,site_2,Customize;
    private Button move;

    public timer_site(){}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_site);

        inittimer_site();
    }

    public void inittimer_site(){

        site_1 = (ImageButton) findViewById(R.id.site_1);
        site_2 = (ImageButton) findViewById(R.id.site_2);
        Customize = (ImageButton) findViewById(R.id.customize);

        move = (Button) findViewById(R.id.move);

        move.setOnClickListener(moving);

    }

    //to view timer_move
    private Button.OnClickListener moving = new Button.OnClickListener() {
        public void onClick(View v) {
            Log.e(LOG_TAG,"move clicked");

            //TODO this function will increase in stack, need to be optimized.
            startActivity(new Intent(timer_site.this, timer_move.class));

        }
    };

}
