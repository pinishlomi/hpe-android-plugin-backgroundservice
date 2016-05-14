package com.hpe.android.plugin.backgroundservice.data;


import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Entity implements Serializable{

	private String id;
	private long disableEndTime;
	private boolean isAssociatedAlertsDisabled;
	private long associatedAlertsDisableStartTime;
	private long associatedAlertsDisableEndTime;
	private String acknowledgmentComment;
	private String disableDescription;
	private long disableStartTime;
	private boolean isDisabledPermanently;
	private String associatedAlertsDisableDescription;

	private String parent_id;
	private String fullPath;
	private String name;
	private String description;
	private String type;
	private String status;
	private String summary;
	private String row_data;
	private String updatedDate;
	private boolean isFavorite;

    private String entityType; //SoapCalls.MONITOR_ENITYTYPE OR SoapCalls.GROUP_ENITYTYPE;

	private String errorMsg;

	private SiteScopeServer siteScopeServer;

	public Entity(SiteScopeServer siteScopeServer) {
		super();
		this.siteScopeServer = siteScopeServer;
		this.associatedAlertsDisableStartTime = 0;
		this.disableStartTime = 0;
		this.setFavorite(false);
	}

    public Entity() {

    }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getRow_data() {
		return row_data;
	}
	public void setRow_data(String row_data) {
		this.row_data = row_data;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public SiteScopeServer getSiteScopeServer() {
		return siteScopeServer;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	public boolean isFavorite() {
		return isFavorite;
	}
	public long getDisableEndTime() {
		return disableEndTime;
	}
	public void setDisableEndTime(long disableEndTime) {
		this.disableEndTime = disableEndTime;
	}
	public boolean isAssociatedAlertsDisabled() {
		return isAssociatedAlertsDisabled;
	}
	public void setAssociatedAlertsDisabled(boolean isAssociatedAlertsDisabled) {
		this.isAssociatedAlertsDisabled = isAssociatedAlertsDisabled;
	}
	public long getAssociatedAlertsDisableStartTime() {
		return associatedAlertsDisableStartTime;
	}
	public String getAcknowledgmentComment() {
		return acknowledgmentComment;
	}
	public void setAcknowledgmentComment(String acknowledgmentComment) {
		this.acknowledgmentComment = acknowledgmentComment;
	}
	public String getDisableDescription() {
		return disableDescription;
	}
	public void setDisableDescription(String disableDescription) {
		this.disableDescription = disableDescription;
	}
	public long getDisableStartTime() {
		return disableStartTime;
	}
	public boolean isDisabledPermanently() {
		return isDisabledPermanently;
	}
	public void setDisabledPermanently(boolean isDisabledPermanently) {
		this.isDisabledPermanently = isDisabledPermanently;
	}
	public String getAssociatedAlertsDisableDescription() {
		return associatedAlertsDisableDescription;
	}
	public void setAssociatedAlertsDisableDescription(
			String associatedAlertsDisableDescription) {
		this.associatedAlertsDisableDescription = associatedAlertsDisableDescription;
	}
	public long getAssociatedAlertsDisableEndTime() {
		return associatedAlertsDisableEndTime;
	}
	public void setAssociatedAlertsDisableEndTime(
			long associatedAlertsDisableEndTime) {
		this.associatedAlertsDisableEndTime = associatedAlertsDisableEndTime;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
