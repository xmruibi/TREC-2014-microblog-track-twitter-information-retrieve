package cc.twittertools.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;

import cc.twittertools.entity.QueryList;
import cc.twittertools.entity.TweetList;
import cc.twittertools.google.GoogleURLGetter;
import cc.twittertools.htmlprocessor.FilterTweet;
import cc.twittertools.search.TrecTopicSet;
import cc.twittertools.search.api.TrecSearchThriftClient;
import cc.twittertools.thrift.gen.TResult;

public class RawResultGtr {

  static String queryFile = "data/topics2014.txt";
  static String host = "nest.umiacs.umd.edu";
  static int port = 9091;
  static String group = "tw13t017";
  static String token = "fda951";
  static int numResults = 1000;
  static String runtag = "lucene4lm";
  static boolean verbose = true;


  public static void main(String[] args) throws Exception {

    if (!new File(queryFile).exists()) {
      System.err.println("Error: " + queryFile + " doesn't exist!");
      System.exit(-1);
    }
    TrecTopicSet topicsFile = TrecTopicSet.fromFile(new File(queryFile));

    TrecSearchThriftClient client = new TrecSearchThriftClient(host, port, group, token);
    
    //QueriesIndexGtr queriesIndexGtr = new QueriesIndexGtr();

    for (cc.twittertools.search.TrecTopic query : topicsFile) {

      //add single query to queries list
      QueryList singleQuery = new QueryList();
      String queryID = query.getId();
      String queryContent = query.getQuery();
      GoogleURLGetter googleGetter = new GoogleURLGetter(queryContent);
      singleQuery.setQueryId(queryID);
      singleQuery.setQueryContent(queryContent);
      singleQuery.setURLPage(googleGetter.getPageContent());
      //queriesIndexGtr.createIndex(singleQuery);
      
      //add tweets result to tweet list
      List<TResult> results = client
          .search(query.getQuery(), query.getQueryTweetTime(), numResults);
      int i = 1;
      Set<Long> tweetIds = new HashSet<Long>();
      TweetIndexGtr tweetIndexGtr = new TweetIndexGtr("QueryResult4"+queryID);
      for (TResult result : results) {
        if (!tweetIds.contains(result.id)) {
          //tweetIds.add(result.id);
          FilterTweet filterTweet = new FilterTweet(result.text);       
          if(!filterTweet.isRetweet()){
            TweetList tweetList= new TweetList();
            tweetList.setQueryId(queryID);
            tweetList.setTweetId(Long.toString(result.id));
            tweetList.setTweetContent(filterTweet.tweetContent());
            tweetList.setTweetURLPage(filterTweet.tweetLink());
          tweetIndexGtr.createIndex(tweetList);
          }
              
        }
      }
      tweetIndexGtr.closeIndexGtr();
      
      //test one query
      break;
    }
    
    //queriesIndexGtr.closeIndexGtr();

  }
  
 

}
