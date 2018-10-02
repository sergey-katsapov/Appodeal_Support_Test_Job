package com.appodeal.support.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream;

import java.util.List;

public class StateAdapter extends BaseAdapter {

    NativeAdViewContentStream nav_cs;
    NativeAd nativeAd;


    private LayoutInflater inflater;
    private int layout;
    private List<State> states;


    public StateAdapter(Context context, List<State> states) {
        this.states = states;
//        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return states.size();
    }


    @Override
    public Object getItem(int position) {
        return states.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null ){
            if (position == 1){
                convertView = inflater.inflate(R.layout.ad_list, parent, false);
                nativeAd = Appodeal.getNativeAds(1).get(0);
                nav_cs = convertView.findViewById(R.id.native_ad);
                nav_cs.setNativeAd(nativeAd);

            } else {
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        State state = states.get(position);

        try{
            viewHolder.imageView.setImageResource(state.getFlagResource());
            viewHolder.nameView.setText(state.getName());
            viewHolder.capitalView.setText(state.getCapital());
        } catch (Exception e){
           e.printStackTrace();
        }


        return convertView;
    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView nameView, capitalView;

        ViewHolder(View view) {

            imageView = view.findViewById(R.id.flag);
            nameView = view.findViewById(R.id.name);
            capitalView = view.findViewById(R.id.capital);
        }
    }
}
