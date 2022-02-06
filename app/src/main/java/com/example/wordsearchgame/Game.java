package com.example.wordsearchgame;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;


public class Game {
    private ArrayList<Word> words = new ArrayList<Word>();
    private LetterMatrix letterMatrix;
    //private ArrayList<Word> wordsToFind = new ArrayList<Word>();
    // private ArrayList<int[]> Answers = new ArrayList<int[2]>();
    // private int score;
    private Context myContext;
    private Record record;
    private String gameType;
    private int difficulty;


//    private final static int MAXSCORE = 9999;
//    private final static String CLASSIC = "Classic";
//    private final static String CHESS = "Chess";

    // Constructor
    public Game(int difficulty, Context context, String gameType){
        this.myContext = context;
        setGameType(gameType);
        setRecord(gameType);
        setDifficulty(difficulty); //Easy

        // Generate random words and initialise the positions:
        setWords(generateWords(difficulty, context));
//        letterMatrix = new LetterMatrix(myContext, difficulty, words);
//        letterMatrix.generateMatrix();

        setLetterMatrix();
//        // Initialise wordsToFind:
//        wordsToFind = (ArrayList<Word>) words.clone();
    }

    // getters and setters:
    public void setRecord(String gameType){ record = new Record(gameType); }
    public Record getRecord(){
        return this.record;
    }
    public void setGameType(String gameType){ this.gameType = gameType; }
    public String getGameType(){ return gameType; }
    public void setDifficulty(int difficulty){ this.difficulty = difficulty; }
    public int getDifficulty(){ return this.difficulty; }
        // Getter and Setter for Words;
    private void setWords(ArrayList<Word> words){ this.words = words; }
    public ArrayList<Word> getWords(){ return this.words; }

    public ArrayList<Word> generateWords(int difficulty, Context context){
        WordsGenerator wordsGenerator = new WordsGenerator(difficulty, myContext);

        return wordsGenerator.generateWords();
    }

    public void setLetterMatrix(){
        this.letterMatrix = new LetterMatrix(this.myContext, this.difficulty, this.words);
        letterMatrix.generateMatrix();
    }
    public LetterMatrix getLetterMatrix(){ return this.letterMatrix; }

//    public void deleteWord(Word wordToDelete){
//        for(Word eachWord:words){
//            if(eachWord.equals(wordToDelete)){
//                words.remove(eachWord);
//            }
//        }
//    }

    public void printWords(){
        for(Word eachWord: words){
            System.out.println("wordAnswer:"+eachWord.getWordString());
        }
    }


//    public ArrayList<LetterSeries> getInputSeries(){
//
//    }
//
//    public boolean getGameStatus(){
//
//    }
//
//    public int calculateScore(){
//
//    }
//
//    public void gaming(){
//        // while the game isn't over:
//        int wordStatus = 0;
//        while(getGameStatus()){
//            //wait for an input:
//            ArrayList<LetterSeries> chosenSeries = getInputSeries();
//            // Check if there is a match in the series.
//            for(Word eachWord:words){
//                // id there is a match
//                if(chosenSeries.equals(eachWord)){
//                    eachWord.setStatus(++wordStatus); // Update the status of the word
//                    // Update the display of a word?
//                }
//            }
//        }
//        /* when the game is over, calculate score based on the difficulty and consumed time.
//         * Update the score and date then:
//         */
//        getRecord().setScore(calculateScore());
//        getRecord().setDate();
//    }


//    public ArrayList<Word> generateMatrix(int difficulty, Context context){
//        WordsGenerator wordsGenerator = new WordsGenerator(difficulty, myContext);
//
//        Matrix matrix  = new Matrix(difficulty, context, );
//        return wordsGenerator.generateWords();
//    }
}
