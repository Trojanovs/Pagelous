package com.smurtup.pagelous;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.simonvt.menudrawer.MenuDrawer;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.smurtup.pagelous.lists.CategoriesListFragment;
import com.smurtup.pagelous.lists.SideMenuListFragment;
import com.smurtup.pagelous.models.Category;
import com.smurtup.pagelous.references.CategoryType;

public class CategoryActivity extends SherlockFragmentActivity implements OnItemClickListener, OnNavigationListener  {
    private static final String STATE_MENUDRAWER = MainActivity.class.getName() + ".menuDrawer";
	
	private MenuDrawer mMenuDrawer;

	private JSONObject jsonObject;
	private final Handler myHandler = new Handler();
	private List<Category> categoryList = new ArrayList<Category>();	
	private CategoriesListFragment list;

	private GoogleMap googleMap;

	private Location myLocation;
	private CategoryType categoryType;

	private LocationManager locationManager;
	
	private String url = "http://www.pagelous.com/api/";


	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		int categoryKey = getIntent().getExtras().getInt("category");
		categoryType = CategoryType.getByNum(categoryKey);
		
		
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
		mMenuDrawer.setContentView(R.layout.category);
		mMenuDrawer.setMenuView(R.layout.side_menu_list);	
		
		SideMenuListFragment menu = (SideMenuListFragment)getSupportFragmentManager().findFragmentById(R.id.f_menu);
		menu.getListView().setOnItemClickListener(this);
		
	
		
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(categoryType.getCode());
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		list = (CategoriesListFragment)getSupportFragmentManager().findFragmentById(R.id.f_categories);
		
		Context context = actionBar.getThemedContext();
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, CategoryType.getList());
	    listAdapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(listAdapter, this);
        actionBar.setSelectedNavigationItem(categoryKey-1);
		initMap();
	}
	
	
	public void initMap() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		 
        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
          
            //Getting Current Location
            myLocation = getLastBestLocation();       
         
            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            
            if(myLocation!=null){
            	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
            googleMap.getUiSettings().setZoomControlsEnabled(false);
        }
	}
	
	 private Location getLastBestLocation() {
	 	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    
	    long GPSLocationTime = 0;
	    if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

	    long NetLocationTime = 0;

	    if (null != locationNet) {
	        NetLocationTime = locationNet.getTime();
	    }

	    if ( 0 < GPSLocationTime - NetLocationTime ) {
	        return locationGPS;
	    }
	    else{
	        return locationNet;
	    }

	 }		
	
	
	public void getCategoryItemsFromUrl(final String url) {
        Thread thread = new Thread()
        {
			@Override
            public void run() {
                try {
                	System.out.println("gogogo");
            		JSONParser jsonParser = new JSONParser();
            		jsonObject = jsonParser.getJSONFromUrl(url);
        			JSONArray dataArray = jsonObject.getJSONArray("pages");
        			categoryList.clear();
        			for (int i = 0; i < dataArray.length(); i++) {
        				categoryList.add(new Category((JSONObject) dataArray.get(i))) ;    				
        			}                		
                	myHandler.post(updateRunnable);               	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };                      
        thread.start();           
	}
	
    final Runnable updateRunnable = new Runnable() {
        public void run() {
    		list.setCategoryList(categoryList);
    		list.setListShown(true);
        }
    };
    
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		mMenuDrawer.setActiveView(view);
		mMenuDrawer.closeMenu();
	}
	
	@Override
	public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }
		super.onBackPressed();
	}
	
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        mMenuDrawer.restoreState(inState.getParcelable(STATE_MENUDRAWER));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public List<Category> getCategoryList() {
		return categoryList;
	}


	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	}


	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		list.setListShown(false);
        if(myLocation!=null){
    		getCategoryItemsFromUrl(url + CategoryType.getByNum(itemPosition+1).getCode()+"?radius=500&coordinates="+myLocation.getLatitude()+","+myLocation.getLongitude());	
        }	
        else
        	getCategoryItemsFromUrl(url + CategoryType.getByNum(itemPosition+1).getCode());
		return false;
	}
	
}


