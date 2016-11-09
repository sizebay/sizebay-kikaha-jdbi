package sizebay.kikaha.jdbi.cache;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static sizebay.kikaha.jdbi.cache.Stubs.*;
import org.jdbi.v3.sqlobject.Handler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test for {@link CachedMethodHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedMethodHandlerTest {

	@Spy Stubs.ObjectWithTwoMethods objectWithTwoMethods;
	@Mock CacheMechanism cacheMechanism;
	@Mock Handler handle;

	@Test
	public void shouldBeAbleToRetrieveDataFromCacheIfCacheMechanismReturnedAPreviouslyStoredValue() throws Exception {
		doReturn( 32 ).when( cacheMechanism ).loadDataFor( any( SQLObjectMethod.class ) );

		final CachedMethodHandler handler = new CachedMethodHandler(handle, cacheMechanism);
		final Object resultObject = handler.invoke(objectWithTwoMethods, ANNOTATED_METHOD, asArray(), null, null);
		assertEquals( 32, resultObject );

		verify( cacheMechanism ).loadDataFor( any(SQLObjectMethod.class) );
		verify( cacheMechanism, never() ).storeDataFor( any(), any() );
	}

	@Test
	public void shouldBeAbleToStoreDataReturnedFromOriginalMethodIntoTheCache() throws Exception {
		doReturn( null ).when( cacheMechanism ).loadDataFor( any( SQLObjectMethod.class ) );
		doReturn( 42 ).when( handle ).invoke( any(),any(),any(),any(),any() );

		final CachedMethodHandler handler = new CachedMethodHandler(handle, cacheMechanism);
		final Object resultObject = handler.invoke(objectWithTwoMethods, ANNOTATED_METHOD, asArray(), null, null);
		assertEquals( 42, resultObject );

		verify( cacheMechanism ).loadDataFor( any(SQLObjectMethod.class) );
		verify( cacheMechanism ).storeDataFor( any(SQLObjectMethod.class), eq(42) );
	}

}