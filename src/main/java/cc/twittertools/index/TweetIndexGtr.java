package cc.twittertools.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.pitt.sis.iris.squirrel.analysis.TextAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

import cc.twittertools.entity.TweetList;
import cc.twittertools.search.TrecTopicSet;
import cc.twittertools.search.api.TrecSearchThriftClient;

public class TweetIndexGtr {

  FieldType type = new FieldType();

  Directory directory;
  IndexWriter indexWriter;

  public TweetIndexGtr(String filePath) throws Exception {
    File myFilePath = new File(filePath);
    if (!myFilePath.exists()) {
      myFilePath.mkdir();
    }
    directory = FSDirectory.open(new File(filePath));
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, new PorterStemAnalyzer());
    indexWriter = new IndexWriter(directory, indexWriterConfig);
  
  }

  public void createIndex(TweetList tweetList) throws Exception {

    type.setIndexed(true);
    type.setStored(true);
    type.setTokenized(true);
    type.setStoreTermVectors(true);

    Document document = new Document();
    Field QueryID = new Field("QueryID", tweetList.getQueryId(), type);
    Field TwitterID = new Field("TwitterID", tweetList.getTweetId(), type);
    Field TweetContent = new Field("TweetContent", tweetList.getTweetContent(), type);
    Field PageContent = new Field("PageContent", tweetList.getTweetURLPage(), type);
    // Field dateField = new Field("date", content.getdate().toString(), type);

    document.add(QueryID);
    document.add(TwitterID);
    document.add(TweetContent);
    document.add(PageContent);
    indexWriter.addDocument(document);
  }

  public void closeIndexGtr() throws Exception {
    indexWriter.close();  
    directory.close();
  }
}
