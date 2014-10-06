package cc.twittertools.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cc.twittertools.entity.TweetList;
import cc.twittertools.entity.TweetSimScoreList;
import cc.twittertools.optimization.SimilarTweetExtraction;
import cc.twittertools.optimization.TweetsScoreList;

public class UpdateTweetResultIndex {

  public UpdateTweetResultIndex(String queryID) throws Exception {

    SimilarTweetExtraction simtweetExtract = new SimilarTweetExtraction("QueryResult4"+queryID);
    List<TweetList> list = new ArrayList<>();
    list = simtweetExtract.newList;
    TweetIndexGtr tweetIndexGtr = new TweetIndexGtr("ExpendedResult4"+queryID);
    
    for(int i=0;i<list.size();i++){
      tweetIndexGtr.createIndex(list.get(i));
     System.out.println(list.get(i).getTweetId());
    }
    tweetIndexGtr.closeIndexGtr();
  }
}
