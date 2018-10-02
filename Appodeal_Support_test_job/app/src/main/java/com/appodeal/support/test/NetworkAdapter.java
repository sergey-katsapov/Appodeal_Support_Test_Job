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

public class NetworkAdapter extends BaseAdapter {
    NativeAdViewContentStream nav_cs;
    NativeAd nativeAd;
    private LayoutInflater inflater;
    private List<Network> networks;

    public NetworkAdapter(Context context, List<Network> states) {
        this.networks = states;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return networks.size();
    }

    @Override
    public Object getItem(int position) {
        return networks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null ){
            if (position == 2){
                convertView = inflater.inflate(R.layout.ad_list, parent, false);
                nativeAd = Appodeal.getNativeAds(1).get(0);
                nav_cs = convertView.findViewById(R.id.native_ad);
                nav_cs.setNativeAd(nativeAd);
            }
            else {
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                }
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Network network = networks.get(position);
        try{
            viewHolder.imageView.setImageResource(network.getflagg());
            viewHolder.nameView.setText(network.getName());
            viewHolder.founderView.setText(network.getfounder());
        } catch (Exception e){
           e.printStackTrace();
        }
        return convertView;
    }

        private class ViewHolder {
        final ImageView imageView;
        final TextView nameView, founderView;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.flag);
            nameView = view.findViewById(R.id.name);
            founderView = view.findViewById(R.id.founder);
        }
    }
}
