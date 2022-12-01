package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityResBinding;

import java.util.ArrayList;
import java.util.Collections;

public class ResActivity extends AppCompatActivity {
    ListView usersList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        Bundle arguments = getIntent().getExtras();
        ArrayList<String> items = (ArrayList<String>) arguments.get("findItems");
        //Список
        // получаем элемент ListView
        usersList = findViewById(R.id.medicalSuppliesListFiltred);
        // создаем адаптер
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, items);
        // устанавливаем для списка адаптер
        usersList.setAdapter(adapter);


        Button homeButton = findViewById(R.id.home);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}