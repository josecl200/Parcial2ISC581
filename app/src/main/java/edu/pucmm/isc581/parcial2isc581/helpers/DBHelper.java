package edu.pucmm.isc581.parcial2isc581.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PARCIALSQLITE.DB";
    private static final Integer DB_VERSION = 1;

    public static final String PRODUCT_ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String CATEGORY_ID = "id";
    public static final String B64_IMG = "image";

    private  static final String CATEGORIAS = "CREATE TABLE IF NOT EXISTS CATEGORIES(" + CATEGORY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR UNIQUE NOT NULL)";
    private static final String PRODUCTOS = "CREATE TABLE IF NOT EXISTS PRODUCTS(" +
            PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR NOT NULL, " + PRICE + " INTEGER NOT NULL," + B64_IMG + " TEXT NOT NULL, CATEGORY INTEGER NOT NULL ,FOREIGN KEY(CATEGORY) REFERENCES CATEGORIES("+CATEGORY_ID+"))" ;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CATEGORIAS);
        sqLiteDatabase.execSQL(PRODUCTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("Sociedad");
    }
}
