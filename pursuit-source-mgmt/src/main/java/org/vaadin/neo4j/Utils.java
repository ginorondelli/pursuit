package org.vaadin.neo4j;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Utils {

	public static LinkedHashSet<String> enforceSetOrder(Set<String> theSet) {
		TreeSet<String>orderedSet=new TreeSet<String>(theSet);
		return (null!=theSet?new LinkedHashSet<String>(orderedSet):(LinkedHashSet<String>)theSet);
		
	}
	public static boolean checkContentsOfCollections(Set<String>firstCollection, Set<String>secondCollection) {
		boolean equals=false;
		if (null!=firstCollection&&firstCollection.size()>0&&
				null!=secondCollection&&secondCollection.size()>0) {
			for (String first:firstCollection) {
				for (String second:secondCollection) {
					if (!equals) {
						equals=second.equals(first);
					} else {
						break;
					}
				}
			}
		}
		return equals;
	}
	public static boolean checkContentsOfCollections(String theString, Set<String>theCollection) {
		boolean matches=false;
		if (null!=theCollection&&theCollection.size()>0) {
			for (String currentElement: theCollection) {
				if (!matches&&currentElement.equals(theString)) {
					matches=true;
				}
			}
			
		}
		return matches;
	}
	}
