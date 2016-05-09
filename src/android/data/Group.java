package com.hpe.hybridsitescope.data;

import com.hpe.hybridsitescope.soap.SoapCalls;

@SuppressWarnings("serial")
public class Group extends Entity{

	public Group(SiteScopeServer siteScopeServer) {
		super(siteScopeServer);
        setEntityType(SoapCalls.GROUP_ENITYTYPE);
	}

}
