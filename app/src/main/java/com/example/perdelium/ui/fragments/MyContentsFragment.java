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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.Content;
import com.example.perdelium.model.ContentResponse;
import com.example.perdelium.ui.adapter.MyContentAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyContentsFragment extends Fragment {

    private RecyclerView rvMyContents;
    private MyContentAdapter adapter;   // 🔥 DOĞRU ADAPTER
    private List<Content> myContentList = new ArrayList<>();

    private static final String TAG = "MyContents";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycontents, container, false);

        rvMyContents = view.findViewById(R.id.rvMyContents);
        rvMyContents.setLayoutManager(new LinearLayoutManager(requireContext()));

        // ✅ SADECE 2 PARAMETRE
        adapter = new MyContentAdapter(
                requireContext(),
                myContentList
        );

        rvMyContents.setAdapter(adapter);

        getMyContents();

        return view;
    }

    private void getMyContents() {

        ApiService apiService = ApiClient.getApiService(requireContext());

        apiService.getMyContents().enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call,
                                   Response<ContentResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getContents() != null) {

                    adapter.update(response.body().getContents());

                    Log.d(TAG, "My contents loaded: "
                            + response.body().getContents().size());

                } else {
                    Toast.makeText(requireContext(),
                            "Eser bulunamadı",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                Log.e(TAG, "API ERROR", t);
                Toast.makeText(requireContext(),
                        "Bağlantı hatası",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
