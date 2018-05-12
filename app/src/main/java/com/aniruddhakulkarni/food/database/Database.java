package com.aniruddhakulkarni.food.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.aniruddhakulkarni.food.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniruddhakulkarni on 12/05/18.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "food.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public List<Order> getCarts(){
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        String[] selection = {"ProductId", "ProductName", "Quantity", "Price", "Discount"};
        String tableName = "OrderDetail";
        sqLiteQueryBuilder.setTables(tableName);
        Cursor cursor = sqLiteQueryBuilder.query(database, selection, null, null, null, null, null);
        cursor.getCount();
        final List<Order> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("Insert into OrderDetail (ProductId, ProductName, Quantity, Price, Discount) values ('%s', '%s', '%s', '%s', '%s');",
                order.getProductId(), order.getProductName(),
                order.getQuantity(), order.getPrice(), order.getDiscount());
        sqLiteDatabase.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "Delete from OrderDetail";
        sqLiteDatabase.execSQL(query);
    }

}
