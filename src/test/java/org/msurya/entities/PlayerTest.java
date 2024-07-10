package org.msurya.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testArePlayersSame() {
        Player player1 = new Player(40, 50, 50,"random");
        Player player2 = new Player(40, 50, 50, "random");
        assertTrue(player1.equals(player2));
    }
    @Test
    public void testIsPlayerNull() {
        Player player1 = new Player(0, 0, 0, "");
        assertTrue(player1.isNull());
    }

    @Test
    public void testIsPlayerAlive() {
        Player player1 = new Player(10, 30, 30, "random");
        assertTrue(player1.isAlive());
    }

    @Test
    public void testIsPlayerCreatedRandomly() {
        Player player1 = new Player();
        assertTrue(player1.getHealth() > 0);
        assertTrue(player1.getAttack() > 0);
        assertTrue(player1.getStrength() > 0);
        assertEquals(5, player1.getName().length());
    }
}
