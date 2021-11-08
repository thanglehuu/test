package com.example.mytracnghiem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class quanlycauhoi extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tablecauhoi";
    private static final String KEY_ID = "_id";
    private static final String KEY_CAUHOI = "cauhoi";
    private static final String KEY_A = "cau_a";
    private static final String KEY_B = "cau_b";
    private static final String KEY_C = "cau_c";
    private static final String KEY_D = "cau_d";
    private static final String KEY_DA = "dapan";
    private static String DB_PATH = "/data/data/com.example.mytracnghiem/databases/";
    private static String DB_NAME = "databasecauhoi.sqlite";
    private final Context myContext;
    private SQLiteDatabase myDatabase;


    public quanlycauhoi(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDataBase() throws SQLiteException {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDatabase != null) {
            myDatabase.close();
        }
        super.close();
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkdb = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkdb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.d("error1", "Chưa có database");
        }

        if(checkdb != null) {
            checkdb.close();
        }
        return checkdb != null ? true : false;
    }

    private  void copyDatabase() throws IOException {
        InputStream input = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if(dbExist) {
            // Khong lam gi ca
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                Log.d("error 2","Error copying database");
            }
        }
    }

    public Cursor laycauhoi() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tablecauhoi", null);
        return cursor;
    }

    public List<cauhoi> layngaunhien(int socau) {
        List<cauhoi> ds_cauhoi = new ArrayList<cauhoi>();
        String limit = "0, " + socau;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, "random()", limit);

        cursor.moveToFirst();
        do {
            cauhoi x = new cauhoi();
            x._id = Integer.parseInt(cursor.getString(0));
            x.cauhoi = cursor.getString(1);
            x.cau_a = cursor.getString(2);
            x.cau_b = cursor.getString(3);
            x.cau_c = cursor.getString(4);
            x.cau_d = cursor.getString(5);
            x.dapan = cursor.getString(6);
            ds_cauhoi.add(x);
        } while (cursor.moveToNext());
        return ds_cauhoi;
    }

    public void addData(String cauhoi, String cau_a, String cau_b, String cau_c, String cau_d, String dapan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cauhoi", cauhoi);
        values.put("cau_a", cau_a);
        values.put("cau_b", cau_b);
        values.put("cau_c", cau_c);
        values.put("cau_d", cau_d);
        values.put("dapan", dapan);
        db.insert("tablecauhoi", null, values);
        db.close();
    }
}
