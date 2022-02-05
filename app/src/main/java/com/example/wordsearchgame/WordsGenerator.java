package com.example.wordsearchgame;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WordsGenerator {
    private int difficulty; // difficulty level: 0=>Easy, 1=>Medium, 2=>Hard
    private int wordListLen; // Length of the word list
    private Context myContext;
    //String filePath;
    public static final int EASYLISTLEN = 10; // <9*9
    public static final int MEDIUMLISTLEN = 100; // <12*12
    public static final int HARDLISTLEN = 150; // List of words with length <15(game board:15*15)

    public WordsGenerator(int difficulty, Context context){
        setDifficulty(difficulty);
        switch(difficulty){
            case 0:
                wordListLen = EASYLISTLEN;
                //matShape = EASYMATSHAPE;
                break;
            case 1:
                wordListLen = MEDIUMLISTLEN;
                //matShape = MEDIUMMATSHAPE;
                break;
            case 2:
                wordListLen = HARDLISTLEN;
                //matShape = HARDMATSHAPE;
                break;
            default:
                wordListLen = EASYLISTLEN;
                //matShape = EASYMATSHAPE;
        }
        setMyContext(context);
        //filePath = ""
    }

    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
    public int getDifficulty(){
        return difficulty;
    }
    public int getWordListLen(){
        return wordListLen;
    }
    public void setMyContext(Context context){
        myContext = context;
    }
    public Context getMyContext(){
        return myContext;
    }

//    private Resources getResources() {
//        Resources myResources = null;
//        myResources = getResources();
//        return myResources;
//    }


    /*Select a certain number of words from the list for the current game*/
    public ArrayList<Word> generateWords(){
        int wordNumMax = (difficulty) * 2 + 7;
        // int[] selectedPos = new int[wordNumMax];
        ArrayList<Word> words = new ArrayList<Word>();
        ArrayList<Integer> selectedPos = new ArrayList<Integer>();

        // Generate a set of positions
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        while(selectedPos.size() < wordNumMax){
            int randomPos = random.nextInt(wordListLen);
//            System.out.println("randomPos: "+ randomPos);
            boolean isRedundant = false;
            for(int j = 0; j < selectedPos.size(); j++){
                if(selectedPos.get(j) == randomPos){
                    isRedundant = true;
                    break;
                }
            }
            if(!isRedundant){
               selectedPos.add(randomPos);
            }
        }

        Collections.sort(selectedPos);
        for(int i=0; i < selectedPos.size(); i ++){
            System.out.println("SelectedPos: "+ i +" "+ selectedPos.get(i));
        }

        // Given a set of generated positions, search for the file and get the words at those locations
        String wordAtIndex = "";
        String input = "";
        InputStream inputStream = myContext.getResources().openRawResource(R.raw.easy_words);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //BufferedReader bufferedReader = null;

        int wordNum = 0, lineIndex = 0;
        while(true){
            try {
                if (!((input = bufferedReader.readLine()) != null) || wordNum >=  wordNumMax) break;
                if (lineIndex == selectedPos.get(wordNum)){
                    words.add(new Word(input));
                    wordNum++;
                }
                lineIndex++;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // Generate positions for each word:
        // int wordsSize = words.size();
//        for(int wordNum = 0; wordNum < wordNumMax; wordNum++){
////            System.out.println("wordNum: " + wordNum + " selectedPos: " + selectedPos.get(wordNum));
//            for(int lineIndex = 0; lineIndex < wordListLen; lineIndex++){
//                System.out.println("lineIndex: " + lineIndex + " selectedPos: " + selectedPos.get(wordNum));
//                try {
//                    // System.out.println("bufferedReader.readLine(): " + bufferedReader.readLine());
//                    if ((input = bufferedReader.readLine()) != null){
//                        System.out.println("input: " + input);
//                        if(lineIndex == selectedPos.get(wordNum)) {
//                            // System.out.println("input: " + input);
//                            wordAtIndex = input;
//                            System.out.println("wordAtIndex: " + wordAtIndex);
//                            words.add(new Word(wordAtIndex));
//                            break;
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
////                if(lineIndex == selectedPos.get(wordNum)){
////                    try {
////                        //if (!((input = bufferedReader.readLine()) != null)){}
////                        if (((input = bufferedReader.readLine()) != null)){
////                            System.out.println("input: " + input);
////                            wordAtIndex = input;
////                            words.add(new Word(wordAtIndex));
////                        }
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    break;
////                }
//            }
//        }
//        for(int wordNum = 0; wordNum < wordNumMax; wordNum++){
//            System.out.println("wordNum: " + wordNum + " selectedPos: " + selectedPos.get(wordNum));
//            for(int lineIndex = 0; lineIndex < wordListLen; lineIndex++){
//                System.out.println("lineIndex: " + lineIndex + " selectedPos: " + selectedPos.get(wordNum));
//                if(lineIndex == selectedPos.get(wordNum)){
//                    try {
//                        //if (!((input = bufferedReader.readLine()) != null)){}
//                        if (((input = bufferedReader.readLine()) != null)){
//                            System.out.println("input: " + input);
//                            wordAtIndex = input;
//                            words.add(new Word(wordAtIndex));
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//        for(int lineIndex = 0; lineIndex < wordListLen; lineIndex++){
//            for(int wordNum=0; wordNum < wordNumMax; wordNum++){
//                if(lineIndex == selectedPos[wordNum]){
//                    try {
//                        //if (!((input = bufferedReader.readLine()) != null)){}
//                        if (((input = bufferedReader.readLine()) != null)){
//                            wordAtIndex = input;
//                            words.add(new Word(wordAtIndex));
//                            break;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }

        return words;
    }
}
