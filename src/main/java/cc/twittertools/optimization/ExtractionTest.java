package cc.twittertools.optimization;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cc.twittertools.entity.TweetSimScoreList;

public class ExtractionTest {

  public static void main(String[] args) throws Exception {
    FeatureExtraction extraction = new FeatureExtraction();
    FileOutputStream fos = new FileOutputStream("FeatureWordsScore.txt");
    for (int i = 0; i < extraction.getSize(); i++) {
      RankQueryExtension extension = new RankQueryExtension(extraction.getTfidfResult(i));
      TreeMap<String, Float> map = new TreeMap<String, Float>();
      map = extension.sortedMap;
      String line1 = "Query:"+extraction.getQueryContent(i)+"\r\n";
      fos.write(line1.getBytes());
//      String lineP = "Page:"+extraction.getPageContent(i)+"\r\n";
//      fos.write(lineP.getBytes());
      Iterator<String> it = map.keySet().iterator();
      for (int j=0;j<20;j++) {
      
      String line2 = it.next();
      line2+="  "+map.get(line2);
      fos.write(line2.getBytes());
      }
      String newLine = "\r\n";
      fos.write(newLine.getBytes());
      fos.write(newLine.getBytes());
    }
    fos.close();
  }
}
