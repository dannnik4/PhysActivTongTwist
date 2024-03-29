package com.example.physactivtongtwist;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class TongTwistTab extends Fragment {

    private LinearLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        this.container = view.findViewById(R.id.container);
        loadWidgetText("TongTwistTab");

        Button createWidgetButton = view.findViewById(R.id.WidgetButton);
        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showWidgetDialog(new WidgetDialogCallback() {
                    @Override
                    public void onPositiveClick(String widgetText) {
                        addWidget(widgetText);
                    }
                }, "TongTwistTab");
            }
        });

        return view;
    }

    private void loadWidgetText(String tabIndex) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedText = preferences.getString("widgetText_" + tabIndex, "");
        String[] blocks = savedText.split("\\|");

        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (String block : blocks) {
            View blockView = inflater.inflate(R.layout.widget_block, container, false);
            TextView textView = blockView.findViewById(R.id.text_view_block);
            Button editButton = blockView.findViewById(R.id.edit_button);
            Button deleteButton = blockView.findViewById(R.id.delete_button);

            textView.setText(block);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    container.removeView(blockView);
                    DeleteWidget(tabIndex, block);
                }
            });

            container.addView(blockView);
        }
    }

    private void DeleteWidget(String tabIndex, String widgetText) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedText = preferences.getString("widgetText_" + tabIndex, "");
        String newText = savedText.replaceFirst(Pattern.quote(widgetText + "|"), "");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("widgetText_" + tabIndex, newText);
        editor.apply();
        loadWidgetText(tabIndex);
        Toast.makeText(requireContext(), "Виджет удален", Toast.LENGTH_SHORT).show();
    }

    private void addWidget(String text) {
        View blockView = LayoutInflater.from(requireContext()).inflate(R.layout.widget_block, container, false);
        TextView textView = blockView.findViewById(R.id.text_view_block);
        Button editButton = blockView.findViewById(R.id.edit_button);
        Button deleteButton = blockView.findViewById(R.id.delete_button);
        textView.setText(text);
        container.addView(textView);

        // Сохраняем текст в файл
        saveToFile(text);
    }

    private void saveToFile(String text) {
        FileOutputStream fos = null;
        try {
            // Открываем файл для записи
            fos = requireContext().openFileOutput("blocks.txt", Context.MODE_APPEND);
            // Записываем текст в файл
            fos.write(text.getBytes());
            // Добавляем символ новой строки для разделения блоков текста
            fos.write(System.getProperty("line.separator").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}