package com.hpe.hybridsitescope.utils;

import com.hpe.hybridsitescope.data.Entity;

import java.util.Comparator;

/**
 * comparator for entities, helps sort the entities on the home screen and the search results screen
 * should be by sitescope name and then by entity name in alphabetical order
 */
public class EntityComparator implements Comparator<Object> {

	@Override
	public int compare(Object entity1, Object entity2) {
		String str1 = ((Entity)entity1).getSiteScopeServer().getName().toLowerCase().concat(((Entity)entity1).getName().toLowerCase());
		String str2 = ((Entity)entity2).getSiteScopeServer().getName().toLowerCase().concat(((Entity)entity2).getName().toLowerCase());
	
		return str1.compareTo(str2);
	}

}
