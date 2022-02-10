package com.example.wordsearchgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class ClassicLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_level);


        Button buttonMenu = (Button)findViewById(R.id.button_menu);
        buttonMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ClassicLevelActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonEasy = (Button)findViewById(R.id.button_easy);
        buttonEasy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ClassicLevelActivity.this, ClassicEasyActivity.class);
                intent.putExtra("difficulty", 0);
                startActivity(intent);
                finish();
            }
        });
//
//        Button buttonMedium = (Button)findViewById(R.id.button_medium);
//        buttonMedium.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(ClassicLevelActivity.this, ClassicActivity.class);
//                intent.putExtra("difficulty", 1);
//                  startActivity(intent);
//            }
//        });
//
//        Button buttonHard = (Button)findViewById(R.id.button_hard);
//        buttonHard.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(ClassicLevelActivity.this, ClassicActivity.class);
//                intent.putExtra("difficulty", 2);
//        startActivity(intent);
//            }
//        });
    }
}