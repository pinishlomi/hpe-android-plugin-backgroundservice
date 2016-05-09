package com.hpe.hybridsitescope.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hpe.hybridsitescope.MonitorDetails;
import com.hpe.hybridsitescope.R;
import com.hpe.hybridsitescope.data.Entity;
import com.hpe.hybridsitescope.data.FavoritesList;
import com.hpe.hybridsitescope.data.Group;
import com.hpe.hybridsitescope.data.Monitor;
import com.hpe.hybridsitescope.data.MonitorHelper;
import com.hpe.hybridsitescope.db.AccountInfoDbAdapter;
import com.hpe.hybridsitescope.db.AccountInfoDbAdapterImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class FavoritesListAdapter extends BaseAdapter implements Observer{
    public static final String TAG = FavoritesListAdapter.class.getSimpleName();
	public static final String POSITION = "position";
	private final Context context;
    private AccountInfoDbAdapter mDbHelper;

	public FavoritesListAdapter(Context context) {
        this.context = context;
        mDbHelper =  new AccountInfoDbAdapterImpl(context);

        FavoritesList.getInstance(context).addObserver(this);

        mDbHelper.open();
    }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final View view = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.monitor_row, parent, false);

		final Entity entity = FavoritesList.getInstance(context).get(position);
		
		//handle status_error message
		if(entity.getErrorMsg()!=null && entity.getErrorMsg().length()>0)
		{
			((LinearLayout)view.findViewById(R.id.status_area)).setVisibility(View.GONE);
			final FavoritesList favoritesList = FavoritesList.getInstance(context);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int maxWidthInPixels = (int) (width * 0.8);

            if(entity instanceof Monitor)
			{
                final CheckBox monitorFaveChkBox = ((CheckBox)(view.findViewById(R.id.add_to_faves)));
				monitorFaveChkBox.setButtonDrawable(R.drawable.monitor_broken_fave_icon);

                final Monitor favesMonitor = (Monitor) FavoritesList.getInstance(context).get(entity);

                monitorFaveChkBox.setChecked(favesMonitor != null ? true : entity.isFavorite());

				monitorFaveChkBox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//determine if adding or removing from favorites
				    	if(((CheckBox)v).isChecked())
				    	{   			    		
				    		entity.setFavorite(true);
				    		FavoritesList.getInstance(context).add(entity, false);
                            monitorFaveChkBox.setButtonDrawable(R.drawable.monitor_broken_fave_icon);
				    	}
				    	else
				    	{
				    		entity.setFavorite(false);
				    		FavoritesList.getInstance(context).remove(entity, false);
                            monitorFaveChkBox.setButtonDrawable(R.drawable.monitor_broken_not_fave_icon);
				    	}
					}
				});
				
				((LinearLayout)view.findViewById(R.id.path_text_row)).setVisibility(View.GONE);
				((LinearLayout)view.findViewById(R.id.group_text_row)).setVisibility(View.VISIBLE);
				((LinearLayout)view.findViewById(R.id.target_text_row)).setVisibility(View.VISIBLE);
				TextView groupName = ((TextView)(view.findViewById(R.id.group_name)));
				groupName.setMaxWidth(maxWidthInPixels);
				groupName.setText(MonitorHelper.getGroupFromFullPath(entity.getFullPath()));
				TextView targetName = ((TextView)(view.findViewById(R.id.target_name)));
				targetName.setMaxWidth(maxWidthInPixels);
				targetName.setText(((Monitor)entity).getTargetName());
			}
			else
			{
				final CheckBox groupFaveChkBox = ((CheckBox)(view.findViewById(R.id.add_to_faves)));
				groupFaveChkBox.setButtonDrawable(R.drawable.group_broken_fave_icon);

                final Group favesGroup = (Group) FavoritesList.getInstance(context).get(entity);

                groupFaveChkBox.setChecked(favesGroup != null ? true : entity.isFavorite());

                groupFaveChkBox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//determine if adding or removing from favorites
				    	if(((CheckBox)v).isChecked())
				    	{   			    		
				    		entity.setFavorite(true);
				    		FavoritesList.getInstance(context).add(entity, false);
                            groupFaveChkBox.setButtonDrawable(R.drawable.group_broken_fave_icon);
				    	}
				    	else
				    	{
				    		entity.setFavorite(false); 	
                            FavoritesList.getInstance(context).remove(entity, false);
                            groupFaveChkBox.setButtonDrawable(R.drawable.group_broken_not_fave_icon);
				    	}				
					}
				});
				
				((LinearLayout)view.findViewById(R.id.path_text_row)).setVisibility(View.VISIBLE);
				((LinearLayout)view.findViewById(R.id.group_text_row)).setVisibility(View.GONE);
				((LinearLayout)view.findViewById(R.id.target_text_row)).setVisibility(View.GONE);
				TextView pathName = ((TextView)(view.findViewById(R.id.path_name)));
				pathName.setMaxWidth(maxWidthInPixels);
				pathName.setText(entity.getFullPath().replace(MonitorHelper.SIS_PATH_DELIMITER, MonitorHelper.FORWARD_SLASH));
			}		
			TextView monitorName = ((TextView)(view.findViewById(R.id.monitor_name)));
			monitorName.setMaxWidth(maxWidthInPixels);
			monitorName.setText(entity.getName());
			TextView sitescopeName = ((TextView)(view.findViewById(R.id.sitescope_name)));
			sitescopeName.setMaxWidth(maxWidthInPixels);
			sitescopeName.setText(entity.getSiteScopeServer().getName());			
			TextView errorMsgTextView = ((TextView)(view.findViewById(R.id.error_msg)));
			errorMsgTextView.setVisibility(View.VISIBLE);
			errorMsgTextView.setText(entity.getErrorMsg());			
		}
		//else handle favorites normally
		else			
		{
			((TextView)(view.findViewById(R.id.monitor_name))).setText(entity.getName());				
			((TextView)(view.findViewById(R.id.sitescope_name))).setText(entity.getSiteScopeServer().getName());
			
			if(entity instanceof Group)
			{
                ((ImageView)(view.findViewById(R.id.monitor_status))).setImageResource(MonitorHelper.getStatusImg(entity.getStatus(), false));
				((LinearLayout)view.findViewById(R.id.path_text_row)).setVisibility(View.VISIBLE);
				((LinearLayout)view.findViewById(R.id.group_text_row)).setVisibility(View.GONE);
				((LinearLayout)view.findViewById(R.id.target_text_row)).setVisibility(View.GONE);
				((TextView)(view.findViewById(R.id.path_name))).setText(entity.getFullPath().replace(MonitorHelper.SIS_PATH_DELIMITER, MonitorHelper.FORWARD_SLASH));
				((ImageView)(view.findViewById(R.id.monitor_availability))).setVisibility(View.INVISIBLE);
				CheckBox groupFaveChkBox = ((CheckBox)(view.findViewById(R.id.add_to_faves)));
                final Group favesGroup = (Group) FavoritesList.getInstance(context).get(entity);

                //check the monitor status and decide whether we need to show enabled/or disabled monitor icon
                if (entity.getStatus().toUpperCase().equals(MonitorHelper.STATUS_DISABLED)) {
                    groupFaveChkBox.setButtonDrawable(R.drawable.group_favorites_disabled_icon);
                } else {
                    groupFaveChkBox.setButtonDrawable(R.drawable.group_favorites_icon);
                }

                groupFaveChkBox.setChecked(favesGroup != null ? true : entity.isFavorite());

				groupFaveChkBox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//determine if adding or removing from favorites
				    	if(((CheckBox)v).isChecked())
				    	{   			    		
				    		entity.setFavorite(true);
				    		FavoritesList.getInstance(context).add(entity, false);
				    	}
				    	else
				    	{
				    		entity.setFavorite(false);   
							FavoritesList.getInstance(context).remove(entity, false);
				    	}				
					}
				});			
				
			}
			else
			{
                ((ImageView)(view.findViewById(R.id.monitor_status))).setImageResource(MonitorHelper.getStatusImg(entity.getStatus(), true));
				((LinearLayout)view.findViewById(R.id.path_text_row)).setVisibility(View.GONE);
				((LinearLayout)view.findViewById(R.id.group_text_row)).setVisibility(View.VISIBLE);
				((LinearLayout)view.findViewById(R.id.target_text_row)).setVisibility(View.VISIBLE);
				((TextView)(view.findViewById(R.id.group_name))).setText(MonitorHelper.getGroupFromFullPath(entity.getFullPath()));
				((ImageView)(view.findViewById(R.id.monitor_availability))).setImageResource(MonitorHelper.getAvailabilityImg(((Monitor) entity).getAvailability()));
				((TextView)(view.findViewById(R.id.target_name))).setText(((Monitor)entity).getTargetName());
				CheckBox monitorFaveChkBox = ((CheckBox)(view.findViewById(R.id.add_to_faves)));


                final Monitor favesMonitor = (Monitor) FavoritesList.getInstance(context).get(entity);

                //check the monitor status and decide whether we need to show enabled/or disabled monitor icon
                if (entity.getStatus().toUpperCase().equals(MonitorHelper.STATUS_DISABLED)) {
                    monitorFaveChkBox.setButtonDrawable(R.drawable.monitor_favorites_disabled_icon);
                } else {
                    monitorFaveChkBox.setButtonDrawable(R.drawable.monitor_favorites_icon);
                }

                monitorFaveChkBox.setChecked(favesMonitor != null ? true : entity.isFavorite());

				monitorFaveChkBox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//determine if adding or removing from favorites
				    	if(((CheckBox)v).isChecked())
				    	{   			    		
				    		entity.setFavorite(true);
				    		FavoritesList.getInstance(context).add(entity, false);
				    	}
				    	else
				    	{
				    		entity.setFavorite(false);  
							FavoritesList.getInstance(context).remove(entity, false);
				    	}				
					}
				});				
				
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
                        if (FavoritesList.getInstance(context).getRefreshError(entity.getSiteScopeServer())) {
                            Log.d(TAG, "Favorites List is not up to date. Return.");
                            return;
                        }
                        Intent intent = new Intent(context,MonitorDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra(MonitorHelper.ENTITY_OBJECT, entity);
                       context.startActivity(intent);
					}
				});
			}

		}
		//setSelectedEntity(entity);
		return view;
	}

	@Override
	public int getCount() {
		return FavoritesList.getInstance(context).size();
	}

	@Override
	public Entity getItem(int arg0) {
		return FavoritesList.getInstance(context).get(arg0);
	}
	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		//clean up favorites
		ArrayList<Entity>updatedFaves = new ArrayList<Entity>();
		Iterator<Entity> iterator = FavoritesList.getInstance(context).iterator();
		while(iterator.hasNext())			
		{
			Entity entity = (Entity)iterator.next();
			if(entity.isFavorite())
				updatedFaves.add(entity);
		}
		FavoritesList.getInstance(context).clearForRefresh();
		FavoritesList.getInstance(context).addAllForRefresh(updatedFaves);
		notifyDataSetChanged();
	}
}
