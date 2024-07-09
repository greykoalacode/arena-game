package org.msurya.utils;

public class MatchUtils {
    public static int generateRandom(int start, int end){
        return start+(int) Math.floor(((end-start)+1)*Math.random());
    }

    public static String generateRandomString(int length){
        String generated = "";
        for(int i=0; i<length; i++){
            generated += String.valueOf((char) generateRandom('a', 'z'));
        }
        return generated;
    }

    public static int rollDice(){
        int generated = generateRandom(1,6);
        System.out.println(generated);
        return generated;
    }
}
