package cc.twittertools.htmlprocessor;

import java.io.IOException;

import javax.lang.model.element.Element;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cc.twittertools.util.UrlTools;

public class HTMLTagFilter {
  public String Crawler(String url ){
    String html;
    try {
      Connection conn = UrlTools.getLongUrl(url);
      html = conn.execute().body();
      //System.out.println(html);
      String noHTMLString = html.replaceAll("<script[^>]*>[\\d\\D]*?</script>", "").toLowerCase();
      
      noHTMLString = noHTMLString.replaceAll("<style[^>]*>[\\d\\D]*?</style>", "").toLowerCase();
    
      noHTMLString = noHTMLString.replaceAll("<head[^>]*>[\\d\\D]*?</head>", "").toLowerCase();
      noHTMLString = noHTMLString.replaceAll("\\<div[\\d\\D][^\\>]*?foot[\\d\\D]*?\\>[\\d\\D]*?</div>", "").toLowerCase();
      noHTMLString = noHTMLString.replaceAll("\\<div[\\d\\D]*?head[\\d\\D]*?\\>[\\d\\D]*?</div>", "").toLowerCase();
      noHTMLString = noHTMLString.replaceAll("\\<div[\\d\\D]*?aside[\\d\\D]*?\\>[\\d\\D]*?</div>", "").toLowerCase();
      noHTMLString = noHTMLString.replaceAll("<footer[^>]*>[\\d\\D]*?</footer>", "").toLowerCase();
      noHTMLString = noHTMLString.replaceAll("<aside[^>]*>[\\d\\D]*?</aside>", "").toLowerCase();
      noHTMLString = noHTMLString.replaceAll("\\<.[\\d\\D]*?\\>", "").toLowerCase();
       
       
      noHTMLString = noHTMLString.replaceAll("\\pP", "").trim();
      //System.out.println(noHTMLString);
      noHTMLString = noHTMLString.replaceAll("nbsp", "").trim();
      noHTMLString = noHTMLString.replaceAll("[^a-zA-Z]", " ");
      noHTMLString = noHTMLString.replaceAll("\\s+", " ").trim();
         //System.out.println("这是新的" + noHTMLString); 
        //System.out.println(html);
        return noHTMLString;
    } catch (IOException e) {
      return "";
    }
    
  }
  public String FilterContent(String content){
    String  newContent = content.replaceAll("\\<.[\\d\\D]*?\\>", "").toLowerCase();
    newContent = newContent.replaceAll("\\W", " ").trim();
    newContent = newContent.replaceAll("\\pP", "").trim();
      //System.out.println(noHTMLString);
    newContent = newContent.replaceAll("nbsp", "").trim();
    newContent = newContent.replaceAll("[^a-zA-Z]", " ");
    newContent = newContent.replaceAll("\\s+", " ");
    return newContent;
  }
//  public static void main(String[] args) {
//    HTMLTagFilter hl = new HTMLTagFilter();
//    hl.Crawler("http://t.co/8Wx5Y9vYOU");
//  }
 
}
