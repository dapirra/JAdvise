package jadvise.objects;

/**
 * @author David Pirraglia
 */
public class MySQLAccount {
	private String username;
	private String password;
	private String ip;
	private int port;
	private String table;

	public MySQLAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.ip = "127.0.0.1";
		this.port = 3306;
		this.table = "jadvise";
	}

	public MySQLAccount(String username, String password, String ip, int port, String table) {
		this.username = username;
		this.password = password;
		if (ip.endsWith("/")) { // Remove ending forward slash, if included
			this.ip = ip.substring(0, ip.length() - 1);
		} else {
			this.ip = ip;
		}
		this.port = Math.abs(port);
		this.table = table;
	}

	public String getMySQLLink() {
		return "jdbc:mysql://" + ip + ':' + port + '/' + table;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
