package com.example.myapplication.Interface;

import com.example.myapplication.Models.ElementModel;

import java.util.List;

public interface IElementStorage {
    List<ElementModel> getFullList();
    List<ElementModel> findSimilar(ElementModel model);
    void insert(ElementModel model);
    void update(ElementModel model);
    void delete(ElementModel model);
}
