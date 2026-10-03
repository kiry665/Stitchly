package com.kiry665.stitchly;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kiry665.stitchly.settings.SettingsFragment;
import com.kiry665.stitchly.settings.SettingsRepository;

public class MainActivity extends AppCompatActivity {

    private SettingsRepository settingsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.nav_host_fragment);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {

            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
            );

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    0
            );

            return insets;
        });

        settingsRepository =
                new SettingsRepository(this);

        applyKeepScreenOnSetting();
    }

    @Override
    protected void onResume() {
        super.onResume();

        applyKeepScreenOnSetting();
    }

    private void applyKeepScreenOnSetting() {
        if (settingsRepository.isKeepScreenOnEnabled()) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            );
        } else {
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            );
        }
    }
}
