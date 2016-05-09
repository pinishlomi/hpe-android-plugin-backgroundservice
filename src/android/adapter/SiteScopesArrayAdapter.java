package com.hpe.hybridsitescope.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.hpe.hybridsitescope.R;
import com.hpe.hybridsitescope.SearchSettingsActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Array adapter for sitescope accounts checkboxes shown on the Search Settings screen
 */
public class SiteScopesArrayAdapter extends ArrayAdapter<String> implements OnClickListener{

	private Context context;
	private ArrayList<String> items;
	private ArrayList<String> selectedItems = new ArrayList<String>();
	private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

	public SiteScopesArrayAdapter(int layout, ArrayList<String> items,Context context) {
		super(context, layout, items);
		this.context = context;
		this.items = items;
		loadData();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		String sitescopeName = items.get(position);
		View ssItem = convertView;
		if(ssItem == null)
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ssItem = inflater.inflate(R.layout.sitescope_search_acct_row, null);
		}
        CheckBox checkBoxItem = ((CheckBox)ssItem.findViewById(R.id.sitescope_name));
        checkBoxItem.setText(sitescopeName);
        checkBoxItem.setOnClickListener(this);
        boolean checkBoxValue = this.selectedItems.contains(sitescopeName);
        checkBoxItem.setSelected(checkBoxValue);
        checkBoxItem.setChecked(checkBoxValue);
		if(!checkBoxes.contains(checkBoxItem))
			checkBoxes.add(checkBoxItem);
		return (ssItem);
	}

	@Override
	public void onClick(View v) {
		CheckBox checkBox = (CheckBox)v;
		String itemText = (String)checkBox.getText();
		
		//handle the ALL checkbox
		if(itemText.equalsIgnoreCase(context.getString(R.string.all)))
		{
			if(checkBox.isChecked())
			{
				//de-select the other checkboxes
				this.selectedItems.clear();
				this.selectedItems.add(this.items.get(0));
				
				for(int i=1; i<getCount(); i++)
				{
					checkBoxes.get(i).setChecked(false);
				}
			}
			else
			{				
				//cannot de-select all items
				checkBoxes.get(0).setChecked(true);
				return;
			}
		}
		else if(checkBox.isChecked())
		{
			if(!this.selectedItems.contains(itemText))
			{
				this.selectedItems.add(itemText);			
			}
			
			//handle the select ALL checkbox
			this.selectedItems.remove(this.items.get(0)); //remove the ALL selection
			this.checkBoxes.get(0).setChecked(false);			
		}
		else
		{
			if(this.selectedItems.size()>1)
			{
				if(this.selectedItems.contains(itemText))
				{
					this.selectedItems.remove(itemText);
				}				
			}
			else
			{
				checkBox.setChecked(true);
			}
		}
	}

	public ArrayList<String> getSelectedItems() {
		this.selectedItems.trimToSize();
		return this.selectedItems;
	}
	
	private void loadData()
	{
		SharedPreferences searchSettings = this.context.getSharedPreferences("SiteScopePrefs", 0);
		String siteScopes = searchSettings.getString(SearchSettingsActivity.SITESCOPES, "");
		if(siteScopes.length()==0)
		{
			this.selectedItems.add(this.items.get(0));
		}
		else
		{			
			//remove spaces
			ArrayList<String> savedSelections = new ArrayList<String>(Arrays.asList(siteScopes.substring(1, siteScopes.length()-1).split(",")));
			ArrayList<String> trimmedSelections = new ArrayList<String>();
			for(String str: savedSelections)
			{
				//if All was selected then no need to select the rest
				if(str.trim().equals(this.items.get(0)))
					break;
				
				if(this.items.contains(str.trim()))
					trimmedSelections.add(str.trim());
			}
			if(trimmedSelections.size()==0)
				this.selectedItems.add(this.items.get(0));
			else
				this.selectedItems.addAll(trimmedSelections);
		}
	}
	
}
