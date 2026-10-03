package com.kiry665.stitchly.settings;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.kiry665.stitchly.R;

public class SettingsFragment extends Fragment {

    private SettingsRepository settingsRepository;

    public SettingsFragment(){
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.settings));

        settingsRepository =
                new SettingsRepository(requireContext());

        setupSettings(view);
    }

    private void setupSettings(View view) {

        View vibrationRow =
                view.findViewById(R.id.vibrationRow);

        View keepScreenOnRow =
                view.findViewById(R.id.keepScreenOnRow);

        MaterialCheckBox vibrationCheckBox =
                view.findViewById(R.id.vibrationCheckBox);

        MaterialCheckBox keepScreenOnCheckBox =
                view.findViewById(R.id.keepScreenOnCheckBox);

        vibrationCheckBox.setChecked(
                settingsRepository.isVibrationEnabled()
        );

        keepScreenOnCheckBox.setChecked(
                settingsRepository.isKeepScreenOnEnabled()
        );

        vibrationRow.setOnClickListener(v ->
                vibrationCheckBox.setChecked(
                        !vibrationCheckBox.isChecked()
                )
        );

        keepScreenOnRow.setOnClickListener(v ->
                keepScreenOnCheckBox.setChecked(
                        !keepScreenOnCheckBox.isChecked()
                )
        );

        vibrationCheckBox.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        settingsRepository
                                .setVibrationEnabled(isChecked)
        );

        keepScreenOnCheckBox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    settingsRepository
                            .setKeepScreenOnEnabled(isChecked);

                    applyKeepScreenOn(isChecked);
                }
        );
    }

    private void applyKeepScreenOn(boolean enabled) {
        if (enabled) {
            requireActivity()
                    .getWindow()
                    .addFlags(
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    );
        } else {
            requireActivity()
                    .getWindow()
                    .clearFlags(
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    );
        }
    }

}
