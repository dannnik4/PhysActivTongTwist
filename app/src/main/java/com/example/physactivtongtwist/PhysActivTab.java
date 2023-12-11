package com.example.physactivtongtwist;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

public class PhysActivTab extends Fragment {

    public PhysActivTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.physactiv_layout, container, false);

        TextView textView = view.findViewById(R.id.WidgetText);
        Button createWidgetButton = view.findViewById(R.id.WidgetButton);

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWidgetDialog();
            }
        });

        return view;
    }

    private void showWidgetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("New Widget")
                .setMessage("This is your new widget!")
                .setPositiveButton("OK", null) // You can add a positive button action here
                .setNegativeButton("Cancel", null) // You can add a negative button action here
                .show();
    }
}