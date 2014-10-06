package cc.twittertools.htmlprocessor;

public class FilterTweet {
  String tweet;
  boolean retweet = false;
  String link = null;
  String tweetContent;

  public FilterTweet(String originalTweet) {
    tweet = originalTweet;
    String subString = tweet.substring(0, 2);
    if (subString.equals("RT")) { // filter retweet
      retweet=true;;// not retweet
    }
    if(!retweet){
    String reg = ".*(?i)(http|https)://\\s*([^\\s]+)\\s*.*";
    String str = tweet.replaceAll(reg, "http://$2");
    if (str.length()>=7 && str.substring(0, 7).equals("http://")) { // filter tweet with link
      link = str;
    }
    
    if (link!=null) { // filter tweet with link
      String removeLink = tweet.replace(str, "");
      HTMLTagFilter fl = new HTMLTagFilter();
      tweetContent = fl.FilterContent(removeLink);
    } else {
      HTMLTagFilter fl = new HTMLTagFilter();
      tweetContent = fl.FilterContent(str);
    }
    }
  }

  public String getLink() {
    return this.link;
  }

  public Boolean isRetweet() { 
    return this.retweet;// retweet
  }

  public String getLinkContent() {
    if (link != null) { // filter tweet with link
      HTMLTagFilter fl = new HTMLTagFilter();
      String linkPage = fl.Crawler(link);
      return linkPage;
    }
    return "";
  }

  public String getTweetContent() {
    return this.tweetContent;
  }

}
