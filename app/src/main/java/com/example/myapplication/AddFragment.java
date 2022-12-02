package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.ElementModel;

public class AddFragment extends Fragment {

    MainActivity activity;
    public interface AddElementEvent {
        public void addEvent(String s);
    }

    AddElementEvent addElementEvent;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            activity = (MainActivity) context;
        }
        try {
            addElementEvent = (AddElementEvent) activity;
        }
        catch (ClassCastException ex){
            throw new ClassCastException(activity.toString() + " must implement AddFragmentData");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button addButton = view.findViewById(R.id.addbutton2);
        TextView name = view.findViewById(R.id.nameMedicalSupplies2);
        MainActivity activity = new MainActivity();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    addElementEvent.addEvent(name.getText().toString());
                    Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}