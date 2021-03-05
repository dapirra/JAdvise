package jadvise.objects;

/**
 * @author David Pirraglia
 */
public class MySQLAccount {
	private final String username;
	private final String password;
	private final String ipDomain;
	private final int port;
	private final String database;

	/**
	 * Stores information used to connect to a MySQL Server. IP, Port, and
	 * name of the database are assumed with this constructor.
	 *
	 * @param username The username to log into the MySQL server with.
	 * @param password The password to log into the MySQL server with.
	 */
	public MySQLAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.ipDomain = "127.0.0.1";
		this.port = 3306;
		this.database = "jadvise";
	}

	/**
	 * Stores information used to connect to a MySQL Server.
	 *
	 * @param username The username to log into the MySQL server with.
	 * @param password The password to log into the MySQL server with.
	 * @param ipDomain The ip or domain of the server.
	 * @param port     The port of the server.
	 * @param database The name of the database.
	 */
	public MySQLAccount(String username, String password, String ipDomain, int port, String database) {
		this.username = username;
		this.password = password;
		if (ipDomain.endsWith("/")) { // Remove ending forward slash, if included
			this.ipDomain = ipDomain.substring(0, ipDomain.length() - 1);
		} else {
			this.ipDomain = ipDomain;
		}
		this.port = Math.abs(port);
		this.database = database;
	}

	/**
	 * Generates a link that is used to connect to the MySQL server.
	 *
	 * @param includeDatabase Whether or not the name of the database should be
	 *                        included in the link.
	 * @return A link used to connect to the MySQL server.
	 */
	public String getMySQLLink(boolean includeDatabase) {
		return "jdbc:mysql://" + ipDomain + ':' + port
				+ (includeDatabase ? '/' + database : "")
				+ "?characterEncoding=utf-8&connectionCollation=utf8mb4_unicode_520_ci";
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabase() {
		return database;
	}
}
