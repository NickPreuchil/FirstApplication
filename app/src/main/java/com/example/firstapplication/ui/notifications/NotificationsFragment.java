package com.example.firstapplication.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.App;
import com.example.firstapplication.AppDatabase;
import com.example.firstapplication.CurrentUser;
import com.example.firstapplication.FavouriteRecipe;
import com.example.firstapplication.FavouriteRecipeDao;
import com.example.firstapplication.R;
import com.example.firstapplication.Receipt;
import com.example.firstapplication.ReceiptAdapter;
import com.example.firstapplication.RecipesDao;
import com.example.firstapplication.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private CurrentUser currentUser;

    List<Receipt> favouriteReceipts = new ArrayList<Receipt>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        currentUser = new CurrentUser(getContext());

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("MyThread", "InBackground");

                AppDatabase db = App.getInstance().getDatabase();
                FavouriteRecipeDao favouriteRecipeDao = db.favouriteRecipeDao();
                favouriteReceipts = favouriteRecipeDao.getReceiptsByUser(currentUser.getuserid());

                updateView(favouriteReceipts);
            }
        });

        thread.start();

        return root;
    }

    public void updateView(List<Receipt> receipts) {
        final List<Receipt> r = receipts;
        FragmentActivity MainActivity2 = getActivity();
        MainActivity2.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View root = binding.getRoot();
                RecyclerView recyclerView = root.findViewById(R.id.list);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
                recyclerView.setLayoutManager(layoutManager);
                ReceiptAdapter.OnStateClickListener stateClickListener = new ReceiptAdapter.OnStateClickListener() {
                    @Override
                    public void onStateClick(Receipt receipt, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", receipt.getName());
                        bundle.putString("kalorie", receipt.getCalorie().toString());
                        bundle.putString("difficulty", receipt.getDifficulty().toString());
                        bundle.putString("ingredients", receipt.getIngredients().toString());
                        bundle.putString("time", receipt.getTime().toString());
                        Navigation.findNavController(root).navigate(R.id.action_navigation_notifications_to_navigation_dashboard, bundle);
                    }
                };

                ReceiptAdapter adapter = new ReceiptAdapter(root.getContext(), r, stateClickListener, null);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}