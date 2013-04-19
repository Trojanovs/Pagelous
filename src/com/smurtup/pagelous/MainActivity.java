package com.smurtup.pagelous;


import net.simonvt.menudrawer.MenuDrawer;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends SherlockFragmentActivity implements OnItemClickListener, LocationListener {

    private static final String STATE_MENUDRAWER = MainActivity.class.getName() + ".menuDrawer";
	
	private MenuDrawer mMenuDrawer;

	private LocationManager locationManager;

	private GoogleMap googleMap;

	private Location myLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
		mMenuDrawer.setContentView(R.layout.activity_main);
		mMenuDrawer.setMenuView(R.layout.side_menu_list);
		SideMenuListFragment menu = (SideMenuListFragment)getSupportFragmentManager().findFragmentById(R.id.f_menu);
		menu.getListView().setOnItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        initMap();
        
        initMainMenuButtons();
        
	}
	
	private void initMainMenuButtons() {
		FrameLayout frameLayout1 = (FrameLayout)findViewById(R.id.FrameLayout01);
		FrameLayout frameLayout2 = (FrameLayout)findViewById(R.id.FrameLayout02);
		FrameLayout frameLayout3 = (FrameLayout)findViewById(R.id.FrameLayout03);
		FrameLayout frameLayout4 = (FrameLayout)findViewById(R.id.FrameLayout04);
		FrameLayout frameLayout5 = (FrameLayout)findViewById(R.id.FrameLayout05);
		FrameLayout frameLayout6 = (FrameLayout)findViewById(R.id.FrameLayout06);
		FrameLayout frameLayout7 = (FrameLayout)findViewById(R.id.FrameLayout07);
		FrameLayout frameLayout8 = (FrameLayout)findViewById(R.id.FrameLayout08);
		FrameLayout frameLayout9 = (FrameLayout)findViewById(R.id.FrameLayout09);
		
		frameLayout1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 1);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 2);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 3);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 4);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 5);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 6);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 7);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 8);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
		frameLayout9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), CategoryActivity.class);
				myIntent.putExtra("category", 9);
                startActivityForResult(myIntent, 0);
				
			}
		});
		
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			break;
		}
		
		return super.onOptionsItemSelected(item);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mMenuDrawer.setActiveView(view);
		mMenuDrawer.closeMenu();
	}

	@Override
	public void onLocationChanged(Location location) {
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude())));
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
