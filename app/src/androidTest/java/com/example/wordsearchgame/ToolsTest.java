package com.example.wordsearchgame;

import android.util.Log;

import junit.framework.TestCase;

import org.junit.Test;

public class ToolsTest extends TestCase {
    @Test
    public void testIfConsecutive(){
        Tools tools = new Tools();
        Log.d("testIfConsecutive", "testIfConsecutive");

        Letter letter1 = new Letter('a');
        letter1.setPos(0,0);
        tools.getSelectedLetters().add(letter1);

        Letter letter2 = new Letter('b');
        letter2.setPos(0,1);
        tools.getSelectedLetters().add(letter2);

        assertEquals(tools.getSelectedLetters().indexOf(letter2), 1);

        assertTrue(tools.ifConsecutiveToLast(letter2));

    }
}