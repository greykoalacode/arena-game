package org.msurya;

import org.msurya.entities.Match;
import org.msurya.entities.Player;
import org.msurya.exceptions.WrongInputException;

import java.util.List;
import java.util.Scanner;

public class Game {
    public static void setup() {
        System.out.println("Welcome to Arena");
        System.out.println("Choose your options \n1. Play game with computer\n2. Play with friend\n3. Simulate game\n4. Exit\n");
    }

    public static Match createMatch(Scanner sc, int input) throws Exception {
        if (input == 1) {
            Player player1 = getPlayerDetails(sc);
            return new Match(player1, new Player());
        } else if (input == 2) {
            Player player1 = getPlayerDetails(sc);
            Player player2 = getPlayerDetails(sc);
            return new Match(player1, player2);
        } else if (input == 3) {
            return new Match(new Player(), new Player());
        } else if (input == 4) {
            return null;
        } else throw new WrongInputException("Invalid input received. Responses should be in the range of 1-4");
    }

    public static Player getPlayerDetails(Scanner sc) throws WrongInputException {
        System.out.println("Each Player has following values: Name, Strength, Attack & Health.");
        System.out.println("Enter Player Details in the format: <name> <strength> <attack> <health>\nEg: dwight 80 50 65");
        String nextLine = sc.nextLine().trim();
        if (nextLine.length() == 0 || nextLine.split(" ").length < 4)
            throw new WrongInputException("Invalid Input. Please try again.");
        List<String> values = List.of(nextLine.split(" "));
        String name = values.get(0);
        int strength = Integer.parseInt(values.get(1));
        int attack = Integer.parseInt(values.get(2));
        int health = Integer.parseInt(values.get(3));
        return new Player(health, strength, attack, name);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            int response = 0;
            do {
                setup();
                response = sc.nextInt();
                sc.nextLine();
                Match newMatch = createMatch(sc, response);
                if (newMatch != null) {
                    Player winner = newMatch.startMatch();
                    if (winner != null) {
                        System.out.println("Winner is: " + winner.getName() + "\n");
                    } else {
                        System.out.println("It is a tie between " + newMatch.getPlayer1().getName() + " & " + newMatch.getPlayer2().getName() + "\n");
                    }
                }
            } while (response != 4);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
    }
}