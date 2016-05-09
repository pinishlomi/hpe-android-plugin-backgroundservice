package com.hpe.android.plugin.backgroundservice.data;


import com.hpe.android.plugin.backgroundservice.rest.RestCalls;

@SuppressWarnings("serial")
public class Group extends Entity {

	public Group(SiteScopeServer siteScopeServer) {
		super(siteScopeServer);
        setEntityType(RestCalls.GROUP_ENITYTYPE);
	}

}
