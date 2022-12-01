package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements AddFragment.AddElementEvent, EditFragment.EditElementEvent {

    ArrayList<String> users = new ArrayList<String>();
    ArrayList<String> selectedUsers = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView usersList;
    AddFragment addFragment = new AddFragment();
    EditFragment editFragment = new EditFragment();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("list", users);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            users = savedInstanceState.getStringArrayList("list");
        }
        setContentView(R.layout.activity_main);


        //Список
        // получаем элемент ListView
        usersList = findViewById(R.id.medicalSuppliesList);
        // создаем адаптер
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, users);
        // устанавливаем для списка адаптер
        usersList.setAdapter(adapter);

        // обработка установки и снятия отметки в списке
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем нажатый элемент
                String user = adapter.getItem(position);
                if(usersList.isItemChecked(position))
                    selectedUsers.add(user);
                else
                    selectedUsers.remove(user);
            }
        });

        //Удаление выбранных элементов
        Button btnDeletefragment = findViewById(R.id.buttonDeletefragment);
        btnDeletefragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(view);
            }
        });

        //Выбрать все элементы
        Button btnSelectAll = findViewById(R.id.buttonSelectAll);
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUsers.clear();
                for(int i = 0; i < users.size(); i++){
                    usersList.setItemChecked(i,true);
                    String user = adapter.getItem(i);
                    selectedUsers.add(user);
                }
            }
        });

        //Отменить выбор всех элементов
        Button btnCancel = findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedUsers.clear();
                for(int i = 0; i < users.size(); i++){
                    usersList.setItemChecked(i,false);
                }
            }
        });

        //Вывести выбранные элементы в тост
        Button btnInput = findViewById(R.id.buttonInput);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s ="";
                for(int i = 0; i < selectedUsers.size(); i++){
                    s = s + selectedUsers.get(i) + " , ";
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

        //Новая активити и поиск элемента
        ArrayList<String> find_list = new ArrayList<String>(); //отобранные значения
        Button btnActivityFiltr = findViewById(R.id.filtr);
        EditText name_search = findViewById(R.id.nameMedicalSupplies_filtr);
        btnActivityFiltr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String item : users) {
                    if (item.startsWith(name_search.getText().toString())) {
                        find_list.add(item);
                    }
                }
                    Intent intent = new Intent(MainActivity.this, ResActivity.class);
                    intent.putExtra("findItems", find_list);
                    startActivity(intent);
            }
        });


    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentAdd, fragment);
        ft.commit();
    }

    //Добавление элементов в список
    public void addEvent(String s){
        adapter.add(s);
        adapter.notifyDataSetChanged();
    }

    public void remove(View view){
        // получаем и удаляем выделенные элементы
        for(int i=0; i< selectedUsers.size();i++){
            adapter.remove(selectedUsers.get(i));
        }
        // снимаем все ранее установленные отметки
        usersList.clearChoices();
        // очищаем массив выбраных объектов
        selectedUsers.clear();

        adapter.notifyDataSetChanged();
    }

    //Редактирование элемента из списка
    public void editEvent(String s){
        int k = 0;
        String name_elem = "";
        for(int i = 0; i < selectedUsers.size(); i++){
            k = k + 1;
            name_elem = selectedUsers.get(i);
        }
        if (k == 0){
            Toast.makeText(getApplicationContext(), "Выберите элемент", Toast.LENGTH_LONG).show();
        }
        else if(k != 1){
            Toast.makeText(getApplicationContext(), "Выберите только один элемент из списка", Toast.LENGTH_LONG).show();
        }
        else{
            int index = users.indexOf(name_elem);
            users.set(index,s);
            Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }

}