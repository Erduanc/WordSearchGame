package com.example.wordsearchgame;

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class LetterMatrixTest extends TestCase{
    private Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();
    // private ArrayList<Word> words = new ArrayList<Word>();
    private WordsGenerator wordsGenerator = new WordsGenerator(0, context);
    private ArrayList<Word> words = wordsGenerator.generateWords();


    LetterMatrix letterMat = new LetterMatrix(context,0, words);

    public static final int[] EASYMATSHAPE = {9, 9};
    public static final int[] MEDIUMMATSHAPE = {12, 12};
    public static final int[] HARDMATSHAPE = {12, 15};

    @Test
    public void testConstructor(){
        assertEquals(context, letterMat.getMyContext());
        assertEquals(EASYMATSHAPE[0], letterMat.getMatShape()[0]);
        assertEquals(EASYMATSHAPE[1], letterMat.getMatShape()[1]);

        for(int i=0; i < letterMat.getMatShape()[0]; i++){
            for(int j=0; j < letterMat.getMatShape()[1]; j++){
                assertEquals('@', letterMat.getMat()[i][j].getCharLetter());
            }
        }
    }


    @Test
    public void testTryPlaceLetter(){
        Letter letter = new Letter('A');

        // The first time when place 'A' at (0,0):
        letterMat.tryPlaceLetter(letter, 0, 0);
        assertTrue(letterMat.getIfSuccessful());
        // Try place 'A' at the edge of the matrix:
        letterMat.tryPlaceLetter(letter, letterMat.getMatShape()[0]-1, 0);
        assertTrue(letterMat.getIfSuccessful());
        letterMat.tryPlaceLetter(letter, 0, letterMat.getMatShape()[1]-1);
        assertTrue(letterMat.getIfSuccessful());
        letterMat.tryPlaceLetter(letter, letterMat.getMatShape()[0]-1, letterMat.getMatShape()[1]-1);
        assertTrue(letterMat.getIfSuccessful());
        // Try place 'A' out of the matrix:
        letterMat.tryPlaceLetter(letter, letterMat.getMatShape()[0], 0);
        assertFalse(letterMat.getIfSuccessful());
        letterMat.tryPlaceLetter(letter, 0, letterMat.getMatShape()[1]);
        assertFalse(letterMat.getIfSuccessful());
        letterMat.tryPlaceLetter(letter, -1, 0);
        assertFalse(letterMat.getIfSuccessful());
        letterMat.tryPlaceLetter(letter, 0, -1);
        assertFalse(letterMat.getIfSuccessful());

        // Try place 'A' again at (0,0) BUT this needs to be done after the A is placed.
        // Letter letter1 = new Letter('A');
        letterMat.placeLetterAt('A', 0, 0);
        letterMat.tryPlaceLetter(letter, 0, 0);
        assertTrue(letterMat.getIfSuccessful());
        // Try place 'A' again at (1,0) when (1,0) is occupied by 'C'.
        letterMat.placeLetterAt('C', 1,0);
        letterMat.tryPlaceLetter(letter, 1, 0);
        assertFalse(letterMat.getIfSuccessful());

    }
    @Test
    public void testTryAllDirections(){
        Word hello1 = new Word("HELLO");
        // Try to place "Hello" at (0,0)
        letterMat.tryAllDirections(hello1, 0, 0);
        assertTrue(letterMat.getIfSuccessful());
        // position of hello is successfully updated! Checked!
//        System.out.println("-----Positions: ");
//        for(int i=0; i<hello.getWordLen(); i++){
//            System.out.println("i: "+hello.getWordLetters().get(i).getPos()[0]+", "+hello.getWordLetters().get(i).getPos()[1]);
//
//        }
        Word hello2 = new Word("HELLO");
        letterMat.tryAllDirections(hello2, 0, 8);
        assertTrue(letterMat.getIfSuccessful());
        // position of hello is successfully updated! Checked!
        System.out.println("-----Positions: ");
        for(int i=0; i<hello2.getWordLen(); i++){
            System.out.println("i: "+hello2.getWordLetters().get(i).getPos()[0]+", "+hello2.getWordLetters().get(i).getPos()[1]);

        }
        // assertEquals(true, letterMat.tryAllDirections(hello, 0, 4));
        // assertEquals(false, letterMat.tryAllDirections(hello, 0, 5));
    }

    @Test
    public void testTryAllPositions(){
        Word hello = new Word("HELLO");

        letterMat.tryAllPositions(hello);
        assertTrue(letterMat.getIfSuccessful());

        //position of hello is successfully updated! Checked!
//        System.out.println("-----Positions: ");
//        for(int i=0; i<hello.getWordLen(); i++){
//            System.out.println("i: "+hello.getWordLetters().get(i).getPos()[0]+", "+hello.getWordLetters().get(i).getPos()[1]);
//
//        }
//        for(int i=0; i < letterMat.getMatShape()[0]; i++){
//            for(int j=0; j < letterMat.getMatShape()[1]; j++){
//                assertEquals('&', letterMat.getMat()[i][j].getCharLetter());
//            }
//        }

    }

    @Test
    public void testUpdateMatrix(){
        Word Hi = new Word("HI");
        letterMat.tryAllPositions(Hi);

        System.out.println("before:");
        letterMat.printMatrix();
        letterMat.updateMatrix(Hi);
        System.out.println("after:");
        letterMat.printMatrix();
    }

    @Test
    public void testTryAllWords(){
        System.out.println("before:");
        letterMat.printMatrix();
        assertTrue(letterMat.tryAllWords());
        System.out.println("after:");
        letterMat.printMatrix();
    }

    @Test
    public void testFillInTheBlanks(){
        System.out.println("before:");
        letterMat.printMatrix();
        letterMat.fillInTheBlanks();
        System.out.println("after:");
        letterMat.printMatrix();
    }
    @Test
    public void testgenerateMatrix(){
        System.out.println("before:");
        letterMat.printMatrix();
        letterMat.generateMatrix();
        System.out.println("after:");
        letterMat.printMatrix();
    }




}
