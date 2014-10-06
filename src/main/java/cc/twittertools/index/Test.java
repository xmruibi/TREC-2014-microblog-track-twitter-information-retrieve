package cc.twittertools.index;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import cc.twittertools.optimization.ValueComparator2;

public class Test {

  public static void main(String[] args) throws Exception {
    
    for(int i=197;i<198;i++){
      String queryID = "MB"+i;
      UpdateTweetResultIndex update = new UpdateTweetResultIndex(queryID);
     
   
    }
//    HashMap<Integer,Double> map = new HashMap<Integer,Double>();
//    map.put(1, 2.0);
//    map.put(5, 4.2);
//    map.put(6, 1.7);
//    map.put(3, 3.6);
//    map.put(8, 9.9);
//    map.put(2, 0.0);
//    ValueComparator2 bvc =  new ValueComparator2(map);
//    TreeMap<Integer,Double> finalResult = new TreeMap<Integer,Double>(bvc);
//    finalResult.putAll(map);
//    System.out.println(finalResult);
//    
//    FileWriter fos = new FileWriter("output4Final.txt");
//    BufferedWriter bw=new BufferedWriter(fos);
//    
//     
//    int firstKey = finalResult.firstKey();
//    System.out.println(firstKey);
//    finalResult.remove(finalResult.firstKey());
//    Set<Integer> sets = finalResult.keySet();
//    int i = 0;
//    for(int set : sets){
//     
//      if(i<=3){
////        String a ="aaa"+(717+i);
////        System.out.println(a);
//      System.out.println(set + "  "+ map.get(set)+ " "+finalResult.values().toArray()[i]+ " "+ (i+1));
//      i++;
//      }
//      else
//      break;
//      
//    }
////    String twitterID = ft.getTweetID(firstKey);
////    String queryID = ft.getQueryID(firstKey);
////    String str = queryID + " Q0 " + twitterID + " " + i+1 + " " + result.get(firstKey) + " Upitt";
////    bw.write(str);
////    bw.newLine();
////    result.remove(result.firstKey());
//     
//    
//    bw.close();
//    fos.close();
  }
}
