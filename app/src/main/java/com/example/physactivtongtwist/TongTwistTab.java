package com.example.physactivtongtwist;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TongTwistTab extends Fragment {

    public TongTwistTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tongtwist_layout, container, false);
        TextView textView = view.findViewById(R.id.textView2);
        return view;
    }
}
