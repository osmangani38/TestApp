/*
 * Copyright 2015 www.vibin.it (vic.choy@gmail.com, victor.cui@vibin.it) 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.testapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.testapp.R;
import com.testapp.dataset.USStateItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Spinner;

public class CountryMaster {

	public static final String TAG = "CountryMaster";
	
	private static CountryMaster sInstance = null;
	private Context mContext = null;
	private String[] mCountryList;
	
	private ArrayList<USStateItem> mCountries = new ArrayList<USStateItem>();
	
	private CountryMaster(Context context) {
		mContext = context;
		Resources res = mContext.getResources();
		
		// builds country data from json
		InputStream is = res.openRawResource(R.raw.us_states);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String jsonString = writer.toString();
		JSONArray json = new JSONArray();
		try {
			json = new JSONArray(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mCountryList = new String[json.length()];
		for (int i = 0; i < json.length(); i++) {
			JSONObject node = json.optJSONObject(i);
			if (node != null) {
				USStateItem stateItem = new USStateItem();
				stateItem.mCountryName = node.optString("name");

				mCountries.add(stateItem);
				mCountryList[i] = stateItem.mCountryName;
			}
		}
	}

	public static CountryMaster getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new CountryMaster(context);
		}
		return sInstance;
	}
	
	/**
	 * Returns all {@link USStateItem} objects as an ArrayList<Country>
	 * 
	 * @return mCountries {@link CountryMaster#mCountries}
	 */
	public ArrayList<USStateItem> getCountries() {
		return mCountries;
	}
	
	/*
	 * <b>If you are using a SpinnerAdapter!<b> You can feed this payload into {@link SpinnerAdapter} constructor.
	 * 
	 * @return mCountryList {@link CountryMaster#mCountryList}
	 */
	public String[] getCountriesAsArray() {
		return mCountryList;
	}
	
	/**
	 * Again, helpful if you are using {@link Spinner#setSelection(int)}, to set the default country.
	 * 
	 * @param position	Integer
	 * @return country	{@link USStateItem}
	 */
	public USStateItem getCountryByPosition(int position) {
		USStateItem country = mCountries.get(position);
		//Log.d(TAG, country.mCountryIso);
		return country;
	}
	


}
