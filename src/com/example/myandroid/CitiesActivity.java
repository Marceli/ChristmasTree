package com.example.myandroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CitiesActivity extends ListActivity{

       // Data to put in the ListAdapter
       private String[] cities = new String[] {"Bergen", "New York", "Paris"};
       private ListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_layout);
        fillData();
    }

    // Fill the list with some data, this can be anything really
    public void fillData() {
    	ArrayAdapter citiesAdapter = new ArrayAdapter(this, R.layout.city_row, cities);
        setListAdapter(citiesAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);

        Intent i = new Intent(this, ShowProgram.class);
        String city_name = (String) getListAdapter().getItem(position);
        i.putExtra("city_name", city_name);

        // Create the view using FirstGroup's LocalActivityManager
        View view = FirstGroup.group.getLocalActivityManager()
        .startActivity("ShowProgram", i
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        .getDecorView();

       
        FirstGroup.group.replaceView(view);
    }
}

