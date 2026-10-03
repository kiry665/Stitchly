package com.kiry665.stitchly.project.ui.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kiry665.stitchly.R;
import com.kiry665.stitchly.part.PartEntity;

import java.util.ArrayList;
import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder>{

    private List<PartEntity> parts = new ArrayList<>();

    private final OnPartClickListener listener;

    public PartAdapter(OnPartClickListener listener) {
        this.listener = listener;
    }

    public void setParts(List<PartEntity> parts) {
        this.parts = parts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PartViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_part,
                        parent,
                        false
                );

        return new PartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PartViewHolder holder,
            int position
    ) {

        PartEntity part = parts.get(position);

        holder.partName.setText(part.name);

        if (part.targetRows != null) {

            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressText.setVisibility(View.VISIBLE);

            int progress = (int) (
                    (part.currentRow * 100.0)
                            / part.targetRows
            );

            progress = Math.min(progress, 100);

            holder.progressBar.setProgress(progress);

            holder.progressText.setText(
                    part.currentRow + " / " + part.targetRows
            );

        } else {

            holder.progressBar.setVisibility(View.GONE);
            holder.progressText.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v ->
                listener.onPartClick(part)
        );
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    static class PartViewHolder
            extends RecyclerView.ViewHolder {

        TextView partName;
        TextView progressText;
        LinearProgressIndicator progressBar;

        public PartViewHolder(@NonNull View itemView) {
            super(itemView);

            partName =
                    itemView.findViewById(R.id.partName);

            progressText =
                    itemView.findViewById(R.id.progressText);

            progressBar =
                    itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnPartClickListener {
        void onPartClick(PartEntity part);
    }

}
