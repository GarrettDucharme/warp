package com.example.garrett.square;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

import static android.os.SystemClock.elapsedRealtime;
import static com.example.garrett.square.R.id.theFrame;
import static java.lang.Math.pow;

/**
 * Created by Garrett on 12/19/2014.
 */
public class game_thread extends Thread {
    private Context context;
    private Player thePlayer;
    private TextView tv;
    private TextView highest;
    private RelativeLayout game_menu;
    private int[] high_score;
    private int level_index;
    private cubeCanvas canv;
    private volatile boolean GameOver = false;
    private HashMap<Integer, Bitmap> imgStore = new HashMap<>();

    game_thread(Context cont, TextView tv1, TextView tv2, Player initPlayer, RelativeLayout menu, int[] high, cubeCanvas c, int initLevel){
        Log.d("start game thread", "entered game_thread");
        context = cont;
        tv = tv1;
        highest = tv2;
        thePlayer = initPlayer;
        game_menu = menu;
        high_score = high;
        canv = c;
        level_index = initLevel;
        imgStore.put(0, BitmapFactory.decodeResource(cont.getResources(), R.drawable.ice_ball ));
        imgStore.put(1, BitmapFactory.decodeResource(cont.getResources(), R.drawable.iron_rock ));
        imgStore.put(2, BitmapFactory.decodeResource(cont.getResources(), R.drawable.cold_planet ));
        imgStore.put(3, BitmapFactory.decodeResource(cont.getResources(), R.drawable.earth ));
        imgStore.put(4, BitmapFactory.decodeResource(cont.getResources(), R.drawable.green_pink_clouds ));
        imgStore.put(5, BitmapFactory.decodeResource(cont.getResources(), R.drawable.planet_b ));
        imgStore.put(6, BitmapFactory.decodeResource(cont.getResources(), R.drawable.sun ));
        imgStore.put(7, BitmapFactory.decodeResource(cont.getResources(), R.drawable.red_giant ));
        imgStore.put(8, BitmapFactory.decodeResource(cont.getResources(), R.drawable.blue_sun ));
        imgStore.put(9, BitmapFactory.decodeResource(cont.getResources(), R.drawable.yellow_sun ));

    }

    //@Override
    public void run() {
        Random r = new Random();
        Obstacle aObstacle;
        int i;
        int score = 0;
        float difficulty = 0;
        long gameTime = elapsedRealtime();
        long lastDraw = elapsedRealtime();
        Level level = new Level();
        long starting_time = elapsedRealtime();

        Log.d("start game thread", "game_thread running");
        GameOver = false;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canv.setVisibility(View.VISIBLE);
            }
        });
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int numJunkIce, numJunkIron;
        int numPlanetCold, numPlanetEarth, numPlanetGreenPink, numPlanetJupiter;
        int numStarBasic, numStarBlue, numStarRed, numStarYellow;
        final int delay = 300;
        double[] rates = new double[10];
        //if rate of 10,
        //value should be 10*period/total
        while(!GameOver && (elapsedRealtime()-starting_time < 120000)){

            if(elapsedRealtime() > gameTime + delay) {
                gameTime = elapsedRealtime();

                for (i = 0; i < 10; i++) {
                    rates[i] = level.spawn_rates[level_index][i][(int) ((gameTime - starting_time) / 10000)];
                }
                numJunkIce = r.nextInt(1+(int) (rates[0]*delay))/500;
                numJunkIron = r.nextInt(1+(int) (rates[1]*delay))/500;
                numPlanetCold = r.nextInt(1+(int) (rates[2]*delay))/500;
                numPlanetEarth = r.nextInt(1+(int) (rates[3]*delay))/500;
                numPlanetGreenPink = r.nextInt(1+(int) (rates[4]*delay))/500;
                numPlanetJupiter = r.nextInt(1+(int) (rates[5]*delay))/500;
                numStarBasic = r.nextInt(1+(int) (rates[6]*delay))/500;
                numStarBlue = r.nextInt(1+(int) (rates[7]*delay))/500;
                numStarRed = r.nextInt(1+(int) (rates[8]*delay))/500;
                numStarYellow = r.nextInt(1+(int) (rates[9]*delay))/500;

                //Junk
                for(i = 0; i < numJunkIce; i++){
                    aObstacle = new JunkIce(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numJunkIron; i++){
                    aObstacle = new JunkIron(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }

                //Planets
                for(i = 0; i < numPlanetCold; i++){
                    aObstacle = new PlanetCold(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numPlanetEarth; i++){
                    aObstacle = new PlanetEarth(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numPlanetGreenPink; i++){
                    aObstacle = new PlanetGreenPink(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numPlanetJupiter; i++){
                    aObstacle = new PlanetJupiter(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }

                //Stars
                for(i = 0; i < numStarBasic; i++){
                    aObstacle = new StarBasic(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numStarRed; i++){
                    aObstacle = new StarRed(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numStarBlue; i++){
                    aObstacle = new StarBlue(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                for(i = 0; i < numStarYellow; i++){
                    aObstacle = new StarYellow(0, 0, r, context, thePlayer, this, canv, imgStore);
                    canv.cubes.addElement(aObstacle);
                }
                if(difficulty < 10000){difficulty = difficulty+10;}
                score += numJunkIce + numJunkIron + numPlanetCold + numPlanetEarth + numPlanetGreenPink
                        + numPlanetJupiter + numStarBasic + numStarBlue + numStarRed + numStarYellow;
                final String text = "Score: " + score;
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(text);                }
                });
            }
            if(elapsedRealtime() > lastDraw + 50) {
                lastDraw = elapsedRealtime();
                canv.updateCubes();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        canv.invalidate();
                    }
                });
            }
        }
        if(score > high_score[0]){
            high_score[0] = score;
            final String text2 = "Highest: " + score;
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    highest.setText(text2);
                }
            });
        }

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                game_menu.setVisibility(View.VISIBLE);
                //restart_game.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setGameOver(boolean val){
        GameOver = val;
    }
}
