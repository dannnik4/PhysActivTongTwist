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

import java.util.ArrayList;
import java.util.List;

public class PhysActivTab extends Fragment {

    private TextView textView;
    private List<TextBlock> textBlocks = new ArrayList<>();
    private int currentBlockId = 0;

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

        loadTextBlocks();

        return view;
    }

    private void displayTextBlocks() {
        StringBuilder stringBuilder = new StringBuilder();

        for (TextBlock textBlock : textBlocks) {
            stringBuilder.append("Block ").append(textBlock.getId()).append(": ").append(textBlock.getText()).append("\n");
        }

        textView.setText(stringBuilder.toString());
    }

    private void loadTextBlocks() {
        displayTextBlocks();
    }
}