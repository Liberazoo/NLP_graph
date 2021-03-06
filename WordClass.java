/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map; 
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
/**
 *
 * @author David
 * 
 * This is a utility class of classes each representing a part of speech.
 * It is final, cannot be extended and should only be accessed to create objects
 * of the inner classes.
 */
public final class WordClass  {

    public WordClass() {}; // the outer class cannot be instantiated
    
    
    
    public class Noun  {
        
        /* map of irregular nouns */
        ArrayList<Map.Entry<String, String>> irregularPlurals = 
            new ArrayList<Map.Entry<String, String>>();
        
        String noun = null;
        String possessive = null;
        String plural = null;
        
        /*  Boolean or string fields (with their corresponding methods below)
            be necessary here for cases/declensions required by verb alternations. 
            See note in inner verb class below. E.g., the dative alternation
            may require only nouns (and pronouns?) that function as datives.
        */

        public Noun(String n) throws IOException, ClassNotFoundException{
            if(n != null && !n.isEmpty())
            noun = n.toLowerCase();
            setPlural();
        }
        public Noun(String singular, String plural)throws IOException, ClassNotFoundException {
            if(singular != null && !singular.isEmpty())
            noun = singular.toLowerCase();
            
            
            if(plural != null && !plural.isEmpty())
                plural = plural.toLowerCase();
            else setPlural();
        }
        
        public Noun(String singular, String plural, String possessive) throws IOException, ClassNotFoundException {
            if(singular != null && !singular.isEmpty())
            noun = singular.toLowerCase();
            
            if(plural != null && !plural.isEmpty())
                plural = plural.toLowerCase();
            else setPlural();
            
            if(possessive != null && !possessive.isEmpty())
            possessive = possessive.toLowerCase();
        }
       
        public String getNoun() {
            return noun;
        }

        public String getPossessive() {
            return possessive;
        }
        
        public void setPossessive() {
            char[] nounArray = noun.toCharArray();
            int last = nounArray.length;
            if(nounArray[last-1] == 's'){
                possessive = noun + "'";
            }
            else {
                possessive = noun + "s";
            }
        }

        public String getPlural() {
            return plural;
        }

        public void setPlural() throws IOException, ClassNotFoundException {
            
            
            //add iregular plurals as an abstract map
            //location of irregular noun plurals file
            String fileName = "C:\\Users\\David\\Documents\\nlp_project\\NLP Data Files\\irregularNounPlurals.txt";
      
            String line,word1,word2;
        
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);


            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            //read from file until end of file is reached
            while((line = bufferedReader.readLine()) != null) {
                if(!line.isEmpty())
                {
                    String[] field = line.split(",");
                    word1 = field[0].trim();
                    word2 = field[1].trim();           
                    irregularPlurals.add(new AbstractMap.SimpleEntry(word1,word2));
                }
            }
            /*
            for(Map.Entry<String,String> m : irregularPlurals){
                System.out.println(m.getKey() + " " + m.getValue());
            }*/
            
            /* create the plural form from the noun stem */
           char[] nounArray = noun.toCharArray();
            int last = nounArray.length;
            //ends in sh or ch
            if((nounArray[last-1] == 's') || (nounArray[last-1] == 'c') &&
                    (nounArray[last-2] == 'h')) {
                plural = noun + "es";
            }
            //ends in s, x or z
            else if ((nounArray[last-1] == 's') || (nounArray[last-1] == 'x') &&
                    (nounArray[last-2] == 'z')){
                plural = noun + "es";
            }
            //ends in y preceded by consonant
            else if((nounArray[last-1] == 'y') &&
                    (Letter.isConsonant(nounArray[last-2]))){
                nounArray = Arrays.copyOfRange(nounArray,0,last-2);
                plural = noun + "ies";
            }
            //ends in y preceded by vowel
            else if((nounArray[last-1] == 'y') &&
                    (Letter.isVowel(nounArray[last-2]))){
                plural = noun + "s";
            }
            //ends in f
            else if(nounArray[last-1] == 'f'){
                nounArray = Arrays.copyOfRange(nounArray,0,last-2);
                plural = noun + "ves";
            }
            else if((nounArray[last-1] == 'e') &&
                    (nounArray[last-2] == 'f')){
                nounArray = Arrays.copyOfRange(nounArray,0,last-3);
                plural = noun + "ves";
            }
            /* if noun is found in irregular map */
            else if (contains()) {
                plural = getValue(noun);
            }
            
