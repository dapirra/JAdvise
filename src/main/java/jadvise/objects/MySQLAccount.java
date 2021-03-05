package jadvise.objects;

/**
 * @author David Pirraglia
 */
public class MySQLAccount {
	private String username;
	private String password;
	private String ip;
	private int port;
	private String database;

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
		this.ip = "127.0.0.1";
		this.port = 3306;
		this.database = "jadvise";
	}

	/**
	 * Stores information used to connect to a MySQL Server.
	 *
	 * @param username The username to log into the MySQL server with.
	 * @param password The password to log into the MySQL server with.
	 * @param ip       The ip or domain of the server.
	 * @param port     The port of the server.
	 * @param database The name of the database.
	 */
	public MySQLAccount(String username, String password, String ip, int port, String database) {
		this.username = username;
		this.password = password;
		if (ip.endsWith("/")) { // Remove ending forward slash, if included
			this.ip = ip.substring(0, ip.length() - 1);
		} else {
			this.ip = ip;
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
		return "jdbc:mysql://" + ip + ':' + port
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
