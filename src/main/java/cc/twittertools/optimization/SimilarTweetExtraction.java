package cc.twittertools.optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cc.twittertools.entity.*;

public class SimilarTweetExtraction {

  public List<TweetList> newList = new ArrayList<TweetList>();

  public SimilarTweetExtraction(String path) throws Exception {
    
    TweetsScoreList TSL = new TweetsScoreList(path);
    Map<Integer, ArrayList<TweetSimScoreList>> TSLmap = TSL.TSLmap;

    for (int i = 0; i < TSL.getDocNum(); i++) {
      TweetList updateTweet = new TweetList();
      updateTweet = TSL.getTweet(i);
      String newContent = TSL.getTweet(i).getTweetContent();
      if(TSLmap.get(i).size()!=0){
        
      for (int j = 0; j <100&&j<TSLmap.get(i).size(); j++) {
        newContent += " "+TSL.getTweet(TSLmap.get(i).get(j).getDocNum()).getTweetContent();
      }
      }
      updateTweet.setTweetContent(newContent);
      newList.add(updateTweet);
      
    }
    TSL.close();
  }
}
