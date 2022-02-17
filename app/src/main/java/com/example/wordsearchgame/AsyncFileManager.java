package com.example.wordsearchgame;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AsyncFileManager extends Thread{
    private Context myContext;
    private int high = 0;
    private int scoreNow;
    private int difficulty = 0;

    private static final int TOPNUM = 10;
    private String FILENAME = "classic_records.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private short task = 0;

    private Handler handler = null; // This handler should be the same as in main thread.
    public AsyncFileManager(Context context, int scoreNow, int difficulty, Handler handler){
        super();
        myContext = context;
        this.scoreNow = scoreNow;
        this.difficulty = difficulty;
        this.handler = handler;
        this.task = 0;
    }
    public AsyncFileManager(Context context, Handler handler, String fileName){
        super();
        myContext = context;
        this.handler = handler;
        this.FILENAME = fileName;
        this.task = 1;
    }

    @Override
    public void run() {
//        deleteFile(FILENAME);
        if(task==0){
            try {
                updateRecords();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Looper.prepare();
            Message message = new Message();
            message.what = 0;
            message.obj = ""+high;
            handler.sendMessage(message);
            Log.d("Message", "0: threadToMain");
        }else if(task == 1){
            try {
                ArrayList<Record> records = makeArrayContent(readFileContent(FILENAME));
                if(records != null){
                    Looper.prepare();
                    Message message = new Message();
                    message.what = 1;
                    message.obj = records;
                    handler.sendMessage(message);
                    Log.d("Message", "1: threadToMain");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateRecords() throws ParseException {
        // write data to file:
        boolean isUpdated = false;
        Date date = new Date();
////        String dateString = dateFormat.format(date);
//        Log.d("recordNowDate", "date: "+date);
//        writeToFile(FILENAME, "append", ""+scoreNow+"\t"+"classic"+"\t"+difficulty+"\t"+dateString+"\n");
        writeToFile(FILENAME, "append", null);

        // read file to arrayList form of Record type instances:
        ArrayList<Record> records = makeArrayContent(readFileContent(FILENAME));

        // rearrange the order of the records
        Record recordNow = new Record(scoreNow,"classic",  difficulty, date);
        if(records.size() == 0 || recordNow.compareTo(records.get(0)) >= 0){
            Log.d("updateRecord", recordNow.getScore()+": "+"Top");
            records.add(0, recordNow);
            isUpdated = true;
        }
        int size = records.size();
        for(int i = 0; !isUpdated && i+1<size; i++){
            if(recordNow.compareTo(records.get(i)) <= 0 && recordNow.compareTo(records.get(i+1)) >= 0){
                Log.d("updateRecord",recordNow.getScore()+": "+(i+1));
                records.add(i+1, recordNow);
                isUpdated = true;
            }
        }
        if(!isUpdated){
            Log.d("updateRecord", recordNow.getScore()+": "+"last");
            records.add(size, recordNow);
        }

        for(Record eachRecord:records){
            Log.d("updateRecords","eachRecord: "+ eachRecord.toString());
        }

         // writeToFile:
        for(int i = 0; i < TOPNUM && i < records.size(); i++){
            if(i==0){
                // use private mode so it can cover the old records (instead of using append mode).
                high = records.get(i).getScore();
                writeToFile(FILENAME, "private", records.get(i).toString());
            }else{
                // then append to the end of last record.
                writeToFile(FILENAME, "append", records.get(i).toString());
            }
        }

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
    }

    private ArrayList<Record> makeArrayContent(ArrayList<String> lines) throws ParseException {
        Log.d("makeArrayContent", "in method");
        ArrayList<Record> arrayRecord = new ArrayList<Record>();

        for(String eachLine:lines){
            /* int score \t String gameType \t int difficulty \t Date date \n */
            int enterIndex = eachLine.indexOf("\n");
            int lastIndex = 0; int nextIndex = eachLine.indexOf("\t");

            int score = -1; String gameType = null; int difficulty = -1; Date date = null;
            if(nextIndex != -1 && nextIndex > lastIndex && nextIndex != enterIndex){
                score = Integer.parseInt(eachLine.substring(lastIndex, nextIndex));
                Log.d("makeArrayContent", "score: "+score);
            }
            lastIndex = nextIndex+1; nextIndex = eachLine.indexOf("\t", lastIndex);
            if(nextIndex != -1 && nextIndex > lastIndex && nextIndex != enterIndex){
                gameType = eachLine.substring(lastIndex, nextIndex);
                Log.d("makeArrayContent", "gameType: "+gameType);
            }
            lastIndex = nextIndex+1; nextIndex = eachLine.indexOf("\t", lastIndex);
            if(nextIndex != -1 && nextIndex > lastIndex && nextIndex != enterIndex){
                difficulty = Integer.parseInt(eachLine.substring(lastIndex, nextIndex));
                Log.d("makeArrayContent", "difficulty: "+difficulty);
            }
            lastIndex = nextIndex+1; nextIndex = eachLine.indexOf("\n", lastIndex);
            if(nextIndex != -1 && nextIndex > lastIndex && nextIndex == enterIndex){
                String stringDate = eachLine.substring(lastIndex, nextIndex);
//                String string = "2016-10-24 21:59:06";
                date = dateFormat.parse(stringDate);
                Log.d("makeArrayContent", "date: "+date);
            }
            // add to arrayList
            if(score != -1 && gameType != null && difficulty != -1 && date != null){
                Record record  = new Record(score, gameType, difficulty, date);
                arrayRecord.add(record);
                Log.d("makeArrayContent", "arrayListElement: "+record.toString());
            }else{
                Log.e("makeArrayContent", "failed to make arrayList records");
                return null;
            }
        }

//        stringRecord.substring(0,3);
//        stringRecord.indexOf("\t", 0);
//        // split by "\t"
//        ArrayList<String> eachLine = new ArrayList<String>();
//        int lastIndex = 0; int currentIndex = stringRecord.indexOf("\n");
//        while(currentIndex > lastIndex){
//
//        }

        return arrayRecord;
    }

    private void getHigh(){


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
//        int wordNum = 0, lineIndex = 0;
//        String input = "";
//        while (true) {
//            try {
//                if ((input = bufferedReader.readLine()) == null) break;
//                int recordNum = Integer.parseInt(input);
//                if(recordNum > high){
//                    high = recordNum;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private ArrayList<String> readFileContent(String fileName){
        Log.d("readContent", "in method");
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
//        StringBuilder content = new StringBuilder();
        ArrayList<String> lines = new ArrayList<String>();
        try{
            inputStream = myContext.openFileInput(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while((line = bufferedReader.readLine())!=null){
                Log.d("readContent", "line:" + line);
                lines.add(line+"\n");
//                content.append(line);
//                content.append("\n"); // cuz readLine will remove \n and \r automatically.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(lines == null){
            Log.d("readFileContent","lines: null");
        }else{
            for(String eachLine: lines){
                Log.d("readFileContent", "lines"+ eachLine);
            }
        }
        return lines;
    }

    private void writeToFile(String fileName, String mode, String data){
        FileOutputStream outputStream=null;
        BufferedWriter bufferedWriter=null;
        try{
            if(mode == "private"){
                outputStream = myContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            }else{
                outputStream = myContext.openFileOutput(fileName, Context.MODE_APPEND);
            }

            bufferedWriter =new BufferedWriter(new OutputStreamWriter(outputStream));
            if(data == null){
                return;
            }else{
                bufferedWriter.write(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedWriter!=null){
                    bufferedWriter.close();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void deleteFile(String fileName){
        if(myContext.deleteFile(fileName)) {
            Log.d("deleteFile", "delete: "+ fileName + " successfully");
        } else {
            Log.d("deleteFile", "delete: " + fileName +"failed");
        }
    }
}
