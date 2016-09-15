package sizebay.kikaha.jdbi;

import java.lang.reflect.*;
import java.util.*;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.*;
import org.jdbi.v3.core.argument.*;
import org.jdbi.v3.core.spi.JdbiPlugin;

/**
 * Temporary fix of JDBI's #478 issue.<br>
 * Link: https://github.com/jdbi/jdbi/issues/478
 */
@Slf4j
@Singleton
@Deprecated
public class ListJDBIArgumentFactory implements ArgumentFactory, JdbiPlugin {

	final ArgumentFactory delegate = new BuiltInArgumentFactory();

	@Override
	public Optional<Argument> build(final Type type, final Object o, final StatementContext statementContext) {
		if ( type instanceof ParameterizedType ) {
			final ParameterizedType pType = (ParameterizedType)type;
			final boolean isCollection = Collection.class.isAssignableFrom( (Class)pType.getRawType() );
			if ( isCollection )
				return Optional.of( new NullArgument(0) );
		}
		return delegate.build( type, o, statementContext );
	}

	@Override
	public void customizeDbi(Jdbi dbi) {
		log.warn( "Registering a temporary ArgumentFactory. Please consider watch to issue https://github.com/jdbi/jdbi/issues/478." );
		dbi.registerArgumentFactory( this );
	}
}
