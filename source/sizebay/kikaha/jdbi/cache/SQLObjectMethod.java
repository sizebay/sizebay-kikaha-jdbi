package sizebay.kikaha.jdbi.cache;

import java.lang.reflect.Method;
import lombok.Value;

/**
 * Store data that represents a given SQL Object method.
 */
@Value
public class SQLObjectMethod {

	final Method method;
	final Object[] args;
}
