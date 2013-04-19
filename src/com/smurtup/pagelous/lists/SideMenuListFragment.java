package com.smurtup.pagelous.lists;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.smurtup.pagelous.R;

public class SideMenuListFragment extends SherlockListFragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		ListView listView = getListView();
		List<String> menuItems = new ArrayList<String>(); 
		menuItems.add("Refine");
		menuItems.add("Restaraunt/cafe");
		menuItems.add("Food/beverages");
		menuItems.add("Food/grocery");
		menuItems.add("Bar");
		menuItems.add("Kitchen/cooking");
		menuItems.add("Wine/spirints");
		menuItems.add("Cities nearby");
		menuItems.add("Moscow");
		menuItems.add("St. Preterburg");
		menuItems.add("Rostov Don");		
				
		ColorDrawable side_menu_separator = new ColorDrawable(this.getResources().getColor(R.color.side_menu_separator));
		listView.setDivider(side_menu_separator);
		listView.setDividerHeight(2);
		
		SideMenuListAdapter listAdapter = new SideMenuListAdapter(getActivity() , R.layout.side_menu_list_item , menuItems);
		
		setListAdapter(listAdapter);
	}	
}

