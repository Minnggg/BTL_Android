package com.example.btl_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NhiemVuAdapter extends BaseAdapter {
    Context context;
    private ArrayList<NhiemVuModel> mData;

    public NhiemVuAdapter(ArrayList<NhiemVuModel> mData, Context context) {
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
        view = inflater.inflate(R.layout.item_nhiem_vu,null);

        TextView tvTgianBdau = view.findViewById(R.id.TgianBdau);
        tvTgianBdau.setText(String.format("%02d",mData.get(i).hstart) + "h" +String.format("%02d",mData.get(i).mstart));
        TextView tvTgianKetThuc = view.findViewById(R.id.TgianKthuc);
        tvTgianKetThuc.setText(String.format("%02d",mData.get(i).hend) + "h" +String.format("%02d",mData.get(i).mend));

        TextView tenNhiemVu = view.findViewById(R.id.tenNv);
        tenNhiemVu.setText(mData.get(i).getTenNhiemVu());

        TextView ghiChu = view.findViewById(R.id.ghiChu);
        ghiChu.setText(mData.get(i).getGhiChu());

        return view;
    }
}