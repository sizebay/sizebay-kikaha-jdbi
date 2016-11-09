package sizebay.kikaha.jdbi.cache;

import java.lang.reflect.Method;
import java.util.*;
import org.jdbi.v3.sqlobject.*;

/**
 * A {@link HandlerDecorator} implementation for SQL Object methods which its result should be cached.
 */
public class CachedSQLMethodDecorator implements HandlerDecorator {

	static List<CacheMechanismFactory> factories = loadCacheMechanismFactories();

	static List<CacheMechanismFactory> loadCacheMechanismFactories() {
		final ServiceLoader<CacheMechanismFactory> factories = ServiceLoader.load(CacheMechanismFactory.class);
		final List<CacheMechanismFactory> factoriesAsList = new ArrayList<>();
		factories.forEach( factoriesAsList::add );
		Collections.sort( factoriesAsList );
		return factoriesAsList;
	}

	@Override
	public Handler decorateHandler(Handler base, Class<?> sqlObjectType, Method method) {
		final Cached annotation = method.getAnnotation(Cached.class);

		if ( annotation != null )
			for ( final CacheMechanismFactory factory : factories ) {
				if ( factory.canHandle( annotation.value() ) ) {
					final CacheMechanism cacheMechanism = factory.createCacheMechanismFor(annotation.value());
					return new CachedMethodHandler( base, cacheMechanism );
				}
			}

		return base;
	}
}
