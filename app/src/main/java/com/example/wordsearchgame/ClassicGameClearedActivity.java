package com.example.wordsearchgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassicGameClearedActivity extends AppCompatActivity {
    private int difficulty;
    private int hintCount;
    private ArrayList<Long> times = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_game_cleared);

        // get passed values:
        Bundle bundle = getIntent().getExtras();
        difficulty = bundle.getInt("difficulty", 0);
        Log.d("difficulty", ""+difficulty);
        hintCount = bundle.getInt("hintCount", 0);
        Log.d("hintCount", ""+hintCount);
        Serializable serializable = bundle.getSerializable("times");
        times = (ArrayList<Long>) serializable;
        for(Long eachTime:times){
            Log.d("times",""+eachTime);
        }

        // register button
        Button btmButton = findViewById(R.id.clear_btm_button);
        btmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ClassicGameClearedActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // register and change scoreView:
        TextView scoreView = findViewById(R.id.clear_score_textview);
        int scoreNow = calculateScore();
        scoreView.setText("Score: "+scoreNow);
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/Viga-Regular.otf");
        scoreView.setTypeface(typeface);

        // Update highest score

        // register and change highView:
        TextView highView = findViewById(R.id.cleared_high_textview);
        // create handler:
        Handler handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                Log.d("handleMsg", "handleMsg");
                if(msg.what == 0){
                    highView.setText((""+msg.obj));
                }
//                if((msg.obj) instanceof Integer){
//                    Log.d("msg.obj", ""+msg.obj);
//                    highView.setText(""+msg.obj);
//                }
            }
        };

//        handler.post(task);
        // send message to the thread:
//        Message message = new Message();
//        message.obj = "0001";
//        handler.sendMessage(message);

//        Runnable task = new Runnable(){
//            @Override
//            public void run(){
//                Message msg = handler.obtainMessage();
//                msg.obj = "1100";
//                handler.sendMessage(msg);
//            }
//        };
//        handler.post(task);
//        Thread highThread = new Thread(){
//            @Override
//            public void run(){
//                Looper.prepare();
////                Message msg = handler.obtainMessage();
//                Message msg = new Message();
//                msg.obj = "1100";
//                Log.d("message", "threadToMain");
//                handler.sendMessage(msg);
//            }
//        };
//        highThread.start();

        Thread fileReader = new AsyncFileManager(this, scoreNow, difficulty, handler);
        fileReader.start();
        highView.setTypeface(typeface);
    }

    private int calculateScore(){
        int score = 100;
        int baseScore = 100;
        int bonusScore;
        int size = times.size();
        int count = 1;
        for(int i=1; i < size; i++){
            if(times.get(i)-times.get(i-1) <=5){
                count++;
            }else{
                count = 1;
            }
            score += baseScore * count;
            Log.d("score", ""+score);
        }

        switch (difficulty){
            case 0:
                score  = (score * 110)/100;
                break;
            case 1:
                score = (score * 125)/100;
                break;
            case 2:
                score = (score * 155)/100;
                break;
        }
        Log.d("score", ""+score);

        long gameLength = times.get(size-1);
        float scoreCom = (float) (0.01* (float) (gameLength));
        if(scoreCom != 0){
            score = (int) (score /scoreCom);
        }

        score -= hintCount * 50;

        if(score <= 0){
            score = 10;
        }else if(score > 99999){
            score = 99999;
        }
        return score;
    }
}


