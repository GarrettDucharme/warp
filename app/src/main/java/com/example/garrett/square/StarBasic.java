package com.example.garrett.square;

/**
 * Created by Garrett on 9/4/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Random;

import static android.os.SystemClock.elapsedRealtime;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by Garrett on 12/18/2014.
 */
public class StarBasic extends Obstacle{
    public StarBasic(int xp, int yp, Random r, Context cont, Player initPlayer, game_thread game, cubeCanvas c, HashMap<Integer, Bitmap> resources) {
        super(xp, yp, r, cont, initPlayer, game, c, resources);
        rad = (float) ((c.getWidth()/100)*(c.getHeight()/100)*2*(r.nextInt(5)+1));
        steer = 0.001;
        duration = 4000;
        img_index = 6;
    }
}

