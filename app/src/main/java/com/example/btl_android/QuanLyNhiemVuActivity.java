package com.example.btl_android;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuanLyNhiemVuActivity extends AppCompatActivity {
    ListView lvNhiemVu;
    ImageButton btn_add;
    ArrayList<NhiemVuModel> data = new ArrayList<>();
    DBHelper mDbHelper = new DBHelper(QuanLyNhiemVuActivity.this);
    NhiemVuAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_nhiem_vu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvNhiemVu = findViewById(R.id.lvNhiemVu);
        btn_add = findViewById(R.id.btn_add_nhiem_vu);

        mDbHelper.addNhiemVu(new NhiemVuModel("2","2",2,2,2,2));
        data = mDbHelper.getAllNhiemVu();


        adapter = new NhiemVuAdapter(data,QuanLyNhiemVuActivity.this);
        lvNhiemVu.setAdapter(adapter);

        lvNhiemVu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopupEditSuKien(Gravity.CENTER,QuanLyNhiemVuActivity.this,data.get(i));
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupAddSuKien(Gravity.CENTER,QuanLyNhiemVuActivity.this);
            }
        });

    }

    public void showPopupEditSuKien(int gravity, Context context, NhiemVuModel model){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_nhiem_vu);
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtrributes = window.getAttributes();
        windowAtrributes.gravity = gravity;
        window.setAttributes(windowAtrributes);

        NumberPicker giobatdau = dialog.findViewById(R.id.hourPickers);
        NumberPicker phutbatdau = dialog.findViewById(R.id.minutePickers);

        NumberPicker gioketthuc = dialog.findViewById(R.id.hourPickere);
        NumberPicker phutketthuc = dialog.findViewById(R.id.minutePickere);

        Button btnThem = dialog.findViewById(R.id.btn_them);
        Button btn_xoa_su_kien = dialog.findViewById(R.id.btn_xoa);

        EditText edTenNhiemVu = dialog.findViewById(R.id.edTenNhiemVu);
        edTenNhiemVu.setText(model.getTenNhiemVu());
        EditText edghiChu = dialog.findViewById(R.id.edGhiChu);
        edghiChu.setText(model.getGhiChu());

// Thiết lập giá trị tối thiểu và tối đa cho giờ (từ 0 đến 23)
        giobatdau.setMinValue(0);
        giobatdau.setMaxValue(23);
        giobatdau.setValue(model.hstart);
        giobatdau.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ
        gioketthuc.setMinValue(0);
        gioketthuc.setMaxValue(23);
        gioketthuc.setValue(model.hend);
        gioketthuc.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ

        //Thiết lập giá trị tối thiểu và tối đa cho phút (từ 0 đến 59)
        phutbatdau.setMinValue(0);
        phutbatdau.setMaxValue(59);
        phutbatdau.setValue(model.mstart);
        phutbatdau.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ
        phutketthuc.setMinValue(0);
        phutketthuc.setMaxValue(59);
        phutketthuc.setValue(model.mend);
        phutketthuc.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ

        btnThem.setText("Sửa");
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNhiemVu = edTenNhiemVu.getText().toString().trim();
                String ghichu = edghiChu.getText().toString().trim();
                if (tenNhiemVu.length()!=0){
                    mDbHelper.addNhiemVu(new NhiemVuModel(tenNhiemVu,ghichu,giobatdau.getValue(),phutbatdau.getValue(),gioketthuc.getValue(),phutketthuc.getValue()));
                    update();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập tên nhiệm vụ",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btn_xoa_su_kien.setVisibility(View.VISIBLE);
        btn_xoa_su_kien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbHelper.deleteNhiemVu(model.ID_NhiemVu);
            }
        });
        dialog.show();
    }


    public void showPopupAddSuKien(int gravity, Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_nhiem_vu);
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtrributes = window.getAttributes();
        windowAtrributes.gravity = gravity;
        window.setAttributes(windowAtrributes);

        NumberPicker giobatdau = dialog.findViewById(R.id.hourPickers);
        NumberPicker phutbatdau = dialog.findViewById(R.id.minutePickers);

        NumberPicker gioketthuc = dialog.findViewById(R.id.hourPickere);
        NumberPicker phutketthuc = dialog.findViewById(R.id.minutePickere);

        Button btnThem = dialog.findViewById(R.id.btn_them);
        Button btn_xoa_su_kien = dialog.findViewById(R.id.btn_xoa);

        EditText edTenNhiemVu = dialog.findViewById(R.id.edTenNhiemVu);
        EditText edghiChu = dialog.findViewById(R.id.edGhiChu);

// Thiết lập giá trị tối thiểu và tối đa cho giờ (từ 0 đến 23)
        giobatdau.setMinValue(0);
        giobatdau.setMaxValue(23);
        giobatdau.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ
        gioketthuc.setMinValue(0);
        gioketthuc.setMaxValue(23);
        gioketthuc.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ

        //Thiết lập giá trị tối thiểu và tối đa cho phút (từ 0 đến 59)
        phutbatdau.setMinValue(0);
        phutbatdau.setMaxValue(59);
        phutbatdau.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ
        phutketthuc.setMinValue(0);
        phutketthuc.setMaxValue(59);
        phutketthuc.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNhiemVu = edTenNhiemVu.getText().toString().trim();
                String ghichu = edghiChu.getText().toString().trim();
                if (tenNhiemVu.length()!=0){
                    mDbHelper.addNhiemVu(new NhiemVuModel(tenNhiemVu,ghichu,giobatdau.getValue(),phutbatdau.getValue(),gioketthuc.getValue(),phutketthuc.getValue()));
                    update();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập tên nhiệm vụ",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btn_xoa_su_kien.setVisibility(View.GONE);
        dialog.show();
    }

    public void update(){
        data = mDbHelper.getAllNhiemVu();
        adapter = new NhiemVuAdapter(data,getApplicationContext());
        lvNhiemVu.setAdapter(adapter);
    }


}