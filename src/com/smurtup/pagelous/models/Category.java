<<<<<<< HEAD
package com.smurtup.pagelous.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.smurtup.pagelous.imageCache.DrawableManager;

import android.graphics.drawable.Drawable;

public class Category {
	private String id;
	private String name;
	private String categoryName;
	private String categoryType;
	private String iconURL;
	private String county;
	private Drawable icon;
	
	
	public Category (JSONObject jsonObject) throws JSONException{
		this.id = jsonObject.getString("id");
		this.name = jsonObject.getString("name");
		this.categoryName = jsonObject.getJSONObject("category").getString("name");
		this.categoryType = jsonObject.getJSONObject("category").getString("type");
		this.iconURL = jsonObject.getString("icon");
		this.county = jsonObject.getJSONObject("location").getJSONObject("country").getString("name");
		DrawableManager drawableManager = new DrawableManager();
		setIcon(drawableManager.fetchDrawable(getIconURL()));
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	


}
=======
package com.smurtup.pagelous.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.smurtup.pagelous.imageCache.DrawableManager;

import android.graphics.drawable.Drawable;

public class Category {
	private String id;
	private String name;
	private String categoryName;
	private String categoryType;
	private String iconURL;
	private String county;
	private Drawable icon;
	
	
	public Category (JSONObject jsonObject) throws JSONException{
		this.id = jsonObject.getString("id");
		this.name = jsonObject.getString("name");
		this.categoryName = jsonObject.getJSONObject("category").getString("name");
		this.categoryType = jsonObject.getJSONObject("category").getString("type");
		this.iconURL = jsonObject.getString("icon");
		this.county = jsonObject.getJSONObject("location").getJSONObject("country").getString("name");
		DrawableManager drawableManager = new DrawableManager();
		setIcon(drawableManager.fetchDrawable(getIconURL()));
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	


}
>>>>>>> 1a9625daadac7067e76207f94d54ce0fc6f6ecf3
