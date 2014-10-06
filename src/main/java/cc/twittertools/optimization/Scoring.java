package cc.twittertools.optimization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
//import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
 
 

public class Scoring {

//  private String indexPath = "ExpendedResult4MB171";
  protected File dir;
  private Directory directory;
  private IndexReader reader;
//  private String fieldname = "PageContent";
  TFIDFSimilarity tfidfSIM = new DefaultSimilarity();

  public Scoring(String indexPath) throws Exception {
    dir = new File(indexPath);
    directory = FSDirectory.open(dir);
    reader = DirectoryReader.open(directory);
    
  }

 
  public float getDf(String term,String fieldname) {
    float df;
    try {
      df = reader.docFreq(new Term(fieldname, term));
      return df;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      return 0;
    }
    
  }
  public int getNumDocs(){
    return reader.numDocs(); 
  }
  public Map<String, Float> getTf(int docID,String fieldname){
    Map<String, Float> countTf = new HashMap<String, Float>();
    try {
    TermsEnum termsEnum = MultiFields.getTerms(reader, fieldname).iterator(null);
    Terms vector = reader.getTermVector(docID, fieldname);

    
      termsEnum = vector.iterator(termsEnum);
    
    BytesRef bytesRef = null;
    while ((bytesRef = termsEnum.next()) != null) {
      if (termsEnum.seekExact(bytesRef, true)) {
        String term = bytesRef.utf8ToString();
        float tf = 0;
        DocsEnum docsEnum = termsEnum.docs(null, null);
        while (docsEnum.nextDoc() != DocIdSetIterator.NO_MORE_DOCS) {
          tf = tfidfSIM.tf(docsEnum.freq());
        }
        countTf.put(term, tf);

      }
    }
    return countTf;
    } catch (Exception e) {

    return null;
    }
     
  }
 
 public int docLength( int docID,String fieldname ) {
   try {
   TermsEnum termsEnum = MultiFields.getTerms(reader, fieldname).iterator(null);
   Terms vector = reader.getTermVector(docID, fieldname); 
     termsEnum = vector.iterator(termsEnum);
  
   int doc_length = 0;
   BytesRef bytesRef = null;
     while ((bytesRef = termsEnum.next()) != null) {
       if (termsEnum.seekExact(bytesRef, true)) {
         float tf = 0;
         DocsEnum docsEnum = termsEnum.docs(null, null);
         while (docsEnum.nextDoc() != DocIdSetIterator.NO_MORE_DOCS) {
           tf = tfidfSIM.tf(docsEnum.freq());
         }
         doc_length+=tf;  
       }
     } 
   return doc_length;
   } catch (Exception e) {
     return 0;
   }
 }
 
 public int collectionLength (String fieldname,String indexPath) throws Exception{
   int col_Length = 0;
    for(int i = 0; i<reader.numDocs(); i++){
      Scoring score = new Scoring(indexPath);
      int doc_length = score.docLength(i,fieldname);
      col_Length +=doc_length;
    }
   return col_Length;
 }
 
 public double avgLength(int collectionLength, int docNum){
   double avg = collectionLength/docNum ;
   return avg;
 }
 public double getScore(String term, int docID,String fieldname,double avgLength,String indexPath){
   int k = 2;
   double b = 0.75;
   Scoring element;

   Map<String, Float> tfMap;
  try {
    
    element = new Scoring(indexPath);
    
    tfMap = element.getTf(docID,fieldname);
    if(tfMap.containsKey(term)){
     float tf = tfMap.get(term);
     int doc_length = element.docLength(docID,fieldname);
     double IDF = Math.log((element.getNumDocs()-element.getDf(term,fieldname)+0.5)/(element.getDf(term,fieldname)+0.5));
     IDF = Math.abs(IDF);
     double score = IDF*tf*(k+1)/(tf+k*(1-b+b*doc_length/avgLength));
     if(score < 0){
       return 0;
     }
     else return score;
    }
    else return 0;
    
  } catch (Exception e) {
    return 0.0;
  }   
 }
 
