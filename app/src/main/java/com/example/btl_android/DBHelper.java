package com.example.btl_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    public static final int version = 1;
    public static final String TABLE_NhiemVu = "QLNhiemVu";
    public static final String TABLE_SuKien = "QLSuKien";

    public static final String ID_NhiemVu = "idNhiemVu";
    public static final String Ten_NhiemVu = "TenNhiemVu";
    public static final String GhiChuNhiemVu = "GhiChuNhiemVu";
    public static final String GioBatGiau = "GioBatGiau";
    public static final String PhutBatDau = "PhutBatDau";
    public static final String GioKetThuc = "GioKetThuc";
    public static final String PhutKetThuc = "PhutKetThuc";

    public static final String ID_SuKien = "idSuKien";
    public static final String Ten_SuKien = "TenSuKien";
    public static final String GioSuKien = "GioSuKien";
    public static final String PhutSuKien = "PhutSuKien";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlNhiemVu = "CREATE TABLE "+TABLE_NhiemVu+" ("
                +ID_NhiemVu+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                +Ten_NhiemVu+" TEXT NOT NULL,"
                +GhiChuNhiemVu+" TEXT NOT NULL,"
                +GioBatGiau+" INTEGER NOT NULL,"
                +PhutBatDau+" INTEGER NOT NULL,"
                +GioKetThuc+" INTEGER NOT NULL,"
                +PhutKetThuc+" INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(sqlNhiemVu);

        String sqlSuKien = "CREATE TABLE "+TABLE_SuKien+" ("
                +ID_SuKien+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                +GioSuKien+" INTEGER NOT NULL ,"
                +PhutSuKien+" INTEGER NOT NULL,"
                +Ten_SuKien+" TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sqlSuKien);
    }



    public boolean addSuKien(SuKienModal sukien){
        if(sukien !=null){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Ten_SuKien,sukien.getTenSuKien());
            contentValues.put(GioSuKien,sukien.gio);
            contentValues.put(PhutSuKien,sukien.phut);
            long response = db.insert(TABLE_SuKien,null,contentValues);
            db.close();
            if(response >-1) return false;
            return true;
        }
        return false;
    }
    public boolean addNhiemVu(NhiemVuModel nhiemVuModel){
        if(nhiemVuModel !=null){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Ten_NhiemVu,nhiemVuModel.getTenNhiemVu());
            contentValues.put(GhiChuNhiemVu,nhiemVuModel.getGhiChu());
            contentValues.put(GioBatGiau,nhiemVuModel.hstart);
            contentValues.put(PhutBatDau,nhiemVuModel.mstart);
            contentValues.put(GioKetThuc,nhiemVuModel.hend);
            contentValues.put(PhutKetThuc,nhiemVuModel.mend);
            long response = db.insert(TABLE_NhiemVu,null,contentValues);
            db.close();
            if(response >-1) return false;
            return true;
        }
        return false;
    }

    public boolean updateSuKien(int id, SuKienModal sukien){
        if(id>=0 && sukien !=null){
            SQLiteDatabase db = getReadableDatabase();
            String whereClause = ID_SuKien+" =?";
            String[] whereArs = {id+""};
            ContentValues contentValues = new ContentValues();
            contentValues.put(Ten_SuKien,sukien.getTenSuKien());
            contentValues.put(GioSuKien,sukien.gio);
            contentValues.put(PhutSuKien,sukien.phut);
            db.update(TABLE_SuKien,contentValues,whereClause,whereArs);
            return true;
        }
        return false;
    }

    public boolean updateNhiemVu (int id, NhiemVuModel nhiemVuModel){
        if(id>=0 && nhiemVuModel !=null){
            SQLiteDatabase db = getReadableDatabase();
            String whereClause = ID_SuKien+" =?";
            String[] whereArs = {id+""};
            ContentValues contentValues = new ContentValues();
            contentValues.put(Ten_NhiemVu,nhiemVuModel.getTenNhiemVu());
            contentValues.put(GhiChuNhiemVu,nhiemVuModel.getGhiChu());
            contentValues.put(GioBatGiau,nhiemVuModel.hstart);
            contentValues.put(PhutBatDau,nhiemVuModel.mstart);
            contentValues.put(GioKetThuc,nhiemVuModel.hend);
            contentValues.put(PhutKetThuc,nhiemVuModel.mend);
            db.update(TABLE_NhiemVu,contentValues,whereClause,whereArs);
            return true;
        }
        return false;
    }


    public boolean deleteNhiemVu(int idNhiemVu){
        if(idNhiemVu>=0){
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = ID_NhiemVu+" =?";
            String[] whereArs = {idNhiemVu+""};
            db.delete(TABLE_NhiemVu,whereClause,whereArs);
            db.close();
            return true;
        }
        return false;
    }

    public boolean deleteSuKien(int idSuKien){
        if(idSuKien>=0){
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = ID_SuKien+" =?";
            String[] whereArs = {idSuKien+""};
            db.delete(TABLE_SuKien,whereClause,whereArs);
            db.close();
            return true;
        }
        return false;
    }


    @SuppressLint("Range")
    public ArrayList<NhiemVuModel> getAllNhiemVu(){
        ArrayList<NhiemVuModel> res = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NhiemVu;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                NhiemVuModel nhiemVuModel = new NhiemVuModel(
                        cursor.getString(cursor.getColumnIndex(Ten_NhiemVu)),
                        cursor.getString(cursor.getColumnIndex(GhiChuNhiemVu)),
                        cursor.getInt(cursor.getColumnIndex(GioBatGiau)),
                        cursor.getInt(cursor.getColumnIndex(PhutBatDau)),
                        cursor.getInt(cursor.getColumnIndex(GioKetThuc)),
                        cursor.getInt(cursor.getColumnIndex(PhutKetThuc)));
                nhiemVuModel.ID_NhiemVu = (cursor.getInt(cursor.getColumnIndex(ID_NhiemVu)));
                res.add(nhiemVuModel);
            }
        }
        return res;
    }

    @SuppressLint("Range")
    public ArrayList<SuKienModal> getAllSuKien(){
        ArrayList<SuKienModal> res = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_SuKien;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                SuKienModal suKienModal = new SuKienModal(
                        cursor.getString(cursor.getColumnIndex(Ten_SuKien)),
                        cursor.getInt(cursor.getColumnIndex(GioSuKien)),
                        cursor.getInt(cursor.getColumnIndex(PhutSuKien))
                );
                suKienModal.ID_SuKien = (cursor.getInt(cursor.getColumnIndex(ID_SuKien)));
                res.add(suKienModal);
            }
        }
        return res;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
