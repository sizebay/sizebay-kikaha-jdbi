package sizebay.kikaha.jdbi.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static sizebay.kikaha.jdbi.cache.Stubs.*;
import org.junit.Test;

/**
 * Unit tests for {@link SQLObjectMethod}.
 */
public class SqlObjectMethodTest {

	@Test
	public void ensureThatIsAbleToMatchSQLMethodsWithSameParameters(){
		final SQLObjectMethod first = new SQLObjectMethod(NON_ANNOTATED_METHOD, asArray("First", 1));
		final SQLObjectMethod another = new SQLObjectMethod(NON_ANNOTATED_METHOD, asArray("First", 1));
		assertEquals( first, another );
	}

	@Test
	public void ensureThatIsNotAbleToMatchSQLMethodsWithDifferentParameters(){
		final SQLObjectMethod first = new SQLObjectMethod(NON_ANNOTATED_METHOD, asArray("First", 2));
		final SQLObjectMethod another = new SQLObjectMethod(NON_ANNOTATED_METHOD, asArray("First", 1));
		assertNotEquals( first, another );
	}

	@Test
	public void ensureThatIsNotAbleToMatchDifferentSQLMethods(){
		final SQLObjectMethod first = new SQLObjectMethod(NON_ANNOTATED_METHOD, asArray("First", 1));
		final SQLObjectMethod another = new SQLObjectMethod(ANNOTATED_METHOD, asArray());
		assertNotEquals( first, another );
	}

	@Test
	public void ensureThatIsNotAbleToMatchDifferentSQLMethodsEvenWithSameParameters(){
		final SQLObjectMethod first = new SQLObjectMethod(NON_ANNOTATED_METHOD, asArray("First", 1));
		final SQLObjectMethod another = new SQLObjectMethod(ANNOTATED_METHOD, asArray("First", 1));
		assertNotEquals( first, another );
	}
}
