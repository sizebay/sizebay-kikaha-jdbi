package sizebay.kikaha.jdbi.cache;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static sizebay.kikaha.jdbi.cache.CachedSQLMethodDecorator.loadCacheMechanismFactories;
import static sizebay.kikaha.jdbi.cache.Stubs.ANNOTATED_METHOD;
import static sizebay.kikaha.jdbi.cache.Stubs.NON_ANNOTATED_METHOD;
import org.jdbi.v3.sqlobject.Handler;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sizebay.kikaha.jdbi.cache.sample.LocalCacheFactory;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedSQLMethodDecoratorTest {

	@Mock Handler baseHandler;
	@Mock CacheMechanismFactory factory;

	@Before
	public void reloadFactories(){
		CachedSQLMethodDecorator.factories = loadCacheMechanismFactories();
	}

	@Test
	public void ensureCanNotHandleMethodsNotAnnotatedWithCachedAnnotation(){
		final CachedSQLMethodDecorator decorator = new CachedSQLMethodDecorator();
		final Handler handler = decorator.decorateHandler(baseHandler, Stubs.ObjectWithTwoMethods.class, NON_ANNOTATED_METHOD);
		assertEquals( handler, baseHandler );
	}

	@Test
	public void ensureCanNotAnnotatedHandleMethodsWhichFactoriesIsNotAbleToCache(){
		CachedSQLMethodDecorator.factories = asList( factory );
		doReturn( false ).when( factory ).canHandle( anyString() );

		final CachedSQLMethodDecorator decorator = new CachedSQLMethodDecorator();
		final Handler handler = decorator.decorateHandler(baseHandler, Stubs.ObjectWithTwoMethods.class, ANNOTATED_METHOD);
		assertEquals( handler, baseHandler );
	}

	@Test
	public void ensureCanHandleAnnotatedMethodsWhichFactoriesIsAbleToCache(){
		CachedSQLMethodDecorator.factories = asList( factory );
		doReturn( true ).when( factory ).canHandle( anyString() );

		final CachedSQLMethodDecorator decorator = new CachedSQLMethodDecorator();

		final Handler handler = decorator.decorateHandler(baseHandler, Stubs.ObjectWithTwoMethods.class, ANNOTATED_METHOD);
		assertTrue( CachedMethodHandler.class.isInstance( handler ) );
	}

	@Test
	public void ensureIsAbleToLoadAllFactories(){
		assertEquals( 2, CachedSQLMethodDecorator.factories.size() );
		assertTrue( DumpCacheMechanismFactory.class.isInstance( CachedSQLMethodDecorator.factories.get(0) ) );
		assertTrue( LocalCacheFactory.class.isInstance( CachedSQLMethodDecorator.factories.get(1) ) );
	}
}
