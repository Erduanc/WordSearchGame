package com.example.wordsearchgame;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class WordsGeneratorTest extends TestCase {
    private Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();
    private WordsGenerator wordsGenerator = new WordsGenerator(0, context);
    private ArrayList<Word> words = new ArrayList<Word>();

    public static final int EASYLISTLEN = 10; // <9*9
    public static final int MEDIUMLISTLEN = 100; // <12*12
    public static final int HARDLISTLEN = 150; // List of words with length <15(game board:15*15)
//    public static final int EASYMATSHAPE[] = {9, 9};
//    public static final int MEDIUMMATSHAPE[] = {12, 12};
//    public static final int HARDMATSHAPE[] = {12, 15};

    @Test
    public void testConstructor(){
        assertEquals(0, wordsGenerator.getDifficulty());
        assertEquals(EASYLISTLEN, wordsGenerator.getWordListLen());
//        assertEquals(EASYMATSHAPE[0], wordsGenerator.getMatShape()[0]);
//        assertEquals(EASYMATSHAPE[0], wordsGenerator.getMatShape()[1]);
    }
    @Test
    public void testGenerateWords(){
        words = wordsGenerator.generateWords();
        int wordNumMax = (wordsGenerator.getDifficulty()) * 2 + 7;
        assertEquals(wordNumMax, words.size());
        assertEquals(7, words.size());

        for(int i=0; i < wordNumMax; i++){
            // Log.v(i+"", words.get(i).wordString);
            System.out.println(words.get(i).wordString);
        }

    }


}