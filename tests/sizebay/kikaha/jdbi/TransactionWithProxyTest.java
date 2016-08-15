package sizebay.kikaha.jdbi;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import javax.inject.Inject;
import kikaha.core.test.KikahaRunner;
import lombok.SneakyThrows;
import org.junit.*;
import org.junit.runner.RunWith;
import sizebay.kikaha.jdbi.serializers.*;

/**
 *
 */
@RunWith(KikahaRunner.class)
public class TransactionWithProxyTest {

	@Inject
	UserQueries q;

	@Before
	public void initializeDatabase(){
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
			q.insertUserAndRole(paul, "Developer");
		}
		catch ( Throwable c ) {}
	}

	private void ensureThatDoesNotSavedARoleForPaul( User paul ) throws IOException {
		assertEquals( q.retrieveUserRoleByUserId( paul.id ).size(), 1 );
	}
}
