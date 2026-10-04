package com.kiry665.stitchly.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiry665.stitchly.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter
        extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryEntity> history = new ArrayList<>();

    public void setHistory(List<HistoryEntity> history) {
        this.history = history;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_history,
                        parent,
                        false
                );

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull HistoryViewHolder holder,
            int position
    ) {
        HistoryEntity item = history.get(position);

        holder.valueText.setText(
                item.oldValue + " → " + item.newValue
        );

        switch (item.action) {
            case INCREMENT:
                holder.actionIcon.setImageResource(
                        R.drawable.ic_plus
                );
                break;

            case DECREMENT:
                holder.actionIcon.setImageResource(
                        R.drawable.ic_minus
                );
                break;

            case RESET:
                holder.actionIcon.setImageResource(
                        R.drawable.ic_reset
                );
                break;
        }

        SimpleDateFormat format =
                new SimpleDateFormat(
                        "dd.MM HH:mm",
                        Locale.getDefault()
                );

        holder.timeText.setText(
                format.format(
                        new Date(item.time)
                )
        );
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    static class HistoryViewHolder
            extends RecyclerView.ViewHolder {

        ImageView actionIcon;
        TextView valueText;
        TextView timeText;

        public HistoryViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            actionIcon =
                    itemView.findViewById(
                            R.id.actionIcon
                    );

            valueText =
                    itemView.findViewById(
                            R.id.valueText
                    );

            timeText =
                    itemView.findViewById(
                            R.id.timeText
                    );

            actionIcon.setColorFilter(
                    valueText.getCurrentTextColor()
            );
        }
    }
}