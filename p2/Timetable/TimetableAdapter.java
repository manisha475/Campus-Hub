package com.example.p2.Timetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2.R;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    private List<item_timetable> timetableList;

    public TimetableAdapter(List<item_timetable> timetableList) {
        this.timetableList = timetableList;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        item_timetable currentItem = timetableList.get(position);

        holder.textClassName.setText(currentItem.getCname());
        holder.textTime.setText(currentItem.getCtime());
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    public static class TimetableViewHolder extends RecyclerView.ViewHolder {
        public TextView textClassName;
        public TextView textTime;

        public TimetableViewHolder(@NonNull View itemView) {
            super(itemView);
            textClassName = itemView.findViewById(R.id.textClassName);
            textTime = itemView.findViewById(R.id.textTime);
        }
    }
}
