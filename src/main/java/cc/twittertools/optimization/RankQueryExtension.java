package cc.twittertools.optimization;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cc.twittertools.util.ValueComparator;

public class RankQueryExtension {

  public TreeMap<String,Float> sortedMap;
    
    public RankQueryExtension(Map<String, Float> map){
      ValueComparator bvc =  new ValueComparator(map);
      sortedMap = new TreeMap<String,Float>(bvc);
      sortedMap.putAll(map);
    }
}

