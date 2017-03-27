package com.example.garrett.square;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import static android.os.SystemClock.elapsedRealtime;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

// Calculation of spherical vertex
public class Sphere {
    float	angleA, angleB;
    float	cos, sin;
    float	r1, r2;
    float	h1, h2;
    float	step = 18.0f;
    float[][] v = new float[32][3];
    ByteBuffer vbb;
    FloatBuffer vBuf;
    float x, y, z;
    float r;
    long duration = 2000;
    long spawnTime, lastUpdate;
    double drawscale;
    Player thePlayer;
    game_thread theGame;
    cubeCanvas canvas;

    Sphere() {
        vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();
        r = 1;
    }

    Sphere(float x_in, float y_in, int z_in, float r_in, Random rand, Player initPlayer, game_thread game, cubeCanvas c) {
        theGame = game;
        thePlayer = initPlayer;
        canvas = c;
        r = r_in;

        if(x_in == 0 && y_in == 0) {
            x = ((float)rand.nextInt(1000)-500)/500;
            y = ((float)rand.nextInt(1000)-500)/500;
            //x = (float) (rand.nextInt(c.getWidth())/c.getWidth() - .5*c.getWidth());
            //y = (float) (rand.nextInt(c.getHeight())/c.getHeight() - .5*c.getHeight());
            z = -3;
        }
        else {
            x = x_in;
            y = y_in;
            z = z_in;
        }

        vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();

        spawnTime = elapsedRealtime();
        lastUpdate = spawnTime;
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        for (angleA = -90.0f; angleA <90.0f; angleA += step) {
            int	n = 0;

            r1 = r*(float)Math.cos(angleA * Math.PI / 180.0);
            r2 = r*(float)Math.cos((angleA + step) * Math.PI / 180.0);
            h1 = r*(float)Math.sin(angleA * Math.PI / 180.0);
            h2 = r*(float)Math.sin((angleA + step) * Math.PI / 180.0);

            // Fixed latitude, 360 degrees rotation to traverse a weft
            for (angleB = 0.0f; angleB <= 360.0f; angleB += step) {

                cos = (float)Math.cos(angleB * Math.PI / 180.0);
                sin = -(float)Math.sin(angleB * Math.PI / 180.0);

                v[n][0] = x+(r2 * cos);
                v[n][1] = y+(h2);
                v[n][2] = z+(r2 * sin);
                v[n + 1][0] = x+(r1 * cos);
                v[n + 1][1] = y+(h1);
                v[n + 1][2] = z+(r1 * sin);

                vBuf.put(v[n]);
                vBuf.put(v[n + 1]);

                n += 2;

                if(n>31){
                    vBuf.position(0);

                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

                    n = 0;
                    angleB -= step;
                }

            }
            vBuf.position(0);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    public boolean updatePosition(long currentTime) {
        if(currentTime == 0) {
            currentTime = elapsedRealtime();
        }
        long lifeTime = currentTime - spawnTime;
        double progress = abs((double)lifeTime/duration);
        if(lifeTime > duration) {
            return true;
        }
        long sinceUpdate = currentTime - lastUpdate;
        lastUpdate = currentTime;
        //double dxPlayer = x - thePlayer.x;
        //double dyPlayer = y - thePlayer.y;
        z = (float) (3*(progress-1));
        //x += (int)((double)sinceUpdate*.002*progress*dxPlayer);
        //y += (int) ((double)sinceUpdate*.002*progress*dyPlayer);

        //drawscale = .01 + .99*pow(progress, 2);
        return false;
    }
}