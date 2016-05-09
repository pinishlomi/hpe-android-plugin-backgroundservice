package android.data;

public class SiteScopeServerHelper {

	public static final String PROTOCOL = "protocol";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	public static final String COLON = ":";
	public static final String DOUBLE_FORWARD_SLASHES = "//";
	public static final String FORWARD_SLASH = "/";
	
	/**
	 * construct a URL from the protocol, host and port passed in
	 * @param protocol
	 * @param host
	 * @param port
	 * @return
	 */
	public static String getUrl(String protocol, String host, String port)
	{
		return protocol.concat(SiteScopeServerHelper.COLON)
				.concat(SiteScopeServerHelper.DOUBLE_FORWARD_SLASHES)
				.concat(host)
				.concat(SiteScopeServerHelper.COLON)
				.concat(port);
	}
}
