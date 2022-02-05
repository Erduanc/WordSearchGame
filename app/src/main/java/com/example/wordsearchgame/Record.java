package com.example.wordsearchgame;

import java.util.Date;

public class Record implements Comparable<Record>{
    private int score;
    private Date date; // How to use this thing? Generated at the end of the game.
    private String gameType;

    private final static int MAXSCORE = 9999;
    private final static String CLASSIC = "Classic";
    private final static String CHESS = "Chess";

    public Record(String gameType){
        setScore(-1);
        setDate();
        setGameType(gameType);
    }

    // getters and setters:
    public int getScore(){ return this.score; }
    public void setScore(int score){
        this.score = -1;
        if(score <= MAXSCORE && score >= 0) { this.score = score; }
    }
    public Date getDate(){ return date; }
    public void setDate(){ date = new Date(); }
    public String getGameType(){ return gameType; }
    public void setGameType(String gameType){ this.gameType = gameType; }

    @Override
    public int compareTo(Record record){
        if(score > record.getScore()){
            return (score - record.getScore());
        }else if(score == record.getScore()){
            return (date.compareTo(record.getDate()));
        }else{
            return (record.getScore() - score);
        }
    }



}
