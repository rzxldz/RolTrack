package com.example.roltrack.running;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.RunPoint;
import com.example.roltrack.viewmodel.RunViewModel;

import java.util.List;

public class RunHistoryFragment extends Fragment {

    private RunViewModel runViewModel;
    private RunSessionAdapter adapter;

    public RunHistoryFragment() {
        super(R.layout.fragment_run_history);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerRunHistory = view.findViewById(R.id.recyclerRunHistory);

        runViewModel = new ViewModelProvider(this).get(RunViewModel.class);

        adapter = new RunSessionAdapter(sessionId -> runViewModel.getPointsForSession(sessionId));

        recyclerRunHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerRunHistory.setAdapter(adapter);

        runViewModel.getAllSessions().observe(getViewLifecycleOwner(), sessions -> adapter.setSessions(sessions));
    }
}