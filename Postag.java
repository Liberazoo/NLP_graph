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
        //get file name with path from user
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the path and filename of words/tags to create word objects from: ");
        String fileName = keyboard.nextLine();        
        String line = null;
        
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
        
        //read from file until end of file is reached
        while((line = bufferedReader.readLine()) != null) {
            if(!line.isEmpty())
            {
                System.out.println("Processing line: " + line);
                
                String[] field = line.split("_");
                word = field[0].trim();
                tag = field[1].trim();   //tag string is not recognizable unless trimmed             
                System.out.println("Word: "+word+ " Tag: " + tag);

                /*  Next a switch statement will use field[1] to determine which
                    word class will be created by passing field[0] to it's 
                    constructor. 
                    Each of the cases in the switch statement are string versions
                    of the Penn Tree Tags produced by the Stanford POSTagger
                */                                                  

                switch (tag) {
                    case "NN" :                     //Noun, singular or mass
                    case "NNS" :                    //Noun, plural
                    case "NNP" :                    //Proper noun, singular
                    case "NNPS" :                   //Proper noun, plural   
                    {
                        WordClass.Noun n = wc.new Noun(word);
                        System.out.println("New noun created: " + n.getNoun());
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
                        WordClass.Adjective a = wc.new Adjective(word);
                        System.out.println("New adjective created: " + a.getAdjective());
                        break;
                    }
                    case "RB" :                 //Adverb
                    case "RBR" :                //Adverb, comparative
                    case "RBS" :                //Adverb, superlative 
                    case "WRB" :                //Wh-adverb ... when, why, how, etc.
                    {
                        WordClass.Adverb a = wc.new Adverb(word);
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
