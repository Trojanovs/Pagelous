package com.smurtup.pagelous.references;

import java.util.ArrayList;
import java.util.List;

import com.smurtup.pagelous.models.Category;



public enum CategoryType {

    EAT("Eat", "eat",  1),
    SHOP("Shop", "shop", 2),
    ACTION("Action", "action", 3),
    SPORT("Sport", "sport", 4),
    FEEL("Feel good", "feel_good", 5),
    TRAVEL("Travel", "travel", 6),
    BUSINESS("Business", "business", 7),
    SOCIETY("Society", "society", 8),
    LIFESTYLE("Lifestyle", "lifestyle", 9);


    private final String code;
    private final String type;
    private final String localeCode;
    private final int raceNum;
    public final static String localeCodePrefix="CategoryType.";

    CategoryType(String code, String type, int num) {
        this.code = code;
        this.type = type;
        this.localeCode = localeCodePrefix + name();
        this.raceNum = num;
    }

    public String getCode() {
        return code;
    }
    
    public String getType() {
        return type;
    }


    public String getLocaleCode() {
        return localeCode;
    }

    public int getCategoryNum() {
        return raceNum;
    }

    public static CategoryType getByType(String type) {
        for(CategoryType e : values()) {
            if(e.getType().equals(type)) return e;
        }
        return null;
    }

    public static CategoryType getByNum(int num) {
        for(CategoryType e : values()) {
            if(e.getCategoryNum() == num) return e;
        }
        return null;
    }
    
    public static List<String> getList(){
    	List<String> categoryList = new ArrayList<String>();	
        for(CategoryType e : values()) {
            categoryList.add(e.getCode());
        }
        return categoryList;
    }

}