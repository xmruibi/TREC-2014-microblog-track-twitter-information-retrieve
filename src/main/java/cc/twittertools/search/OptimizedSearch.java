package cc.twittertools.search;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import cc.twittertools.search.QueryTokenize;
import cc.twittertools.optimization.Scoring;
import cc.twittertools.util.ValueComparator2;

public class OptimizedSearch {

  private Scoring score;
  public TreeMap<Integer,Double> finalResult;
 
  public OptimizedSearch(String query) throws Exception {
    score = new Scoring();
    
    HashMap<Integer,Double> score4TweetContent = this.rankList(query, "TweetContent");
    HashMap<Integer,Double> score4PageContent = this.rankList(query, "PageContent");
    HashMap<Integer,Double> temp = new HashMap<Integer,Double>();
    
    Set<Integer> docidSet = score4TweetContent.keySet();
    for(int key : docidSet){
      Double combinedScore = 0.8*score4TweetContent.get(key)+0.2*score4PageContent.get(key);
      temp.put(key, combinedScore);
    }
    ValueComparator2 bvc =  new ValueComparator2(temp);
    finalResult = new TreeMap<Integer,Double>(bvc);
    finalResult.putAll(temp);
  }
  
  private HashMap<Integer,Double> rankList(String query, String fieldname) throws Exception{
    
    QueryTokenize qt=new QueryTokenize();
    String[] queryString=qt.toStringArray(query);
    HashMap<Integer,Double> DocScore = new HashMap<Integer,Double>();
    HashMap<Integer,Double> FinalDocScore = new HashMap<Integer,Double>();
    ValueComparator2 bvc =  new ValueComparator2(DocScore);
    TreeMap<Integer, Double> sortedMap = new TreeMap<Integer,Double>(bvc);
//    TreeMap<Integer, Double> FinalsortedMap = new TreeMap<Integer,Double>(bvc);
    for(int i = 0; i<score.getNumDocs();i++){
      
      double termScorePerDoc = 0;
      for(int j = 0; j<queryString.length;j++){
        
        double termScore = score.getScore(queryString[j], i, fieldname,avgLength);
        termScorePerDoc += termScore;
      }
      DocScore.put(i, termScorePerDoc);
    }
    
    sortedMap.putAll(DocScore);
    Double HighestScore = sortedMap.get(sortedMap.firstKey());
    Set<Integer> docIDs = DocScore.keySet();
    for(int key : docIDs){
      Double newScore = DocScore.get(key)/HighestScore;
      FinalDocScore.put(key, newScore);
    }
//    FinalsortedMap.putAll(FinalDocScore);
    return FinalDocScore;
    
  }
}
