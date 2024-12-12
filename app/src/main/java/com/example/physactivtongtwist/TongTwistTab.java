package com.example.physactivtongtwist;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.regex.Pattern;

public class TongTwistTab extends Fragment {

    private LinearLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        this.container = view.findViewById(R.id.container);
        loadWidgetText("TongTwistTab");

        Button createWidgetButton = view.findViewById(R.id.WidgetButton); // Изменено

        createWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).showWidgetDialog(new WidgetDialogCallback() {
                    @Override
                    public void onPositiveClick(String widgetText) {
                        showWidget(widgetText);
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

            final View finalBlockView = blockView;

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditDialog(new WidgetDialogCallback() {
                        @Override
                        public void onPositiveClick(String newText) {
                            textView.setText(newText);
                            updateWidgetText(tabIndex);
                        }
                    }, textView.getText().toString());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    container.removeView(finalBlockView);
                    DeleteWidget(tabIndex, textView.getText().toString());
                }
            });

            container.addView(blockView);
        }
    }

    private void showWidget(String text) {
        View blockView = LayoutInflater.from(requireContext()).inflate(R.layout.widget_block, container, false);
        TextView textView = blockView.findViewById(R.id.text_view_block);
        Button editButton = blockView.findViewById(R.id.edit_button);
        Button deleteButton = blockView.findViewById(R.id.delete_button);
        textView.setText(text);

        container.addView(blockView);
    }

    private void updateWidgetText(String tabIndex) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < container.getChildCount(); i++) {
            View childView = container.getChildAt(i);
            if (childView instanceof LinearLayout) {
                TextView textView = childView.findViewById(R.id.text_view_block);
                newText.append(textView.getText().toString()).append("|");
            }
        }
        editor.putString("widgetText_" + tabIndex, newText.toString());
        editor.apply();
    }

    private void showEditDialog(final WidgetDialogCallback callback, final String currentText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Редактировать текст");

        final EditText editText = new EditText(requireContext());
        editText.setText(currentText);
        builder.setView(editText);

        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newText = editText.getText().toString();
                callback.onPositiveClick(newText);
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void DeleteWidget(String tabIndex, String widgetText) {
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedText = preferences.getString("widgetText_" + tabIndex, "");
        String newText = savedText.replaceFirst(Pattern.quote(widgetText + "|"), "");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("widgetText_" + tabIndex, newText);
        editor.apply();
        requireActivity().recreate();
        Toast.makeText(requireContext(), "Виджет удалён", Toast.LENGTH_SHORT).show();
    }
}