package com.example.physactivtongtwist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new PhysActivTab());
        transaction.commit();
    }

    public void showWidgetDialog(final WidgetDialogCallback callback, final String tabIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.widget_text, null);
        builder.setView(dialogView);

        EditText WidgetText = dialogView.findViewById(R.id.WidgetText);

        String dialogTitle;
        if ("PhysActivTab".equals(tabIndex)) {
            dialogTitle = "Добавить виджет в физ. нагрузки";
        } else if ("TongTwistTab".equals(tabIndex)) {
            dialogTitle = "Добавить виджет в скороговорки";
        } else {
            dialogTitle = "Ошибка";
        }

        builder.setTitle(dialogTitle).setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String widgetText = WidgetText.getText().toString();
                saveWidgetText(widgetText, tabIndex);
                if (callback != null) {
                    callback.onPositiveClick(widgetText);
                }
            }
        }).setNegativeButton("Отменить", null).show();
    }

    private void saveWidgetText(String text, String tabIndex) {
            if (tabIndex == null || (!tabIndex.equals("PhysActivTab") && !tabIndex.equals("TongTwistTab"))) {
                throw new IllegalArgumentException("Invalid tabIndex: " + tabIndex);
            }

            SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
            String existingText = preferences.getString("widgetText_" + tabIndex, "");
            String newText = existingText.isEmpty() ? text : existingText + "|" + text;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("widgetText_" + tabIndex, newText);
            editor.apply();
    }
}