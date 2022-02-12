package com.example.wordsearchgame;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import junit.framework.TestCase;

import org.junit.Test;

public class MyChronometerViewTest extends TestCase {
    private Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();

    @Test
    public void testGetText(){
        MyChronometerView myChronometerView = new MyChronometerView(context);
        CharSequence charSequence = myChronometerView.getChronometer().getText();
        Log.d("charSequence","Length: "+charSequence.length());

        long time = 1000;
        myChronometerView.getChronometer().setBase(SystemClock.elapsedRealtime()-time);
        assertEquals((time/1000), myChronometerView.getNow());

        time = 1000*23;
        myChronometerView.getChronometer().setBase(SystemClock.elapsedRealtime()-time);
        assertEquals((time/1000), myChronometerView.getNow());

        time = 1000*60;
        myChronometerView.getChronometer().setBase(SystemClock.elapsedRealtime()-time);
        assertEquals((time/1000), myChronometerView.getNow());

        time = 1000*60*17;
        myChronometerView.getChronometer().setBase(SystemClock.elapsedRealtime()-time);
        assertEquals((time/1000), myChronometerView.getNow());

        time = 1000*3600;
        myChronometerView.getChronometer().setBase(SystemClock.elapsedRealtime()-time);
        assertEquals((time/1000), myChronometerView.getNow());

        time = 1000*3600*35;
        myChronometerView.getChronometer().setBase(SystemClock.elapsedRealtime()-time);
        assertEquals((time/1000), myChronometerView.getNow());
    }
}
