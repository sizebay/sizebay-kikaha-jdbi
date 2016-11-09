package sizebay.kikaha.jdbi.cache;

/**
 *
 */
public class DumpCacheMechanismFactory implements CacheMechanismFactory {

	@Override
	public CacheMechanism createCacheMechanismFor(String cacheName) {
		throw new UnsupportedOperationException("createCacheMechanismFor not implemented yet!");
	}

	@Override
	public boolean canHandle(String cacheName) {
		return false;
	}
}
