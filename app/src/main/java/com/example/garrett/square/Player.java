package com.example.garrett.square;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Garrett on 12/18/2014.
 */
public class Player extends SurfaceView{
    public volatile boolean pressed = false;
    public volatile double x = 500;
    public volatile double y = 500;
    public volatile int r = 100;
    Paint paint = new Paint();
    SurfaceView sv;
    SurfaceHolder holder;

    public volatile Context context;

    public Player(Context cont){
        super(cont);
        context = cont;
        holder = getHolder();
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        setWillNotDraw(false);
        paint.setColor(Color.argb(255, 255, 157, 0));
        paint.setStyle(Paint.Style.FILL);
    }

    public void setX(int x_in){
        x = x_in;
    }
    public void setY(int y_in){
        y = y_in;
    }

    protected void onDraw(Canvas canvas){
        canvas.drawCircle((int)x, (int)y, r, paint);
    }
}
