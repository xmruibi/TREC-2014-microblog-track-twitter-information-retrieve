package cc.twittertools.index;

import java.io.Reader;





import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.util.Version;

public class PorterStemAnalyzer extends Analyzer {


	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		
		//CharArraySet stopWord = new CharArraySet(ver);
		
        Tokenizer source = new LowerCaseTokenizer(Version.LUCENE_43, reader);
        TokenStream stem = new org.apache.lucene.analysis.en.PorterStemFilter(source);
        TokenStream stopRemoval = new StopFilter(Version.LUCENE_43, stem, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
 
        
        
        return new TokenStreamComponents(source, stopRemoval);
      }
}

