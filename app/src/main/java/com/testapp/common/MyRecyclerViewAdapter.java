package com.testapp.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.testapp.R;
import com.testapp.dataset.LocationItem;

import java.util.List;

/**
 * Created by osman on 04-11-2016.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<LocationItem> bookItemList;
    private Context mContext;

    public MyRecyclerViewAdapter(Context context, List<LocationItem> bookItemList) {
        this.bookItemList = bookItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_locations_list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        LocationItem bookItem = bookItemList.get(i);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(bookItem.getThumbnail())) {
            Picasso.with(mContext).load(bookItem.getThumbnail())
                    .error(R.drawable.locaton_icon)
                    .placeholder(R.drawable.locaton_icon)
                    .into(customViewHolder.imageView);
        }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(bookItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != bookItemList ? bookItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }
}