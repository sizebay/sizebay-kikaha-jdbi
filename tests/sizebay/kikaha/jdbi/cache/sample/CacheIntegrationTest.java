package sizebay.kikaha.jdbi.cache.sample;

import static org.junit.Assert.assertEquals;
import javax.inject.Inject;
import kikaha.core.test.KikahaRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 */
@RunWith(KikahaRunner.class)
public class CacheIntegrationTest {

	@Inject Queries queries;

	@Test(timeout = 1500)
	public void ensureThatTheSecondExecutionOfACachedMethodWontCallTheOriginalMethod(){
		final long firstResult = queries.returnASolutionForMyProblem();
		assertEquals( firstResult, queries.returnASolutionForMyProblem() );
		assertEquals( firstResult, queries.returnASolutionForMyProblem() );
	}
}
