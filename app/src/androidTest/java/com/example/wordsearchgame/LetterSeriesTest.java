package com.example.wordsearchgame;

import android.content.Context;

import junit.framework.TestCase;
import org.junit.Test;

public class LetterSeriesTest extends TestCase{
    @Test
    public void testEquals(){
        LetterSeries helloSeries = new LetterSeries("HELLO");
        LetterSeries ollehSeries = new LetterSeries("OLLEH");
        LetterSeries lolehSeries = new LetterSeries("LOLEH");
        Word helloWord = new Word("HELLO");
        Word heyWord = new Word("HEY");

        // Same length, original and reversed sequence: should return true.
        assertTrue(helloSeries.equals(helloWord));
        assertTrue(ollehSeries.equals(helloWord));
        // Different length, should return false:
        assertFalse(helloSeries.equals(heyWord));
        assertFalse(ollehSeries.equals(heyWord));
        // Same length but random sequence: should return false.
        assertFalse(lolehSeries.equals(helloWord));
        // Not even a word, should return false:
        assertFalse(helloSeries.equals(new Object()));

        // Try to compare two series:
        assertTrue(helloSeries.equals(ollehSeries));
        assertTrue(ollehSeries.equals(helloSeries));
    }
}
