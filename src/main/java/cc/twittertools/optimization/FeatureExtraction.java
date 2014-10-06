package cc.twittertools.optimization;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
//import org.apache.lucene.index.TermDocs;
//import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queries.function.valuesource.TermFreqValueSource;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class FeatureExtraction {

  private String indexPath = "QueriesIndex";
  protected File dir;
  private Directory directory;
  private IndexReader reader;
  private final Set<String> terms = new HashSet<>();
  private String fieldname = "PageContent";
  private Map<String, Float> idf = new HashMap<>();
  TFIDFSimilarity tfidfSIM = new DefaultSimilarity();

  public FeatureExtraction() throws Exception {
    dir = new File(indexPath);
    directory = FSDirectory.open(dir);
    reader = DirectoryReader.open(directory);
    this.getIdfs();
  }

  public String getQueryContent(int i) throws Exception {
    return reader.document(i).getField("QueryContent").stringValue();
  }
  public String getPageContent(int i) throws Exception {
    return reader.document(i).getField("PageContent").stringValue();
  }
  public int getSize() {
    return reader.numDocs();

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

  Map<String, Float> getTfidfResult(int docID) throws Exception {
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
    return tf_Idf_Weights;
  }

}
