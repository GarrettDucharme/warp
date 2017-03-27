package com.example.garrett.square;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Garrett on 1/28/2017.
 */

public class JunkIron extends Obstacle {
    public JunkIron(int xp, int yp, Random r, Context cont, Player initPlayer, game_thread game, cubeCanvas c, HashMap<Integer, Bitmap> resources) {
        super(xp, yp, r, cont, initPlayer, game, c, resources);
        rad = (float) (.1*(c.getWidth() / 100) * (c.getHeight() / 100) * (r.nextInt(5) + 5));
        steer = .002;
        duration = 1000;
        img_index = 1;
    }
}