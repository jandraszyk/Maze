package com.example.janek.maze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelection extends Activity {

    public int level = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level_selection);

        final Button bt_level1 = (Button) findViewById(R.id.bt_level1);
        final Button bt_level2 = (Button) findViewById(R.id.bt_level2);
        final Button bt_level3 = (Button) findViewById(R.id.bt_level3);
        final Button bt_level4 = (Button) findViewById(R.id.bt_level4);
        bt_level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                Intent newGame = new Intent(LevelSelection.this,Game.class);
                newGame.putExtra("level", level);
                startActivity(newGame);

            }
        });

        bt_level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 2;
                Intent newGame = new Intent(LevelSelection.this,Game.class);
                newGame.putExtra("level", level);
                startActivity(newGame);
            }
        });

        bt_level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 3;
                Intent newGame = new Intent(LevelSelection.this,Game.class);
                newGame.putExtra("level", level);
                startActivity(newGame);
            }
        });

        bt_level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 4;
                Intent newGame = new Intent(LevelSelection.this,Game.class);
                newGame.putExtra("level", level);
                startActivity(newGame);
            }
        });

    }




}
