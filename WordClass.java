/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgraph;

/**
 *
 * @author David
 * 
 * This is a utility class of classes each representing a part of speech.
 * It is final, cannot be extended and should only be accessed to create objects
 * of the inner classes.
 */
public final class WordClass {

    public WordClass() {}; // the outer class cannot be instantiated
    
    public class Noun {
    
        String noun = null;
        String possesive = null;
        String plural = null;

        public Noun(String n) {
            noun = n;
        }

        public Noun(String possesive, String plural) {
            this.possesive = possesive;
            this.plural = plural;
        }
       
        public String getNoun() {
            return noun;
        }

        public String getPossessive() {
            char[] nounArray = noun.toCharArray();
            int last = nounArray.length;
            if(nounArray[last-1] == 's'){
                possesive = noun + "'";
            }
            else {
                possesive = noun + "s";
            }
            return possesive;
        }

        public String getPlural() {
            return plural;
        }
    
    } //end class Noun
    
    public class Verb {
    
        String verb = null; //base form (to + base = infinitive)

        public Verb(String v) {
            verb = v;
        }

        public String getVerb() {
            return verb;
        }

        public void setVerb(String verb) {
            this.verb = verb;
        }
    }//end inner class Verb
    
    //inner class Pronoun
    public class Pronoun {
        
        String pronoun = null;
        String possesive = null;

        public Pronoun(String p) {
            pronoun = p;
        }

        public String getPronoun() {
            return pronoun;
        }

        public void setPronoun(String pronoun) {
            this.pronoun = pronoun;
        }

        public String getPossesive() {
            return possesive;
        }

        public void setPossesive(String possesive) {
            this.possesive = possesive;
        }
        
    }//end inner class Pronoun

} // end out class WordClass
    
