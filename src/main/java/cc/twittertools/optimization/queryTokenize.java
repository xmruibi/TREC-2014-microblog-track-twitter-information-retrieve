package cc.twittertools.optimization;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cc.twittertools.index.PorterStemAnalyzer;
//import edu.pitt.sis.iris.squirrel.analysis.TextAnalyzer;

import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.IOException;

public class queryTokenize {
  private TokenStream ts;
  private Analyzer analyzer;
  private String indexPath = "QueryResult4MB171";
  protected File dir;
  private Directory directory;
  private IndexReader reader;
//  private String fieldname = "PageContent";
  TFIDFSimilarity tfidfSIM = new DefaultSimilarity();
//
  public queryTokenize() throws Exception {

    try {
      // get the stopwords inputstream
      analyzer= new PorterStemAnalyzer();
    } catch (Exception e) {
      System.out.println(e);
    }
//    dir = new File(indexPath);
//    directory = FSDirectory.open(dir);
//    reader = DirectoryReader.open(directory);
  }
	public String toStringArray(String query) throws IOException{
	  
		String newQuery=query.toLowerCase();
		String[] queryTerms=newQuery.split(" ");
		String queryStemming ="";
		for (String term : queryTerms) {
		  
      Reader stringreader=new StringReader(term);
      ts = analyzer.tokenStream(null, stringreader);
      CharTermAttribute charTermAttribute = ts.getAttribute(CharTermAttribute.class);
      ts.reset();
      while (ts.incrementToken()) {
        String queryTerm = charTermAttribute.toString();
        queryStemming = queryStemming+" "+queryTerm;
        }
      }
     queryStemming = queryStemming.trim();
		return queryStemming;	
	}
	public String getTweetContent(int i) throws Exception {
	   return reader.document(i).getField("TweetContent").stringValue();
	 }
	public static void main(String []args) throws Exception{
	  queryTokenize qt = new queryTokenize();
	  try {
//	    System.out.println(qt.getTweetContent(1));
      System.out.println(qt.toStringArray("harry poter ") );
   
    } catch (IOException e) {
      // TODO Auto-generated catch block
       
    }
	}

}
