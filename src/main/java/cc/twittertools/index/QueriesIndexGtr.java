package cc.twittertools.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cc.twittertools.entity.QueryList;
import cc.twittertools.entity.TweetList;
//import edu.pitt.sis.iris.squirrel.analysis.TextAnalyzer;

public class QueriesIndexGtr {
  FieldType type = new FieldType();
 String filePath="QueriesIndex";
  Directory directory;
  IndexWriter indexWriter;
  
  public QueriesIndexGtr() throws Exception {
    directory = FSDirectory.open(new File(filePath));
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, new PorterStemAnalyzer());
    indexWriter = new IndexWriter(directory, indexWriterConfig);
  }
  
  public void createIndex(QueryList queryList) throws Exception {

    type.setIndexed(true);
    type.setStored(true);
    type.setTokenized(true);
    type.setStoreTermVectors(true);

    Document document = new Document();
    Field QueryID = new Field("QueryID", queryList.getQueryId(), type);
    Field QueryContent = new Field("QueryContent", queryList.getQueryContent(), type);
    Field PageContent = new Field("PageContent", queryList.getURLPage(), type);
    // Field dateField = new Field("date", content.getdate().toString(), type);

    document.add(QueryID);
    document.add(QueryContent);
    document.add(PageContent);
    indexWriter.addDocument(document);
  }

  public void closeIndexGtr() throws Exception {
    indexWriter.close();  
    directory.close();
  }
}
