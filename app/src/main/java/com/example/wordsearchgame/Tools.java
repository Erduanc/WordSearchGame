package com.example.wordsearchgame;

import android.util.Log;

import java.util.ArrayList;

public class Tools {
    private ArrayList<Letter> selectedLetters = new ArrayList<Letter>();

    public ArrayList<Letter> getSelectedLetters(){
        return selectedLetters;
    }

    public boolean ifConsecutiveToLast(Letter letter){
        int index = selectedLetters.indexOf(letter);
        boolean returnVal = false;

        if(index == -1){
            returnVal = false;
        }else if( index == 0 ){
            returnVal = true;
            //return true;
        }else {
            returnVal = (Math.abs(selectedLetters.get(index).getPos()[0] - selectedLetters.get((index - 1)).getPos()[0]) <= 1
                    && Math.abs(selectedLetters.get(index).getPos()[1] - selectedLetters.get((index - 1)).getPos()[1]) <= 1);
        }
        Log.d("ifConsecutiveToLast", ""+returnVal);
        return returnVal;
    }
}
