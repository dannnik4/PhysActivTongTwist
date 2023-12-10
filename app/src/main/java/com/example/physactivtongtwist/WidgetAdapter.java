package com.example.physactivtongtwist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WidgetAdapter extends RecyclerView.Adapter<WidgetAdapter.WidgetViewHolder> {

    private List<String> widgetList;

    public WidgetAdapter(List<String> widgetList) {
        this.widgetList = widgetList;
    }

    @NonNull
    @Override
    public WidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_item, parent, false);
        return new WidgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WidgetViewHolder holder, int position) {
        String text = widgetList.get(position);
        holder.bind(text);
    }

    @Override
    public int getItemCount() {
        return widgetList.size();
    }

    static class WidgetViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public WidgetViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.widgetText);
        }

        public void bind(String text) {
            textView.setText(text);
        }
    }
}