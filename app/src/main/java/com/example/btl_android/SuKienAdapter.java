package com.example.btl_android;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.TextView;
import java.util.ArrayList;

public class SuKienAdapter extends BaseAdapter {
    Context context;
    private ArrayList<SuKienModal> mData;

    public SuKienAdapter(ArrayList<SuKienModal> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_su_kien,null);

        TextView tvTgian = view.findViewById(R.id.TgianBdau);
        tvTgian.setText(String.format("%02d",mData.get(i).gio) + "h" +String.format("%02d",mData.get(i).phut));
        TextView tvTenSuKien = view.findViewById(R.id.tenSuKien);
        tvTenSuKien.setText(mData.get(i).getTenSuKien());
        return view;
    }
}