/**
 * 
 */
package com.user.management.utilities;

/**
 * @author dharita.chokshi
 *
 */
public final class Utils {

	private static Utils utils;

	/**
	 * private constructor
	 */
	private Utils() {

	}

	/**
	 * Method to access Utils Singleton object
	 *
	 * @return {@link Utils}
	 */
	public static synchronized Utils getInstance() {
		if (utils == null) {
			utils = new Utils();
		}
		return utils;
	}

	public boolean isEmpty(String text) {
		return (null == text || text.isEmpty()) ? true : false;
	}

}
