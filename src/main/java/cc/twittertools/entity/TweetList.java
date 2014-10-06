package cc.twittertools.entity;

import java.util.List;

public class TweetList {

  String queryId;
  String tweetId;
  String tweetContent;
  String tweetURLPage;

  
  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public void setTweetId(String tweetId) {
    this.tweetId = tweetId;
  }
  public void setTweetContent(String tweetContent) {
    this.tweetContent = tweetContent;
  }

  public void setTweetURLPage(String tweetURLPage) {
    this.tweetURLPage = tweetURLPage;
  }

  public String getQueryId() {
    return queryId;
  }
  
  public String getTweetId() {
    return tweetId;
  }

  public String getTweetContent() {
    return tweetContent;
  }

  public String getTweetURLPage() {
    return tweetURLPage;
  }
}
