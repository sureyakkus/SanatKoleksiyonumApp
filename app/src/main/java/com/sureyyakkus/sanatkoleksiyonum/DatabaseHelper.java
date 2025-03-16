package com.sureyyakkus.sanatkoleksiyonum;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SanatKoleksiyonu.db";
    private static final int DATABASE_VERSION = 3;

    // Tablo ve sütun isimleri
    public static final String TABLE_ESER = "eserler";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AD = "eserAdi";
    public static final String COLUMN_SANATCI = "sanatciAdi";
    public static final String COLUMN_YIL = "eserYili";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Yeni tabloyu oluştur
        String CREATE_TABLE_ESER = "CREATE TABLE " + TABLE_ESER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AD + " TEXT NOT NULL, "
                + COLUMN_SANATCI + " TEXT NOT NULL, "
                + COLUMN_YIL + " INTEGER NOT NULL)";
        db.execSQL(CREATE_TABLE_ESER);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eski tabloyu sil (eğer varsa) ve yeni tabloyu oluştur
        db.execSQL("DROP TABLE IF EXISTS eserler");
        onCreate(db);
    }


    // Eser ekleme
    public long insertEser(String eserAdi, String sanatciAdi, int eserYili) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("eserAdi", eserAdi);
        values.put("sanatciAdi", sanatciAdi);
        values.put("eserYili", eserYili);

        return db.insert("eserler", null, values); // Yeni kaydı ekle
    }


    // Tüm eserleri getir

    public List<Eser> getAllEserler() {
        List<Eser> eserList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("eserler", null, null, null, null, null, "id DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String eserAdi = cursor.getString(cursor.getColumnIndex("eserAdi"));
                String sanatciAdi = cursor.getString(cursor.getColumnIndex("sanatciAdi"));
                int eserYili = cursor.getInt(cursor.getColumnIndex("eserYili"));

                eserList.add(new Eser(id, eserAdi, sanatciAdi, eserYili));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return eserList;
    }

    public boolean updateEser(int id, String yeniAd, String yeniSanatci, int yeniYil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("eserAdi", yeniAd);
        values.put("sanatciAdi", yeniSanatci);
        values.put("eserYili", yeniYil);

        int rows = db.update("eserler", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0; // En az bir satır güncellenmişse true döner
    }

    /*// Eser güncelleme ilk kod
    public int updateEser(int id, String eserAdi, String sanatciAdi, int eserYili) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AD, eserAdi);
        values.put(COLUMN_SANATCI, sanatciAdi);
        values.put(COLUMN_YIL, eserYili);

        // Eseri güncelle
        int rows = db.update(TABLE_ESER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    } */
       /*  önceki güncelleme fonksiyonu
    public int updateEser(int id, String yeniAd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AD, yeniAd);

        int rows = db.update(TABLE_ESER, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;

    }*/


    // Eser silme
    public int deleteEser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_ESER, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}

