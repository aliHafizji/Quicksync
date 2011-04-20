/**
 * 
 */
package com.quicksync.twitter.data;

import android.content.SearchRecentSuggestionsProvider;

/**
 * @author torgny.bjers
 *
 */
public class TimelineSearchSuggestionProvider extends SearchRecentSuggestionsProvider {

	public final static String AUTHORITY = "com.quicksync.twitter.TimelineSuggestionProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	/**
	 * 
	 */
	public TimelineSearchSuggestionProvider() {
		super();
    	setupSuggestions(AUTHORITY, MODE);
	}
}
