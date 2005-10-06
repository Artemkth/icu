/*
 *******************************************************************************
 * Copyright (C) 1996-2005, International Business Machines Corporation and    *
 * others. All Rights Reserved.                                                *
 *******************************************************************************
 */
package com.ibm.icu.dev.test.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;

public final class CollectionUtilities {
	/**
	 * Utility like Arrays.asList()
	 */
	public static Map asMap(Object[][] source, Map target, boolean reverse) {
		int from = 0, to = 1;
		if (reverse) {
			from = 1; to = 0;
		}
    	for (int i = 0; i < source.length; ++i) {
    		target.put(source[i][from], source[i][to]);
    	}
    	return target;
	}
	
	public static Map asMap(Object[][] source) {
    	return asMap(source, new HashMap(), false);
	}
	
	/**
	 * Utility that ought to be on Map
	 */
	public static Map removeAll(Map m, Collection itemsToRemove) {
	    for (Iterator it = itemsToRemove.iterator(); it.hasNext();) {
	    	Object item = it.next();
	    	m.remove(item);
	    }
	    return m;
	}
	
	public Object getFirst(Collection c) {
		Iterator it = c.iterator();
		if (!it.hasNext()) return null;
		return it.next();
	}
	
	public static Object getBest(Collection c, Comparator comp, int direction) {
		Iterator it = c.iterator();
		if (!it.hasNext()) return null;
		Object bestSoFar = it.next();
		while (it.hasNext()) {
			Object item = it.next();
			if (comp.compare(item, bestSoFar) == direction) {
				bestSoFar = item;
			}
		}
		return bestSoFar;
	}
	
	public interface Filter {
		boolean matches(Object o);
	}

	public static Collection removeAll(Collection c, Filter f) {
		for (Iterator it = c.iterator(); it.hasNext();) {
			Object item = it.next();
			if (f.matches(item)) it.remove();
		}
		return c;
	}
	
	public static Collection retainAll(Collection c, Filter f) {
		for (Iterator it = c.iterator(); it.hasNext();) {
			Object item = it.next();
			if (!f.matches(item)) it.remove();
		}
		return c;
	}
	
	public static String remove(String source, UnicodeSet removals) {
		StringBuffer result = new StringBuffer();
		int cp;
		for (int i = 0; i < source.length(); i += UTF16.getCharCount(cp)) {
			cp = UTF16.charAt(source, i);
			if (!removals.contains(cp)) UTF16.append(result, cp);
		}
		return result.toString();
	}
}