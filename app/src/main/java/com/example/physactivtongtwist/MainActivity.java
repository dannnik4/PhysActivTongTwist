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

    public void showWidgetDialog(final WidgetDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.widget_text, null);
        builder.setView(dialogView);

        EditText WidgetText = dialogView.findViewById(R.id.WidgetText);

        builder.setTitle("Новый виджет")
                .setPositiveButton("Создать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String widgetText = WidgetText.getText().toString();
                        saveWidgetText(widgetText);
                        if (callback != null) {
                            callback.onPositiveClick(widgetText);
                        }
                    }
                })
                .setNegativeButton("Отменить", null)
                .show();
    }

    private void saveWidgetText(String text) {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("widgetText", text);
        editor.apply();
    }
}
