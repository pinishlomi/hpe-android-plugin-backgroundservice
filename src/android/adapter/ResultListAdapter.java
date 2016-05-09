package com.hpe.hybridsitescope.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.hpe.hybridsitescope.data.ResultList;
import com.hpe.hybridsitescope.db.AccountInfoDbAdapter;
import com.hpe.hybridsitescope.db.AccountInfoDbAdapterImpl;

import java.util.Observable;
import java.util.Observer;

public class ResultListAdapter extends BaseAdapter implements Observer{

	public static final String POSITION = "position";	
	private Context context;

    private AccountInfoDbAdapter dbHelperProvider;

    private AccountInfoDbAdapter mDbHelper;

    public ResultListAdapter(Context context) {
        this.context = context;
        mDbHelper =  new AccountInfoDbAdapterImpl(context.getApplicationContext());
        ResultList.getInstance().addObserver(this);
    }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final View view = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.monitor_row, parent, false);
		view.setBackgroundResource(R.drawable.entity_row_selector);
		final Entity entity = ResultList.getInstance().get(position);
		
		//prepopulate favorite flag
		if(FavoritesList.getInstance(context).itemWithBaseInfoExists(entity.getName(), entity.getSiteScopeServer().getName(),
																entity.getClass().getSimpleName(), entity.getFullPath()))
			entity.setFavorite(true);
		else
			entity.setFavorite(false);
		
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
			CheckBox groupFavesChkBox = ((CheckBox)(view.findViewById(R.id.add_to_faves)));

            final Group favesGroup = (Group)FavoritesList.getInstance(context).get(entity);

            //check the monitor status and decide whether we need to show enabled/or disabled monitor icon
            if (entity.getStatus().toUpperCase().equals(MonitorHelper.STATUS_DISABLED)) {
                groupFavesChkBox.setButtonDrawable(R.drawable.group_favorites_disabled_icon);
            } else {
                groupFavesChkBox.setButtonDrawable(R.drawable.group_favorites_icon);
            }

            groupFavesChkBox.setChecked(favesGroup != null ? true : entity.isFavorite());


			groupFavesChkBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//determine if adding or removing from favorites
			    	if(((CheckBox)v).isChecked())
			    	{   		    		
			    		entity.setFavorite(true);
			    		FavoritesList.getInstance(context).add(entity, true);
			    	}
			    	else
			    	{
			    		entity.setFavorite(false);
			    		FavoritesList.getInstance(context).remove(entity, true);
			    	}				
				}
			});		
			
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
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
			((ImageView)(view.findViewById(R.id.monitor_availability))).setImageResource(MonitorHelper.getAvailabilityImg(((Monitor)entity).getAvailability()));
			((TextView)(view.findViewById(R.id.target_name))).setText(((Monitor)entity).getTargetName());
			CheckBox monitorFavesChkBox = ((CheckBox)(view.findViewById(R.id.add_to_faves)));

            final Monitor favesMonitor = (Monitor)FavoritesList.getInstance(context).get(entity);

            //check the monitor status and decide whether we need to show enabled/or disabled monitor icon
            if (entity.getStatus().toUpperCase().equals(MonitorHelper.STATUS_DISABLED)) {
                monitorFavesChkBox.setButtonDrawable(R.drawable.monitor_favorites_disabled_icon);
            } else {
                monitorFavesChkBox.setButtonDrawable(R.drawable.monitor_favorites_icon);
            }

            monitorFavesChkBox.setChecked(favesMonitor != null ? true : entity.isFavorite());

			monitorFavesChkBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//determine if adding or removing from favorites
			    	if(((CheckBox)v).isChecked())
			    	{   		    		
			    		entity.setFavorite(true);
			    		FavoritesList.getInstance(context).add(entity, true);
			    	}
			    	else
			    	{
			    		entity.setFavorite(false);
			    		FavoritesList.getInstance(context).remove(entity, true);
			    	}				
				}
			});			
			
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, MonitorDetails.class);
					intent.putExtra(POSITION, position);
					intent.putExtra(MonitorHelper.ENTITY_OBJECT, entity);
					view.getContext().startActivity(intent);				
				}
			});
		}	
		
		return view;
	}

	@Override
	public int getCount() {
		return ResultList.getInstance().size();
	}

	@Override
	public Object getItem(int arg0) {
		return ResultList.getInstance().get(arg0);
	}
	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public void update(Observable observable, Object data) {
		notifyDataSetChanged();
		
	}
}
