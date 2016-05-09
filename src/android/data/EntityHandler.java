package com.hpe.hybridsitescope.data;

import android.content.Context;

import com.hpe.hybridsitescope.utils.SiteScopeError;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Date;

//import com.hp.sitescope.mobile.android_app.soap.SoapCalls;

public class EntityHandler {
	
	private Entity entity = null;
	private Context context = null;


	public EntityHandler(Entity entity, Context context) {
		super();
		this.entity = entity;
		this.context = context;
	}

    public EntityHandler(Entity entity) {
        this.entity = entity;
    }

    public Entity getMonitor(SoapObject so, int propertyIndex)
	{		
		SoapObject monitorSO = (SoapObject) so.getProperty(propertyIndex); //this soap object has TWO property objects, the first is the monitor name the second is monitor details
		SoapObject snapshotObject = (SoapObject) ((SoapObject)(monitorSO.getProperty(1))).getProperty(0);
		SoapObject possibleErrorObject = (SoapObject) ((SoapObject)(monitorSO.getProperty(1))).getProperty(1);
		String type = ((SoapObject) snapshotObject).getProperty(0).toString(); //determine if key is configuration_snapshot or error_code
		if(!type.contains("error"))//"status_error"))
		{
			JSONObject snapshot_json_obj = new JSONObject();
			JSONObject runtime_snapshot_json_obj = new JSONObject();
			JSONObject configuration_snapshot_json_obj = new JSONObject();
			//SiteScope 11 this is runtime_snapshot, SiteScope 10 is configuration_snapshot_json_obj
			SoapObject config_snapshot = (SoapObject) ((SoapObject) snapshotObject).getProperty(1);    		
			
			for(int i=0; i<config_snapshot.getPropertyCount(); i++)
			{
				String propertyName = ((SoapPrimitive)(((SoapObject)config_snapshot.getProperty(i)).getProperty(0))).toString();
				String propertyValue = "";
				if((((SoapObject)config_snapshot.getProperty(i)).getProperty(1)) instanceof SoapPrimitive)
					propertyValue = ((SoapPrimitive)(((SoapObject)config_snapshot.getProperty(i)).getProperty(1))).toString();
				else
				{
				   if(((SoapObject)(((SoapObject)config_snapshot.getProperty(i)).getProperty(1))).getPropertyCount()>1)
					  propertyValue = ((SoapObject)(((SoapObject)config_snapshot.getProperty(i)).getProperty(1))).getProperty(0).toString();
				}	    			
				setEntity(propertyName, propertyValue);
				try {
					configuration_snapshot_json_obj.put(propertyName, propertyValue);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			//SiteScope 11 this is configuration_snapshot_json_obj, SiteScope 10 is runtime_snapshot
			SoapObject runtime_snapshot = (SoapObject)((SoapObject)((SoapObject)(monitorSO.getProperty(1))).getProperty(1)).getProperty(1);
			for(int i=0; i<runtime_snapshot.getPropertyCount(); i++)
			{
				String propertyName = ((SoapPrimitive)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(0))).toString();
				String propertyValue = "";
				if((((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1)) instanceof SoapPrimitive)
					propertyValue = ((SoapPrimitive)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1))).toString();
				else
				{
					if(((SoapObject)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1))).getPropertyCount()>1)
						propertyValue = ((SoapObject)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1))).getProperty(0).toString();
				}
				setEntity(propertyName, propertyValue);
				try {
					runtime_snapshot_json_obj.put(propertyName, propertyValue);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			try {
				snapshot_json_obj.put("runtime_snapshot", runtime_snapshot_json_obj);
				snapshot_json_obj.put("configuration_snapshot", configuration_snapshot_json_obj);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			setEntity("row_data", snapshot_json_obj.toString());
		}
		else
		{
			//Handle status_error messages here
			//get name of monitor			
			String	errorString = ((SoapObject) snapshotObject).getProperty(0).toString();
			String errorCode;
			if("error_code".equals(errorString))
				errorCode = ((SoapObject) snapshotObject).getProperty(1).toString();
			else
				errorCode = ((SoapObject) possibleErrorObject).getProperty(1).toString();
			
			String fullPath = monitorSO.getProperty(0).toString();
			String monitorName = MonitorHelper.getMonitorNameFromFullPath(fullPath);
			entity.setName(monitorName);
			entity.setFullPath(fullPath);
			entity.setErrorMsg(SiteScopeError.getMonitorErrorMsg(errorCode));
		}		
		//entity.setEntityType(SoapCalls.MONITOR_ENITYTYPE);
		return entity;
	}
	
	public Entity getGroup(SoapObject so, int propertyIndex)
	{
		SoapObject groupSO = (SoapObject) so.getProperty(propertyIndex); //this soap object has TWO property objects, the first is the monitor name the second is monitor details
		SoapObject snapshotObject = (SoapObject) ((SoapObject)(groupSO.getProperty(1))).getProperty(0);
		SoapObject possibleErrorObject = (SoapObject) ((SoapObject)(groupSO.getProperty(1))).getProperty(1);
		String type = ((SoapObject) snapshotObject).getProperty(0).toString(); //determine if key is configuration_snapshot or error_code
        if(!type.contains("error"))
		{
			//SiteScope 11 this is runtime_snapshot, SiteScope 10 is configuration_snapshot
			SoapObject config_snapshot = (SoapObject)((SoapObject)((SoapObject)(groupSO.getProperty(1))).getProperty(0)).getProperty(1);    		
			for(int i=0; i<config_snapshot.getPropertyCount(); i++)
			{
				String propertyName = ((SoapPrimitive)(((SoapObject)config_snapshot.getProperty(i)).getProperty(0))).toString();
				String propertyValue = "";
				if((((SoapObject)config_snapshot.getProperty(i)).getProperty(1)) instanceof SoapPrimitive)
					propertyValue = ((SoapPrimitive)(((SoapObject)config_snapshot.getProperty(i)).getProperty(1))).toString();
				else
				{
				   if(((SoapObject)(((SoapObject)config_snapshot.getProperty(i)).getProperty(1))).getPropertyCount()>1)
					  propertyValue = ((SoapObject)(((SoapObject)config_snapshot.getProperty(i)).getProperty(1))).getProperty(0).toString();
				}	    			
				setEntity(propertyName, propertyValue);	    			
			}
			
			//SiteScope 11 this is configuration_snapshot, SiteScope 10 is runtime_snapshot
			SoapObject runtime_snapshot = (SoapObject)((SoapObject)((SoapObject)(groupSO.getProperty(1))).getProperty(1)).getProperty(1);
			for(int i=0; i<runtime_snapshot.getPropertyCount(); i++)
			{
				String propertyName = ((SoapPrimitive)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(0))).toString();
				String propertyValue = "";
				if((((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1)) instanceof SoapPrimitive)
					propertyValue = ((SoapPrimitive)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1))).toString();
				else
				{
					if(((SoapObject)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1))).getPropertyCount()>1)
						propertyValue = ((SoapObject)(((SoapObject)runtime_snapshot.getProperty(i)).getProperty(1))).getProperty(0).toString();
				}
				setEntity(propertyName, propertyValue);
				
			}
		}
		else
		{
			//get name of group
			String	errorString = ((SoapObject) snapshotObject).getProperty(0).toString();
			String errorCode;
			if("error_code".equals(errorString))
				errorCode = ((SoapObject) snapshotObject).getProperty(1).toString();
			else
				errorCode = ((SoapObject) possibleErrorObject).getProperty(1).toString();
			
			String fullPath = groupSO.getProperty(0).toString();
			String[] mPaths = fullPath.split(MonitorHelper.SIS_PATH_DELIMITER);
			String groupName = mPaths[mPaths.length-1].trim();
			entity.setName(groupName);
			entity.setFullPath(fullPath);
            entity.setErrorMsg(SiteScopeError.getGroupErrorMsg(errorCode));
		}
		return entity;
	}
	
	
	//update monitor object based on property values returned from soap call
	private void setEntity(String detailName, String detailValue)
	{
		if(MonitorHelper.MONITOR_NAME.equals(detailName))
		{
			entity.setName(detailValue);
		}		
		else if(MonitorHelper.MONITOR_DISABLE_END_TIME.equals(detailName))
		{
			entity.setDisableEndTime(detailValue.length()==0?0:Long.parseLong(detailValue));
		}
		else if(MonitorHelper.MONITOR_IS_ASSOCIATED_ALERTS_DISABLED.equals(detailName))
		{
			entity.setAssociatedAlertsDisabled(Boolean.valueOf(detailValue));
		}
		else if(MonitorHelper.MONITOR_ACKNOWLEDGMENT_COMMENT.equals(detailName))
		{
			entity.setAcknowledgmentComment(detailValue);
		}
		else if(MonitorHelper.MONITOR_DISABLE_DESCRIPTION.equals(detailName))
		{
			entity.setDisableDescription(detailValue);
		}
		else if(MonitorHelper.MONITOR_UPDATED_DATE.equals(detailName))
		{
			entity.setUpdatedDate(detailValue.length()==0?new Date():new Date(Long.valueOf(detailValue)));
		}
		else if(MonitorHelper.MONITOR_IS_DISABLED_PERMANENTLY.equals(detailName))
		{
			entity.setDisabledPermanently(Boolean.valueOf(detailValue));
		}
		else if(MonitorHelper.MONITOR_DESCRIPTION.equals(detailName))
		{
			entity.setDescription(detailValue);
		}
		else if(MonitorHelper.MONITOR_ASSOCIATED_ALERTS_DISABLE_DESCRIPTION.equals(detailName))
		{
			entity.setAssociatedAlertsDisableDescription(detailValue);
		}		
		else if(MonitorHelper.MONITOR_ASSOCIATED_ALERTS_DISABLE_END_TIME.equals(detailName))
		{
			entity.setAssociatedAlertsDisableEndTime(detailValue.length()==0?0:Long.parseLong(detailValue));
		}
		else if(MonitorHelper.MONITOR_SUMMARY.equals(detailName))
		{
			entity.setSummary(detailValue);
		}		
		else if(MonitorHelper.MONITOR_TYPE.equals(detailName))
		{
			entity.setType(detailValue);
		}
		else if(MonitorHelper.MONITOR_STATUS.equals(detailName))
		{
			entity.setStatus(detailValue);
		}
		else if(MonitorHelper.MONITOR_FULL_PATH.equals(detailName))
		{
			entity.setFullPath(detailValue);
		}
		else if(MonitorHelper.MONITOR_ROW_DATA.equals(detailName))
		{
			entity.setRow_data(detailValue);
		}

		//update monitor specific fields
		if(entity instanceof Monitor)
		{
			if(MonitorHelper.MONITOR_TARGET_IP.equals(detailName))
			{
				((Monitor)entity).setTargetIp(detailValue);
			}
			else if(MonitorHelper.MONITOR_AVAILABILITY_DESCRIPTION.equals(detailName))
			{
				((Monitor)entity).setAvailabilityDescription(detailValue);
			}
			else if(MonitorHelper.MONITOR_AVAILABILITY.equals(detailName))
			{
				((Monitor)entity).setAvailability(Boolean.valueOf(detailValue));
			}
			else if(MonitorHelper.MONITOR_TARGET_NAME.equals(detailName))
			{
				((Monitor)entity).setTargetName(detailValue);
			}
			else if(MonitorHelper.MONITOR_TARGET_DISPLAY_NAME.equals(detailName))
			{
				((Monitor)entity).setTargetDisplayName(detailValue);
			}
		}
	}
	
}
