package com.kiry665.stitchly.project.ui.list;

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
import com.kiry665.stitchly.util.DialogHelper;

public class ProjectListFragment extends Fragment {

    private ProjectListViewModel viewModel;
    private ProjectListAdapter adapter;

    public ProjectListFragment() {
        super(R.layout.fragment_project_list);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Stitchly");

        RecyclerView recyclerView =
                view.findViewById(
                        R.id.projectsRecyclerView
                );

        FloatingActionButton addProjectButton =
                view.findViewById(
                        R.id.addProjectButton
                );

        viewModel = new ViewModelProvider(this)
                .get(ProjectListViewModel.class);

        viewModel.getProjects().observe(
                getViewLifecycleOwner(),
                projects -> adapter.setProjects(projects)
        );

        adapter = new ProjectListAdapter(project -> {
            Bundle args = new Bundle();
            args.putLong("projectId", project.id);

            NavHostFragment.findNavController(this)
                    .navigate(
                            R.id.action_projectsFragment_to_projectFragment,
                            args
                    );
        });

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        recyclerView.setAdapter(adapter);

        addProjectButton.setOnClickListener(v -> {
            showCreateProjectDialog();
        });

        setupMenu();
    }

    private void showCreateProjectDialog() {
        DialogHelper.showTextInputDialog(
                requireContext(),
                getString(R.string.create_project),
                getString(R.string.project_name),
                null,
                getString(R.string.create),
                name -> viewModel.createProject(name)
        );
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
                                R.menu.menu_project_list,
                                menu
                        );
                    }

                    @Override
                    public boolean onMenuItemSelected(
                            @NonNull MenuItem menuItem
                    ) {
                        int id = menuItem.getItemId();

                        if (id == R.id.action_settings) {
                            openSettings();
                            return true;
                        }

                        return false;
                    }
                },
                getViewLifecycleOwner(),
                Lifecycle.State.RESUMED
        );
    }
    private void openSettings() {
        NavHostFragment.findNavController(this)
                .navigate(
                        R.id.action_projectsFragment_to_settingsFragment
                );
    }

}
