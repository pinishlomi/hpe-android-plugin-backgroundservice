package com.hpe.hybridsitescope.data;

import android.content.Context;
import android.util.Log;

import com.hpe.hybridsitescope.db.AccountInfoDbAdapterImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Favorites list singleton
 *
 */
public class FavoritesList extends Observable implements List<Entity> {
    public static final String TAG = FavoritesList.class.getSimpleName();

	List<Entity> favoritesList;
	private static FavoritesList instance;


    private AccountInfoDbAdapterImpl mDbHelper;

    private final Map<SiteScopeServer, Boolean> refreshServerErrorMap = new ConcurrentHashMap<SiteScopeServer, Boolean>();


    public final boolean getRefreshError(SiteScopeServer server) {
        if (server != null) {
            final Boolean err = refreshServerErrorMap.get(server);
            if (err != null) {
                return err.booleanValue();
            }
        }
        return isAllServersInError();
    }

    private boolean isAllServersInError() {
        if (refreshServerErrorMap.size() == 0) return false;

        for (Map.Entry<SiteScopeServer, Boolean> entry : refreshServerErrorMap.entrySet()) {
            if (entry != null) {
                Boolean isError = entry.getValue();
                if (!isError) return false;
            }
        }
        return true;
    }

    public void refreshList() {
        setChanged();
        notifyObservers();
    }

    public final void setRefreshError(SiteScopeServer server, boolean error) {
        if (server != null) refreshServerErrorMap.put(server, Boolean.valueOf(error));
    }

    public void applyErrorToEntityAtServer(String errorMessage, String entityType, SiteScopeServer siteScopeServer) {
        Log.d(TAG, String.format("Applying %s to entityType=%s at %s", errorMessage, entityType, siteScopeServer.getUrl()));
        for (Entity entity : favoritesList) {
            if (entity.getEntityType().equals(entityType) && entity.getSiteScopeServer().equals(siteScopeServer)) {
                entity.setErrorMsg(errorMessage);
            }
        }

        refreshList();
    }

	private FavoritesList(Context context) {
		this.favoritesList = new ArrayList<Entity>();
        mDbHelper = new AccountInfoDbAdapterImpl(context.getApplicationContext());
	}

	public static synchronized FavoritesList getInstance(Context context) {
		if (instance == null)
			instance = new FavoritesList(context);
		return instance;
	}
	
	public void add(Entity entity, boolean update) {

		//in all cases, add favorite to the database
		
		//gather this information to display during errors
		String target_display_name = "";
		String availability = "";
		if(entity instanceof Monitor)
		{
			target_display_name = ((Monitor)entity).getTargetDisplayName();
			availability = String.valueOf(((Monitor)entity).getAvailability());
		}
    	
    	//add favorite to the database
		long result = mDbHelper.insertFavorite(
				entity.getSiteScopeServer().getName(),
				entity.getName(),
				String.valueOf(entity.getUpdatedDate()),
				entity.getType(),
				entity.getStatus(),
				entity.getSummary(),
				entity.getFullPath(),
				target_display_name,
				entity.getDescription(),
				availability,
				entity.getRow_data()
				);
	
		
		//do not allow duplicates to be added
		//another way of doing a replace
		int existingFavePos = this.itemWithBaseInfo(entity.getName(), entity.getSiteScopeServer().getName(), entity.getClass().getSimpleName(), entity.getFullPath());
		if(existingFavePos>-1)
		{
			removeNoUpdate(existingFavePos);
			favoritesList.add(existingFavePos, entity);
		}
		else			
		{		
			if (result>-1 && update)
	    	{
				favoritesList.add(entity);
	        	setChanged();
	        	notifyObservers();	        	
	        }			
		}
	}
	
	public void updateEntity(Entity object) 
	{	
		//another way of doing a replace
		int existingFavePos = this.itemWithBaseInfo(object.getName(), object.getSiteScopeServer().getName(), object.getClass().getSimpleName(), object.getFullPath());
		if(existingFavePos>-1)
		{
			removeNoUpdate(existingFavePos);
			favoritesList.add(existingFavePos, object);
		}
	}

    public void bunchUpdate(List<? extends Entity> entities, boolean setFavorite) {
        for (Entity entity : entities) {
            if(setFavorite) entity.setFavorite(true);
            updateEntity(entity);
        }
    }

	@Override
	public void add(int location, Entity object) {
		//do not allow duplicates to be added
		if(this.get(object)!=null)
			return;
		favoritesList.add(location, object);
        setChanged();
        notifyObservers();		
	}
	
	public boolean addAllForRefresh(Collection<? extends Entity> c) {
		return favoritesList.addAll(c);
 	}

