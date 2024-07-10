package org.msurya;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.msurya.entities.Match;
import org.msurya.entities.Player;
import org.msurya.exceptions.WrongInputException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    private static String WRONG_INPUT_MSG_1 = "Invalid Input. Please try again.";
    private static String WRONG_INPUT_MSG_2 = "Invalid input received. Responses should be in the range of 1-4";
    @Mock
    private Scanner mockScanner;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testValidGetPlayerDetails() throws Exception {
        String name = "John";
        int strength = 20;
        int attack = 49;
        int health = 80;
        when(mockScanner.nextLine()).thenReturn(String.format("%s %s %s %s", name, strength, attack, health));
        Player player1 = Game.getPlayerDetails(mockScanner);
        assertNotNull(player1);
        assertEquals(name, player1.getName());
        assertEquals(strength, player1.getStrength());
        assertEquals(attack, player1.getAttack());
        assertEquals(health, player1.getHealth());
    }

    @Test
    public void testEmptyInputGetPlayerDetails() {
        when(mockScanner.nextLine()).thenReturn("");
        Exception wrongInputException = assertThrows(WrongInputException.class, () -> {
            Game.getPlayerDetails(mockScanner);
        });
        assertNotNull(wrongInputException);
        assertEquals(WRONG_INPUT_MSG_1, wrongInputException.getMessage());
    }

    @Test
    public void testInvalidInputGetPlayerDetails() {
        when(mockScanner.nextLine()).thenReturn("John 100 80");
        Exception wrongInputException = assertThrows(WrongInputException.class, () -> {
            Game.getPlayerDetails(mockScanner);
        });
        assertNotNull(wrongInputException);
        assertEquals(WRONG_INPUT_MSG_1, wrongInputException.getMessage());
    }

    @Test
    public void testInvalidCreateMatch() {
        Exception wrongInputException = assertThrows(WrongInputException.class, () -> {
            Game.createMatch(mockScanner, 100);
        });
        assertNotNull(wrongInputException);
        assertEquals(WRONG_INPUT_MSG_2, wrongInputException.getMessage());
    }

    @Test
    public void testNullCreateMatch() throws Exception {
        Match newMatch = Game.createMatch(mockScanner, 4);
        assertNull(newMatch);
    }

    @Test
    public void testSimulatedCreateMatch() throws Exception {
        Match newMatch = Game.createMatch(mockScanner, 3);
        assertNotNull(newMatch);
        assertNotNull(newMatch.getPlayer1());
        assertNotNull(newMatch.getPlayer2());
        assertFalse(newMatch.getPlayer1().isNull());
        assertFalse(newMatch.getPlayer2().isNull());
    }

    @Test
    public void testOneInputCreateMatch() throws Exception {
        String name = "Adam";
        int strength = 80;
        int attack = 80;
        int health = 80;
        String inputFormat = String.format("%s %s %s %s", name, strength, attack, health);
        when(mockScanner.nextLine()).thenReturn(inputFormat);
        Match newMatch = Game.createMatch(mockScanner, 1);
        assertNotNull(newMatch);
        assertNotNull(newMatch.getPlayer1());
        assertNotNull(newMatch.getPlayer2());
        assertEquals(name, newMatch.getPlayer1().getName());
        assertEquals(strength, newMatch.getPlayer1().getStrength());
        assertEquals(attack, newMatch.getPlayer1().getAttack());
        assertEquals(health, newMatch.getPlayer1().getHealth());

    }

    @Test
    public void testTwoInputCreateMatch() throws Exception {
        String name = "Adam";
        int strength = 80;
        int attack = 80;
        int health = 80;
        String inputFormat = String.format("%s %s %s %s", name, strength, attack, health);
        when(mockScanner.nextLine()).thenReturn(inputFormat);
        Match newMatch = Game.createMatch(mockScanner, 2);
        assertNotNull(newMatch);
        assertNotNull(newMatch.getPlayer1());
        assertNotNull(newMatch.getPlayer2());
        assertEquals(name, newMatch.getPlayer1().getName());
        assertEquals(strength, newMatch.getPlayer1().getStrength());
        assertEquals(attack, newMatch.getPlayer1().getAttack());
        assertEquals(health, newMatch.getPlayer1().getHealth());
        assertEquals(name, newMatch.getPlayer2().getName());
        assertEquals(strength, newMatch.getPlayer2().getStrength());
        assertEquals(attack, newMatch.getPlayer2().getAttack());
        assertEquals(health, newMatch.getPlayer2().getHealth());
    }

    @Test
    public void testMain(){
        System.setIn(new ByteArrayInputStream("5\n".getBytes()));
        String[] args = new String[]{};
        Game.main(args);
    }

}
