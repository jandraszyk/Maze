package com.example.janek.maze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Toast;

public class Game extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = "SurfaceView";
    //Board settings
    private Integer level = 1;
    private Level currLevel;
    private Ball ball;

    //Board
    SurfaceView maze;
    SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint paint;

    private boolean wait = false;

    //Accelerometer settings
    private SensorManager mSensorManager;
    private float mAccelX = 0;
    private float mAccelY = 0;
    private float mAccelZ = 0;

    private float mSensorBuffer = 0;

    private final SensorListener mSensorAccelerometer = new SensorListener() {
        @Override
        public void onSensorChanged(int sensor, float[] values) {
            mAccelX = values[0];
            mAccelY = values[1];
            mAccelZ = values[2];
        }


        @Override
        public void onAccuracyChanged(int sensor, int accuracy) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        //Getting current level number
        Intent received_intent = getIntent();
        level = received_intent.getIntExtra("level",1);
        //Preparing surface to paint current maze on it
        maze = (SurfaceView) findViewById(R.id.sv_level);
        surfaceHolder = maze.getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint();
        paint.setAntiAlias(true);
        //Setting up accelerometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorAccelerometer, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        //Preparing the maze





    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        currLevel = new Level(this, maze);
        currLevel.setLevel(this,level);
        ball = new Ball(maze);
        onDraw(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void onDraw(SurfaceHolder holder) {
        canvas = holder.lockCanvas();
        if(canvas == null) {
            Log.e(TAG,"Cannot draw onto the canvas as it's null");
        } else {
            currLevel.drawLevel(canvas,paint);
            ball.start();
            ball.draw(canvas,paint);
            holder.unlockCanvasAndPost(canvas);

        }
    }

    public void moveBall() {

        if(mAccelX > mSensorBuffer || mAccelX < -mSensorBuffer)
            ball.updateX(mAccelX);
        if(mAccelY > mSensorBuffer || mAccelY < -mSensorBuffer)
            ball.updateY(mAccelY);

        if(currLevel.getWallType(ball.getX(),ball.getY()) == currLevel.wall){
            if(ball.getLives() > 0) {
                ball.setLives(ball.getLives() - 1);
                ball.start();
                wait = true;
            }
            else {
                Toast.makeText(getApplicationContext(),"GAME OVER", Toast.LENGTH_LONG).show();
            }
        }
        else if(currLevel.getWallType(ball.getX(),ball.getY()) == currLevel.exit) {
            Toast.makeText(getApplicationContext(),"CONGRATULATIONS", Toast.LENGTH_LONG).show();

        }
    }

}
