package com.kiry665.stitchly.part;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.kiry665.stitchly.R;
import com.kiry665.stitchly.history.HistoryBottomSheetFragment;
import com.kiry665.stitchly.project.ui.details.ProjectDetailsViewModel;
import com.kiry665.stitchly.settings.SettingsRepository;
import com.kiry665.stitchly.util.DialogHelper;

public class PartFragment extends Fragment {

    private PartViewModel viewModel;

    private boolean showTargetRows = false;

    public PartFragment() {
        super(R.layout.fragment_part);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel(view);
        setupButtons(view);
        setupMenu();
    }

    private void setupViewModel(View view){
        TextView counter =
                view.findViewById(R.id.counterText);

        viewModel = new ViewModelProvider(this)
                .get(PartViewModel.class);

        viewModel.getPart().observe(
                getViewLifecycleOwner(),
                part -> {
                    requireActivity().setTitle(part.name);
                    updateCounterText(counter, part);
                }
        );

        counter.setOnClickListener(v -> {
            PartEntity part = viewModel.getPart().getValue();

            if (part == null || part.targetRows == null) {
                return;
            }

            showTargetRows = !showTargetRows;

            updateCounterText(counter, part);
        });
    }

    private void setupButtons(View view){
        MaterialButton incrementButton =
                view.findViewById(R.id.incrementButton);

        MaterialButton decrementButton =
                view.findViewById(R.id.decrementButton);

        MaterialButton resetButton =
                view.findViewById(R.id.resetButton);

        incrementButton.setOnClickListener(v -> {
            performHapticFeedback(view);
            viewModel.increment();
        });

        decrementButton.setOnClickListener(v -> {
            performHapticFeedback(view);
            viewModel.decrement();
        });

        resetButton.setOnClickListener(v -> {
            performHapticFeedback(view);
            showResetPartCounterDialog();
        });
    }

    private void showResetPartCounterDialog(){
        DialogHelper.showConfirmDialog(
                requireContext(),
                getString(R.string.reset_counter),
                getString(R.string.reset_message),
                getString(R.string.reset),
                () -> viewModel.reset()
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
                                R.menu.menu_part,
                                menu
                        );
                    }

                    @Override
                    public boolean onMenuItemSelected(
                            @NonNull MenuItem menuItem
                    ) {
                        int id = menuItem.getItemId();

                        if (id == R.id.action_edit_part) {
                            showEditPartDialog();
                            return true;
                        }

                        if (id == R.id.action_delete_part) {
                            showDeletePartDialog();
                            return true;
                        }

                        if (id == R.id.action_history) {
                            Bundle args = new Bundle();
                            args.putLong(
                                    "partId",
                                    viewModel.getPart().getValue().id
                            );

                            HistoryBottomSheetFragment bottomSheet =
                                    new HistoryBottomSheetFragment();

                            bottomSheet.setArguments(args);

                            bottomSheet.show(
                                    getParentFragmentManager(),
                                    "HistoryBottomSheet"
                            );

                            return true;
                        }

                        return false;
                    }
                },
                getViewLifecycleOwner(),
                Lifecycle.State.RESUMED
        );
    }

    private void showEditPartDialog(){

        PartEntity part = viewModel.getPart().getValue();

        if (part == null) {
            return;
        }

        String targetRowsValue =
                part.targetRows == null
                        ? ""
                        : String.valueOf(part.targetRows);

        DialogHelper.show2TextInputDialog(
                requireContext(),
                getString(R.string.edit_part),
                getString(R.string.part_name),
                getString(R.string.target),
                part.name,
                targetRowsValue,
                getString(R.string.save),
                (name, targetRows) ->
                        viewModel.updatePart(name, targetRows)
        );
    }

    private void showDeletePartDialog(){
        DialogHelper.showConfirmDialog(
                requireContext(),
                getString(R.string.delete_part),
                getString(R.string.delete_part_message),
                getString(R.string.delete),
                () -> {
                    viewModel.deletePart();
                    NavHostFragment.findNavController(this)
                            .navigateUp();
                }
        );
    }

    private void updateCounterText(
            TextView counter,
            PartEntity part
    ) {
        if (showTargetRows && part.targetRows != null) {
            counter.setText(
                    part.currentRow + " / " + part.targetRows
            );
        } else {
            counter.setText(
                    String.valueOf(part.currentRow)
            );
        }
    }

    private void performHapticFeedback(View view) {
        SettingsRepository settingsRepository =
                new SettingsRepository(requireContext());

        if (settingsRepository.isVibrationEnabled()) {
            view.performHapticFeedback(
                    HapticFeedbackConstants.KEYBOARD_TAP
            );
        }
    }

}
