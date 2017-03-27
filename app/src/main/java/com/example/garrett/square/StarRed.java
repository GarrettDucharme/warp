package com.example.garrett.square;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Garrett on 1/28/2017.
 */

public class StarRed extends Obstacle{
    public StarRed(int xp, int yp, Random r, Context cont, Player initPlayer, game_thread game, cubeCanvas c, HashMap<Integer, Bitmap> resources) {
        super(xp, yp, r, cont, initPlayer, game, c, resources);
        rad = (float) ((c.getWidth()/100)*(c.getHeight()/100)*2*(r.nextInt(5)+1));
        steer = 0.001;
        duration = 4000;
        img_index = 8;
    }
}