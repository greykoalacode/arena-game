package org.msurya.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.msurya.exceptions.PlayerNotReadyException;
import org.msurya.utils.MatchUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MatchTest {

    private MockedStatic<MatchUtils> mockMatchUtils;

    @BeforeEach
    public void setup() {
        mockMatchUtils = mockStatic(MatchUtils.class);
    }

    @AfterEach
    public void close() {
        mockMatchUtils.close();
    }


    @Test
    public void testIsMatchCreated() {

        // create match with no arguments
        Match newMatch = new Match();
        assertNotNull(newMatch.getPlayer1());
        assertFalse(newMatch.getPlayer1().isNull());
        assertNotNull(newMatch.getPlayer2());
        assertFalse(newMatch.getPlayer2().isNull());

        // create match with 2 arguments
        Player player1 = new Player();
        Player player2 = new Player();
        Match match2 = new Match(player1, player2);
        assertNotNull(match2.getPlayer1());
        assertFalse(match2.getPlayer1().isNull());
        assertNotNull(match2.getPlayer2());
        assertFalse(match2.getPlayer2().isNull());
    }

    @Test
    public void testHasMatchEnded() {
        Player player = spy(new Player());

        // when one of the player is null
        Player player1 = new Player();
        Match match1 = new Match(player1, null);
        Exception playersNotReadyException1 = assertThrows(PlayerNotReadyException.class, match1::startMatch);
        String message = "Cannot start a match without both players present.";
        assertNotNull(playersNotReadyException1);
        assertEquals(message, playersNotReadyException1.getMessage());

        // when one of the player has empty attributes
        Match match2 = new Match(player, new Player());
        Exception playersNotReadyException2 = assertThrows(PlayerNotReadyException.class, () -> {
            when(player.isNull()).thenReturn(true);
            match2.startMatch();
        });
        assertNotNull(playersNotReadyException2);
        assertEquals(message, playersNotReadyException2.getMessage());
    }

    @Test
    public void testEachRoundResults() {
        // Case 1: Defender wins since Attacker dies
        Player player1 = new Player(4, 7, 5, "first");
        Player player2 = new Player(5, 4, 6, "second");
        Match match1 = new Match(player1, player2);
        mockMatchUtils.when(MatchUtils::rollDice).thenReturn(5);
        Player winner1 = match1.startRound(player1, player2, 1);
        assertEquals("first", winner1.getName());

        // Case 2: Attacker wins since defender dies
        Player player3 = new Player(4, 4, 5, "third");
        Player player4 = new Player(0, 5, 6, "fourth");
        Match match2 = new Match(player1, player2);
        Player winner2 = match2.startRound(player3, player4, 1);
        assertEquals("third", winner2.getName());
    }

    @Test
    public void testExceededRoundMatchResults() {

        // Case 1: Choose winner based on health
        Player player1 = new Player(4, 6, 8, "first");
        Player player2 = new Player(5, 8, 5, "second");
        Match match1 = new Match(player1, player2);
        mockMatchUtils.when(MatchUtils::rollDice).thenReturn(1);
        Player winner1 = match1.startRound(player1, player2, 1);
        // expect - second to win because of greater health
        assertEquals("second", winner1.getName());

        // Case 2: Choose winner based on power (attack * strength)
        Player player3 = new Player(5, 6, 8, "first");
        Player player4 = new Player(5, 8, 5, "second");
        Match match2 = new Match(player3, player4);
        mockMatchUtils.when(MatchUtils::rollDice).thenReturn(1);
        Player winner2 = match2.startRound(player3, player4, 1);
        // expect - first to win because of greater power, i.e. 48 > 40
        assertEquals("first", winner2.getName());

        // Case 2: Choose first player as winner if all attributes are same
        Player player5 = new Player(5, 8, 8, "first");
        Player player6 = new Player(5, 8, 8, "second");
        Match match3 = new Match(player5, player6);
        mockMatchUtils.when(MatchUtils::rollDice).thenReturn(1);
        Player winner3 = match3.startRound(player5, player6, 1);
        // expect - null since it is a tie
        assertNull(winner3);
    }

    @Test
    public void testMatchResults() {
        try {

            Player player1 = new Player(40, 50, 45, "first");
            Player player2 = new Player(50, 60, 60, "second");

            // Assert the number of rounds
            Match match1 = new Match(player1, player2);
            Match spyMatch = spy(match1);
            mockMatchUtils.when(MatchUtils::rollDice).thenReturn(3);
            Player winner2 = spyMatch.startMatch();
            verify(spyMatch, times(5)).startRound(Mockito.any(Player.class), Mockito.any(Player.class), Mockito.anyInt());
            assertEquals(player2.getName(), winner2.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
