package com.example.firstapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.firstapplication.R;
import com.example.firstapplication.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        if (getArguments() == null || getArguments().getString("name") == null ) {
            TextView tv = root.findViewById(R.id.text_name);
            tv.setText("Страница выбранного рецепта");
        } else {
            TextView tv = root.findViewById(R.id.text_name);
            tv.setText(getArguments().getString("name"));
            tv = root.findViewById(R.id.text_kalorie);
            tv.setText(getArguments().getString("kalorie"));
            tv = root.findViewById(R.id.text_time);
            tv.setText(getArguments().getString("time"));
            tv = root.findViewById(R.id.text_ingredients);
            tv.setText(getArguments().getString("ingredients"));
            tv = root.findViewById(R.id.text_difficulty);
            tv.setText(getArguments().getString("difficulty"));
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}