package cc.twittertools.util;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator2 implements Comparator<Integer> {

	    Map<Integer, Double> base;
	    public ValueComparator2(Map<Integer,Double> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(Integer a, Integer b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
