package kikaha.jdbi.serializers;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.*;

@SuppressWarnings( { "unchecked", "rawtypes" } )
public abstract class ResultSetDataRetriever {

	static final Map<Class<?>, Retriever> RETRIEVERS = new HashMap<>();

	static {
		RETRIEVERS.put( Boolean.class, ResultSetDataRetriever::retrieveBoolean );
		RETRIEVERS.put( boolean.class, ( c, rs, nm ) -> rs.getBoolean( nm ) );
		RETRIEVERS.put( Character.class, ResultSetDataRetriever::retrieveCharacter );
		RETRIEVERS.put( char.class, ResultSetDataRetriever::retrieveChar );
		RETRIEVERS.put( Byte.class, ResultSetDataRetriever::retrieveByte );
		RETRIEVERS.put( byte.class, ( c, rs, nm ) -> rs.getByte( nm ) );
		RETRIEVERS.put( Short.class, ResultSetDataRetriever::retrieveShort );
		RETRIEVERS.put( short.class, ( c, rs, nm ) -> rs.getShort( nm ) );
		RETRIEVERS.put( Integer.class, ResultSetDataRetriever::retrieveInteger );
		RETRIEVERS.put( int.class, ( c, rs, nm ) -> rs.getInt( nm ) );
		RETRIEVERS.put( Long.class, ResultSetDataRetriever::retrieveLong );
		RETRIEVERS.put( long.class, ( c, rs, nm ) -> rs.getLong( nm ) );
		RETRIEVERS.put( Float.class, ResultSetDataRetriever::retrieveFloat );
		RETRIEVERS.put( float.class, ( c, rs, nm ) -> rs.getFloat( nm ) );
		RETRIEVERS.put( Double.class, ResultSetDataRetriever::retrieveDouble );
		RETRIEVERS.put( double.class, ( c, rs, nm ) -> rs.getDouble( nm ) );
		RETRIEVERS.put( BigDecimal.class, ResultSetDataRetriever::retrieveBigDecimal );
		RETRIEVERS.put( Timestamp.class, ResultSetDataRetriever::retrieveTimestamp );
		RETRIEVERS.put( LocalDateTime.class, ResultSetDataRetriever::retrieveLocalDateTime );
		RETRIEVERS.put( LocalDate.class, ResultSetDataRetriever::retrieveLocalDate );
		RETRIEVERS.put( LocalTime.class, ResultSetDataRetriever::retrieveLocalTime );
		RETRIEVERS.put( Time.class, ResultSetDataRetriever::retrieveTime );
		RETRIEVERS.put( Date.class, ResultSetDataRetriever::retrieveDate );
		RETRIEVERS.put( java.sql.Date.class, ( c, rs, nm ) -> rs.getDate( nm ) );
		RETRIEVERS.put( String.class, ( c, rs, nm ) -> rs.getString( nm ) );
		RETRIEVERS.put( UUID.class, ResultSetDataRetriever::retrieveUUID );
	}

	private static UUID retrieveUUID(Class<?> t, ResultSet rs, String nm) throws SQLException {
		final String string = rs.getString(nm);

		return (string != null && !string.isEmpty()) ? UUID.fromString(string) : null;
	}

	static BigDecimal retrieveBigDecimal( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getBigDecimal( nm ) : null;
	}

	static Timestamp retrieveTimestamp( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getTimestamp( nm ) : null;
	}

	static LocalDateTime retrieveLocalDateTime( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getTimestamp(nm).toLocalDateTime() : null;
	}

	static LocalDate retrieveLocalDate( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getTimestamp( nm ).toLocalDateTime().toLocalDate() : null;
	}

	static LocalTime retrieveLocalTime( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getTimestamp( nm ).toLocalDateTime().toLocalTime() : null;
	}

	static Date retrieveDate( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getTimestamp( nm ) : null;
	}

	static Time retrieveTime( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? rs.getTime( nm ) : null;
	}

	static Boolean retrieveBoolean( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );

		try {
			return Integer.parseInt( string ) == 1;
		} catch ( NumberFormatException e ) {
			return ( string != null && !string.isEmpty() ) ? Boolean.getBoolean( string ) : null;
		}
	}

	static Byte retrieveByte( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Byte.parseByte(string) : null;
	}

	static Short retrieveShort( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Short.parseShort(string) : null;
	}

	static Integer retrieveInteger( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Integer.valueOf(string) : null;
	}

	static Long retrieveLong( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Long.valueOf(string) : null;
	}

	static Float retrieveFloat( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Float.parseFloat(string) : null;
	}

	static Double retrieveDouble( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Double.parseDouble(string) : null;
	}

	static Character retrieveCharacter( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? string.charAt( 0 ) : null;
	}

	static char retrieveChar( Class<?> t, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? string.charAt( 0 ) : ' ';
	}

	public static Retriever getDataRetrieverFor( Class<?> type ) {
		Retriever retriever = RETRIEVERS.get( type );
		if ( retriever != null )
			return retriever;
		if ( type.isEnum() )
			return findAndRegisterEnumRetriever( type );
		return ResultSetDataRetriever::throwExceptionForUnknowType;
	}

	private static Retriever findAndRegisterEnumRetriever(Class<?> type) {
		final Retriever retriever = ResultSetDataRetriever::retrieveEnum;
		registerRetrieverFor(type, retriever);
		return retriever;
	}

	static Object retrieveEnum( Class<?> enumType, ResultSet rs, String nm ) throws SQLException {
		final String string = rs.getString( nm );
		return ( string != null && !string.isEmpty() ) ? Enum.valueOf( (Class<? extends  Enum>)enumType, string ) : null;
	}

	static Object throwExceptionForUnknowType( Class<?> enumType, ResultSet rs, String nm ) throws SQLException {
		throw new SQLException( "No retriever defined for type " + enumType );
	}

	public static void registerRetrieverFor( Class<?> type, Retriever retriever ) {
		RETRIEVERS.put( type, retriever );
	}
}