package com.example.garrett.square;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Garrett on 11/13/2016.
 */

public class PlanetGreenPink extends Obstacle {
    public PlanetGreenPink(int xp, int yp, Random r, Context cont, Player initPlayer, game_thread game, cubeCanvas c, HashMap<Integer, Bitmap> resources) {
        super(xp, yp, r, cont, initPlayer, game, c, resources);
        rad = (float) ((c.getWidth()/100)*(c.getHeight()/100)*(r.nextInt(5)+1));
        img_index = 4;
    }
}
