package com.smurtup.pagelous;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.simonvt.menudrawer.MenuDrawer;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.ac;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.smurtup.pagelous.imageCache.DrawableManager;
import com.smurtup.pagelous.models.Category;

public class CategoryActivity extends SherlockFragmentActivity implements OnItemClickListener  {
    private static final String STATE_MENUDRAWER = MainActivity.class.getName() + ".menuDrawer";
	
	private MenuDrawer mMenuDrawer;

	private JSONObject jsonObject;
	private final Handler myHandler = new Handler();
	private List<Category> categoryList = new ArrayList<Category>();;	
	private CategoriesListFragment list;

	private GoogleMap googleMap;

	private Location myLocation;

	private LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		int categoryKey = getIntent().getExtras().getInt("category");
		String url = null;
		switch (categoryKey) {
		case 1:
			url = "http://www.pagelous.com/api/eat";
			break;
		case 2:
			url = "http://www.pagelous.com/api/shop";
			break;
		case 3:
			url = "http://www.pagelous.com/api/action";
			break;
		case 4:
			url = "http://www.pagelous.com/api/sport";
			break;
		case 5:
			url = "http://www.pagelous.com/api/feel";
			break;
		case 6:
			url = "http://www.pagelous.com/api/travel";
			break;
		case 7:
			url = "http://www.pagelous.com/api/business";
			break;
		case 8:
			url = "http://www.pagelous.com/api/society";
			break;
		case 9:
			url = "http://www.pagelous.com/api/lifestyle";
			break;
		default:
			break;
		}
		
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
		mMenuDrawer.setContentView(R.layout.category);
		mMenuDrawer.setMenuView(R.layout.side_menu_list);
		
		if (url != null)
			getCategoryItemsFromUrl(url);		
		SideMenuListFragment menu = (SideMenuListFragment)getSupportFragmentManager().findFragmentById(R.id.f_menu);
		menu.getListView().setOnItemClickListener(this);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(url);
		actionBar.setDisplayHomeAsUpEnabled(true);
		list = (CategoriesListFragment)getSupportFragmentManager().findFragmentById(R.id.f_categories);	
		
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
            		JSONParser jsonParser = new JSONParser();
            		jsonObject = jsonParser.getJSONFromUrl(url);
        			JSONArray dataArray = jsonObject.getJSONArray("pages");
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
	
}


