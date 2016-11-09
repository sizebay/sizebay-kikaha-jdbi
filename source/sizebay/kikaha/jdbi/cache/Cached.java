package sizebay.kikaha.jdbi.cache;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.*;
import org.jdbi.v3.sqlobject.SqlMethodDecoratingAnnotation;

/**
 * Marks a SQL Object method as "cacheable". Each time a method annotated with this annotation is invoked,
 * caching behavior will be applied, checking whether the method has been already invoked for the given arguments.
 */
@Inherited
@Documented
@Target(METHOD)
@Retention(RUNTIME)
@SqlMethodDecoratingAnnotation(CachedSQLMethodDecorator.class)
public @interface Cached {

	/**
	 * Defines an alias in which the cache will be stored. This alias can be used later to
	 * configure how the cache would behave (eviction policy, max memory used to cache this elements, etc...).
	 *
	 * @return
	 */
	String value();
}
