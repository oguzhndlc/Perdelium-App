package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.perdelium.model.ContentResponse;
import com.example.perdelium.ui.adapter.ContentAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView rvSuggestions;
    private ContentAdapter contentAdapter;
    private List<Content> contentList = new ArrayList<>();

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvSuggestions = view.findViewById(R.id.rvSuggestions);

        rvSuggestions.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        contentAdapter = new ContentAdapter(
                requireContext(),   // 🔥 EKLENDİ
                contentList,
                content -> {

                    NavController navController =
                            NavHostFragment.findNavController(HomeFragment.this);

                    Bundle bundle = new Bundle();
                    bundle.putInt("contentId", content.getId());

                    navController.navigate(
                            R.id.action_homeFragment_to_contentDetailFragment,
                            bundle
                    );
                }
        );


        rvSuggestions.setAdapter(contentAdapter);

        getSuggestionContents();

        return view;
    }

    private void getSuggestionContents() {

        ApiService apiService = ApiClient.getApiService(getContext());

        apiService.getSuggestionContents().enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(
                    Call<ContentResponse> call,
                    Response<ContentResponse> response
            ) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Content> suggestions = response.body().getContents();

                    if (suggestions != null && !suggestions.isEmpty()) {
                        contentAdapter.updateContentList(suggestions);
                        Log.d(TAG, "Suggestions loaded: " + suggestions.size());
                    } else {
                        Log.d(TAG, "Suggestions EMPTY");
                    }

                } else {
                    Log.e(TAG, "Response error");
                }
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                Log.e(TAG, "API ERROR", t);
                Toast.makeText(getContext(),
                        "Öneriler alınamadı",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
