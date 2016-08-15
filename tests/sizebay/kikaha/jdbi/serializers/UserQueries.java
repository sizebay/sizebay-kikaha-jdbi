package sizebay.kikaha.jdbi.serializers;

import java.util.List;
import org.jdbi.v3.sqlobject.*;
import sizebay.kikaha.jdbi.JDBI;

@JDBI
public interface UserQueries {

	default void initializeDatabase() {
		deleteUsers();
		deleteRoles();
		populateUserTable();
		populateRoleTable();
	}

	@SqlUpdate( "DELETE FROM users" )
	void deleteUsers();

	@SqlUpdate( "DELETE FROM roles" )
	void deleteRoles();

	@SqlUpdate( "INSERT INTO users(id,user_name) VALUES (1,'Joseph'), (2,'John')" )
	void populateUserTable();

	@SqlUpdate( "INSERT INTO roles(user_id, role_name) VALUES (1,'Manager'), (2,'Executive')" )
	void populateRoleTable();

	@Transaction
	default void insertUserAndRole( User user, String role ) {
		setUserRole( user.id, role );
		insertUser( user );
	}

	default void insertUserAndRoleNonTransactional( User user, String role ) {
		setUserRole( user.id, role );
		insertUser( user );
	}

	@SqlUpdate( "INSERT INTO users(id, user_name) VALUES (:id, :name)" )
	void insertUser(@BindBean User user);

	@SqlUpdate( "INSERT INTO roles(user_id, role_name) VALUES (:user_id, :name)" )
	void setUserRole( @Bind("user_id") long userId, @Bind("name") String name );

	@SqlQuery( "SELECT * FROM users WHERE id = :id" )
	User retrieveUserById( @Bind("id") long id );

	@SqlQuery( "SELECT role_name FROM roles WHERE user_id = :id" )
	List<String> retrieveUserRoleByUserId(@Bind("id") long id );

	@SqlQuery( "SELECT u.id, u.user_name, r.role_name FROM users u JOIN roles r ON r.user_id = u.id WHERE id = :id" )
	User retrieveUserFullProfileById( @Bind("id") long id );
}
