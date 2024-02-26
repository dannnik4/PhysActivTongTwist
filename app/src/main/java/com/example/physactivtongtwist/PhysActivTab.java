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

    private LinearLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        this.container = view.findViewById(R.id.container);
        loadWidgetText("PhysActivTab");

        Button createWidgetButton = view.findViewById(R.id.WidgetButton);
        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showWidgetDialog(new WidgetDialogCallback() {
                    @Override
                    public void onPositiveClick(String widgetText) {
                        addWidget(widgetText);
                    }
                }, "PhysActivTab");
            }
        });

        return view;
    }

    private void loadWidgetText(String tabIndex) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedText = preferences.getString("widgetText_" + tabIndex, "");
        String[] blocks = savedText.split("\\|");

        for (String block : blocks) {
            addWidget(block);
        }
    }

    private void addWidget(String text) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);
        container.addView(textView);
    }
}