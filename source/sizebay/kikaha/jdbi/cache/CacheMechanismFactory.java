package sizebay.kikaha.jdbi.cache;

/**
 * Creates {@link CacheMechanism}.
 */
public interface CacheMechanismFactory extends Comparable<CacheMechanismFactory> {

	/**
	 * Creates a {@link CacheMechanism} for a given {@code cacheName}.
	 * @param cacheName
	 * @return
	 */
	CacheMechanism createCacheMechanismFor( String cacheName );

	/**
	 * Defines if this factory is able to create a {@link CacheMechanism} to a given cache name. By default it will
	 * return true whenever the {@code cacheName} is neither null nor empty. Developers are encouraged to override this
	 * method and provide a better algorithm once some cache mechanisms are configuration sensible. If the cache is
	 * stored on an external cache database like Redis or Hazelcast then it would useful to check if sensible
	 * configurations related to this cache endpoint is available on a configuration file or on a centralized service
	 * like ZooKeeper or Consul.io.
	 *
	 * @param cacheName
	 * @return
	 */
	default boolean canHandle( String cacheName ) {
		return cacheName != null && !cacheName.isEmpty();
	}

	/**
	 * Defines the priority of this factory. It is useful to avoid conflicts when more than one
	 * cache mechanism is available and the {@link CacheMechanismFactory#canHandle(String)} was not override.
	 *
	 * @return
	 */
	default int priority(){
		return Integer.MIN_VALUE;
	}

	/**
	 * Compare two {@link CacheMechanismFactory}. Used to sort factories by priority.
	 *
	 * @param o
	 * @return
	 */
	default int compareTo(CacheMechanismFactory o) {
		return Integer.compare( priority(), o.priority() );
	}
}
