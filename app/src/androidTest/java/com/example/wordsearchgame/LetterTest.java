package com.example.wordsearchgame;

import junit.framework.TestCase;

import org.junit.Test;

public class LetterTest extends TestCase {
    @Test
    public void testEquals(){
        Letter letter1 = new Letter('A');
        Letter letter2 = new Letter('a');
        Letter letter3 = new Letter('A');
        Letter letter4 = new Letter('a');

        assertTrue(letter1.equals(letter2));
        assertTrue(letter2.equals(letter1));
        assertTrue(letter1.equals(letter3));
        assertTrue(letter2.equals(letter4));
    }

}
