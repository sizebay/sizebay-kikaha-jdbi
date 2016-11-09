package sizebay.kikaha.jdbi.cache;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.HandleSupplier;
import org.jdbi.v3.sqlobject.*;

/**
 * A {@link Handler} implementation that checks for cached results before actually execute the expected the method.
 */
@RequiredArgsConstructor
public class CachedMethodHandler implements Handler {

	final Handler base;
	final CacheMechanism cacheMechanism;

	@Override
	public Object invoke(Object target, Method method, Object[] args, SqlObjectConfig config, HandleSupplier handle) throws Exception {
		final SQLObjectMethod sqlObjectMethod = new SQLObjectMethod(method, args);
		Object foundData = cacheMechanism.loadDataFor(sqlObjectMethod);
		if ( foundData == null ) {
			foundData = base.invoke( target, method, args, config, handle );
			cacheMechanism.storeDataFor( sqlObjectMethod, foundData );
		}
		return foundData;
	}
}
