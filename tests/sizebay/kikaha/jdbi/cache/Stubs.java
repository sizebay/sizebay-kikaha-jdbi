package sizebay.kikaha.jdbi.cache;

import java.lang.reflect.Method;

/**
 *
 */
public interface Stubs {

	Method
		NON_ANNOTATED_METHOD = retrieveStubMethod( "methodOne", String.class, int.class ),
		ANNOTATED_METHOD = retrieveStubMethod( "methodTwo" );

	@SafeVarargs
	static <T> T[] asArray( T...ts ){
		return ts;
	}

	@SafeVarargs
	static Method retrieveStubMethod( String name, Class<?>...args ){
		try {
			return ObjectWithTwoMethods.class.getDeclaredMethod( name, args );
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unused")
	class ObjectWithTwoMethods {

		Integer methodOne( String firstParameter, int secondParameter ) {
			return null;
		}

		@Cached("cache-endpoint-name")
		Integer methodTwo() { return 42; }
	}
}
