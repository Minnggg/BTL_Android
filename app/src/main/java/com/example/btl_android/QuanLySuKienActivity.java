package com.example.btl_android;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class QuanLySuKienActivity extends AppCompatActivity  {
    SuKienAdapter adapter;
    ListView lvSuKien;
    ImageButton btn_add_su_kien;
    DBHelper mDbHelper ;
    ArrayList<SuKienModal> data = new ArrayList<>();

    AlarmManager alarmManager ;
    Intent intent = new Intent(QuanLySuKienActivity.this,AlarmReceiver.class);
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_su_kien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE) ;
        mDbHelper= new DBHelper(this.getApplicationContext());


        lvSuKien = findViewById(R.id.lvSuKien);
        btn_add_su_kien = findViewById(R.id.btn_add_su_kien);

        btn_add_su_kien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupAddSuKien(Gravity.CENTER,QuanLySuKienActivity.this);
            }
        });

        data = mDbHelper.getAllSuKien();
        adapter = new SuKienAdapter(data,getApplicationContext());
        lvSuKien.setAdapter(adapter);

        lvSuKien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopupEditSuKien(Gravity.CENTER,QuanLySuKienActivity.this,data.get(i));
            }
        });

    }

    public void showPopupEditSuKien(int gravity, Context context , SuKienModal modal){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_su_kien);
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtrributes = window.getAttributes();
        windowAtrributes.gravity = gravity;
        window.setAttributes(windowAtrributes);

        NumberPicker hourPicker = dialog.findViewById(R.id.hourPicker);
        NumberPicker minutePicker = dialog.findViewById(R.id.minutePicker);
        Button btnThem = dialog.findViewById(R.id.btn_them);
        Button btnXoa = dialog.findViewById(R.id.btn_xoa);
        EditText editText = dialog.findViewById(R.id.edTenSuKien);

        btnThem.setText("Sửa");

// Thiết lập giá trị tối thiểu và tối đa cho giờ (từ 0 đến 23)
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setValue(modal.gio);
        hourPicker.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ

// Thiết lập giá trị tối thiểu và tối đa cho phút (từ 0 đến 59)
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(modal.phut);
        minutePicker.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ

        editText.setText(modal.getTenSuKien());
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensukien = editText.getText().toString().trim();
                if (tensukien.length()!=0){
                    mDbHelper.addSuKien(new SuKienModal(tensukien,hourPicker.getValue(),minutePicker.getValue()));
                    update();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập tên sự kiện",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuanLySuKienActivity.this,"Xoa",Toast.LENGTH_SHORT).show();
                mDbHelper.deleteSuKien(modal.ID_SuKien);
                update();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showPopupAddSuKien(int gravity, Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_su_kien);
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtrributes = window.getAttributes();
        windowAtrributes.gravity = gravity;
        window.setAttributes(windowAtrributes);

        NumberPicker hourPicker = dialog.findViewById(R.id.hourPicker);
        NumberPicker minutePicker = dialog.findViewById(R.id.minutePicker);
        Button btnThem = dialog.findViewById(R.id.btn_them);
        Button btn_xoa_su_kien = dialog.findViewById(R.id.btn_xoa);

        EditText editText = dialog.findViewById(R.id.edTenSuKien);

// Thiết lập giá trị tối thiểu và tối đa cho giờ (từ 0 đến 23)
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ

// Thiết lập giá trị tối thiểu và tối đa cho phút (từ 0 đến 59)
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setTextColor(Color.parseColor("#FF0000")); // Đặt màu chữ thành màu đỏ
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensukien = editText.getText().toString().trim();
                if (tensukien.length()!=0){
                    mDbHelper.addSuKien(new SuKienModal(tensukien,hourPicker.getValue(),minutePicker.getValue()));

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourPicker.getValue()); // giờ (gio là biến chứa giá trị giờ)
                    calendar.set(Calendar.MINUTE, minutePicker.getValue()); // phút (phut là biến chứa giá trị phút)

                    pendingIntent = PendingIntent.getBroadcast(
                            QuanLySuKienActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    update();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập tên sự kiện",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btn_xoa_su_kien.setVisibility(View.GONE);


        dialog.show();
    }

    public void update(){
        data = mDbHelper.getAllSuKien();
        adapter = new SuKienAdapter(data,getApplicationContext());
        lvSuKien.setAdapter(adapter);
    }

}