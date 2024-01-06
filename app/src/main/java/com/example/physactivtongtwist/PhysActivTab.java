package com.example.physactivtongtwist;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class PhysActivTab extends Fragment {

    public PhysActivTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.physactiv_layout, container, false);

        TextView textView = view.findViewById(R.id.WidgetText);
        Button createWidgetButton = view.findViewById(R.id.WidgetButton);

        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedText = preferences.getString("widgetText", "");
        textView.setText(savedText);

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showWidgetDialog(new WidgetDialogCallback() {
                    @Override
                    public void onPositiveClick(String widgetText) {
                        textView.setText(widgetText);
                    }
                });
            }
        });

        return view;
    }
}