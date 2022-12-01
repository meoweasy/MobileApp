package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditFragment extends Fragment {

    MainActivity activity;

    public interface EditElementEvent {
        public void editEvent(String name);
    }

    EditElementEvent editElementEvent;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            activity = (MainActivity) context;
        }
        try {
            editElementEvent = (EditElementEvent) activity;
        }
        catch (ClassCastException ex){
            throw new ClassCastException(activity.toString() + " must implement UpdateFragmentData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button editButton = view.findViewById(R.id.addbutton);
        TextView name = view.findViewById(R.id.nameMedicalSupplies);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editElementEvent.editEvent(name.getText().toString());
            }
        });
    }
}