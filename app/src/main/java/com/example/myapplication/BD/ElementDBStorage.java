package com.example.myapplication.BD;

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import com.example.myapplication.Interface.IElementStorage;
import com.example.myapplication.Models.ElementModel;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ElementDBStorage implements IElementStorage {
    DBHelper dbHelper;
    private ArrayList<ElementModel> medicalSupplies = new ArrayList<ElementModel>();
    public ElementDBStorage(Context context){
        dbHelper = new DBHelper(context);
        readAll();
    }

    public void readAll(){
        medicalSupplies.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("medicalSupplies", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("Id");
            int nameColIndex = c.getColumnIndex("Name");
            do {
                ElementModel pr = new ElementModel();
                pr.Id = c.getInt(idColIndex);
                pr.Name = c.getString(nameColIndex);
                medicalSupplies.add(pr);
            } while (c.moveToNext());
        }
        dbHelper.close();
    }

    @Override
    public List<ElementModel> getFullList() {
        return medicalSupplies;
    }

    @Override
    public void insert(ElementModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", model.Name);
        long rowID = db.insert("medicalSupplies", null, cv);
        model.Id = (int) rowID;
        medicalSupplies.add(model);
        dbHelper.close();
    }

    @Override
    public void update(ElementModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", model.Name);
        db.update("medicalSupplies", cv, "Id = ?", new String[] {String.valueOf(model.Id)});
        dbHelper.close();
        readAll();
    }

    @Override
    public void delete(ElementModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", model.Name);
        db.delete("medicalSupplies", "Id = " + model.Id, null);
        dbHelper.close();
        readAll();
    }

    @Override
    public List<ElementModel> findSimilar(ElementModel model) {
        ArrayList<ElementModel> medicalSupplies = new ArrayList<ElementModel>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("medicalSupplies", null, "Name = ?", new String[] {model.Name}, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("Id");
            int nameColIndex = c.getColumnIndex("Name");
            do {
                ElementModel pr = new ElementModel();
                pr.Id = c.getInt(idColIndex);
                pr.Name = c.getString(nameColIndex);
                medicalSupplies.add(pr);
            } while (c.moveToNext());
        }
        dbHelper.close();
        return medicalSupplies;
    }

    private void importFromJSON(Context context, String fileName) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try(FileInputStream fileInputStream = context.openFileInput(fileName);
                    InputStreamReader streamReader = new InputStreamReader(fileInputStream)){
                    Gson gson = new Gson();
                    DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
                    medicalSupplies = (ArrayList<ElementModel>) dataItems.getElements();
                    for (int i = 0; i < medicalSupplies.size(); ++i){
                        insert(medicalSupplies.get(i));
                    }
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }
    private static class DataItems {
        private List<ElementModel> medicalSupplies;

        List<ElementModel> getElements() {
            return medicalSupplies;
        }
        void setProducts(List<ElementModel> medicalSupplies) {
            this.medicalSupplies = medicalSupplies;
        }
    }

    public int ElementCount(){
        return medicalSupplies.size();
    }
}
