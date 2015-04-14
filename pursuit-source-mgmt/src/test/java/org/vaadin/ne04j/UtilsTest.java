package org.vaadin.ne04j;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.neo4j.Utils;

public class UtilsTest {

	Set<String>firstCollection=new LinkedHashSet<String>();
	Set<String>secondCollection=new LinkedHashSet<String>();
	
	@Before
	public void setUp() throws Exception {
		
	}
	@After
	public void flatten() {
		firstCollection.clear();
		secondCollection.clear();
	}

	@Test
	public void testCheckContentsOfCollectionsPass() {
		firstCollection.add("Test String 1");
		secondCollection.add("Test String 1");
		assertTrue(Utils.checkContentsOfCollections(firstCollection, secondCollection));
	}
	@Test
	public void testCheckContentsOfCollectionsFail() {
		firstCollection.add("Test String 1");
		secondCollection.add("Test String 2");
		assertFalse(Utils.checkContentsOfCollections(firstCollection, secondCollection));
	}
	@Test
	public void testCheckContentsOfCollectionsPassWithMoreElements() {
		firstCollection.add("Test String 1");
		secondCollection.add("Test String 1");
		secondCollection.add("Test String 2");
		secondCollection.add("Test String 12");
		assertTrue(Utils.checkContentsOfCollections(firstCollection, secondCollection));
	}
	@Test
	public void testCheckContentsWithStringPass() {
		firstCollection.add("Test String 1");
		assertTrue(Utils.checkContentsOfCollections("Test String 1", firstCollection));
	}
	@Test
	public void testCheckContentsWithStringFail() {
		firstCollection.add("Test String 1");
		assertFalse(Utils.checkContentsOfCollections("Test String 2", firstCollection));
	}
}
