package sizebay.kikaha.jdbi.cache.sample;

import java.util.*;
import sizebay.kikaha.jdbi.cache.*;

/**
 * Very, very straightforward In-Memory CacheMechanism implementation designed for test propose only.
 * This class is available as SPI for {@link CacheMechanismFactory} on the Test-Scope's classpath,
 * thus it will be automatically loaded during the tests.
 */
public class LocalCacheFactory implements CacheMechanismFactory {

	@Override
	public CacheMechanism createCacheMechanismFor(String cacheName) {
		return new LocalCache();
	}

	class LocalCache implements CacheMechanism {

		final Map<SQLObjectMethod, Object> cache = new HashMap<>();

		@Override
		public Object loadDataFor(SQLObjectMethod sqlObjectMethod) {
			return cache.get( sqlObjectMethod );
		}

		@Override
		public void storeDataFor(SQLObjectMethod sqlObjectMethod, Object data) {
			cache.put( sqlObjectMethod, data );
		}
	}
}