	@Override
	public boolean addAll(Collection<? extends Entity> c) {
		boolean result = favoritesList.addAll(c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean addAll(int position, Collection<? extends Entity> c) {
		boolean result = favoritesList.addAll(position, c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public void clear() {
		favoritesList.clear();
        setChanged();
        notifyObservers();
	}
	
	public void clearForRefresh()
	{
		favoritesList.clear();
	}

    public Entity getEntityForAlertTriger(Entity object) {
        Entity entity = object;
        boolean isEqual = false;
        Iterator<Entity> iterator = this.favoritesList.iterator();
        Entity entityInFave = null;
        while(iterator.hasNext()){
            isEqual = true;
            entityInFave = (Entity)iterator.next();
            if(!entityInFave.getFullPath().equals(entity.getFullPath()))
            {
                isEqual = false;
                continue;
            }
            if(!entityInFave.getName().equals(entity.getName()))
            {
                isEqual = false;
                continue;
            }

            if(!entityInFave.getSiteScopeServer().equals(entity.getSiteScopeServer()))
            {
                isEqual = false;
                continue;
            }
            if(isEqual)
                break;
        }
        if(isEqual)
            return entityInFave;
        else
            return null;
    }
        public Entity get(Entity object) {
		Entity entity = object;
		boolean isEqual = false;
		Iterator<Entity> iterator = this.favoritesList.iterator();
		Entity entityInFave = null;
		while(iterator.hasNext())
		{
			isEqual = true;
			entityInFave = (Entity)iterator.next();
			if(object.getErrorMsg()!=null)
			{
				isEqual = false;
				break;
			}
			if(!entityInFave.getFullPath().equals(entity.getFullPath()))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getName().equals(entity.getName()))
			{
				isEqual = false;
				continue;
			}

			if(!entityInFave.getSiteScopeServer().equals(entity.getSiteScopeServer()))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getStatus().equals(entity.getStatus()))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getType().equals(entity.getType()))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getSiteScopeServer().getUrl().equals(entity.getSiteScopeServer().getUrl()))
			{
				isEqual = false;
				continue;
			}		
			if(entityInFave.isAssociatedAlertsDisabled()!=entity.isAssociatedAlertsDisabled())
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.isDisabledPermanently()!=entity.isDisabledPermanently())
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getAcknowledgmentComment()!=null && !(entityInFave.getAcknowledgmentComment().equals(entity.getAcknowledgmentComment())))
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getAssociatedAlertsDisableDescription()!=null && !(entityInFave.getAssociatedAlertsDisableDescription().equals(entity.getAssociatedAlertsDisableDescription())))
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getAssociatedAlertsDisableEndTime()!=entity.getAssociatedAlertsDisableEndTime())
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getAssociatedAlertsDisableStartTime()!=entity.getAssociatedAlertsDisableStartTime())
			{
				isEqual = false;
				continue;
			}			
			if(entityInFave.getDescription() != null && entity.getDescription() != null && !entityInFave.getDescription().equals(entity.getDescription()))
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getDisableDescription()!=null && !(entityInFave.getDisableDescription().equals(entity.getDisableDescription())))
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getDisableEndTime()!=entity.getDisableEndTime())
			{
				isEqual = false;
				continue;
			}
			if(entityInFave.getDisableStartTime()!=entity.getDisableStartTime())
			{
				isEqual = false;
				continue;
			}
			
			if(entity instanceof Monitor)
			{
				if(((Monitor)entityInFave).getTargetDisplayName()!=null&& !((Monitor)entityInFave).getTargetDisplayName().equals(((Monitor)entity).getTargetDisplayName()))
				{
					isEqual = false;
					continue;
				}
				if(((Monitor)entityInFave).getTargetIp()!=null&& !((Monitor)entityInFave).getTargetIp().equals(((Monitor)entity).getTargetIp()))
				{
					isEqual = false;
					continue;
				}
				if(((Monitor)entityInFave).getTargetName()!=null&& !((Monitor)entityInFave).getTargetName().equals(((Monitor)entity).getTargetName()))
				{
					isEqual = false;
					continue;
				}
				if(((Monitor)entityInFave).getAvailability()!=((Monitor)entity).getAvailability())
				{
					isEqual = false;
					continue;
				}
				if(((Monitor)entityInFave).getAvailabilityDescription()!=null&& !((Monitor)entityInFave).getAvailabilityDescription().equals(((Monitor)entity).getAvailabilityDescription()))
				{
					isEqual = false;
					continue;
				}
			}			
			
			//left out comparison of UpdatedDate, Summary
			
			if(isEqual)
				break;
		}
		if(isEqual)
			return entityInFave;
		else
			return null;
	}
	
	public boolean itemWithBaseInfoExists(String name, String ssAccount, String type, String fullPath) {
		
		boolean isEqual = false;
		Iterator<Entity> iterator = this.favoritesList.iterator();
		Entity entityInFave = null;
		while(iterator.hasNext())
		{
			isEqual = true;
			entityInFave = (Entity)iterator.next();
			
			if(!entityInFave.getFullPath().equals(fullPath))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getName().equals(name))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getSiteScopeServer().getName().equals(ssAccount))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getClass().getSimpleName().equalsIgnoreCase(type))
			{
				isEqual = false;
				continue;
			}			
			if(isEqual)
				break;
		}
		return isEqual;
	}
	
	public int itemWithBaseInfo(String name, String ssAccount, String type, String fullPath) {
		
		boolean isEqual = false;
		Iterator<Entity> iterator = this.favoritesList.iterator();
		Entity entityInFave = null;
		int position = -1;
		int count = -1;
		while(iterator.hasNext())
		{		
			count++;
			isEqual = true;
			entityInFave = (Entity)iterator.next();
			
			if(!entityInFave.getFullPath().equals(fullPath))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getName().equals(name))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getSiteScopeServer().getName().equals(ssAccount))
			{
				isEqual = false;
				continue;
			}
			if(!entityInFave.getClass().getSimpleName().equalsIgnoreCase(type))
			{
				isEqual = false;
				continue;
			}			
			if(isEqual)
			{
				position = count;
				break;
			}							
		}
		return position;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return favoritesList.containsAll(arg0);
	}

	@Override
	public Entity get(int location) {
		return favoritesList.get(location);
	}
	
	@Override
	public int indexOf(Object object) {
		return favoritesList.indexOf(object);
	}
	@Override
	public boolean equals(Object monitor) {
        return monitor.equals(monitor);
    }
	
	@Override
	public int hashCode() {
        return favoritesList.hashCode();
    }

	@Override
	public boolean isEmpty() {
		return favoritesList.isEmpty();
	}

	@Override
	public Iterator<Entity> iterator() {
		return favoritesList.iterator();
	}

	@Override
	public int lastIndexOf(Object monitor) {
		return favoritesList.lastIndexOf(monitor);
	}

	@Override
	public ListIterator<Entity> listIterator() {
		return favoritesList.listIterator();
	}

	@Override
	public ListIterator<Entity> listIterator(int position) {
		return favoritesList.listIterator(position);
	}

	@Override
	public Entity remove(int position) {
		Entity result = favoritesList.remove(position);
        setChanged();
        notifyObservers();
        return result;
	}
	
	public Entity removeNoUpdate(int position) {
		Entity result = favoritesList.remove(position);        
        return result;
	}
	
	public void remove(Entity entity, boolean update) {
		
		boolean result = mDbHelper.deleteFavorite(entity);
        if (result && update && entity != null) {
        	result = favoritesList.remove(this.get(entity));
        	setChanged();
        	notifyObservers();
        }        
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = favoritesList.removeAll(c);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = favoritesList.retainAll(c);
		if (result) {
			setChanged();
		}
		notifyObservers();
		return result;
	}

	@Override
	public Entity set(int position, Entity monitor) {
		Entity result = favoritesList.set(position, monitor);
		setChanged();
		notifyObservers();
		return result;
	}

	@Override
	public int size() {
		return favoritesList.size();
	}

	@Override
	public List<Entity> subList(int fromPosition, int toPosition) {
		return favoritesList.subList(fromPosition, toPosition);
	}

	@Override
	public Object[] toArray() {
		return favoritesList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return favoritesList.toArray(a);
	}

	@Override
	public boolean remove(Object object) {
		return favoritesList.remove(object);
	}

	@Override
	public boolean contains(Object object) {
		return favoritesList.contains(object);
	}

	@Override
	public boolean add(Entity object) {		
		return favoritesList.add(object);
	}

	public void setHasChanged()
	{
		setChanged();
	}

    public void updateOptionalAccountProps(String oldHost,String host,
										   String oldPort,String port,
										   String oldProtocol,String protocol,
										   Boolean allowUntrustedCerts, String displayName, String userName, String password) {
        if (host != null && port != null && protocol != null) {
            for (Entity entity : favoritesList) {
                final SiteScopeServer siteScopeServer = entity.getSiteScopeServer();
                if (siteScopeServer != null) {
                    if (	siteScopeServer.getHost().equalsIgnoreCase(oldHost)
							&& siteScopeServer.getPort().equalsIgnoreCase(oldPort)
                            && siteScopeServer.getProtocol().equalsIgnoreCase(oldProtocol)
							) {
                        //host found, update properties
						if (host != null) siteScopeServer.setHost(host);
						if (port != null) siteScopeServer.setPort(port);
						if (protocol != null) siteScopeServer.setProtocol(protocol);
						if (allowUntrustedCerts != null) siteScopeServer.setAllowUntrustedCerts(allowUntrustedCerts);
						if (displayName != null) siteScopeServer.setName(displayName);
                        if (userName != null) siteScopeServer.setUsername(userName);
                        if (password != null) siteScopeServer.setPassword(password);
                    }
                }
            }
        }
    }
}
