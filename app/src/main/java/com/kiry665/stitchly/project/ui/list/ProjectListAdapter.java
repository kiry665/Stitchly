package com.kiry665.stitchly.project.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiry665.stitchly.R;
import com.kiry665.stitchly.project.model.ProjectEntity;

import java.util.ArrayList;
import java.util.List;

public class ProjectListAdapter
        extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private List<ProjectEntity> projects = new ArrayList<>();

    private final OnProjectClickListener listener;

    public ProjectListAdapter(OnProjectClickListener listener) {
        this.listener = listener;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_project,
                        parent,
                        false
                );

        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ProjectViewHolder holder,
            int position
    ) {

        ProjectEntity project = projects.get(position);

        holder.projectName.setText(project.name);

        holder.itemView.setOnClickListener(v ->
                listener.onProjectClick(project)
        );
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class ProjectViewHolder
            extends RecyclerView.ViewHolder {

        TextView projectName;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);

            projectName =
                    itemView.findViewById(R.id.projectName);
        }
    }

    public interface OnProjectClickListener {
        void onProjectClick(ProjectEntity project);
    }
}
