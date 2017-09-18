package com.example.janek.maze;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Janek on 04.09.2017.
 */

public class GameView extends View {

    private Activity mActivity;
    private Ball mBall;
    private Level currLevel;

    private Canvas mCanvas;
    private Paint mPaint;
    private boolean mWarning = true;

    private int mCanvasWidth = 0;
    private int mCanvasHeight = 0;

    private int currentLevel = 1;

    private SensorManager mSensorManager;
    private float mAccelX = 0;
    private float mAccelY = 0;

    private float mSensorBuffer = 0;

    private final SensorListener mSensorAccelerometer = new SensorListener() {
        @Override
        public void onSensorChanged(int sensor, float[] values) {
            mAccelX = values[0];
            mAccelY = values[1];
        }


        @Override
        public void onAccuracyChanged(int sensor, int accuracy) {

        }
    };

    public GameView(Context context, Activity activity,int level) {
        super(context);
        currentLevel = level;
        mActivity = activity;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorAccelerometer, SensorManager.SENSOR_ACCELEROMETER,SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvasHeight = h;
        mCanvasWidth = w;
        currLevel = new Level(mActivity,mCanvasHeight,mCanvasWidth);
        currLevel.setLevel(mActivity,currentLevel);
        mBall = new Ball(this);
        mBall.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        mCanvas = canvas;
        mPaint.setColor(Color.WHITE);
        mCanvas.drawRect(0,0,mCanvasWidth,mCanvasHeight,mPaint);
        currLevel.drawLevel(mCanvas,mPaint);
        if(mWarning) {
            mBall.draw(mCanvas,mPaint);
            moveBall();
        }
        invalidate();
    }

    public void moveBall() {

        if(mAccelX > mSensorBuffer || mAccelX < -mSensorBuffer)
            mBall.updateX(mAccelX);
        if(mAccelY > mSensorBuffer || mAccelY < -mSensorBuffer)
            mBall.updateY(mAccelY);

        if(currLevel.getWallType(mBall.getX(),mBall.getY()) == currLevel.wall){
            if(mBall.getLives() > 0) {
                mBall.setLives(mBall.getLives() - 1);
                mBall.start();
                //wait = true;
            }
            else {
                mWarning = false;
                Toast.makeText(mActivity.getApplicationContext(),"GAME OVER", Toast.LENGTH_LONG).show();
            }
        }
        else if(currLevel.getWallType(mBall.getX(),mBall.getY()) == currLevel.exit) {
            mWarning = false;
            DialogFragment newFragment = DialogAlerts.newInstance();
            newFragment.show(mActivity.getFragmentManager(),"Level finished");
            Toast.makeText(mActivity.getApplicationContext(),"CONGRATULATIONS", Toast.LENGTH_LONG).show();

        }
    }
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            DialogFragment newFragment = DialogAlerts.newInstance();
            newFragment.show(mActivity.getFragmentManager(),"Game Paused");
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    */

    public void registerListener() {
        mSensorManager.registerListener(mSensorAccelerometer, SensorManager.SENSOR_ACCELEROMETER,SensorManager.SENSOR_DELAY_GAME);

    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(mSensorAccelerometer);
    }

    public void setmWarning(boolean warn) {
        mWarning = warn;
       // mBall.draw(mCanvas,mPaint);

    }
}