            //otherwise noun is regular, add s
            else{
                plural = noun + "s";
            }
                              
            
        }
        
        /* return true if arraylist irregularPlurals contains a particular
        map key which is equal to the current noun
        */
        public Boolean contains(){
            for(Map.Entry<String,String> m : irregularPlurals){
                if (m.getKey().equals(noun)) return true;
            }
            return false;
        }
        
        /* return given value in irregularPlurals for a partculat map key */
        public String getValue(String key){
            for(Map.Entry<String,String> m : irregularPlurals){
                if (m.getKey().equals(key)) return m.getValue();
            }
            return null;
        }
        
    
    } //end class Noun
    
    public class Verb {
    
        String verb = null; //base form (to + base = infinitive)
        String alternation = null; //refers to the verb alternation
        String verbClass = null; //refers to the class of verbs that the
                                 //verb's alternation belongs to
        
        /*  See https://www.wikiwand.com/en/Alternation_(linguistics)
            for general definition of linguistic alternation  
        
            According to Pinker in "The Stuff of Thought," verb alternations
            are rules that transform "not the arrangement of the phrases in a
            construction, but something more abstract, namely, the framing
            of events that goes into its meaning" (p.43) It may be that
            using verb and other alternations, along with other algorithms
            in the final MIM (Multidimensional Idea Model) package, will be 
            enough to mimic meaning from syntax and semantics. If that is not 
            the case see note below
        */
        
        /*  Other string or boolean fields will go here to handle other ways
            of categorizes verbs linguistically that may become necessary, such
            as transitive, instranstive, those that take a direct object, those
            that take an oblique object (requiring a preposition, etc.)
            
             It also may be necessary to add other linguistic categories such
             as the roles the other parts of the sentence play, such as actor,
             agent, theta-role, etc. according to linguistic theories other 
             than Pinker's.
        */

        public Verb(String v) {
            verb = v;
        }

        public String getVerb() {
            return verb;
        }

        public void setVerb(String verb) {
            this.verb = verb;
        }

        public String getVerbClass() {
            return verbClass;
        }

        public void setVerbClass(String verbClass) {
            this.verbClass = verbClass;
        }
        
        

        /*
            The verb alternation and verb_class field will take the form of
            three integers which correspond to Beth Levin's
            classification in English Verb Classes and Alternations: A 
            Preliminary Investigation. 
        
            The alternation field corresponds to Levin's number classification
            1-8, and the verb_class field corresponds to 9-57
        
            The three integers will be separated by periods, e.g. "40.1.2"
            which correspond to the overall class and its first and second
            subdivision. When a verb belongs to one of Levin's classes
            that does not have a second subdivision, such as "3.5", it will 
            be represented here with a zero in place of the missing integer. 
        
            It may be necessary to create a file of the names of these classes
            which could then be read into the MIM as a set of relationship/edges
            between vertices, e.g. if such a verb took the "Dative Alternation"
            (2.1 in Levin and 2.1.0 in our representation) then this verb
            would point to the vertex for the words "Dative" and "Alternation"
            using an edge/relationship labeled "Alternation". 
        */
        public String getAlternation() {
            return alternation;
        }

        public void setAlternation(String alternation) {
            this.alternation = alternation;
        }
        
        
        
        /*  we may need a method to break the alternation field into the three
            separate integers here, as well as a method to break the verb
            class field into three integers. 
        */
        
        
    }//end inner class Verb
    
    //inner class Pronoun
    /*
        There are two possibilities for storing pronouns discussed below. The first version
        will store all forms of the pronouns as String, the second version will store the base form 
        as a String and case/number/possession as Booleans. These are described in more detail below,
        and as of yet, the situation of the possessive endings (the single apostrophe and the apostrophe 's)
        which have their own part of speech tags in the PennTree system is not handled by the class.
        it may turn out that it will not be necessary to have those two morphemes stored in this class,
        so they have not been added yet. The PennTree tag for possessive ending is "POS".
    */ 
    
    /*  IMPORTANT NOTE: IT MAY BE THAT OTHER WORD CLASSES WOULD BE BETTER OFF STORING ALL FORMS
        (cases,declensions,or whatever the names of those different forms would be) as a base form
        with Boolean variables for the other forms (with the other forms accessible in an external list), 
        or storing all functionally different forms within the word/idea object as Strings.
        Only the pronoun class contains two versions of the code at present.
    
        Here are some considerations for deciding which method of storage (all forms in object, 
        or base-form only with other forms stored externally to the eventual WordGraph):
        1)  Consistency of class code? (Meaning that all word classes will store their data either in-object,
            or base-form with external list.)
        2)  Closed word classes (like pronouns where no new pronouns are likely to be added)
            could easily hold all the data internal to the object, but open word classes like
            verbs which would have a lot of irregular possibilities (Sing, sang, sung; Go, went, gone)
            would probably benefit from storing only the base form in-object and keeping the 
            other forms externally. 
        3)  There are probably lots of related issues like the relationship between verbs and their 
            participles, what happens when a verb functions as a noun and vice versa, etc. As those are 
            found to be pertinent,  they will be added to this comment.
       
    */
    public class Pronoun {
        
        //
        /*  if it turns out to be best to store each pronoun and the various forms
            as a single java object, then each functional version of the pronoun
            (object,subject,possessive, plural,singular) can be stored as a string.
            This may be disadvantageous when looking up a word from a text and locating
            it's word graph correlate, if it is not the base form. For example, if the
            word found in the text being parsed is "we", an additional Java method may need to 
            be invoked to determine which pronoun WordGraph object is correct match.
        */
        String pronoun = null;            //base form or subject form of pronoun, singular
                                          //(I,you,he,she,it)
        String subjectPlural = null;       //subject form of pronoun, plural
                                          //(We,you,them)
        String possessiveSubjectSingular = null; // possessive form of pronoun, singular 
                                          //(my,your,his,hers,its)
        String possessiveSubjectPlural = null; // possessive form of pronoun, plural 
                                          //(our,your,theirs)
        String objectSingluar = null;     //Direct/Indirect object case of pronoun, singluar
                                          //(me,you,him,her,it)
        String objectPlural = null;       //Direct/Indirect object case of pronoun, plural
                                          //(us,you,them)
        
        /*  As an alternative, every pronoun can be stored as a single term in a String
            variable, and the case,number and other characteristics can be stored as
            Booleans. This has the advantage that there will be different java pronoun
            objects for each separate morphology, and so when a word is encountered in a text
            to be converted to a graph or located in a graph, it will not be necessary for the
            programmer to derive the base form of the pronoun from a separate list.
        */
        
        String baseForm = null;            //base form or subject form of pronoun, singular
                                          //(I,you,he,she,it)
        Boolean isSubjectPlural = false;   //set to true for subject form of pronoun, plural
                                          //(We,you,them)
        Boolean isPossessiveSubjectSingular = false; // set to true for possessive form of pronoun, singular 
                                          //(my,your,his,hers,its)
        Boolean isPossessiveSubjectPlural = false; // set to true for possessive form of pronoun, plural 
                                          //(our,your,theirs)
        Boolean isObjectSingluar = false;     //set to true for Direct/Indirect object case of pronoun, singluar
                                          //(me,you,him,her,it)
        Boolean isObjectPlural = false;       //set to true for Direct/Indirect object case of pronoun, plural
                                          //(us,you,them)
        
        
        /* Constructor */
        public Pronoun(String p) {
            pronoun = p;
        }
        
        /* Getters and setters for the fields in the first, String-based possibility for the pronoun word object */
        public String getPronoun() {
            return pronoun;
        }

        public void setPronoun(String pronoun) {
            this.pronoun = pronoun;
        }

        public String getSubjectPlural() {
            return subjectPlural;
        }

        public void setSubjectPlural(String subjectPlural) {
            this.subjectPlural = subjectPlural;
        }

        public String getPossessiveSubjectSingular() {
            return possessiveSubjectSingular;
        }

        public void setPossessiveSubjectSingular(String possessiveSubjectSingular) {
            this.possessiveSubjectSingular = possessiveSubjectSingular;
        }

        public String getPossessiveSubjectPlural() {
            return possessiveSubjectPlural;
        }

        public void setPossessiveSubjectPlural(String possessiveSubjectPlural) {
            this.possessiveSubjectPlural = possessiveSubjectPlural;
        }

        public String getObjectSingluar() {
            return objectSingluar;
        }

        public void setObjectSingluar(String objectSingluar) {
            this.objectSingluar = objectSingluar;
        }

        public String getObjectPlural() {
            return objectPlural;
        }

        public void setObjectPlural(String objectPlural) {
            this.objectPlural = objectPlural;
        }

        /* Getters and setters for the fields in the second, Boolean-based possibility for the pronoun word object */
        public String getBaseForm() {
            return baseForm;
        }

        public void setBaseForm(String baseForm) {
            this.baseForm = baseForm;
        }

        public Boolean getIsSubjectPlural() {
            return isSubjectPlural;
        }

        public void setIsSubjectPlural(Boolean isSubjectPlural) {
            this.isSubjectPlural = isSubjectPlural;
        }

        public Boolean getIsPossessiveSubjectSingular() {
            return isPossessiveSubjectSingular;
        }

        public void setIsPossessiveSubjectSingular(Boolean isPossessiveSubjectSingular) {
            this.isPossessiveSubjectSingular = isPossessiveSubjectSingular;
        }

        public Boolean getIsPossessiveSubjectPlural() {
            return isPossessiveSubjectPlural;
        }

        public void setIsPossessiveSubjectPlural(Boolean isPossessiveSubjectPlural) {
            this.isPossessiveSubjectPlural = isPossessiveSubjectPlural;
        }

        public Boolean getIsObjectSingluar() {
            return isObjectSingluar;
        }

        public void setIsObjectSingluar(Boolean isObjectSingluar) {
            this.isObjectSingluar = isObjectSingluar;
        }

        public Boolean getIsObjectPlural() {
            return isObjectPlural;
        }

        public void setIsObjectPlural(Boolean isObjectPlural) {
            this.isObjectPlural = isObjectPlural;
        }

        
   
    }//end inner class Pronoun
    
    //inner class Adjective
    public class Adjective {
        
        String adjective = null;
        String comparative = null;
        String superlative = null;
        PennTag pt = null;

        public Adjective(String a, PennTag pt) {
            adjective = a;
        }

        public String getAdjective() {
            return adjective;
        }

        public void setAdjective(String adjective) {
            this.adjective = adjective;
        }

        public String getComparative() {
            return comparative;
        }

        public void setComparative(String comparative) {
            this.comparative = comparative;
        }

        public String getSuperlative() {
            return superlative;
        }

        public void setSuperlative(String superlative) {
            this.superlative = superlative;
        }       
    }//end inner class Adjective
    
    //inner class Adverb
    public class Adverb {
        
        String adverb = null;
        Boolean isComparative = false;
        Boolean isSuperlative = false;
        Boolean isWhAdverb = false;

        public Adverb(String a, PennTag pt) {
            adverb = a;
            switch (pt) {
                case RB : { //do nothing. The adverb is in base form 
                }
                case RBR : { isComparative = true; }
                case RBS : { isSuperlative = true;}
                case WRB : { isWhAdverb = true;}
                }//end switch
            }//end constructor

        public String getAdverb() {
            return adverb;
        }

        public void setAdverb(String adverb) {
            this.adverb = adverb;
        }

        public Boolean getIsSuperlative() {
            return isSuperlative;
        }

        public void setIsSuperlative(Boolean isSuperlative) {
            this.isSuperlative = isSuperlative;
        }

        public Boolean getIsWhAdverb() {
            return isWhAdverb;
        }

        public void setIsWhAdverb(Boolean isWhAdverb) {
            this.isWhAdverb = isWhAdverb;
        }

        public Boolean getIsComparative() {
            return isComparative;
        }

        public void setIsComparative(Boolean isComparative) {
            this.isComparative = isComparative;
        }

        
        

    }//end inner class Adverb
    
    public class Number {
    
        String cardinal = null; //counting number (one, two, three, etc.)
        String ordinal = null; //position number (first, second, third, etc.)

        /*  if the word object is created from the cardinal, but the ordinal is not available yet,
            then the code to create the object would be Number n = new Number ("One," null);
            For the reverse, code is Number n = new Number(null,"First");
            In the cases the null variable can be filled in later with a setter
            In the case where both carindal and ordinal are available to create the
            number object, use the second constructor, thusly, Number n = new Number("One","First");
        */  
        public Number(String c, String o) {
            cardinal = c;
            ordinal = o;
        }

        public String getCardinal() {
            return cardinal;
        }

        public void setCardinal(String cardinal) {
            this.cardinal = cardinal;
        }

        public String getOrdinal() {
            return ordinal;
        }

        public void setOrdinal(String ordinal) {
            this.ordinal = ordinal;
        }
        
        


        
    }//end inner class Number

} // end out class WordClass
    
