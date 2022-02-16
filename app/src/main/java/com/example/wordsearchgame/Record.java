package com.example.wordsearchgame;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record implements Comparable<Record>{
    private int score;
    private Date date; // How to use this thing? Generated at the end of the game.
    private String stringDate;
    private String gameType;
    private int difficulty;

    private final static int MAXSCORE = 99999;
    private final static String CLASSIC = "Classic";
    private final static String CHESS = "Chess";

    public Record(String gameType){
        setScore(-1);
        setDate();
        setGameType(gameType);
    }
    public Record(int score, String gameType, int difficulty, Date date){
        setScore(score);
        setDate(date);
        setDifficulty(difficulty);
        setGameType(gameType);
    }

    // getters and setters:
    public int getScore(){ return this.score; }
    public void setScore(int score){
        this.score = -1;
        if(score <= MAXSCORE && score >= 0) { this.score = score; }
    }
    public Date getDate(){ return date; }
    public void setDate(){
        Date date = new Date();
        setDate(date);
    }
    public void setDate(Date date){
        this.date = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        this.stringDate = dateFormat.format(date);
    }
    public String getStringDate(){
        return this.stringDate;
    }
    public String getGameType(){ return gameType; }
    public void setGameType(String gameType){ this.gameType = gameType; }
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
    public int getDifficulty(){
        return this.difficulty;
    }

    @Override
    public int compareTo(Record record){
        if(score > record.getScore() || score < record.getScore()){
            Log.d("Record", "compareTo: score: "+(score - record.getScore()));
            return (score - record.getScore());
        }else{
            Log.d("Record", "compareTo: date: "+(date.compareTo(record.getDate())));
            return (date.compareTo(record.getDate()));
        }
    }

    @Override
    public String toString(){
        return ""+this.getScore()+"\t"+this.getGameType()+"\t"+this.getDifficulty()+"\t"+this.getStringDate()+"\n";
    }


}
