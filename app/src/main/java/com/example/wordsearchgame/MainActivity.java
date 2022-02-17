package com.example.wordsearchgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonClassic = (Button)findViewById(R.id.button_classic);
        buttonClassic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ClassicLevelActivity.class);
                startActivity(intent);
            }
        });

        Button buttonHistory = (Button)findViewById(R.id.button_history);
        buttonHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
            }
        });
    }

    public Context getContext(){
        return this;
    }
    // Generate a Game (original mode)
        /*Select words from file(Easy) randomly*/
        /*Generate coordination for the words:wordLen,startPos,endPos*/
            /*Word Class: Letters(Array of Letters), startPos, endPos, wordLen*/
                /*Letter: Char, Pic*/
        /*Stuff in other positions with random letters to form a 2D array of Letters*/
        /*Map the array to Picture(s)*/

}