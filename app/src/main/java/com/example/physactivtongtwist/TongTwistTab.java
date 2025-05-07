package com.example.physactivtongtwist;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TongTwistTab extends Fragment {

    private LinearLayout container;
    private final String tabIndex = "TongTwistTab";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        this.container = view.findViewById(R.id.container);

        loadWidgetText();

        Button createWidgetButton = view.findViewById(R.id.WidgetButton);
        createWidgetButton.setOnClickListener(v -> {
            Log.d("TongTwistTab", "Нажата кнопка создания виджета");
            ((MainActivity) requireActivity()).showWidgetDialog(
                    widgetText -> {
                        Log.d("TongTwistTab", "Создаётся новый виджет: " + widgetText);
                        addWidget(widgetText);
                        saveWidgetText();
                        loadWidgetText();
                    }, tabIndex);
        });

        return view;
    }

    private void loadWidgetText() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", requireContext().MODE_PRIVATE);
        String savedText = preferences.getString("widgetText_" + tabIndex, "");
        List<String> widgets = new ArrayList<>(Arrays.asList(savedText.split("\\|")));

        container.removeAllViews();
        for (String widget : widgets) {
            if (!widget.isEmpty()) {
                addWidget(widget);
            }
        }
    }

    private void addWidget(String text) {
        Log.d("TongTwistTab", "Добавляем виджет с текстом: " + text);
        View blockView = LayoutInflater.from(requireContext()).inflate(R.layout.widget_block, container, false);

        TextView textView = blockView.findViewById(R.id.text_view_block);
        TextView counterText = blockView.findViewById(R.id.counter_text);
        Button buttonPlus = blockView.findViewById(R.id.button_plus);
        Button buttonMinus = blockView.findViewById(R.id.button_minus);
        Button buttonReset = blockView.findViewById(R.id.button_reset);
        Button editButton = blockView.findViewById(R.id.edit_button);
        Button deleteButton = blockView.findViewById(R.id.delete_button);

        textView.setText(text);
        counterText.setText("0");

        buttonPlus.setOnClickListener(v -> {
            int count = Integer.parseInt(counterText.getText().toString());
            count++;
            counterText.setText(String.valueOf(count));
        });

        buttonMinus.setOnClickListener(v -> {
            int count = Integer.parseInt(counterText.getText().toString());
            if (count > 0) count--;
            counterText.setText(String.valueOf(count));
        });

        buttonReset.setOnClickListener(v -> counterText.setText("0"));

        editButton.setOnClickListener(v -> showEditDialog(textView));
        deleteButton.setOnClickListener(v -> {
            container.removeView(blockView);
            saveWidgetText();
            Toast.makeText(requireContext(), "Виджет удалён", Toast.LENGTH_SHORT).show();
        });

        container.addView(blockView);
    }

    private void showEditDialog(TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Редактировать виджет");

        final EditText editText = new EditText(requireContext());
        editText.setText(textView.getText().toString());
        builder.setView(editText);

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            textView.setText(editText.getText().toString());
            saveWidgetText();
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void saveWidgetText() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder newText = new StringBuilder();

        for (int i = 0; i < container.getChildCount(); i++) {
            View childView = container.getChildAt(i);
            TextView textView = childView.findViewById(R.id.text_view_block);
            newText.append(textView.getText().toString()).append("|");
        }

        if (newText.length() > 0) {
            newText.setLength(newText.length() - 1);
        }

        editor.putString("widgetText_" + tabIndex, newText.toString());
        editor.apply();
    }
}