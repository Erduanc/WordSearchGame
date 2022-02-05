package com.example.wordsearchgame;


import java.util.ArrayList;

public class LetterSeries extends Word {
    public LetterSeries(String series){
        super(series);
    }
    public LetterSeries(ArrayList<Letter> letters){ super(letters); }

//    @Override
//    public int compareTo(LetterSeries letterSeries) {
//
//        return 0;
//    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Word){
            Word word = (Word) object;
            if(this.getWordLen() != word.getWordLen()){ return false; }
            for(int i = 0; i < this.getWordLen() && (this.getWordLen()-1-i) >= 0 && i < word.getWordLen(); i++){
                if(this.getWordLetters().get(i).getCharLetter() != word.getWordLetters().get(i).getCharLetter()
                        && this.getWordLetters().get(this.getWordLen()-1-i).getCharLetter() != word.getWordLetters().get(i).getCharLetter()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
