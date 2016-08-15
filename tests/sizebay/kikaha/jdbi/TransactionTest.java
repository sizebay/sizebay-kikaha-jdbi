package sizebay.kikaha.jdbi;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import javax.inject.Inject;
import javax.sql.DataSource;
import kikaha.core.test.KikahaRunner;
import lombok.SneakyThrows;
import org.jdbi.v3.core.Jdbi;
import org.junit.*;
import org.junit.runner.RunWith;
import sizebay.kikaha.jdbi.serializers.*;

/**
 *
 */
@RunWith(KikahaRunner.class)
public class TransactionTest {

	@Inject
	DataSource dataSource;

	Jdbi db;

	@Before
	@SneakyThrows
	public void initializeDatabase(){
		db = Jdbi.create(dataSource);
		db.registerColumnMapper( new AnnotatedEntityMapperFactory() );
		db.installPlugins();

		final UserQueries q = db.onDemand( UserQueries.class );
		q.initializeDatabase();
	}

	@Test
	@SneakyThrows
	public void ensureThatTransactionalMethodWorksIfSomeQueryFails(){
		final User paul = new User();
		paul.id = 2l;
		paul.name = "Paul";

		savePaulInDB( paul );
		ensureThatDoesNotSavedARoleForPaul( paul );
	}

	private void savePaulInDB( User paul ){
		try {
			final UserQueries q = db.onDemand(UserQueries.class);
			q.insertUserAndRole(paul, "Developer");
		}
		catch ( Throwable c ) {}
	}

	private void ensureThatDoesNotSavedARoleForPaul( User paul ) throws IOException {
		final UserQueries q2 = db.onDemand( UserQueries.class );
		assertEquals( q2.retrieveUserRoleByUserId(paul.id).size(), 1 );
	}
}
