package cc.twittertools.optimization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import cc.twittertools.entity.TweetList;
import cc.twittertools.entity.TweetSimScoreList;

public class TweetsScoreList {

  private String indexPath;
  protected File dir;
  private Directory directory;
  private IndexReader reader;
  private final Set<String> terms = new HashSet<>();
  private String fieldname = "TweetContent";
  private Map<String, Float> idf = new HashMap<>();
  TFIDFSimilarity tfidfSIM = new DefaultSimilarity();
  Map<Integer, RealVector> allDocVectors = new HashMap<Integer, RealVector>();
  public Map<Integer, ArrayList<TweetSimScoreList>> TSLmap = new HashMap<>();
  
  public TweetsScoreList(String path) throws Exception {
    dir = new File(path);
    directory = FSDirectory.open(dir);
    reader = DirectoryReader.open(directory);
    this.getIdfs();
 // get all vectors of each document
    for (int i = 0; i < reader.numDocs(); i++) {
      RealVector v = get_vector(i);
      allDocVectors.put(i, v);
    }
    this.scoreMap();
  }

  TweetList  getTweet(int i) throws Exception {
    TweetList tweetlist = new TweetList();
    
    tweetlist.setQueryId(reader.document(i).getField("QueryID").stringValue());
    tweetlist.setTweetId(reader.document(i).getField("TwitterID").stringValue());
    tweetlist.setTweetContent(reader.document(i).getField("TweetContent").stringValue());
    tweetlist.setTweetURLPage(reader.document(i).getField("PageContent"  ).stringValue());
    
    return tweetlist;

  }

  
  
  
  //
  private void scoreMap() {
    RealVector v1, v2;
    for (int i = 0; i < reader.numDocs(); i++) {
      ArrayList<TweetSimScoreList> targetDocList = new ArrayList<TweetSimScoreList>();
      for (int j = 0; j < reader.numDocs(); j++) {
        if (i != j) {
          v1 = allDocVectors.get(i);
          v2 = allDocVectors.get(j);
          double dotP = v1.dotProduct(v2);
          double score = (double) ((dotP) / (v1.getNorm() * v2.getNorm()));
          if(score>=0.05){
         // System.out.println(score);
          TweetSimScoreList scorelist = new TweetSimScoreList();
          scorelist.setDocNum(j);
          scorelist.setScore(score);
          targetDocList.add(scorelist);
          }
        }
        
      }
      System.out.println(i+" "+targetDocList.size());
      Collections.sort(targetDocList, new Comparator<TweetSimScoreList>() {
        public int compare(TweetSimScoreList arg0, TweetSimScoreList arg1) {
          return Double.compare(arg1.getScore(), arg0.getScore());
        }
      });
      TSLmap.put(i, targetDocList);
    }
  }

  

  // idf calculator
  public void getIdfs() throws IOException {
    Map<String, Float> idfResult = new HashMap<>();

    for (int i = 0; i < reader.numDocs(); i++) {
      Terms docterms = reader.getTermVector(i, fieldname);
      if (docterms != null && docterms.size() > 0) {
        TermsEnum termEnum = docterms.iterator(null);
        BytesRef bytesRef;
        while ((bytesRef = termEnum.next()) != null) {
          if (termEnum.seekExact(bytesRef, true)) {
            String term = bytesRef.utf8ToString();
            float idf = tfidfSIM.idf(reader.docFreq(new Term(fieldname, term)), reader.numDocs());
            idfResult.put(term, idf);
            terms.add(term);
          }
        }
      }
    }
    idf = idfResult;
  }

  RealVector get_vector(int docID) throws Exception {
    Map<String, Float> tf_Idf_Weights = new HashMap<>();
    Set<String> thisTerms = new HashSet<>();
    TermsEnum termsEnum = MultiFields.getTerms(reader, fieldname).iterator(null);
    Terms vector = reader.getTermVector(docID, fieldname);

    try {
      termsEnum = vector.iterator(termsEnum);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    BytesRef bytesRef = null;
    while ((bytesRef = termsEnum.next()) != null) {
      if (termsEnum.seekExact(bytesRef, true)) {
        String term = bytesRef.utf8ToString();
        float tf = 0;
        DocsEnum docsEnum = termsEnum.docs(null, null);
        while (docsEnum.nextDoc() != DocIdSetIterator.NO_MORE_DOCS) {
          tf = tfidfSIM.tf(docsEnum.freq());
        }
        float idfValue = idf.get(term);
        float w = tf * idfValue;
        tf_Idf_Weights.put(term, w);

      }
    }

    RealVector realVector = new ArrayRealVector(terms.size());
    int i = 0;
    for (String term : terms) {
      float value = tf_Idf_Weights.containsKey(term) ? tf_Idf_Weights.get(term) : 0;

      realVector.setEntry(i++, value);
    }
    return (RealVector) realVector.mapDivide(realVector.getL1Norm());
  }

  public int getDocNum() {    
    return reader.numDocs();
  }
  
  public void close() throws Exception {
    reader.close();
    directory.close();
  }
}
