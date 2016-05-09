package com.hpe.android.plugin.backgroundservice.utils;

public class SiteScopeError {

	private static final String CODE_55200 = "55200"; //entity unavailable
	private static final String CODE_55201 = "55201"; //entity unavailable
	public static final String CODE_55524 = "55524"; //authorization problem

	public static String getErrorMsg(String code)
	{		
		if(code.equals(CODE_55524))
			return "User is not authorized to perform requested operation.";
		else
			return "The Application failed to take an action";
	}
	
	public static String getMonitorErrorMsg(String code)
	{		
		if(code.equals(CODE_55200) || code.equals(CODE_55201))
			return "Monitor is unavailable.";
		else
			return getErrorMsg(code);
	}
	
	public static String getGroupErrorMsg(String code)
	{		
		if(code.equals(CODE_55200) || code.equals(CODE_55201))
			return "Group is unavailable.";
		else
			return getErrorMsg(code);
	}
}
