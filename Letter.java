package wordgraph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */


public class Letter {
    
    public static boolean isVowel(char c){
        String vowels = "aeiouAEIOU";
        return vowels.contains(Character.toString(c));
    }

    public static boolean isConsonant(char c){
        String cons = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
        return cons.contains(Character.toString(c));
}   
    
}

