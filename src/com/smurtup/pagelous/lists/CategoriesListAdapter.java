package com.smurtup.pagelous.lists;

import java.util.List;

import com.google.android.gms.internal.m;
import com.smurtup.pagelous.R;
import com.smurtup.pagelous.R.id;
import com.smurtup.pagelous.imageCache.DrawableManager;
import com.smurtup.pagelous.models.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesListAdapter extends ArrayAdapter<Category> {

    private Context mContext;
    private int id;
    private List <Category>items ;

    public CategoriesListAdapter(Context context, int textViewResourceId , List<Category> list ) 
    {
        super(context, textViewResourceId, list);           
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView name = (TextView) mView.findViewById(R.id.category_item_name);
        TextView type = (TextView) mView.findViewById(R.id.category_item_type);
        ImageView imageView = (ImageView) mView.findViewById(R.id.category_item_logo);
        TextView country = (TextView) mView.findViewById(R.id.category_item_country);
        if(items.get(position) != null )
        {        	
        	name.setText(items.get(position).getName());
        	type.setText(items.get(position).getCategoryName());
			imageView.setImageDrawable(items.get(position).getIcon());
			country.setText(items.get(position).getCounty());
        }

        return mView;
    }

}