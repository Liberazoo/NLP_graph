/*required Jar files
stanford-post-tagger-3.6.0.jar
stanford-post-tagger-3.6.0-sources.jar
stanford-post-tagger-3.6.0-javadoc.jar
slf4j-api.jar
slf4j-simple.jar
lingpip-4.1.0.jar
*/
package wordgraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;



/**
 *
 * @author David
 * 
 * The purpose of this program  is to take a file of words and their POS tags
 * and split them into two parts, so that another class take create a Word object
 * which will be composed of the word and it's part of speech.
 * 
 * The file is named dcWordList.tagged.txt and contains one word followed by an
 * underscore and its corresponding POS tag. For example, line is "act_NN".
 * The original, untagged file is dcWordList.txt and was tagged using the Stanford
 * POStagger. 
 * 
 * The original file came from the Dale-Chall Word List.
 * 
 */
public class Postag {
    
    public static void main(String[] args)throws IOException, ClassNotFoundException {
        
        System.out.println("Starting program to break word/tags into word classes ...");
        //current file and path name is => C:\Users\David\Documents\nlp_project\NLP Data Files\dcWordList.tagged.txt
        
        /*  code for getting file name with path from user instead of setting
            it as a String as above code does currently
            Commented out but usable when it comes time to work on
            other files to add to the word hypergraph
        
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Enter the path and filename of words/tags to create word objects from: ");
                String fileName = keyboard.nextLine();  
        */
        
        String fileName = "C:\\Users\\David\\Documents\\nlp_project\\NLP Data Files\\dcWordList.tagged.txt";
      
        String line;
        
        // FileReader reads text files in the default encoding.
        FileReader fileReader = 
            new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);
        
        /*  create a WordClass object (this is an outer class containing
            a set of inner classes, each of which is a part of speech. 
            The purpose of keeping these parts of speech as inner classes
            is to keep them all in one place so that alterations to one
            part of speech that may affect changes to another won't
            require flipping between multiple files.                  */
        WordClass wc = new WordClass();
        
        String word = null; //the dictionary word when extracted from line
        String tag = null;  //the part of speech tag attached to word
        /*  for converting the string version of the POS tag to a java enum 
            type used by some the word classes to determine subtypes of
            a word, e.g. verb forms like go, went, gone or adjective and 
            adverb comaratives and superlatives
        */
        PennTag pt = null;  
        
