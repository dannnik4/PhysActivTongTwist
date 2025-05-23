package com.example.physactivtongtwist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        Button widgetButton = findViewById(R.id.WidgetButton);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.physactivtab:
                    fragment = new PhysActivTab();
                    break;
                case R.id.tongtwisttab:
                    fragment = new TongTwistTab();
                    break;
                default:
                    return false;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        });

        // Кнопка +
        widgetButton.setOnClickListener(v -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof PhysActivTab) {
                showWidgetDialog(((PhysActivTab) currentFragment)::onWidgetAdded, "PhysActivTab");
            } else if (currentFragment instanceof TongTwistTab) {
                showWidgetDialog(((TongTwistTab) currentFragment)::onWidgetAdded, "TongTwistTab");
            }
        });

        // стартовый фрагмент
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PhysActivTab())
                .commit();
    }

    public void showWidgetDialog(final WidgetDialogCallback callback, final String tabIndex) {
        Log.d("MainActivity", "Открываем диалог создания виджета");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.widget_text, null);
        builder.setView(dialogView);

        EditText widgetTextInput = dialogView.findViewById(R.id.WidgetText);

        String dialogTitle = tabIndex.equals("PhysActivTab") ? "Добавить виджет в физ. нагрузки" : "Добавить виджет в скороговорки";

        builder.setTitle(dialogTitle).setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String widgetText = widgetTextInput.getText().toString().trim();
                Log.d("MainActivity", "Введённый текст: " + widgetText);
                if (!widgetText.isEmpty()) {
                    saveWidgetText(widgetText, tabIndex);
                    if (callback != null) {
                        callback.onPositiveClick(widgetText);
                    }
                }
            }
        }).setNegativeButton("Отменить", null).show();
    }

    private void saveWidgetText(String text, String tabIndex) {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String existingText = preferences.getString("widgetText_" + tabIndex, "");
        String newText = existingText.isEmpty() ? text : existingText + "|" + text;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("widgetText_" + tabIndex, newText);
        editor.apply();
    }
}