package com.example.janek.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Janek on 30.08.2017.
 */

public class Ball {

    private View currView;
    private int mX = 0;
    private int mY = 0;
    private int mRadius = 0;
    private int mColor = Color.RED;
    private int mLives = 5;

    public Ball(View view) {
        this.currView = view;
        this.mRadius = view.getWidth() / 45;
    }

    public void start(){
        mX = mRadius * 10;
        mY = mRadius * 10;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(mColor);
        canvas.drawCircle(mX, mY, mRadius, paint);
    }

    public void updateY (float newY) {
        mY -= newY;

        if(mY + mRadius >= currView.getHeight())
            mY = currView.getHeight() - mRadius;
        else if(mY - mRadius < 0)
            mY = mRadius;
    }

    public void updateX (float newX) {
        mX += newX;

        if(mX + mRadius >= currView.getWidth())
            mX = currView.getWidth() - mRadius;
        else if(mX - mRadius < 0)
            mX = mRadius;
    }

    public void setLives(int val) {
        mLives = val;
    }

    public int getLives() {
        return mLives;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
}
