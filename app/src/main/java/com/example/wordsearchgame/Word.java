package com.example.wordsearchgame;

import java.util.ArrayList;

public class Word{
    /*Word is a data structure of the String type of the word, the letters type of the word,
    * and the coordination info*/
    String wordString; // The word in String form. i.e. "Cat".
    private ArrayList<Letter> wordLetters = new ArrayList<Letter>();
    private int startPos[] = new int[2];
    private int endPos[] = new int[2];
    private int wordLen; // wordLen = (start-end)+1
    private int status; // Any changes on the status of a word should sync to all letters.

    public Word(String word){
        setWordString(word);
        setWordLetters(word);
        setStartPos(0, 0);
        setEndPos(0, 0);
        setWordLen(this.wordLetters.size());
        setStatus(0); // 0: not found, 1,2,..., the times being found.
    }

    public Word(ArrayList<Letter> letters){
        String word = "";
        for(Letter eachLetter: letters){
            word= word+eachLetter.getCharLetter();
        }
        setWordString(word);
        setWordLetters(word);
        setStartPos(0, 0);
        setEndPos(0, 0);
        setWordLen(this.wordLetters.size());
        setStatus(0); // 0: not found, 1,2,..., the times being found.
    }

    public void setWordString(String word){
        this.wordString = word;
    }
    public String getWordString(){
        return this.wordString;
    }
    public void setWordLetters(String word){
        for(int i = 0; i < word.length(); i ++){
            Letter letter = new Letter(word.charAt(i));
            this.wordLetters.add(letter);
        }
    }
    public ArrayList<Letter> getWordLetters(){
        return this.wordLetters;
    }
    /*public void printWord(){
        for(int i = 0; i < this.wordLetters.size(); i ++){
           System.out.print(this.wordLetters.get(i) + ", ");
        }
    }*/
    public void setStartPos(int row, int col){
        this.startPos[0] = row;
        this.startPos[1] = col;
    }
    public void setStartPos(ArrayList<Letter> wordLetters){
        this.startPos[0] = wordLetters.get(0).getPos()[0];
        this.startPos[1] = wordLetters.get(0).getPos()[1];
    }
    public int[] getStartPos(){
        return startPos;
    }
    public void setEndPos(int row, int col){
        this.endPos[0] = row;
        this.endPos[1] = col;
    }
    public void setEndPos(ArrayList<Letter> wordLetters){
        this.endPos[0] = wordLetters.get(wordLetters.size()-1).getPos()[0];
        this.endPos[1] = wordLetters.get(wordLetters.size()-1).getPos()[1];
    }
    public int[] getEndPos(){
        return endPos;
    }
    public void setWordLen(int len){
        this.wordLen = len;
    }
    public int getWordLen(){
        return wordLen;
    }
    public void setStatus(int status){
        this.status = status;
        for(Letter eachLetter:getWordLetters()){
            eachLetter.setStatus(this.status);
        }
    }
    public int getStatus(){ return status; }

    @Override
    public boolean equals(Object object){
        if(object instanceof LetterSeries){
            // if it is to compare a series with word:
            LetterSeries series = (LetterSeries) object;
            if(wordLen != series.getWordLen()){ return false; }
            for(int i = 0; i < wordLen && (series.getWordLen()-1-i) >= 0 && i < series.getWordLen(); i++){
//                if((wordLetters.get(i).getCharLetter() != series.getWordLetters().get(i).getCharLetter() && (char)(wordLetters.get(i).getCharLetter()+32) != series.getWordLetters().get(i).getCharLetter() && (char)(wordLetters.get(i).getCharLetter()-32) != series.getWordLetters().get(i).getCharLetter())
//                        && (series.getWordLetters().get(this.getWordLen()-1-i).getCharLetter() != wordLetters.get(i).getCharLetter() && series.getWordLetters().get(this.getWordLen()-1-i).getCharLetter() != wordLetters.get(i).getCharLetter())){
//                    return false;
//                }
                if(!wordLetters.get(i).caseLessCharEquals(series.getWordLetters().get(i))){
                    return false;
                }
            }
            return true;
        }else if(object instanceof Word && !(object instanceof LetterSeries)){
            // if compare two words:
            Word word = (Word) object;
            if(wordLen != word.getWordLen()){ return false; }
            for(int i = 0; i < wordLen && i < word.getWordLen(); i++){
//                if(wordLetters.get(i).getCharLetter() != word.getWordLetters().get(i).getCharLetter()){
//                    return false;
//                }
                if(!wordLetters.get(i).caseLessCharEquals(word.getWordLetters().get(i))){
                    return false;
                }
            }
            return true;
        }
        // If the parameter is neither a series nor a word:
        return false;
    }

}
