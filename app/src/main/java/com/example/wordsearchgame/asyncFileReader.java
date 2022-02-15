package com.example.wordsearchgame;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class asyncFileReader extends Thread{
    private Context myContext;
    int high = 0;
    int scoreNow;
    Handler handler = null;
    public asyncFileReader(Context context, int scoreNow, Handler handler){
        super();
        myContext = context;
        this.scoreNow = scoreNow;
        this.handler = handler;
    }

    @Override
    public void run() {
//        updateRecords();
        getHigh();
        Looper.prepare();
//        Handler handler = new Handler(Looper.myLooper());
        Message message = new Message();
        message.obj = ""+high;
        handler.sendMessage(message);
        Log.d("Message", "threadToMain");
    }

//    private void getHigh(){
//        high = 3333;
//    }

//    private void updateRecords(){
//        ArrayList<Integer> records = new ArrayList<Integer>();
//        InputStream inputStream = myContext.getResources().openRawResource(R.raw.classic_records);
//        InputStreamReader inputStreamReader = null;
//        try {
//            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        //BufferedReader bufferedReader = null;
//
//        // read the file:
//        String input = "";
//        while (true) {
//            try {
//                if ((input = bufferedReader.readLine()) == null) break;
//                int recordNum = Integer.parseInt(input);
//                records.add(recordNum);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // rearrange the order of the records
//        if(scoreNow >= records.get(0) || records.size() == 0){
//            records.add(0, scoreNow);
//        }
//        int size = records.size(); boolean isUpdated = false;
//        for(int i=0; i+1<size; i++){
//            if(scoreNow <= records.get(i) && scoreNow >= records.get(i+1)){
//                records.add(i+1, scoreNow);
//                isUpdated = true;
//            }
//        }
//        if(!isUpdated){
//            records.add(scoreNow);
//        }
//
//        // write to the file:
//        OutputStream outputStream = myContext.getResources().RawResource(R.raw.classic_records);
//        OutputStreamWriter outputStreamWriter = null;
//        try {
//            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//        //BufferedReader bufferedReader = null;
//
//        // read the file:
//        String output = "";
//        while (true) {
//            try {
//                if ((input = bufferedReader.readLine()) == null) break;
//                int recordNum = Integer.parseInt(input);
//                records.add(recordNum);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void getHigh(){
        InputStream inputStream = myContext.getResources().openRawResource(R.raw.classic_records);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //BufferedReader bufferedReader = null;

        int wordNum = 0, lineIndex = 0;
        String input = "";
        while (true) {
            try {
                if ((input = bufferedReader.readLine()) == null) break;
                int recordNum = Integer.parseInt(input);
                if(recordNum > high){
                    high = recordNum;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
