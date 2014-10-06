package cc.twittertools.wordnet;

import edu.smu.tspell.wordnet.*;

public class TestJAWS {

  public static void main(String[] args)
  {
    //System.setProperty("wordnet.database.dir", ".\dict");
    NounSynset nounSynset; 
    NounSynset[] hyponyms;
    NounSynset[] topics;
    WordNetDatabase database = WordNetDatabase.getFileInstance()  ; 
    Synset[] synsets = database.getSynsets("car", SynsetType.NOUN); 
    for (int i = 0; i < synsets.length; i++) { 
        nounSynset = (NounSynset)(synsets[i]); 
        hyponyms = nounSynset.getHyponyms(); 
        topics = nounSynset.getTopics();
        
       // System.err.println(nounSynset.getWordForms()[0] + 
         //       ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms"); 
        for(int j = 0;j<hyponyms.length;j++){
          System.err.println(hyponyms[j]);
          
          }
        /*for(int j = 0;j<topics.length;j++){
        System.err.println(topics[j]);
        }*/
        
        
    }
    
  }
}