        //read from file until end of file is reached
        while((line = bufferedReader.readLine()) != null) {
            if(!line.isEmpty())
            {
                System.out.println("Processing line: " + line);
                
                String[] field = line.split("_");
                word = field[0].trim();
                tag = field[1].trim();   //tag string is not recognizable unless trimmed             
                System.out.println("Word: "+word+ " Tag: " + tag);
                
                /* Next, a switch statement will take the field[1] string of
                   the tag and convert it to a java enum type
                */
                 switch(tag){
                    case "CC": { pt = PennTag.CC ; }	//Coordinating conjunction
                    case "CD": { pt = PennTag.CD ; }	//Cardinal number
                    case "DT": { pt = PennTag.DT ; }	//Determiner
                    case "EX": { pt = PennTag.EX ; }	//Existential there
                    case "FW": { pt = PennTag.FW ; }	//Foreign word
                    case "IN": { pt = PennTag.IN ; }	//Preposition or subordinating conjunction
                    case "JJ": { pt = PennTag.JJ ; }	//Adjective
                    case "JJR": { pt = PennTag.JJR ; }	//Adjective, comparative
                    case "JJS": { pt = PennTag.JJS ; }	//Adjective, superlative
                    case "LS": { pt = PennTag.LS ; }	//List item marker
                    case "MD": { pt = PennTag.MD ; }	//Modal
                    case "NN": { pt = PennTag.NN ; }	//Noun, singular or mass
                    case "NNS": { pt = PennTag.NNS ; }	//Noun, plural
                    case "NNP": { pt = PennTag.NNP ; }	//Proper noun, singular
                    case "NNPS": { pt = PennTag.NNPS ; }	//Proper noun, plural
                    case "PDT": { pt = PennTag.PDT ; }	//Predeterminer
                    case "POS": { pt = PennTag.POS ; }	//Possessive ending
                    case "PRP": { pt = PennTag.PRP ; }	//Personal pronoun
                    case "PRP$": { pt = PennTag.PRP$ ; }	//Possessive pronoun (prolog version PRP-S)
                    case "RB": { pt = PennTag.RB ; }	//Adverb
                    case "RBR": { pt = PennTag.RBR ; }	//Adverb, comparative
                    case "RBS": { pt = PennTag.RBS ; }	//Adverb, superlative
                    case "RP": { pt = PennTag.RP ; }	//Particle
                    case "SYM": { pt = PennTag.SYM ; }	//Symbol
                    case "TO": { pt = PennTag.TO ; }	//to
                    case "UH": { pt = PennTag.UH ; }	//Interjection
                    case "VB": { pt = PennTag.VB ; }	//Verb, base form
                    case "VBD": { pt = PennTag.VBD ; }	//Verb, past tense
                    case "VBG": { pt = PennTag.VBG ; }	//Verb, gerund or present participle
                    case "VBN": { pt = PennTag.VBN ; }	//Verb, past participle
                    case "VBP": { pt = PennTag.VBP ; }	//Verb, non-3rd person singular present
                    case "VBZ": { pt = PennTag.VBZ ; }	//Verb, 3rd person singular present
                    case "WDT": { pt = PennTag.WDT ; }	//Wh-determiner
                    case "WP": { pt = PennTag.WP ; }	//Wh-pronoun
                    case "WP$": { pt = PennTag.WP$ ; }	//Possessive wh-pronoun (prolog version WP-S)
                    case "WRB": { pt = PennTag.WRB ; }	//Wh-adverb
                 }

                /*  Next a switch statement will use field[1] to determine which
                    word class will be created by passing field[0] to it's 
                    constructor. 
                    Each of the cases in the switch statement are string versions
                    of the Penn Tree Tags produced by the Stanford POSTagger
                */                                                  

                switch (tag) {
                    case "NN" :                     //Noun, singular or mass
                    {
                        WordClass.Noun n = wc.new Noun(word);
                        System.out.println("New noun created: " + n.getNoun());
                        System.out.println("new plural created: " + n.getPlural());
                        break;
                    }
                    case "NNS" :                    //Noun, plural
                    {
                        WordClass.Noun n = wc.new Noun(null,word);
                        //System.out.println("New noun created: " + n.getNoun());
                        System.out.println("New plural created: " + n.getPlural());
                        break;
                    } 
                    case "NNP" :                    //Proper noun, singular
                    {
                        WordClass.Noun n = wc.new Noun(word);
                        System.out.println("New noun created: " + n.getNoun());
                        System.out.println("New plural created: " + n.getPlural());
                        break;
                    }
                    case "NNPS" :                   //Proper noun, plural   
                    {
                        WordClass.Noun n = wc.new Noun(null,word);
                        //System.out.println("New noun created: " + n.getNoun());
                        System.out.println("new plural created: " + n.getPlural());
                        break;
                    } 
                    case "VB" :                     //Verb, base form
                    case "VBD" :                    //Verb, past tense
                    case "VBG" :                    //Verb, gerund or present participle
                    case "VBN" :                    //Verb, past participle
                    case "VBP" :                    //Verb, non-3rd person singular present
                    case "VBZ" :                    //Verb, 3rd person singular present
                    {
                        WordClass.Verb v = wc.new Verb(word);
                        System.out.println("New verb created: " + v.getVerb());
                        break;
                    }
                    case "PRP" :                    //Personal pronoun
                    case "PRP$" :                   //Possessive pronoun
                    case "WP" :                     //Wh-pronoun, (interrogative pronouns)
                                                    //who, which, whom, what
                    case "WP$" :                    //Possessive wh-pronoun, whose
                    {
                        WordClass.Pronoun p = wc.new Pronoun(word);
                        System.out.println("New pronoun created: " + p.getPronoun());
                        break;
                    }
                    case "JJ" :                 //Adjective
                    case "JJR" :                //Adjective, comparative
                    case "JJS" :                //Adjective, superlative 
                    {
                        WordClass.Adjective a = wc.new Adjective(word,pt);
                        System.out.println("New adjective created: " + a.getAdjective());
                        break;
                    }
                    case "RB" :                 //Adverb
                    case "RBR" :                //Adverb, comparative
                    case "RBS" :                //Adverb, superlative 
                    case "WRB" :                //Wh-adverb ... when, why, how, etc.
                    {
                        WordClass.Adverb a = wc.new Adverb(word,pt);
                        System.out.println("New adjective created: " + a.getAdverb());
                        break;
                    }
                    case "CD" :                 //Cardinal number
                    {
                        WordClass.Pronoun p = wc.new Pronoun(word);
                        System.out.println("New pronoun created: " + p.getPronoun());
                        break;
                    }
                    default: System.out.println("Nothing happened");
                }

            }
           
        } // while loop reading file     
               
        // Always close files.
        bufferedReader.close(); 
        
        
    }
}
