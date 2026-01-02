package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.Content;
import com.example.perdelium.model.FavoriteResponse;
import com.example.perdelium.ui.adapter.ContentAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private ContentAdapter adapter;
    private List<Content> favoriteList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ContentAdapter(
                requireContext(),
                favoriteList,
                content -> {

                    NavController navController =
                            NavHostFragment.findNavController(FavoritesFragment.this);

                    Bundle bundle = new Bundle();
                    bundle.putInt("contentId", content.getId());

                    navController.navigate(
                            R.id.action_favoritesFragment_to_contentDetailFragment,
                            bundle
                    );
                }
        );

        rvFavorites.setAdapter(adapter);

        getFavorites();

        return view;
    }

    private void getFavorites() {

        ApiService apiService = ApiClient.getApiService(getContext());

        apiService.getFavorites().enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call,
                                   Response<FavoriteResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getFavorites() != null) {

                    adapter.updateContentList(response.body().getFavorites());

                } else {
                    Toast.makeText(getContext(),
                            "Favori bulunamadı",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Bağlantı hatası",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
