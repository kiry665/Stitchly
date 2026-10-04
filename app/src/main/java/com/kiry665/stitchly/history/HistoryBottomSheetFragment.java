package com.kiry665.stitchly.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kiry665.stitchly.R;

public class HistoryBottomSheetFragment
        extends BottomSheetDialogFragment {

    private HistoryViewModel viewModel;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_history_bottom_sheet,
                container,
                false
        );
    }

    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView(view);
        setupViewModel();
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView =
                view.findViewById(R.id.historyRecyclerView);

        adapter = new HistoryAdapter();

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this)
                .get(HistoryViewModel.class);

        viewModel.getHistory().observe(
                getViewLifecycleOwner(),
                history -> adapter.setHistory(history)
        );
    }
}