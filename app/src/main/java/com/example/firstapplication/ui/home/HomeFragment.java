package com.example.firstapplication.ui.home;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.firstapplication.MainActivity;
import com.example.firstapplication.MainActivity2;
import com.example.firstapplication.Receipt;
import com.example.firstapplication.ReceiptAdapter;
import com.example.firstapplication.R;
import com.example.firstapplication.RecipesDao;
import com.example.firstapplication.databinding.FragmentHomeBinding;
import com.example.firstapplication.ui.Parser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CurrentUser currentUser;

    List<Receipt> receipts = new ArrayList<Receipt>();

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        currentUser = new CurrentUser(getContext());


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("MyThread", "InBackground");

                AppDatabase db = App.getInstance().getDatabase();
                RecipesDao recipesDao = db.recipesDao();
//-----------------------------Prepopulate---------------------------------------
//                StringBuilder response = new StringBuilder();
//
//                HttpURLConnection connection = getConnection();
//
//                if (connection != null) {
//                    try {
//
//                        BufferedReader input = new BufferedReader(
//                                new InputStreamReader(connection.getInputStream()),8192);
//                        String line = null;
//
//                        while ((line = input.readLine()) != null)
//                        {
//                            response.append(line);
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.i("test", "Error connection");
//                }
//
//                Gson gson = new Gson();
//                Receipt[] rs = gson.fromJson(response.toString(), Receipt[].class);
//
//
//                Collections.addAll(receipts, rs);
//                for (Receipt recipe : receipts) {
//                    recipesDao.insert(recipe);
//                }
// ------------------------------------------------------------------------------
                receipts = recipesDao.getAll();

                updateView(receipts);
                
//                RecyclerView recyclerView = root.findViewById(R.id.list);
////                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
////                recyclerView.setLayoutManager(layoutManager);
//                ReceiptAdapter adapter = new ReceiptAdapter(root.getContext(), receipts);
//                recyclerView.setAdapter();
            }
        });

        thread.start();

        return root;
    }

    private URL geturl() {
        String urlStr = "https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/recipes2022.json";
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private HttpURLConnection getConnection() {
        HttpURLConnection connection = null;
        URL url = geturl();
        if (url == null) {
            return null;
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
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
                        Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_dashboard, bundle);
                    }
                };

                ReceiptAdapter.OnLongClickListener longClickListener = new ReceiptAdapter.OnLongClickListener() {
                    @Override
                    public void onLongClick(Receipt receipt, int position) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("name", receipt.getName());
//                        bundle.putString("kalorie", receipt.getCalorie().toString());
//                        bundle.putString("difficulty", receipt.getDifficulty().toString());
//                        bundle.putString("ingredients", receipt.getIngredients().toString());
//                        bundle.putString("time", receipt.getTime().toString());
//                        Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_dashboard, bundle);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true)
                                .setTitle(receipt.getName())
                                .setMessage("Add recipe to favourites?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AppDatabase db = App.getInstance().getDatabase();
                                        FavouriteRecipeDao favouriteRecipeDao = db.favouriteRecipeDao();
                                        FavouriteRecipe favouriteRecipe = new FavouriteRecipe();
                                        favouriteRecipe.setUser_id(currentUser.getuserid());
                                        favouriteRecipe.setRecipe_id(receipt.getId());
                                        favouriteRecipeDao.insert(favouriteRecipe);
                                        Log.d("Favourites", "arr: " + Arrays.toString(favouriteRecipeDao.getReceiptsIdByUser(currentUser.getuserid())));
                                        dialog.dismiss();
                                    }
                                })
                                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.show();
                    }
                };

                ReceiptAdapter adapter = new ReceiptAdapter(root.getContext(), r, stateClickListener, longClickListener);
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