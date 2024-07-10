package org.msurya.entities;

import org.msurya.exceptions.PlayerNotReadyException;

import static org.msurya.utils.MatchUtils.rollDice;

public class Match {
    private Player player1;
    private Player player2;

    private static int MAX_ROUNDS = 10;

    public Match() {
        this.player1 = new Player();
        this.player2 = new Player();
    }

    /**
     * Constructor - initializes a new Match
     *
     * @param player1
     * @param player2
     */
    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Function for executing each round between two players.
     *
     * @param attacker    player whose attack attribute will be used to hit
     * @param defender    player whose strength attribute will be used to defend
     * @param roundNumber number of rounds maintained for avoiding long runtime
     * @return Player who wins the round / Player having more power if the rounds exceed the limit.
     */
    public Player startRound(Player attacker, Player defender, int roundNumber) {
        if (roundNumber >= MAX_ROUNDS) {
            System.out.println("Match abandoned due to breach of maximum rounds.\nChoosing player with higher health / power...");
            int comparison = attacker.compareTo(defender);
            return comparison > 0 ? defender : (comparison == 0 ? null : attacker);
        } else if (roundNumber == 1) {
            if (this.player1.getHealth() > this.player2.getHealth()) {
                swapPlayers(player1, player2);
            }
        }
        if (!attacker.isAlive()) {
            System.out.println(attacker.getName() + " dies");
            return defender;
        } else if (!defender.isAlive()) {
            System.out.println(defender.getName() + " dies");
            return attacker;
        } else {
            int damage = rollDice() * attacker.getAttack();
            int defense = rollDice() * defender.getStrength();
            System.out.println("Round starts\nAttacker - Health: " + attacker.getHealth() + " Attack: " + damage);
            System.out.println("Defender - Health: " + defender.getHealth() + " Defense: " + defense);
            if (damage > defense) {
                defender.setHealth(defender.getHealth() - (damage - defense));
                System.out.println("Damage caused to Defender: " + (damage - defense));
            }
            System.out.println("Round ends\n");
            return startRound(defender, attacker, roundNumber + 1);
        }
    }

    public Player startMatch() throws Exception {
        if (this.player1 == null || this.player2 == null || this.player1.isNull() || this.player2.isNull()) {
            throw new PlayerNotReadyException("Cannot start a match without both players present.");
        }
        Player winner = new Player(0, 0, 0, "");
        System.out.println("\n"+this);
        while (this.player1.getHealth() > 0 && this.player2.getHealth() > 0 && winner.isNull()) {
            winner = startRound(this.player1, this.player2, 1);
        }
        return winner;
    }

    private void swapPlayers(Player player1, Player player2){
        String tempName = player1.getName();
        int tempAttack = player1.getAttack();
        int tempStrength = player1.getStrength();
        int tempHealth = player1.getHealth();

        player1.setHealth(player2.getHealth());
        player1.setName(player2.getName());
        player1.setAttack(player2.getAttack());
        player1.setStrength(player2.getStrength());

        player2.setName(tempName);
        player2.setAttack(tempAttack);
        player2.setHealth(tempHealth);
        player2.setStrength(tempStrength);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return "Match details\nplayer1=" + player1 +
                "\nplayer2=" + player2 + "\n";
    }
}
