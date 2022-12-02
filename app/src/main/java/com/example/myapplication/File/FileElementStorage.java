package com.example.myapplication.File;

import android.content.Context;

import com.example.myapplication.Interface.IElementStorage;
import com.example.myapplication.Models.ElementModel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileElementStorage implements IElementStorage {
    private final String fileName = "elementsFile";
    private static ArrayList<ElementModel> medicalSupplies;
    private Context context;

    public FileElementStorage(Context context){
        this.context = context;
        importFromJSON();
    }
    @Override
    public ArrayList<ElementModel> getFullList() {
        return medicalSupplies;
    }

    @Override
    public void insert(ElementModel model) {
        int id = 0;
        for (int i = 0; i < medicalSupplies.size(); ++i){
            if (id <= medicalSupplies.get(i).Id){
                id = medicalSupplies.get(i).Id + 1;
            }
        }
        model.Id = id;
        medicalSupplies.add(model);
        exportToJSON();
    }

    @Override
    public void update(ElementModel model) {
        for (int i = 0; i < medicalSupplies.size(); ++i){
            if (medicalSupplies.get(i).Id == model.Id){
                medicalSupplies.get(i).Name = model.Name;
            }
        }
        exportToJSON();
    }

    @Override
    public void delete(ElementModel model) {
        int index = -1;
        for (int i = 0; i < medicalSupplies.size(); ++i){
            if (medicalSupplies.get(i).Id == model.Id){
                index = i;
            }
        }
        if (index != -1){
            medicalSupplies.remove(index);
        }
        exportToJSON();
    }

    @Override
    public ArrayList<ElementModel> findSimilar(ElementModel model) {
        ArrayList<ElementModel> similarElements = new ArrayList<ElementModel>();
        for (int i = 0; i < medicalSupplies.size(); ++i){
            if (medicalSupplies.get(i).Name.contains(model.Name)){
                similarElements.add(medicalSupplies.get(i));
            }
        }
        return similarElements;
    }
    private void exportToJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                DataItems dataItems = new DataItems();
                dataItems.setElements(medicalSupplies);
                String jsonString = gson.toJson(dataItems);

                try(FileOutputStream fileOutputStream =
                            context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
                    fileOutputStream.write(jsonString.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while(thread.isAlive()){}
    }
    private void importFromJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try(FileInputStream fileInputStream = context.openFileInput(fileName);
                    InputStreamReader streamReader = new InputStreamReader(fileInputStream)){
                    Gson gson = new Gson();
                    DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
                    medicalSupplies = (ArrayList<ElementModel>) dataItems.getElements();
                }
                catch (IOException ex){
                    medicalSupplies = new ArrayList<ElementModel>();
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
        while(thread.isAlive()){}
    }
    private static class DataItems {
        private List<ElementModel> medicalSupplies;

        List<ElementModel> getElements() {
            return medicalSupplies;
        }
        void setElements(List<ElementModel> medicalSupplies) {
            this.medicalSupplies = medicalSupplies;
        }
    }
}
