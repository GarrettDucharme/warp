package com.example.garrett.square;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Garrett on 12/21/2014.
 */
public class over_thread extends Thread {
    private Context context;
    private Player thePlayer;
    private TextView tv;
    private TextView highest;
    private Button start_game;
    private Button restart_game;
    private int[] high_score;
    private volatile boolean GameOver = false;
    over_thread(Context cont, TextView tv1, TextView tv2, Player initPlayer, Button start, Button restart, int[] high){
        context = cont;
        tv = tv1;
        highest = tv2;
        thePlayer = initPlayer;
        start_game = start;
        restart_game = restart;
        high_score = high;
    }

    @Override
    public void run() {
        ((Activity) context).runOnUiThread(new Runnable() {
        @Override
        public void run() {
            restart_game.setVisibility(View.VISIBLE);
            }
        });
    }
}
