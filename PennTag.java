/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgraph;

/**
 *
 * @author David
 */
/*  This enum is a type containing the basic PennTree part of speech
        tags to be used by the various inner word classes in the WordClass
        utility class. For example, the Adjective and Adverb class need
        different penn tree tags passed to its constructor in order to know
        if the adjective or adverb object is base form, comparative or
        superlative, i.e. strong, stronger, strongest or sunny, sunnier,
        sunniest. 
    */
    public enum PennTag { 
        CC,	//Coordinating conjunction
        CD,	//Cardinal number
        DT,	//Determiner
        EX,	//Existential there
        FW,	//Foreign word
        IN,	//Preposition or subordinating conjunction
        JJ,	//Adjective
        JJR,	//Adjective, comparative
        JJS,	//Adjective, superlative
        LS,	//List item marker
        MD,	//Modal
        NN,	//Noun, singular or mass
        NNS,	//Noun, plural
        NNP,	//Proper noun, singular
        NNPS,	//Proper noun, plural
        PDT,	//Predeterminer
        POS,	//Possessive ending
        PRP,	//Personal pronoun
        PRP$,	//Possessive pronoun (prolog version PRP-S)
        RB,	//Adverb
        RBR,	//Adverb, comparative
        RBS,	//Adverb, superlative
        RP,	//Particle
        SYM,	//Symbol
        TO,	//to
        UH,	//Interjection
        VB,	//Verb, base form
        VBD,	//Verb, past tense
        VBG,	//Verb, gerund or present participle
        VBN,	//Verb, past participle
        VBP,	//Verb, non-3rd person singular present
        VBZ,	//Verb, 3rd person singular present
        WDT,	//Wh-determiner
        WP,	//Wh-pronoun
        WP$,	//Possessive wh-pronoun (prolog version WP-S)
        WRB,	//Wh-adverb
    
    };
