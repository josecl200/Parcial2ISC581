package edu.pucmm.isc581.parcial2isc581.dbModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ProgressBar;
import edu.pucmm.isc581.parcial2isc581.helpers.DBHelper;

public class ProductoDB {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public ProductoDB(Context context){
        this.context = context;
    }

    public ProductoDB open() throws SQLException {
        dbHelper = new DBHelper(this.context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, int price, int category, String baseImage){
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, name);
        values.put(DBHelper.PRICE, price);
        values.put("CATEGORY", category);
        values.put(DBHelper.B64_IMG, baseImage);
        sqLiteDatabase.insert("PRODUCTS", null, values);
    }

    public void update(Integer id, String name, int price, int category, String baseImage){
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, name);
        values.put(DBHelper.PRICE, price);
        values.put("CATEGORY", category);
        values.put(DBHelper.B64_IMG, baseImage);
        sqLiteDatabase.update("PRODUCTS", values, "id = ?", new String[]{id.toString()});

    }

    public Cursor fetchAll() {
        String[] columns = new String[]{DBHelper.PRODUCT_ID,
                DBHelper.NAME, DBHelper.PRICE,
                "CATEGORY", DBHelper.B64_IMG};
        Cursor cursor = sqLiteDatabase.query("PRODUCTS",columns,null,null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchByID(Integer id) {
        String[] columns = new String[]{DBHelper.PRODUCT_ID,
                DBHelper.NAME, DBHelper.PRICE,
                "CATEGORY", DBHelper.B64_IMG};
        Cursor cursor = sqLiteDatabase.query("PRODUCTS",columns,"id = ?",new String[]{id.toString()},null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Boolean delete(Integer id) {
        sqLiteDatabase.delete("PRODUCTS", "id = ?", new String[]{id.toString()});
        return Boolean.TRUE;
    }
}
