package org.msurya.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MatchUtilsTest {
    @Test
    public void testGenerateRandomNumber() {
        int start = 1000;
        int end = 20000;
        int generated = MatchUtils.generateRandom(start,end);
        assertTrue(generated >= start);
        assertTrue(generated <= end);

        // Case 2: start > end
        int generated2 = MatchUtils.generateRandom(5,3);
        assertEquals(-1, generated2);

        // Case 3: start == end
        int generated3 = MatchUtils.generateRandom(4,4);
        assertEquals(4, generated3);
    }

    @Test
    public void testRollDice(){
        int diceRoll = MatchUtils.rollDice();
        assertTrue(diceRoll >= 1);
        assertTrue(diceRoll <= 6);
    }

    @Test
    public void testGenerateRandomString(){
        int length1 = 5;
        String name1 = MatchUtils.generateRandomString(length1);
        assertNotNull(name1);
        assertEquals(length1, name1.length());
    }
}
