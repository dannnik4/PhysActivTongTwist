package com.example.physactivtongtwist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class PhysActivTab extends Fragment {

    private LinearLayout widgetContainer;

    public PhysActivTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.physactiv_layout, container, false);

//        widgetContainer = view.findViewById(R.id.widgetContainer);

        Button createWidgetButton = view.findViewById(R.id.WidgetButton);

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void addWidget(String widgetText) {
        View widgetView = LayoutInflater.from(requireContext()).inflate(R.layout.widget_item, null);
        TextView itemText = widgetView.findViewById(R.id.itemText);
        itemText.setText(widgetText);

        widgetContainer.addView(widgetView);
    }
}