package com.example.janek.maze;

import android.app.Activity;
import android.app.DialogFragment;
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

public class Game extends Activity{



    private GameView mView;
    private Integer level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Getting current level number
        Intent received_intent = getIntent();
        level = received_intent.getIntExtra("level",1);
        mView = new GameView(getApplicationContext(),this,level);
        mView.setFocusable(true);
        setContentView(mView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.registerListener();
    }
    @Override
    public void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        mView.unregisterListener();
    }
    @Override
    public void onBackPressed() {
        DialogFragment newFragment = DialogAlerts.newInstance();
        newFragment.show(getFragmentManager(),"Game Paused");
        mView.setmWarning(false);
    }

}
