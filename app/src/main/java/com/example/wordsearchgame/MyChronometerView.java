package com.example.wordsearchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MyChronometerView extends ConstraintLayout {
    private Chronometer myChronometer;
    private Button hideButton;
    private long resumeBase;
    private static final String TAG = MyChronometerView.class.getSimpleName();

    public MyChronometerView(Context context) {
        this(context, null);
    }

    public MyChronometerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MyChronometerView(Context context, AttributeSet attributeSet, int defaultStyle) {
        super(context, attributeSet, defaultStyle);
        View view = (View)View.inflate(context, R.layout.chronometer_classic, this);
        init(view);
    }

    public Chronometer getChronometer(){
        return myChronometer;
    }

    private boolean init(View view){
        if(this != null){
            this.myChronometer = (Chronometer) view.findViewById(R.id.chronometer_classic); //??
            this.myChronometer.setVisibility(View.VISIBLE);
            this.myChronometer.setFormat("00:%s");

            this.hideButton = (Button)view.findViewById(R.id.hide_button);
            this.hideButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Resources resources = getResources();
                    if(myChronometer.getVisibility() == View.VISIBLE){
                        myChronometer.setVisibility(View.INVISIBLE);
                        Drawable icon = getResources().getDrawable(R.drawable.show_timer);
                        hideButton.setCompoundDrawables(icon, null, null, null);

                    }else if(myChronometer.getVisibility() == View.INVISIBLE){
                        myChronometer.setVisibility(View.VISIBLE);
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

        this.myChronometer.start();
    }
    public void resume(){
        this.myChronometer.setBase(this.myChronometer.getBase() + (SystemClock.elapsedRealtime() - resumeBase));
        this.myChronometer.start();
    }
    public void pause(){
        resumeBase = SystemClock.elapsedRealtime();
        this.myChronometer.stop();
    }
    public long getNow(){
        long now = 0;
        int length = this.myChronometer.getText().length();
        CharSequence charSeq = this.myChronometer.getText();

        int j=0;
        for(int index = length-1; index >=0; index--){
            if(charSeq.charAt(index) == ':'){
                index--;
            }else{
                int tempTime = 0;
                if(index >= 0){ tempTime = (charSeq.charAt(index--)-(int)('0'));}
                else{ break; }
                if(index >= 0 && charSeq.charAt(index) != ':'){ tempTime += (charSeq.charAt(index--)-(int)('0')) * 10;}
                now += (long) (tempTime * Math.pow(60,j));
                j += 1;
            }
        }
        Log.d("ChronometerNowLength",""+this.myChronometer.getText().length());
        Log.d("ChronometerNowLong",""+now);
        Log.d("ChronometerNowText", ""+charSeq);

        return now;
    }
}
