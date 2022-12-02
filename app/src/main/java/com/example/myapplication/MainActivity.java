package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.sax.Element;
import android.util.SparseBooleanArray;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.BD.ElementDBStorage;
import com.example.myapplication.File.FileElementStorage;
import com.example.myapplication.Interface.IElementStorage;
import com.example.myapplication.Logic.ElementLogic;
import com.example.myapplication.Models.ElementModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AddFragment.AddElementEvent, EditFragment.EditElementEvent {

    static IElementStorage elementStorage;
    ArrayAdapter<ElementModel> adapter;
    ListView elementList;
    AddFragment addFragment = new AddFragment();
    EditFragment editFragment = new EditFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sPref = getSharedPreferences("settings", MODE_PRIVATE);
        if (sPref.contains("saveMode")){
            String saveMode = sPref.getString("saveMode", "");
            if (Objects.equals(saveMode, "DBMobile")){
                elementStorage = new ElementDBStorage(this);
            }
            if (Objects.equals(saveMode, "Files")){
                elementStorage = new FileElementStorage(this);
            }
        }
        else{
            elementStorage = new FileElementStorage(this);
        }
        elementList = findViewById(R.id.medicalSuppliesList);
        adapter = new ArrayAdapter<ElementModel>(this,android.R.layout.simple_list_item_multiple_choice, elementStorage.getFullList());
        elementList.setAdapter(adapter);
        elementList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Удаление выбранных элементов
        Button btnDeletefragment = findViewById(R.id.buttonDeletefragment);
        btnDeletefragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray sparseBooleanArray  = elementList.getCheckedItemPositions();
                int len = elementList.getCount();
                for(int i = 0; i < len; i++)
                {
                    if(sparseBooleanArray.get(i) == true)
                    {
                        elementStorage.delete(adapter.getItem(i));
                    }
                }
                elementList.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });

        //Вывести выбранные элементы в тост
        Button btnInput = findViewById(R.id.buttonInput);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s ="";
                for(int i = 0; i < elementList.getCount(); i++){
                    s = s + elementList.getItemAtPosition(i).toString() + " , ";
                }
                Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Фрагменты
        Button btnAddfragment = findViewById(R.id.buttonAddfragment);
        Button btnEditfragment = findViewById(R.id.buttonEditfragment);

        setNewFragment(addFragment);

        btnAddfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewFragment(addFragment);
            }
        });

        btnEditfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewFragment(editFragment);
            }
        });

        //Настройки
        Button btnSettings = findViewById(R.id.buttonSetting);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });


    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentAdd, fragment);
        ft.commit();
    }

    //Добавление элементов в список
    public void addEvent(String s){
        //adapter.add(s);
        elementStorage.insert(new ElementModel(s));
        adapter.notifyDataSetChanged();
    }

    //Редактирование элемента из списка
    public void editEvent(String s){
        ElementModel pr = new ElementModel(s);
        SparseBooleanArray sparseBooleanArray  = elementList.getCheckedItemPositions();
        for(int i = 0; i < elementList.getCount(); i++)
        {
            if(sparseBooleanArray.get(i) == true)
            {
                pr.Id = adapter.getItem(i).Id;
                elementStorage.update(pr);
                Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_LONG).show();
            }
        }

        adapter.notifyDataSetChanged();
    }

}