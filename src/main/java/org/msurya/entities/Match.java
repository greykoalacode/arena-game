package org.msurya.entities;

import static org.msurya.utils.MatchUtils.rollDice;

public class Match {
    Player player1;
    Player player2;

    public Match() {
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    private Player startRound(Player attacker, Player defender){
        if(!attacker.isAlive()){
            return defender;
        } else if(!defender.isAlive()){
            return attacker;
        } else {
            int damage = rollDice()*attacker.getAttack();
            int defense = rollDice()*defender.getStrength();
            System.out.println("Round starts\nAttacker - Health: "+attacker.getHealth()+" Attack: "+damage);
            System.out.println("Defender - Health: "+defender.getHealth()+" Defense: "+defense);
            if(damage > defense){
                defender.setHealth(defender.getHealth() - (damage-defense));
                System.out.println("Damage caused to Defender: "+(damage-defense));
            }
            System.out.println("Round ends\n");
            return startRound(defender, attacker);
        }
    }

    public void startMatch() {
        if(this.player1.isNull() || this.player2.isNull()) {
            System.out.println("Cannot start a match without both players present.");
            return;
        }
        Player winner = new Player(0,0,0,"");
        System.out.println("Player Details: \n"+player1+"\n"+player2+"\n");
        while(this.player1.getHealth() > 0 && this.player2.getHealth() > 0 && winner.isNull()){
            if(this.player1.getHealth() < this.player2.getHealth()){
                winner = startRound(this.player1, this.player2);
            } else {
                winner = startRound(this.player2, this.player1);
            }
        }
        if(!winner.isNull()){
            System.out.println("Winner is: "+winner.getName());
        }

    }


}
