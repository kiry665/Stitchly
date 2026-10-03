package com.kiry665.stitchly.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kiry665.stitchly.R;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class DialogHelper {

    private DialogHelper() {
    }

    public static void showTextInputDialog(
            Context context,
            String title,
            String hint,
            String initialValue,
            String positiveButtonText,
            Consumer<String> onConfirm
    ) {
        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_text_input, null);

        TextInputLayout inputLayout =
                dialogView.findViewById(R.id.inputLayout);

        TextInputEditText input =
                dialogView.findViewById(R.id.textInput);

        inputLayout.setHint(hint);

        if (initialValue != null) {
            input.setText(initialValue);
            input.setSelection(initialValue.length());
        }

        AlertDialog dialog =
                new MaterialAlertDialogBuilder(context)
                        .setTitle(title)
                        .setView(dialogView)
                        .setNegativeButton(context.getString(R.string.cancel), null)
                        .setPositiveButton(positiveButtonText, null)
                        .create();

        dialog.setOnShowListener(unused ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(v -> {

                            String value =
                                    input.getText() == null
                                            ? ""
                                            : input.getText()
                                              .toString()
                                              .trim();

                            if (value.isEmpty()) {
                                inputLayout.setError(context.getString(R.string.required));
                                return;
                            }

                            inputLayout.setError(null);

                            onConfirm.accept(value);

                            dialog.dismiss();
                        })
        );

        dialog.show();
    }

    public static void showConfirmDialog(
            Context context,
            String title,
            String message,
            String positiveButtonText,
            Runnable onConfirm
    ) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.cancel), null)
                .setPositiveButton(
                        positiveButtonText,
                        (dialog, which) -> onConfirm.run()
                )
                .show();
    }

    public static void show2TextInputDialog(
            Context context,
            String title,
            String hint1,
            String hint2,
            String initialValue1,
            String initialValue2,
            String positiveButtonText,
            BiConsumer<String, Integer> onConfirm
    ) {
        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_2text_input, null);

        TextInputLayout inputLayout1 =
                dialogView.findViewById(R.id.inputLayout1);

        TextInputEditText input1 =
                dialogView.findViewById(R.id.textInput1);

        TextInputLayout inputLayout2 =
                dialogView.findViewById(R.id.inputLayout2);

        TextInputEditText input2 =
                dialogView.findViewById(R.id.textInput2);

        inputLayout1.setHint(hint1);
        inputLayout2.setHint(hint2);

        if (initialValue1 != null) {
            input1.setText(initialValue1);
            input1.setSelection(initialValue1.length());
        }

        if (initialValue2 != null) {
            input2.setText(initialValue2);
            input2.setSelection(initialValue2.length());
        }

        AlertDialog dialog =
                new MaterialAlertDialogBuilder(context)
                        .setTitle(title)
                        .setView(dialogView)
                        .setNegativeButton(context.getString(R.string.cancel), null)
                        .setPositiveButton(positiveButtonText, null)
                        .create();

        dialog.setOnShowListener(unused ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(v -> {

                            String value1 =
                                    input1.getText() == null
                                            ? ""
                                            : input1.getText()
                                              .toString()
                                              .trim();

                            String value2 =
                                    input2.getText() == null
                                            ? ""
                                            : input2.getText()
                                              .toString()
                                              .trim();

                            if (value1.isEmpty()) {
                                inputLayout1.setError(
                                        context.getString(R.string.required)
                                );
                                return;
                            }

                            inputLayout1.setError(null);

                            Integer number = null;

                            if (!value2.isEmpty()) {
                                try {
                                    number = Integer.parseInt(value2);

                                    if (number <= 0) {
                                        inputLayout2.setError(
                                                context.getString(
                                                        R.string.must_be_greater_than_zero
                                                )
                                        );
                                        return;
                                    }

                                    inputLayout2.setError(null);

                                } catch (NumberFormatException e) {
                                    inputLayout2.setError(
                                            context.getString(
                                                    R.string.invalid_number
                                            )
                                    );
                                    return;
                                }
                            } else {
                                inputLayout2.setError(null);
                            }

                            onConfirm.accept(value1, number);

                            dialog.dismiss();
                        })
        );

        dialog.show();
    }

    @FunctionalInterface
    public interface TwoInputConfirm {
        void accept(String name, int value);
    }
}