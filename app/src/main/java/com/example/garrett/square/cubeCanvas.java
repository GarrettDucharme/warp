package com.example.garrett.square;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


/**
 * Created by Garrett on 1/9/2015.
 */
public class cubeCanvas extends SurfaceView {
    Context cont;
    SurfaceHolder holder;

    Vector<Obstacle> cubes = new Vector<>();

    public cubeCanvas(Context context){
        super(context);
        cont = context;
        holder = getHolder();
        setWillNotDraw(false);
    }
    public cubeCanvas(Context context, AttributeSet att){
        super(context, att);
        cont = context;
        holder = getHolder();
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        setWillNotDraw(false);
    }

    public void updateCubes(){
        for(int i = 0; i < cubes.size(); ++i )
        {
            if(cubes.elementAt(i).updatePosition(0)) {
                cubes.elementAt(i).testHitPlayer();
                cubes.remove(i);
                i--;
            }
            else if (cubes.elementAt(i).testOffScreen()) {
                cubes.remove(i);
                i--;
            }
        }
    }

    class NearComparator implements Comparator<Obstacle> {
        public int compare(Obstacle a, Obstacle b) {
            return (int) ((b.spawnTime + b.duration) - (a.spawnTime + a.duration));
        }
    }

    protected void onDraw(Canvas canvas){
        synchronized (cubes) {
            Collections.sort(cubes, new NearComparator());
            for(int i = 0; i < cubes.size(); ++i ) {
                cubes.elementAt(i).onDraw(canvas);
            }
        }
    }

}
