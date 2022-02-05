package com.example.wordsearchgame;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Test;

public class WordTest extends TestCase {

    Word word = new Word("HelloWorld");
    Letter startLetter = new Letter('h');
    Letter endLetter = new Letter('D');
    int startRow = 3;
    int startCol = 15;
    int endRow = 4;
    int endCol = 0;

    @Test
    public void testWordString(){
        assertEquals("HelloWorld", word.getWordString());
    }
    @Test
    public void testWordLetters(){
        assertEquals(10, word.getWordLetters().size());
        assertEquals("HelloWorld".length(), word.getWordLetters().size());
        assertEquals(new Letter('H').getCharLetter() , word.getWordLetters().get(0).getCharLetter());
        assertEquals(new Letter('e').getCharLetter() , word.getWordLetters().get(1).getCharLetter());
        assertEquals(new Letter('l').getCharLetter() , word.getWordLetters().get(2).getCharLetter());
        assertEquals(new Letter('l').getCharLetter() , word.getWordLetters().get(3).getCharLetter());
        assertEquals(new Letter('o').getCharLetter() , word.getWordLetters().get(4).getCharLetter());
        assertEquals(new Letter('W').getCharLetter() , word.getWordLetters().get(5).getCharLetter());
        assertEquals(new Letter('o').getCharLetter() , word.getWordLetters().get(6).getCharLetter());
        assertEquals(new Letter('r').getCharLetter() , word.getWordLetters().get(7).getCharLetter());
        assertEquals(new Letter('l').getCharLetter() , word.getWordLetters().get(8).getCharLetter());
        assertEquals(new Letter('d').getCharLetter() , word.getWordLetters().get(9).getCharLetter());
    }
    @Test
    public void testWordStartPos(){
        word.setStartPos(startRow, startCol);
        assertEquals(startRow, word.getStartPos()[0]);
        assertEquals(startCol, word.getStartPos()[1]);
    }
    @Test
    public void testWordStartPosByLetters(){
        word.getWordLetters().get(0).setPos(startRow+1, startCol-1);
        word.setStartPos(word.getWordLetters());
        assertEquals(startRow+1, word.getStartPos()[0]);
        assertEquals(startCol-1, word.getStartPos()[1]);
    }
    @Test
    public void testWordEndPos(){
        word.setEndPos(endRow, endCol);
        assertEquals(endRow, word.getEndPos()[0]);
        assertEquals(endCol, word.getEndPos()[1]);
    }
    @Test
    public void testWordEndPosByLetters(){
        word.getWordLetters().get(word.getWordLetters().size()-1).setPos(endRow-1, endCol+1);
        word.setEndPos(word.getWordLetters());
        assertEquals(endRow-1, word.getEndPos()[0]);
        assertEquals(endCol+1, word.getEndPos()[1]);
    }
    @Test
    public void testWordLen(){
        assertEquals("HelloWorld".length(), word.getWordLen());
    }

    @Test
    public void testEquals(){
        LetterSeries helloSeries = new LetterSeries("HELLO");
        LetterSeries ollehSeries = new LetterSeries("OLLEH");
        LetterSeries lolehSeries = new LetterSeries("LOLEH");
        LetterSeries heySeries = new LetterSeries("HEY");
        Word helloWord = new Word("HELLO");
        Word samehelloWord = new Word("HELLO");
        Word ollehWord = new Word("OLLEH");
        Word heyWord = new Word("HEY");
        Word lolehWord = new Word("LOLEH");

        // Comapre two words:
        // Two references pointing to different objects, but with the same string: return true:
        assertTrue(helloWord.equals(samehelloWord));
        assertTrue(samehelloWord.equals(helloWord));
        // Different length: should return false:
        assertFalse(helloWord.equals(heyWord));
        assertFalse(heyWord.equals(helloWord));
        // Same length, but reversed and randomised order:
        assertFalse(helloWord.equals(ollehWord));
        assertFalse(ollehWord.equals(helloWord));
        assertFalse(helloWord.equals(heyWord));
        assertFalse(heyWord.equals(helloWord));

        // Compare a word and a series:
        // Original and reversed orders, should return true:
        assertTrue(helloWord.equals(helloSeries));
        assertTrue(helloSeries.equals(helloWord));
        assertTrue(helloWord.equals(ollehSeries));
        assertTrue(ollehSeries.equals(helloWord));
        assertTrue(ollehWord.equals(helloSeries));
        assertTrue(helloSeries.equals(ollehWord));
        // Different length:
        assertFalse(helloWord.equals(heySeries));
        assertFalse(heySeries.equals(helloWord));
        // randomised order:
        assertFalse(lolehWord.equals(helloSeries));
        assertFalse(helloSeries.equals(lolehWord));

        // Not even a word: should return false:
        assertFalse(helloWord.equals(new Object()));
    }
}