package com.example.janek.maze;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Janek on 29.08.2017.
 */

public class Level {

    private final static int levelColumns = 20;
    private final static int levelRows = 26;
    private  int wallSizeX = 27;
    private int wallSizeY = 25;

    public final static int path = 0;
    public final static int wall = 1;
    public final static int exit = 2;

    private final static int wallColor = Color.BLACK;
    private final static int pathColor = Color.GRAY;
    private final static int exitColor = Color.GREEN;

    private static int [] levelData;
    private Rect rWall = new Rect();
    private int mRow;
    private int mCol;
    private int mX;
    private int mY;

    public Level(Activity activity, int height,int width){
        wallSizeX = width / levelColumns;
        wallSizeY = height / levelRows;
    }

    public void setLevel(Activity activity, int currLevel) {
        String level = "level" + currLevel + ".txt";
        InputStream inputStream = null;

        try {
            levelData = new int [levelColumns * levelRows];
            inputStream= activity.getAssets().open(level);

            for(int i = 0; i < levelData.length; i++) {
                levelData[i] = Character.getNumericValue(inputStream.read());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(inputStream);
        }
    }

    private void closeStream(Closeable stream) {
        if(stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void drawLevel(Canvas canvas, Paint paint) {
        for(Integer i = 0; i< levelData.length; i++) {
            mRow = i / levelColumns;
            mCol = i % levelColumns;

            mX = mCol * wallSizeX;
            mY = mRow * wallSizeY;

            if(levelData[i] == path) {
                rWall.left = mX;
                rWall.top = mY;
                rWall.right = mX + wallSizeX;
                rWall.bottom = mY + wallSizeY;

                paint.setColor(pathColor);
                canvas.drawRect(rWall, paint);
            } else if (levelData[i] == wall) {
                rWall.left = mX;
                rWall.top = mY;
                rWall.right = mX + wallSizeX;
                rWall.bottom = mY + wallSizeY;

                paint.setColor(wallColor);
                canvas.drawRect(rWall, paint);
            } else if (levelData[i] == exit) {
                rWall.left = mX;
                rWall.top = mY;
                rWall.right = mX + wallSizeX;
                rWall.bottom = mY + wallSizeY;

                paint.setColor(exitColor);
                canvas.drawRect(rWall, paint);
            }
        }
    }

    public int getWallType(int x, int y) {
        int mWallCol = x / wallSizeX;
        int mWallRow = y / wallSizeY;

        int mLocation = 0;
        if(mWallRow > 0)
            mLocation = mWallRow * levelColumns;
        mLocation += mWallCol;
        return levelData[mLocation];
    }
}
