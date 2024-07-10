package org.msurya.entities;

import java.util.Objects;

import static org.msurya.utils.MatchUtils.generateRandom;
import static org.msurya.utils.MatchUtils.generateRandomString;

/**
 * Player Entity
 */
public class Player implements Comparable<Player> {
    private int health;

    private int strength;

    private int attack;

    private String name;

    public Player() {
        this.health = generateRandom(1, 100);
        this.strength = generateRandom(1, 100);
        this.attack = generateRandom(1, 100);
        this.name = generateRandomString(5);
    }

    public Player(int health, int strength, int attack) {
        this.health = health;
        this.strength = strength;
        this.attack = attack;
        this.name = generateRandomString(5);
    }

    public Player(int health, int strength, int attack, String name) {
        this.health = health;
        this.strength = strength;
        this.attack = attack;
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "health=" + health +
                ", strength=" + strength +
                ", attack=" + attack +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return health == player.health && strength == player.strength && attack == player.attack && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, strength, attack, name);
    }

    /**
     * Checks if the player properties are not defined
     * @return boolean of the checks
     */
    public boolean isNull() {
        return this.health == 0 && this.attack == 0 && this.strength == 0 && Objects.equals(this.name, "");
    }

    /**
     *
     * @param o the object to be compared.
     * @return comparison result on basis of health / the product of attack & strength
     */
    @Override
    public int compareTo(Player o) {
        int compareHealth = o.getHealth() - this.getHealth();
        int compareStrength = (o.getAttack() * o.getStrength()) - (this.getAttack() * this.getStrength());
        return compareHealth != 0 ? compareHealth : compareStrength;
    }
}
