package com.testapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.testapp.common.MyRecyclerViewAdapter;
import com.testapp.dataset.LocationItem;
import com.testapp.dataset.USStateItem;
import com.testapp.utils.CountryMaster;
import com.testapp.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by osman on 11-11-2016.
 */

public class StoreLocationsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<LocationItem> locationItemList;
    private MyRecyclerViewAdapter adapter;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_store_locations);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.section_label);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CountryMaster cm = CountryMaster.getInstance(context);
        ArrayList<USStateItem> countries = cm.getCountries();
        locationItemList = new ArrayList<>();
        for(int i = 0; i<countries.size(); i++){
            USStateItem country = cm.getCountryByPosition(i);
            LocationItem locationItem = new LocationItem();
            locationItem.setTitle(country.mCountryName);
            locationItemList.add(locationItem);
        }

        adapter = new MyRecyclerViewAdapter(context, locationItemList);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
