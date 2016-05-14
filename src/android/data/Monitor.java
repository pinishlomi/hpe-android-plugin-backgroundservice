package com.hpe.android.plugin.backgroundservice.data;


@SuppressWarnings("serial")
public class Monitor extends Entity {

	private String targetIp;
	private String targetName;
	private String availabilityDescription;
	private String targetDisplayName;
	private boolean availability;

	public Monitor(String type) {
		super();
		setEntityType(type);
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
