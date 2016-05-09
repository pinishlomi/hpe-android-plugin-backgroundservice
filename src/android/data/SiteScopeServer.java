package com.hpe.hybridsitescope.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SiteScopeServer implements Serializable{
	private String protocol;
	private String host;
	private String port;
	private String username;
	private String password;
	private String url;
	private String name;
	private boolean allowUntrustedCerts = false;
	
	public SiteScopeServer(String protocol, String host, String port,
			String username, String password, String name, boolean allowUntrustedCerts) {
		super();
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.name = name;
		this.setAllowUntrustedCerts(allowUntrustedCerts);
	}

	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		if(url==null || url.length()==0)
			return this.protocol + "://" + this.host + ":" + this.port;
		else
			return url;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteScopeServer that = (SiteScopeServer) o;

        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public void setAllowUntrustedCerts(boolean allowUntrustedCerts) {
		this.allowUntrustedCerts = allowUntrustedCerts;
	}

	public boolean isAllowUntrustedCerts() {
		return allowUntrustedCerts;
	}

}
