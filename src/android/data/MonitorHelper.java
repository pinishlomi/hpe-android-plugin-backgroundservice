package com.hpe.hybridsitescope.data;

import com.hpe.hybridsitescope.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitorHelper {
	
	public static final String POSITION = "position";
	public static final String ENTITY_OBJECT = "entity";
	public static final String UPDATED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";//"d MMM yy (hh:mm)";
	public static final String DATETIME_FORMAT = "MMM dd yyyy hh:mm a"; 
	
	/* values returned by getMonitorSnapshots soap call */
	public static final String MONITOR_NAME = "name";
	public static final String MONITOR_TARGET_NAME = "target_name";
	public static final String MONITOR_TARGET_DISPLAY_NAME = "target_display_name";
	public static final String MONITOR_ROW_DATA = "row_data";
	public static final String MONITOR_FULL_PATH = "full_path";
	public static final String MONITOR_AVAILABILITY = "availability";
	public static final String MONITOR_DISABLE_END_TIME = "disable_end_Time";
	public static final String MONITOR_IS_ASSOCIATED_ALERTS_DISABLED = "is_associated_alerts_disabled";
	public static final String MONITOR_ASSOCIATED_ALERTS_DISABLE_START_TIME = "associated_alerts_disable_start_time";
	public static final String MONITOR_ACKNOWLEDGMENT_COMMENT = "acknowledgment_comment";
	public static final String MONITOR_DISABLE_DESCRIPTION = "disable_description";
	public static final String MONITOR_UPDATED_DATE = "updated_date";
	public static final String MONITOR_DISABLE_START_TIME = "disable_start_time";
	public static final String MONITOR_IS_DISABLED_PERMANENTLY = "is_disabled_permanently";
	public static final String MONITOR_DESCRIPTION = "description";
	public static final String MONITOR_ASSOCIATED_ALERTS_DISABLE_DESCRIPTION = "associated_alerts_disable_description";
	public static final String MONITOR_TARGET_IP = "target_ip";
	public static final String MONITOR_ASSOCIATED_ALERTS_DISABLE_END_TIME = "associated_alerts_disable_end_time";
	public static final String MONITOR_SUMMARY = "summary";
	public static final String MONITOR_AVAILABILITY_DESCRIPTION = "availability_description";
	public static final String MONITOR_TYPE = "type";
	public static final String MONITOR_STATUS = "status";
	
	/* values returned by runExistingMonitorExWithIdentifier */
	public static final String RUN_MONITOR_STATUS_SNAPSHOT_STATUSMSG = "runMonitorStatusSnapshot_statusMessage";
	public static final String RUN_MONITOR_STATUS_SNAPSHOT_STATUS = "runMonitorStatusSnapshot_status";
	public static final String RUN_MONITOR_STATUS_SNAPSHOT_LASTRUN = "runMonitorStatusSnapshot_lastRun";
	
	public static final String SIS_PATH_DELIMITER = "_sis_path_delimiter_";
	public static final String FORWARD_SLASH = "/";
	
	public static final String STATUS_ERROR = "ERROR";
	public static final String STATUS_GOOD = "GOOD";
	public static final String STATUS_DISABLED = "DISABLED";
	public static final String STATUS_WARNING = "WARNING";
	
	public static final String DEFAULT_DISABLE_TIME = "10";
	
	/**
	 * determine which drawable to display based on availability
	 * @param available
	 * @return
	 */
	public static int getAvailabilityImg(boolean available)
	{
		return available? R.drawable.available_monitor :R.drawable.uvailable_monitor;
	}
	
	/**
	 * determine which status drawable to display based on status
	 *
     * @param status
     * @param isMonitor
     * @return
	 */
	public static int getStatusImg(String status, boolean isMonitor)
	{
		int statusImg = R.drawable.status_unknown;
        if (status == null)
            return statusImg;
		if(status.equalsIgnoreCase(STATUS_ERROR))
			statusImg = R.drawable.status_error;
		else if(status.equalsIgnoreCase(STATUS_GOOD))
			statusImg = R.drawable.status_good;
		else if(status.equalsIgnoreCase(STATUS_WARNING))
			statusImg = R.drawable.warning;
        else if(status.equalsIgnoreCase(STATUS_DISABLED)) {
            statusImg = R.drawable.status_disabled;
        }
		return statusImg;
			
	}
	
	/**
	 * extract the group name from the full path
	 * @param fullPath
	 * @return
	 */
	public static String getGroupFromFullPath(String fullPath)
	{
		/* get displayable group name replacing _sis_path_delimiter_ with / and dropping the monitor name */
		int lastForwardSlash = fullPath.lastIndexOf(SIS_PATH_DELIMITER);
		if(lastForwardSlash>-1)
		{
			String groupPath = fullPath.substring(0, lastForwardSlash);
			return groupPath.replaceAll(SIS_PATH_DELIMITER, FORWARD_SLASH);
		}
		else
			return fullPath;		
	}
	
	/**
	 * return the date as a formatted date string, given the date and the specified pattern
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormattedDate(Date date, String pattern)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
	/**
	 * extract monitor name from the full path
	 * @param fullPath
	 * @return
	 */
	public static String getMonitorNameFromFullPath(String fullPath)
	{
		String[] mPaths = fullPath.split(SIS_PATH_DELIMITER);
		return mPaths[mPaths.length-1].trim();
	}
}
