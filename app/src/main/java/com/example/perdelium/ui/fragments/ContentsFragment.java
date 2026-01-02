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

public class ContentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    private List<Content> contentList = new ArrayList<>();

    private static final String TAG = "ContentsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contents, container, false);

        recyclerView = view.findViewById(R.id.rvContents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 🔥 Adapter + Detay Click Listener
        contentAdapter = new ContentAdapter(
                requireContext(),
                contentList,
                content -> {

                    NavController navController =
                            NavHostFragment.findNavController(ContentsFragment.this);

                    Bundle bundle = new Bundle();
                    bundle.putInt("contentId", content.getId()); // 🔥 KRİTİK

                    navController.navigate(
                            R.id.action_contentsFragment_to_contentDetailFragment,
                            bundle
                    );
                }
        );


        recyclerView.setAdapter(contentAdapter);

        getContents();

        return view;
    }

    private void getContents() {
        ApiService apiService = ApiClient.getApiService(getContext());

        apiService.getAllContents().enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call,
                                   Response<ContentResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getContents() != null) {

                    contentAdapter.updateContentList(response.body().getContents());

                    Log.d(TAG, "Contents loaded: "
                            + response.body().getContents().size());

                } else {
                    Toast.makeText(getContext(),
                            "İçerikler alınamadı",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                Log.e(TAG, "API Error", t);
                Toast.makeText(getContext(),
                        "Bağlantı hatası",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
