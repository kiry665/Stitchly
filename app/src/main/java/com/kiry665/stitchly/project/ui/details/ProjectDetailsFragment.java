package com.kiry665.stitchly.project.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kiry665.stitchly.R;
import com.kiry665.stitchly.project.model.ProjectEntity;
import com.kiry665.stitchly.util.DialogHelper;

public class ProjectDetailsFragment extends Fragment {

    private ProjectDetailsViewModel viewModel;
    private PartAdapter adapter;

    public ProjectDetailsFragment() {
        super(R.layout.fragment_project_details);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);


        setupAdapter();
        setupViewModel();
        setupRecyclerView(view);
        setupButtons(view);
        setupMenu();
    }

    private void setupViewModel(){
        viewModel = new ViewModelProvider(this)
                .get(ProjectDetailsViewModel.class);

        viewModel.getParts().observe(
                getViewLifecycleOwner(),
                parts -> adapter.setParts(parts)
        );

        viewModel.getProject().observe(
                getViewLifecycleOwner(),
                p -> {
                    requireActivity().setTitle(p.name);
                }
        );
    }

    private void setupAdapter(){
        adapter = new PartAdapter(part -> {
            Bundle args = new Bundle();
            args.putLong("partId", part.id);

            NavHostFragment.findNavController(this)
                    .navigate(
                            R.id.action_projectFragment_to_counterFragment,
                            args
                    );
        });
    }

    private void setupButtons(View view){
        FloatingActionButton addPartButton =
                view.findViewById(
                        R.id.addPartButton
                );

        addPartButton.setOnClickListener(v -> {
            showCreatePartDialog();
        });
    }

    private void setupRecyclerView(View view){
        RecyclerView recyclerView =
                view.findViewById(
                        R.id.partsRecyclerView
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        recyclerView.setAdapter(adapter);
    }

    private void setupMenu() {
        requireActivity().addMenuProvider(
                new MenuProvider() {

                    @Override
                    public void onCreateMenu(
                            @NonNull Menu menu,
                            @NonNull MenuInflater menuInflater
                    ) {
                        menuInflater.inflate(
                                R.menu.menu_project_details,
                                menu
                        );
                    }

                    @Override
                    public boolean onMenuItemSelected(
                            @NonNull MenuItem menuItem
                    ) {
                        int id = menuItem.getItemId();

                        if (id == R.id.action_edit_project) {
                            showEditNameProjectDialog();
                            return true;
                        }

                        if (id == R.id.action_delete_project) {
                            showDeleteProjectDialog();
                            return true;
                        }

                        return false;
                    }
                },
                getViewLifecycleOwner(),
                Lifecycle.State.RESUMED
        );
    }

    private void showCreatePartDialog() {
        DialogHelper.show2TextInputDialog(
                requireContext(),
                getString(R.string.create_part),
                getString(R.string.part_name),
                getString(R.string.target),
                null,
                null,
                getString(R.string.create),
                (name, targetRows) ->
                        viewModel.createPart(name, targetRows)
        );
    }

    private void showEditNameProjectDialog(){

        ProjectEntity project = viewModel.getProject().getValue();

        if (project == null) {
            return;
        }

        DialogHelper.showTextInputDialog(
                requireContext(),
                getString(R.string.rename_project),
                getString(R.string.project_name),
                project.name,
                getString(R.string.save),
                name -> viewModel.renameProject(name)
        );
    }

    private void showDeleteProjectDialog(){
        DialogHelper.showConfirmDialog(
                requireContext(),
                getString(R.string.delete_project),
                getString(R.string.delete_project_message),
                getString(R.string.delete),
                () -> {
                    viewModel.deleteProject();
                    NavHostFragment.findNavController(this)
                            .navigateUp();
                }
        );
    }

}
