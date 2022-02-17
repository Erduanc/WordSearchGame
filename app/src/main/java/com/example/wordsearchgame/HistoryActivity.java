package com.example.wordsearchgame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    String gameMode;
    RecordView[] recordViews = new RecordView[10];
    ArrayList<Record> records = new ArrayList<Record>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//        easyInit();
        init(gameMode = "CLASSIC");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        finish();
    }

//    private void easyInit(){
//        ListView listView = findViewById(R.id.history_listview);
//        Record record = new Record(4201, "CLASSIC", 0, new Date());
//        RecordView recordView = new RecordView(this, record, 2, "CLASSIC");
//        listView.addView(recordView);
//    }

    private void init(String gameMode){
        String fileName;
        if(gameMode =="CLASSIC"){
            fileName = "classic_records.txt";
        }else{
            fileName = "chess_record.txt";
        }

        Button btmButton = findViewById(R.id.history_btm_button);
        btmButton.setClickable(false);
        btmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Handler handler = new Handler(getMainLooper()){
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    // 1: cast to records of type ArrayList<Record>:
                    Object returnedObject = msg.obj;
                    if(returnedObject instanceof ArrayList<?>){
                        ArrayList<?> tempArrayList = (ArrayList<?>) returnedObject;
                        for(Object eachObject: tempArrayList){
                            if(eachObject instanceof Record){
                                records.add((Record)eachObject);
                            }
                        }
                    }
//                if(msg.what == 1 && (msg.obj != null) && (msg.obj instanceof ArrayList)){
//                    records = (ArrayList<Record>) msg.obj;
//                    initViews();
//                }
                    // 2: init views according to records:
                    initViews();

                    btmButton.setClickable(true);
                }
            }
        };

        AsyncFileManager fileManager = new AsyncFileManager(this, handler, fileName);
        fileManager.start();
    }

    private void initViews(){
        ListView listView = findViewById(R.id.history_listview);
        RecordAdapter recordAdapter = new RecordAdapter(this, R.id.history_bar, records);
        listView.setAdapter(recordAdapter);
//        for(int i=0; i < records.size() && i < recordViews.length; i++){
//            recordViews[i] = new RecordView(this, records.get(i), (i+1), gameMode);
//            listView.addView(recordViews[i]);
//        }
    }


    public class RecordView extends ConstraintLayout {
        private int rank = 0;
        private String gameMode = "gameMode";
        private Record record;
//        private Handler handler;
//        private ImageView imageView;

//        private String fileName;
//
//        public RecordView(Context context) {
//            this.
//            init();
//        }

        public RecordView(Context context, /*AttributeSet attrs,*/ Record record, int rank, String gameMode/*, Handler handler*/) {
            super(context);
            this.record = record;
            this.rank = rank;
            this.gameMode = gameMode;
            View view = (View)View.inflate(context, R.layout.record_layout, this);
//            this.setId(IdiUtils.generateViewId());
//            this.layoutParams = ViewGroup.LayoutParams(
//
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//
//                    ViewGroup.LayoutParams.MATCH_PARENT
//
//            )
//            ConstraintLayout.LayoutParams layoutParams= new ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            this.setLayoutParams(layoutParams);
//            this.setRight();

            initView(view);
        }

        private void initView(View view){
            Resources resources = getResources();
            Typeface typeface = Typeface.createFromAsset(this.getContext().getAssets(), "font/Viga-Regular.otf");

            // init title mode:
//            ImageView titleModeView = view.findViewById(R.id.title_mode_view);
//            if(gameMode != "CLASSIC"){
//                titleModeView.setImageResource(R.drawable.history_chess_title);
//            }


            int barSourceId = 0; int textColor = Color.BLACK;
            if(rank == 1){
                barSourceId = resources.getIdentifier("history_bar_1","drawable", getPackageName());
            }else if(rank ==2){
                barSourceId = resources.getIdentifier("history_bar_2","drawable", getPackageName());
            }else if(rank ==3){
                barSourceId = resources.getIdentifier("history_bar_3","drawable", getPackageName());
            }else if(rank ==4){
                barSourceId = resources.getIdentifier("history_bar_4","drawable", getPackageName());
            }else{
                barSourceId = resources.getIdentifier("history_bar_5_10","drawable", getPackageName());
                textColor = Color.WHITE;
            }

            // init bar:
            ImageView barView = view.findViewById(R.id.history_bar);
            if(barView != null && barSourceId != 0){
                barView.setImageResource(barSourceId);
            }
            // init rank:
            TextView rankView = view.findViewById(R.id.rank_view);
            rankView.setText(""+rank);
            rankView.setTextColor(textColor);
            rankView.setTypeface(typeface);
            // init score:
            TextView scoreView = view.findViewById(R.id.score_view);
            scoreView.setText(""+record.getScore());
            scoreView.setTextColor(textColor);
            scoreView.setTypeface(typeface);
            // init game mode:
            TextView modeView = view.findViewById(R.id.mode_view);
            modeView.setText(gameMode);
            modeView.setTextColor(textColor);
            modeView.setTypeface(typeface);
            // init difficulty:
            TextView diffiView = view.findViewById(R.id.diffi_view);
            int difficulty = record.getDifficulty();
            if(difficulty == 0){
                diffiView.setText("EASY");
            }else if(difficulty ==1){
                diffiView.setText("MEDIUM");
            }else{
                diffiView.setText("HARD");
            }
            diffiView.setTextColor(textColor);
            diffiView.setTypeface(typeface);
            // init date
            TextView dateView = view.findViewById(R.id.date_view);
            dateView.setText(record.getStringDate());
            dateView.setTextColor(textColor);
            dateView.setTypeface(typeface);

//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(this);

            // imageView: bar:
//            int barId = resources.getIdentifier("history_bar_"+rank, "id",getPackageName());
//            ImageView barView = new ImageView(getContext());
//            barView.setImageResource(barId);
//
//            barView.setId(IdiUtils.generateViewId());
//            barView.setId(R.id.barView);
//            constraintSet.connect(barView.getId(), ConstraintSet.RIGHT, this.getId(), ConstraintSet.RIGHT);
//            constraintSet.connect(barView.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
//            constraintSet.createHorizontalChain();
//
//            barView.post(new Runnable() {
//                @Override
//                public void run() {
//                    barView.getWidth();
//                }
//            });
//
//            this.addView(barView);
            // TO DO: set constraints

//            // rank:
//            TextView rankView = new TextView(getContext());
//            rankView.setText((""+rank));
//            rankView.setTextSize(24);
//            rankView.setTypeface(typeface);
//            rankView.setLeft();
//            this.addView(rankView);
//
//            // score
//            TextView scoreView = new TextView(getContext());
//            scoreView.setText((""+record.getScore()));
//            scoreView.setTextSize(24);
//            scoreView.setTypeface(typeface);
//            this.addView(scoreView);
//
//            // game_mode
//            TextView modeView = new TextView(getContext());
//            modeView.setText(gameMode);
//            modeView.setTextSize(13);
//            modeView.setTypeface(typeface);
//            this.addView(modeView);
//
//            // difficulty
//            TextView diffView = new TextView(getContext());
//            diffView.setText(record.getDifficulty());
//            diffView.setTextSize(13);
//            diffView.setTypeface(typeface);
//            this.addView(diffView);
//
//            // date:
//            TextView dateView = new TextView(getContext());
//            dateView.setText(record.getStringDate());
//            dateView.setTextSize(13);
//            dateView.setTypeface(typeface);
//            this.addView(diffView);


        }

//        private void init(String fileName){
//            Handler handler = new Handler(getMainLooper()){
//                public void handleMessage(Message msg){
//                    if(msg.what == 1 && msg.obj instanceof Record){
//                        record = (Record) msg.obj;
//                        initView();
//                    }
//                }
//            };
//            AsyncFileManager fileManager = new AsyncFileManager(this.getContext(), handler, fileName);
//            fileManager.start();
//        }
    }

    public class RecordAdapter extends ArrayAdapter<Record>{
        public RecordAdapter(@NonNull Context context, int resourceId, @NonNull List<Record> objects) {
            super(context, resourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Record record = getItem(position);
            RecordView recordView = new RecordView(getContext(), record, (position+1), gameMode);
            return recordView;
//            Record record = getItem(position);
//            return recordView;
        }
    }


}