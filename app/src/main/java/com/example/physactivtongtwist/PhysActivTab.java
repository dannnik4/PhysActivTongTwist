package com.example.physactivtongtwist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class PhysActivTab extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.physactiv_layout, container, false);

        textView = view.findViewById(R.id.WidgetText);
        Button createWidgetButton = view.findViewById(R.id.WidgetButton);

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showWidgetDialog(new WidgetDialogCallback() {
                    @Override
                    public void onPositiveClick(String widgetText) {
                        textView.setText(widgetText);
                    }
                }, "PhysActivTab");
            }
        });

        return view;
    }
}