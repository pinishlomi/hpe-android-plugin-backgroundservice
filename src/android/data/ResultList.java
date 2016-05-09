package com.hpe.hybridsitescope.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

public class ResultList extends Observable implements List<Entity> {

	List<Entity> resultsList;
	
	private static ResultList instance;


    public void bunchUpdate(List<? extends Entity> entities, boolean setFavorite) {
        for (Entity entity : entities) {
            if(setFavorite)
                entity.setFavorite(true);
            updateEntity(entity);
        }
    }

    public void refreshList() {
        setChanged();
        notifyObservers();
    }

	public ResultList(List<Entity> resultsList) {
		this.resultsList = resultsList;
	}	

	private ResultList() {
		this.resultsList = new ArrayList<Entity>();
	}

	public static synchronized ResultList getInstance() {
		if (instance == null)
			instance = new ResultList();
		return instance;
	}

	@Override
	public boolean add(Entity object) {
		boolean result = resultsList.add(object);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public void add(int location, Entity object) {
		resultsList.add(location, object);
        setChanged();
        notifyObservers();		
	}

	@Override
	public boolean addAll(Collection<? extends Entity> c) {
		boolean result = resultsList.addAll(c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean addAll(int position, Collection<? extends Entity> c) {
		boolean result = resultsList.addAll(position, c);
        if(result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public void clear() {
		resultsList.clear();
        setChanged();
        notifyObservers();
	}

	@Override
	public boolean contains(Object object) {
		return resultsList.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return resultsList.containsAll(arg0);
	}

	@Override
	public Entity get(int location) {
		return resultsList.get(location);
	}

	@Override
	public int indexOf(Object object) {
		return resultsList.indexOf(object);
	}
	@Override
	public boolean equals(Object monitor) {
        return monitor.equals(monitor);
    }
	
	@Override
	public int hashCode() {
        return resultsList.hashCode();
    }

	@Override
	public boolean isEmpty() {
		return resultsList.isEmpty();
	}

	@Override
	public Iterator<Entity> iterator() {
		return resultsList.iterator();
	}

	@Override
	public int lastIndexOf(Object monitor) {
		return resultsList.lastIndexOf(monitor);
	}

	@Override
	public ListIterator<Entity> listIterator() {
		return resultsList.listIterator();
	}

	@Override
	public ListIterator<Entity> listIterator(int position) {
		return resultsList.listIterator(position);
	}

	@Override
	public Entity remove(int position) {
		Entity result = resultsList.remove(position);
        setChanged();
        notifyObservers();
        return result;
	}

	@Override
	public boolean remove(Object monitor) {
		boolean result = resultsList.remove(monitor);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = resultsList.removeAll(c);
        if (result) {
        	setChanged();
        }
        notifyObservers();
        return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = resultsList.retainAll(c);
		if (result) {
			setChanged();
		}
		notifyObservers();
		return result;
	}

	@Override
	public Entity set(int position, Entity monitor) {
		Entity result = resultsList.set(position, monitor);
		setChanged();
		notifyObservers();
		return result;
	}

	@Override
	public int size() {
		return resultsList.size();
	}

	@Override
	public List<Entity> subList(int fromPosition, int toPosition) {
		return resultsList.subList(fromPosition, toPosition);
	}

	@Override
	public Object[] toArray() {
		return resultsList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return resultsList.toArray(a);
	}
	
	public void updateEntity(Entity object) 
	{	
		//another way of doing a replace
		int existingFavePos = this.itemWithBaseInfo(object.getName(), object.getSiteScopeServer().getName(), object.getClass().getSimpleName(), object.getFullPath());
		if(existingFavePos>-1)
		{
			resultsList.remove(existingFavePos);
			resultsList.add(existingFavePos, object);
		} else {
            resultsList.add(object);
        }
	}

	public Entity get(Entity object) {
		Entity entity = object;
		boolean isEqual = false;
		Iterator<Entity> iterator = this.resultsList.iterator();
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
			if(!entityInFave.getDescription().equals(entity.getDescription()))
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
		Iterator<Entity> iterator = this.resultsList.iterator();
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
		Iterator<Entity> iterator = this.resultsList.iterator();
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

}