 public HashMap<Integer,Double> rankList(String query, String fieldname,double avgdl,String indexPath){
   
   queryTokenize qt;
  try {
    qt = new queryTokenize();
  
   String[] queryString;
  try {
    queryString = qt.toStringArray(query).split(" ");
  
   HashMap<Integer,Double> DocScore = new HashMap<Integer,Double>();
   HashMap<Integer,Double> FinalDocScore = new HashMap<Integer,Double>();
   ValueComparator2 bvc =  new ValueComparator2(DocScore);
   TreeMap<Integer, Double> sortedMap = new TreeMap<Integer,Double>(bvc);
//   TreeMap<Integer, Double> FinalsortedMap = new TreeMap<Integer,Double>(bvc);
   for(int i = 0; i<reader.numDocs();i++){
     
     double termScorePerDoc = 0;
     for(int j = 0; j<queryString.length;j++){
       Scoring score;
      try {
        score = new Scoring(indexPath);
      
       double termScore = score.getScore(queryString[j], i, fieldname,avgdl,indexPath); 
//       System.out.println("query token "+queryString[j] + " score "+termScore);
       termScorePerDoc += termScore;
      } catch (Exception e) {
         double termScore2 = 0;
         termScorePerDoc += termScore2;
      }
     }
     System.out.println("document "+i+" "+termScorePerDoc);
     DocScore.put(i, termScorePerDoc);
   }
   
   
   sortedMap.putAll(DocScore);
//   System.out.println(sortedMap);
   Double HighestScore = DocScore.get(sortedMap.firstKey());
//   System.out.println("highest score : "+HighestScore);
//   if(HighestScore == 0.0){
//     HighestScore = 1000000000.0;
//   }
//   System.out.println(sortedMap.firstKey());
//   System.out.println(HighestScore);
  
   Set<Integer> docIDs = DocScore.keySet();
     for(int key : docIDs){
       Double newScore = DocScore.get(key)/HighestScore;
       FinalDocScore.put(key, newScore);
     }
  
//   FinalsortedMap.putAll(FinalDocScore);
     
   return FinalDocScore;
  }catch (IOException e1) {
     // TODO Auto-generated catch block
      return null;
   }
  } catch (Exception e2) {
    // TODO Auto-generated catch block
    return null;
  }
 }

 public TreeMap<Integer,Double> CombinedScore(String query, double avgdl1, double avgdl2,String indexPath) throws Exception{
   Scoring score = new Scoring(indexPath);
   
   String field1 = "TweetContent";
   String field2 = "PageContent";
   HashMap<Integer,Double> score4Field1 = score.rankList(query, field1,avgdl1,indexPath);
   HashMap<Integer,Double> score4Field2 = score.rankList(query, field2,avgdl2,indexPath);
   HashMap<Integer,Double> finalRank = new HashMap<Integer,Double>();
   
   Set<Integer> docidSet = score4Field1.keySet();
   for(int key : docidSet){
     Double combinedScore = 0.8*score4Field1.get(key)+0.2*score4Field2.get(key);
     finalRank.put(key, combinedScore);
   }
   ValueComparator2 bvc =  new ValueComparator2(finalRank);
   TreeMap<Integer,Double> finalResult = new TreeMap<Integer,Double>(bvc);
   finalResult.putAll(finalRank);
   return finalResult;
 }
 
 public String getTweetContent(int i) throws Exception {
   return reader.document(i).get("TweetContent");
 }
 
 public String getPageContent(int i) throws Exception {
   return reader.document(i).get("PageContent");
 }
// public static void main(String []args) throws Exception{
//   Scoring score = new Scoring();
//   System.out.println(score.getPageContent(2)+score.getNumDocs());

 // }
 
 public String getTweetID(int docID) throws IOException{

    Document doc = reader.document(docID);
    return (doc==null)?null:doc.get("TwitterID");

 }

 public String getQueryID(int docID) throws IOException{

   Document doc = reader.document(docID);
   return (doc==null)?null:doc.get("QueryID");

}
 
// public static void main(String []args) throws Exception{
//   Scoring ft = new Scoring(indexPath);
//   System.out.println(ft.getDf("harri", "TweetContent"));
//   System.out.println(ft.getQueryID(22));
//   System.out.println(ft.getNumDocs());
//   System.out.println(ft.getTf(3, "TweetContent"));
//   System.out.println(ft.getScore("ron", 3, "TweetContent"));
//   System.out.println(ft.rankList("Ron Weasley birthday ", "TweetContent",10.0));
//   for(int i = 0;i<100;i++)
//   System.out.println("26" + ":" + ft.getTweetContent(1835));
//   System.out.println("collection" + ":" + ft.collectionLength("TweetContent"));
//   System.out.println("document length" + ":" + ft.docLength(1835, "TweetContent"));
//   
//  int collection_length = ft.collectionLength("TweetContent");
//  int docNum = ft.getNumDocs();
//  double avg = collection_length/docNum;
//  System.out.println(avg);
//   System.out.println(ft.CombinedScore("Ron Weasley birthday ", 10));
//   System.out.println("26" + ":" + ft.getTweetContent(26));
//   FileWriter fos = new FileWriter("output4TestZheng.txt");
//   BufferedWriter bw=new BufferedWriter(fos);
//   bw.write(ft.getTweetContent(26));
//   bw.newLine();
//  
//   bw.close();
//   fos.close();
// }
}
