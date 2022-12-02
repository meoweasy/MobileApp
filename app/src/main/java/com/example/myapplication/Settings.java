package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Objects;

import com.example.myapplication.databinding.ActivitySettingsBinding;

public class Settings extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySettingsBinding binding;
    SharedPreferences sPref;
    RadioGroup radioGroup;
    RadioButton radioButtonDB;
    RadioButton radioButtonFile;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        radioGroup = findViewById(R.id.radioGroupSaveMode);
        radioButtonDB = findViewById(R.id.radioButtonUseDB);
        radioButtonFile = findViewById(R.id.radioButtonUseFiles);
        buttonSave = findViewById(R.id.buttonSaveSettings);
        buttonSave.setOnClickListener(v -> {
            saveSettings();
        });

        sPref = getSharedPreferences("settings", MODE_PRIVATE);
        if (sPref.contains("saveMode")){
            String saveMode = sPref.getString("saveMode", "");
            if (Objects.equals(saveMode, "DBMobile")){
                radioButtonDB.setChecked(true);
            }
            if (Objects.equals(saveMode, "Files")){
                radioButtonFile.setChecked(true);
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveSettings();
    }
    protected void saveSettings(){
        Editor ed = sPref.edit();
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            if (radioButtonDB.isChecked()) {
                ed.putString("saveMode", "DBMobile");
            }
            if (radioButtonFile.isChecked()) {
                ed.putString("saveMode", "Files");
            }
            ed.commit();
        }
        Toast.makeText(this, "Настройки успешно сохранены", Toast.LENGTH_SHORT).show();
    }
}