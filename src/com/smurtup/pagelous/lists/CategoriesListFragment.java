<<<<<<< HEAD
package com.smurtup.pagelous.lists;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import com.actionbarsherlock.app.SherlockListFragment;
import com.smurtup.pagelous.R;
import com.smurtup.pagelous.R.layout;
import com.smurtup.pagelous.models.Category;

public class CategoriesListFragment extends SherlockListFragment {
	
	private List<Category> categoryList = new ArrayList<Category>();
	private CategoriesListAdapter listAdapter;
	
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
		listAdapter = new CategoriesListAdapter(getActivity() , R.layout.category_list_item , categoryList);
		getListView().setDividerHeight(0);
		setListAdapter(listAdapter);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}	
}
=======
package com.smurtup.pagelous.lists;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import com.actionbarsherlock.app.SherlockListFragment;
import com.smurtup.pagelous.R;
import com.smurtup.pagelous.R.layout;
import com.smurtup.pagelous.models.Category;

public class CategoriesListFragment extends SherlockListFragment {
	
	private List<Category> categoryList = new ArrayList<Category>();
	private CategoriesListAdapter listAdapter;
	
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
		listAdapter = new CategoriesListAdapter(getActivity() , R.layout.category_list_item , categoryList);
		getListView().setDividerHeight(0);
		setListAdapter(listAdapter);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}	
}
>>>>>>> 1a9625daadac7067e76207f94d54ce0fc6f6ecf3
