package com.example.dailyneedsapp.data;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dailyneedsapp.model.item;
import com.example.dailyneedsapp.util.utils;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {


    public DataBaseHandler(Context context) {
        super(context, utils.DATABASE_NAME,null,utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase databasehandler) {
        String DAILY_NEEDS_TABLE = "CREATE TABLE " + utils.TABLE_NAME + "(" +
                utils.KEY_ID + " INTEGER PRIMARY KEY," +
                utils.KEY_ITEM_NAME + " TEXT," +
                utils.KEY_ITEM_QUANTITY + " INTEGER," +
                utils.KEY_ITEM_TYPE + " TEXT" + ")" ;
        databasehandler.execSQL(DAILY_NEEDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase databasehandler, int i, int i1) {
        String drop = String.valueOf("DROP TABLE IF EXISTS");
        databasehandler.execSQL(drop,new String[]{utils.TABLE_NAME});

        onCreate(databasehandler);

    }

    public void additem(item item){
        SQLiteDatabase databasehandler = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(utils.KEY_ITEM_NAME,item.getItem_name());
        values.put(utils.KEY_ITEM_QUANTITY,item.getItem_quantity());
        values.put(utils.KEY_ITEM_TYPE,item.getItem_type());

        databasehandler.insert(utils.TABLE_NAME,null,values);
        databasehandler.close();
    }
    public item getitem(int id){
        SQLiteDatabase databasehandler = this.getReadableDatabase();
        Cursor cursor = databasehandler.query(utils.TABLE_NAME,new String[]{utils.KEY_ID,utils.KEY_ITEM_NAME,utils.KEY_ITEM_QUANTITY,
                utils.KEY_ITEM_TYPE},utils.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null);

        item item = new item();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setItem_name(cursor.getString(1));
        item.setItem_quantity((cursor.getString(2)));
        item.setItem_type(cursor.getString(3));

        return item;

    }

    public List<item> getitemList(){
        SQLiteDatabase databasehandler = this.getReadableDatabase();
        List<item> itemList = new ArrayList<>();


        Cursor cursor = databasehandler.query(utils.TABLE_NAME,new String[]{utils.KEY_ID,utils.KEY_ITEM_NAME,utils.KEY_ITEM_QUANTITY,utils.KEY_ITEM_TYPE},
                null,null,null,null,utils.KEY_ID );

        if(cursor.moveToFirst()){
            do {
                item item = new item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setItem_name(cursor.getString(1));
                item.setItem_quantity((cursor.getString(2)));
                item.setItem_type(cursor.getString(3));
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        return itemList;

    }
    public int updateitem(item item){
        SQLiteDatabase databasehandler = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(utils.KEY_ITEM_NAME,item.getItem_name());
        values.put(utils.KEY_ITEM_QUANTITY,item.getItem_quantity());
        values.put(utils.KEY_ITEM_TYPE,item.getItem_type());

        return databasehandler.update(utils.TABLE_NAME,values,utils.KEY_ID+"=?",new String[]{String.valueOf(item.getId())});
    }
    public void deletecontact(item item){
        SQLiteDatabase databasehandler = this.getWritableDatabase();

        databasehandler.delete(utils.TABLE_NAME,utils.KEY_ID+"=?",new String[]{String.valueOf(item.getId())});
        databasehandler.close();
    }
    public int gettcount(){
        SQLiteDatabase databasehandler = getReadableDatabase();
        String select = "SELECT * FROM " + utils.TABLE_NAME;
        Cursor cursor = databasehandler.rawQuery(select,null);
        return cursor.getCount();
    }



}
