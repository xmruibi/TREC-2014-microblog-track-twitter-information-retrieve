package cc.twittertools.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UrlTools {
  private static String result = null;

  public static Connection getLongUrl(String shortUrl) throws MalformedURLException, IOException {
    Connection conn = Jsoup.connect(shortUrl).userAgent(
        "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Geco/20100101 Firefox/5.0").timeout(100);
    Document doc = conn.get();
    Elements ele = doc.select("meta");
    if (!ele.isEmpty()) {
      String[] urlClue = ele.get(0).attr("content").split("=");
      if (urlClue.length > 1) {
        result = urlClue[1];
       // System.out.println(result);
        String reg = ".*(?i)(http|https)://\\s*([^\\s]+)\\s*.*";
        String str = result.replaceAll(reg, "http://$2");
        if (str.length() >= 7 && str.substring(0, 7).equals("http://")) {
          return Jsoup.connect(result).userAgent(
              "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Geco/20100101 Firefox/5.0");
        }
      }
    }
    return conn;
  }
}
