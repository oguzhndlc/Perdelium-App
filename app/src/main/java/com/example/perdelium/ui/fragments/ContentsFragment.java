package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.Content;
import com.example.perdelium.model.ContentResponse;
import com.example.perdelium.ui.MainActivity;
import com.example.perdelium.ui.adapter.ContentAdapter;
import com.example.perdelium.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    private List<Content> contentList = new ArrayList<>();  // Başlangıçta boş liste

    private static final String TAG = "ContentsFragment";  // Log tag

    public ContentsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Fragment layout inflating");  // Log: Fragment layout inflating
        View view = inflater.inflate(R.layout.fragment_contents, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contentAdapter = new ContentAdapter(contentList);
        recyclerView.setAdapter(contentAdapter);

        Log.d(TAG, "getContents: Fetching contents from API");  // Log: API'den içerik çekilmesi
        getContents();

        Button btnContDet = view.findViewById(R.id.ContentDetailBtn);

        btnContDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to ContentDetailFragment");  // Log: Butona tıklanmış
                GoToFrag(new ContentDetailFragment());
            }
        });

        return view;
    }

    private void getContents() {
        ApiService apiService = ApiClient.getApiService(getContext());
        apiService.getAllContents().enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contentList = response.body().getContents();
                    Log.d(TAG, "onResponse: Successfully fetched " + contentList.size() + " contents");

                    if (!contentList.isEmpty()) {
                        contentAdapter.updateContentList(contentList);
                        contentAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "onResponse: No contents found");
                        Toast.makeText(getContext(), "No content available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "onResponse: Failed to fetch contents. Response: " + response);
                    Toast.makeText(getContext(), "İçerikler alınamadı", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: API call failed", t);  // Log: API çağrısı başarısız oldu
                Toast.makeText(getContext(), "Bağlantı hatası: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GoToFrag(Fragment fragment) {
        if (getActivity() instanceof MainActivity) {
            Log.d(TAG, "GoToFrag: Navigating to " + fragment.getClass().getSimpleName());  // Log: Fragment'e geçiş yapılıyor
            ((MainActivity) getActivity()).loadFragment(fragment);
        }
    }
}
