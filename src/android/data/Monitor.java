package com.hpe.android.plugin.backgroundservice.data;


import com.hpe.android.plugin.backgroundservice.rest.RestCalls;

@SuppressWarnings("serial")
public class Monitor extends Entity {
	
	private String targetIp;	
	private String targetName;
	private String availabilityDescription;
	private String targetDisplayName;
	private boolean availability;
	
	public Monitor(SiteScopeServer siteScopeServer) {
		super(siteScopeServer);
        setEntityType(RestCalls.MONITOR_ENITYTYPE);
	}

    public boolean getAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTargetDisplayName() {
		return targetDisplayName;
	}
	public void setTargetDisplayName(String targetDisplayName) {
		this.targetDisplayName = targetDisplayName;
	}	
	public String getTargetIp() {
		return targetIp;
	}
	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}	
	public String getAvailabilityDescription() {
		return availabilityDescription;
	}
	public void setAvailabilityDescription(String availabilityDescription) {
		this.availabilityDescription = availabilityDescription;
	}
}
