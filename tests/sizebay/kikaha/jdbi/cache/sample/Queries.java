package sizebay.kikaha.jdbi.cache.sample;

import org.jdbi.v3.sqlobject.SqlQuery;
import sizebay.kikaha.jdbi.JDBI;
import sizebay.kikaha.jdbi.cache.Cached;

/**
 *
 */
@JDBI
public interface Queries {

	default long returnASolutionForMyProblem(){
		return returnTheSolutionForTheUniverseAndEverythingElse();
	}

	@Cached("the-solution")
	@SqlQuery("SELECT NOW()")
	long returnTheSolutionForTheUniverseAndEverythingElse();
}
