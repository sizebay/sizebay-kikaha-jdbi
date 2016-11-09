package sizebay.kikaha.jdbi.cache;

/**
 * Defines how a cache mechanism would retrieve or store data.
 */
public interface CacheMechanism {

	/**
	 * Load the data previously stored for a given {@link SQLObjectMethod}.
	 *
	 * @param sqlObjectMethod
	 * @return
	 */
	Object loadDataFor( SQLObjectMethod sqlObjectMethod );

	/**
	 * Store data for a given {@link SQLObjectMethod}.
	 *
	 * @param sqlObjectMethod
	 * @param data
	 */
	void storeDataFor( SQLObjectMethod sqlObjectMethod, Object data );
}
