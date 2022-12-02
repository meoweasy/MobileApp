package com.example.myapplication.Logic;

import com.example.myapplication.Interface.IElementStorage;
import com.example.myapplication.Models.ElementModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ElementLogic implements IElementStorage{
    private static ArrayList<ElementModel> medicalSupplies;

    public ElementLogic(){
        medicalSupplies = new ArrayList<ElementModel>();
    }

    @Override
    public List<ElementModel> getFullList() {
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
    }

    @Override
    public void update(ElementModel model) {
        for (int i = 0; i < medicalSupplies.size(); ++i){
            if (medicalSupplies.get(i).Id == model.Id){
                medicalSupplies.get(i).Name = model.Name;
            }
        }
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
    }

    @Override
    public ArrayList<ElementModel> findSimilar(ElementModel model) {
        ArrayList<ElementModel> similarProducts = new ArrayList<ElementModel>();
        for (int i = 0; i < medicalSupplies.size(); ++i){
            if (medicalSupplies.get(i).Name.contains(model.Name)){
                similarProducts.add(medicalSupplies.get(i));
            }
        }
        return similarProducts;
    }
}

