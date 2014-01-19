/**
 * 
 */
package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Dec 27, 2013
 */
public class PostgreSQLHelper {
	private Connection connection;

	/**
	 * @return the connection
	 */
	public PostgreSQLHelper(String url, String user, String password) {
		try {
			setConnection(DriverManager.getConnection(url, user, password));

		} catch (SQLException e) {
			System.out
					.println("No se pudo leer la definicion de la base de datos :"
							+ e.getMessage());

		}
	}

	public ResultSet getData(String query) {

		try {
			Statement statement = getConnection().createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			System.out
					.println("No se pudo leer la definicion de la base de datos :"
							+ e.getMessage());
			// Ver para retornar una lista vacia.
			return null;
		}

	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	private void setConnection(Connection connection) {
		this.connection = connection;
	}

}
