package org.vaadin.neo4j;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Utils {

	public static LinkedHashSet<String> enforceSetOrder(Set<String> theSet) {
		TreeSet<String>orderedSet=new TreeSet<String>(theSet);
		return (null!=theSet?new LinkedHashSet<String>(orderedSet):(LinkedHashSet<String>)theSet);
		
	}
}
