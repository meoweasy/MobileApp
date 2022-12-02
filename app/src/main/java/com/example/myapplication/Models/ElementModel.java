package com.example.myapplication.Models;

public class ElementModel {
    public int Id;
    public String Name;
    public ElementModel(String name){
        this.Name = name;
    }
    public ElementModel(){
        Name = "";
    }
    @Override
    public String toString(){
        return Name;
    }
}
