package android.data;

import android.rest.RestCalls;

@SuppressWarnings("serial")
public class Group extends Entity {

	public Group(android.data.SiteScopeServer siteScopeServer) {
		super(siteScopeServer);
        setEntityType(RestCalls.GROUP_ENITYTYPE);
	}

}
