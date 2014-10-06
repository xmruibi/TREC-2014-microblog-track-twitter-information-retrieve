package cc.twittertools.google;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cc.twittertools.htmlprocessor.HTMLTagFilter;

public class GoogleURLGetter {
  String pageContent="";
  
  public GoogleURLGetter(String query) throws IOException, Exception {
    String newQuery = query.replaceAll(" ", "+");
    // System.out.println(newQuery);
    Document document = Jsoup.connect("http://www.google.com/search?q=" + newQuery)
        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();

    Elements links = document.getElementsByClass("r").select("a");
    for (Element link : links) {
      String googleLink = link.attr("abs:href");

      HTMLTagFilter tagFliter = new HTMLTagFilter();
      pageContent += tagFliter.Crawler(googleLink);
    }
  }
  

  
  public String getPageContent() {
    return pageContent;
  }

 
}
