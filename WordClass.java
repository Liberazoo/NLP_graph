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

        public Adjective(String a) {
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
        String comparative = null;
        String superlative = null;
        Boolean whAdverb = false;

        public Adverb(String a) {
            adverb = a;
        }

        public String getAdverb() {
            return adverb;
        }

        public void setAdverb(String adverb) {
            this.adverb = adverb;
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

        public Boolean isWhAdverb() {
            return whAdverb;
        }

        public void setWhAdverb(Boolean whAdverb) {
            this.whAdverb = whAdverb;
        }   
    }//end inner class Adverb
    
    public class Number {
    
        String cardinal = null; //counting number (one, two, three, etc.)
        String ordinal = null; //position number (first, second, third, etc.)

        /*  if the word object is created from the cardinal, but the ordinal is not available yet,
            then the code to create the object would be Number n = new Number ("One," null);
            For the reverse, code is Number n = new Number(null,"First);
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
    
