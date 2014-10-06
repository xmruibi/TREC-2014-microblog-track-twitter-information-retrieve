package cc.twittertools.search;

public class QueryTokenize {
	public String[] toStringArray(String query){
		String newQuery=query.toLowerCase();
		String[] returnQuery=newQuery.split(" ");
		return returnQuery;	
	}

}
