package com.example.garrett.square;

import com.example.garrett.square.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.R.attr.id;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    public volatile boolean Game_Over = false;
    public volatile Player thePlayer;
    public volatile game_thread aGame;
    public volatile int[] high_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content_controls);

        thePlayer = new Player(this);
        //((FrameLayout) findViewById(R.id.theFrame)).addView(thePlayer);
        ((RelativeLayout) findViewById(R.id.fullscreen_content_controls)).addView(thePlayer);
        thePlayer.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        thePlayer.setTranslationZ(5);
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x;
                int y;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        thePlayer.setX(x);
                        thePlayer.setY(y);
                        thePlayer.invalidate();
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        break; // if you want to handle the touch event
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        //Log.d("moving player:", "{"+Integer.toString(x)+","+Integer.toString(y)+"}");
                        thePlayer.setX(x);
                        thePlayer.setY(y);
                        thePlayer.invalidate();
                        return true;
                }
            return true;
            }
        });
        final Button level_one = (Button) findViewById(R.id.level_one);
        final Button level_two = (Button) findViewById(R.id.level_two);
        final Button level_three = (Button) findViewById(R.id.level_three);
        final RelativeLayout menu = (RelativeLayout) findViewById(R.id.game_menu);
        final RelativeLayout store_menu = (RelativeLayout) findViewById(R.id.store_menu);
        final Button store = (Button) findViewById(R.id.store);
        final Button store_back = (Button) findViewById(R.id.store_back);
        //final Button begin_again = (Button) findViewById(R.id.restart_game);
        final FrameLayout theFrame = (FrameLayout) findViewById(R.id.theFrame);
        //begin_again.setVisibility(View.GONE);
        level_one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                game_thread theGame;
                Context cont;
                TextView tv1;
                TextView tv2;
                cubeCanvas c;
                if(high_score != null){
                    //    temp = high_score[1];
                }
                else {
                    high_score = new int[1];
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return false; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        tv1 = (TextView)findViewById(R.id.score);
                        tv2 = (TextView)findViewById(R.id.highest);
                        cont = FullscreenActivity.this;
                        c = (cubeCanvas)findViewById(R.id.theCanvas);

                        theGame = new game_thread(cont, tv1, tv2, thePlayer, menu, high_score, c, 0);
                        aGame = theGame;
                        theGame.start();
                        menu.setVisibility(View.GONE);
                        theFrame.setBackgroundResource(0);
                        //theFrame.setVisibility(View.INVISIBLE);
                        return true; // if you want to handle the touch event
                }
                return true;
            }
        });
        level_two.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                game_thread theGame;
                Context cont;
                TextView tv1;
                TextView tv2;
                cubeCanvas c;
                if(high_score != null){
                    //    temp = high_score[1];
                }
                else {
                    high_score = new int[1];
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return false; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        tv1 = (TextView)findViewById(R.id.score);
                        tv2 = (TextView)findViewById(R.id.highest);
                        cont = FullscreenActivity.this;
                        c = (cubeCanvas)findViewById(R.id.theCanvas);

                        //theGame = new game_thread(cont, tv1, tv2, thePlayer, menu, begin_again, high_score, c, 1);
                        theGame = new game_thread(cont, tv1, tv2, thePlayer, menu, high_score, c, 1);
                        aGame = theGame;
                        theGame.start();
                        menu.setVisibility(View.GONE);
                        theFrame.setBackgroundResource(0);
                        //theFrame.setVisibility(View.INVISIBLE);
                        return true; // if you want to handle the touch event
                }
                return true;
            }
        });
        level_three.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                game_thread theGame;
                Context cont;
                TextView tv1;
                TextView tv2;
                cubeCanvas c;
                if(high_score != null){
                    //    temp = high_score[1];
                }
                else {
                    high_score = new int[1];
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return false; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        tv1 = (TextView)findViewById(R.id.score);
                        tv2 = (TextView)findViewById(R.id.highest);
                        cont = FullscreenActivity.this;
                        c = (cubeCanvas)findViewById(R.id.theCanvas);

                        theGame = new game_thread(cont, tv1, tv2, thePlayer, menu, high_score, c, 2);
                        aGame = theGame;
                        theGame.start();
                        menu.setVisibility(View.GONE);
                        theFrame.setBackgroundResource(0);
                        //theFrame.setVisibility(View.INVISIBLE);
                        return true; // if you want to handle the touch event
                }
                return true;
            }
        });
        /*begin_again.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                game_thread theGame;
                Context cont;
                TextView tv1;
                TextView tv2;
                cubeCanvas c;
                if(high_score != null){
                    //    temp = high_score[1];
                }
                else {
                    high_score = new int[1];
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return false; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        tv1 = (TextView)findViewById(R.id.score);
                        tv2 = (TextView)findViewById(R.id.highest);
                        cont = FullscreenActivity.this;
                        c = (cubeCanvas)findViewById(R.id.theCanvas);
                        theGame = new game_thread(cont, tv1, tv2, thePlayer, menu, begin_again, high_score, c);
                        aGame = theGame;
                        theGame.start();
                        menu.setVisibility(View.GONE);
                        return true; // if you want to handle the touch event
                }
                return true;
            }
        });*/
        store.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                menu.setVisibility(View.GONE);
                store_menu.setVisibility(View.VISIBLE);
                return true;
            }
        });
        store_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                menu.setVisibility(View.VISIBLE);
                store_menu.setVisibility(View.GONE);
                return true;
            }
        });

                // Set up the user interaction to manually show or hide the system UI.
        /*
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
        */
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    void loadStartMenu() {
        final FrameLayout theFrame = (FrameLayout) findViewById(R.id.theFrame);
        theFrame.setBackgroundResource(R.drawable.main_screen);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
