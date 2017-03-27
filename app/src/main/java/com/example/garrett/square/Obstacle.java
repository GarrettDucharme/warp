package com.example.garrett.square;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Random;

import static android.os.SystemClock.elapsedRealtime;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by Garrett on 11/6/2016.
 */

public class Obstacle {
    public Context context;
    public Player thePlayer;
    public game_thread theGame;
    public double x;
    public double y;
    public cubeCanvas canv;
    public long lastUpdate;
    public long spawnTime;
    public int duration = 2000;
    public double duration_scale = 2;
    public double drawscale = .01;
    public double steer = 0.002;
    public double steer_scale = 1.2;
    public float rad;
    public HashMap<Integer, Bitmap> imgStore;
    public int img_index = 0;

    public Obstacle(int xp, int yp, Random r, Context cont, Player initPlayer, game_thread game, cubeCanvas c, HashMap<Integer, Bitmap> resources) {
        context = cont;
        thePlayer = initPlayer;
        theGame = game;
        imgStore = resources;
        if(xp == 0 && yp == 0) {
            x = r.nextInt(c.getWidth()+1);
            y = r.nextInt(c.getHeight()+1);
        }
        else {
            x = xp;
            y = yp;
        }
        canv = c;
        spawnTime = elapsedRealtime();
        lastUpdate = spawnTime;

        rad = (float) ((c.getWidth()/100)*(c.getHeight()/100)*2*(r.nextInt(5)+1));

    }

    public boolean updatePosition(long currentTime) {
        if(currentTime == 0) {
            currentTime = elapsedRealtime();
        }
        long lifeTime = currentTime - spawnTime;
        double progress = abs((double)lifeTime/(duration*duration_scale));
        if(lifeTime > duration*duration_scale) {
            return true;
        }
        long sinceUpdate = currentTime - lastUpdate;
        lastUpdate = currentTime;
        double dxPlayer = x - thePlayer.x;
        double dyPlayer = y - thePlayer.y;
        x += (int)((double)sinceUpdate*steer*steer_scale*pow(progress*dxPlayer, 1));
        y += (int) ((double)sinceUpdate*steer*steer_scale*pow(progress*dyPlayer,1));
        drawscale = .01 + pow(1.5*progress, 4);
        return false;
    }

    public void testHitPlayer() {
        double dist = (thePlayer.x - x)*(thePlayer.x - x) + (thePlayer.y - y)*(thePlayer.y - y);
        if (dist < (drawscale*.9*rad + thePlayer.r)*(drawscale*.9*rad + thePlayer.r)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    theGame.setGameOver(true);
                }
            });
        }
    }

    public boolean testOffScreen() {
        if ( x + rad*drawscale < 0
                || x - rad*drawscale > canv.getWidth()
                || y + rad*drawscale < 0
                || y - rad*drawscale > canv.getHeight()) {
            return true;
        }
        return false;
    }

    public void onDraw(Canvas canvas) {

        Bitmap bmp = imgStore.get(img_index);

        Rect src = new Rect();
        src.left = 0;
        src.right = bmp.getWidth();
        src.top = 0;
        src.bottom = bmp.getHeight();

        Rect dest = new Rect();
        dest.left = (int) (x - rad*drawscale);
        dest.right = (int) (x + rad*drawscale);
        dest.top = (int) (y - rad*drawscale);
        dest.bottom = (int) (y + rad*drawscale);

        canvas.drawBitmap(bmp, src, dest, null);
    }

}
