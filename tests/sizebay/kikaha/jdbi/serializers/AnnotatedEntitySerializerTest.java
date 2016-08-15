package sizebay.kikaha.jdbi.serializers;

import static org.junit.Assert.*;
import javax.inject.Inject;
import javax.sql.DataSource;
import kikaha.core.test.KikahaRunner;
import lombok.SneakyThrows;
import org.jdbi.v3.core.Jdbi;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith( KikahaRunner.class )
public class AnnotatedEntitySerializerTest {

	@Inject DataSource dataSource;

	Jdbi db;

	@Before
	@SneakyThrows
	public void initializeDatabase(){
		db = Jdbi.create(dataSource);
		db.registerColumnMapper( new AnnotatedEntityMapperFactory() );
		db.installPlugins();
	}

	@Test
	@SneakyThrows
	public void ensureThatCanRetrieveAnnotatedEntity() {
		UserQueries q = db.onDemand( UserQueries.class );
		final User user = q.retrieveUserById( 1 );
		ensureIsValidUser( user );
		assertNull( user.role );
	}

	@Test
	@SneakyThrows
	public void ensureThatCanRetrieveAnnotatedEntityWithInvalidColumnSet() {
		UserQueries q = db.onDemand( UserQueries.class );
			final User user = q.retrieveUserFullProfileById( 1 );
			ensureIsValidUser( user );
			assertEquals( "Manager", user.role );
	}

	private void ensureIsValidUser(final User user ) {
		assertNotNull( "No user found", user );
		assertEquals( 1L, user.id, 0 );
		assertEquals( "Joseph", user.name );
		assertTrue( user.calledSetterMethodOfDomainField );
	}

}
