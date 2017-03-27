package com.example.garrett.square;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.ImageView;

import com.example.garrett.square.Obstacle;
import com.example.garrett.square.Player;
import com.example.garrett.square.cubeCanvas;
import com.example.garrett.square.game_thread;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Garrett on 9/4/2016.
 */
public class PlanetJupiter extends Obstacle {
    public PlanetJupiter(int xp, int yp, Random r, Context cont, Player initPlayer, game_thread game, cubeCanvas c, HashMap<Integer, Bitmap> resources) {
        super(xp, yp, r, cont, initPlayer, game, c, resources);
        rad = (float) ((c.getWidth()/100)*(c.getHeight()/100)*(r.nextInt(5)+1));
        img_index = 5;
    }
}

