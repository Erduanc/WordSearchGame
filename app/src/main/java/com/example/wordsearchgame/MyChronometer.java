package com.example.wordsearchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MyChronometer extends ConstraintLayout {
//    private ViewGroup timerView;
    private Chronometer chronometer;
    private Button hideButton;
    private long resumeBase;
    private static final String TAG = MyChronometer.class.getSimpleName();
    //    private final String mNameSpace = "http://schemas.android.com/apk/res-auto";
//    public MyChronometer(Context context){
//        super(context);
//    }
//
    public MyChronometer(Context context) {
        this(context, null);
    }

    public MyChronometer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MyChronometer(Context context, AttributeSet attributeSet, int defaultStyle) {
        super(context, attributeSet, defaultStyle);
        View view = (View)View.inflate(context, R.layout.chronometer_classic, this);
        init(view);
    }

    private boolean init(View view){
        if(this != null){
            this.chronometer = (Chronometer) view.findViewById(R.id.chronometer_classic); //??
            this.chronometer.setVisibility(View.VISIBLE);
            this.chronometer.setFormat("00:%s");
//            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
//            {
//                @Override
//                public void onChronometerTick(Chronometer chronometer)
//                {
//                    if (SystemClock.elapsedRealtime() - chronometer.getBase() > 60 * 1000)
//                    {
//                        chronometer.stop();
//                        chronometer.start.setEnabled(true);
//                    }
//                }
//            });

            this.hideButton = (Button)view.findViewById(R.id.hide_button);
            this.hideButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Resources resources = getResources();
                    if(chronometer.getVisibility() == View.VISIBLE){
                        chronometer.setVisibility(View.INVISIBLE);
                        Drawable icon = getResources().getDrawable(R.drawable.show_timer);
                        hideButton.setCompoundDrawables(icon, null, null, null);

                    }else if(chronometer.getVisibility() == View.INVISIBLE){
                        chronometer.setVisibility(View.VISIBLE);
                        Drawable icon = getResources().getDrawable(R.drawable.hide_timer);
                        hideButton.setCompoundDrawables(icon, null, null, null);
                    }
                }
            });
            return true;
        }
        return false;
    }

    public void start(){

        this.chronometer.start();
    }
    public void resume(){
        this.chronometer.setBase(this.chronometer.getBase() + (SystemClock.elapsedRealtime() - resumeBase));
        this.chronometer.start();
    }
    public void pause(){
        resumeBase = SystemClock.elapsedRealtime();
        this.chronometer.stop();
    }
//    public void resume(){
//        this.chronometer.setBase();
//    }
}
