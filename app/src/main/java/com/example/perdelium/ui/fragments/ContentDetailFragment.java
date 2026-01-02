package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.Content;
import com.example.perdelium.model.ContentResponse;
import com.example.perdelium.model.IsFavoriteResponse;
import com.example.perdelium.model.SimpleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentDetailFragment extends Fragment {

    private static final String TAG = "ContentDetail";

    private TextView tvTitle, tvAuthor, tvDescription;
    private TextView btnFavorite; // 🔥 EKLENDİ
    private WebView webContent;

    private int contentId;
    private boolean isFavorite = false; // 🔥 EKLENDİ

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_detail, container, false);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvAuthor = view.findViewById(R.id.tvAuthor);
        tvDescription = view.findViewById(R.id.tvDescription);
        webContent = view.findViewById(R.id.webContent);
        btnFavorite = view.findViewById(R.id.btnFavorite); // 🔥 EKLENDİ

        webContent.getSettings().setJavaScriptEnabled(false);

        if (getArguments() != null) {
            contentId = getArguments().getInt("contentId");
            getContentDetail();
            checkIsFavorite(); // 🔥 EKLENDİ
        }

        // 🔥 Favori tıklama
        btnFavorite.setOnClickListener(v -> {
            if (isFavorite) {
                removeFavorite();
            } else {
                addFavorite();
            }
        });

        return view;
    }

    private void getContentDetail() {

        ApiService apiService = ApiClient.getApiService(getContext());

        apiService.getContentById(String.valueOf(contentId))
                .enqueue(new Callback<ContentResponse>() {
                    @Override
                    public void onResponse(Call<ContentResponse> call,
                                           Response<ContentResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getContent() != null) {

                            Content content = response.body().getContent();

                            tvTitle.setText(content.getTitle());
                            tvDescription.setText(content.getExplanation());

                            if (content.getUsers() != null) {
                                tvAuthor.setText(content.getUsers().getUsername());
                            }

                            // 🔥 HTML CONTENT
                            if (content.getHtml_content() != null
                                    && !content.getHtml_content().isEmpty()) {

                                Log.d(TAG, "HTML CONTENT LOADED");

                                String styledHtml =
                                        "<html><head><style>" +
                                                "body{color:black;padding:15px;background:#C9C099;" +
                                                "font-size:16px;line-height:1.6;}" +
                                                "h1,h2,h3{color:#B89C58;}" +
                                                "</style></head><body>" +
                                                content.getHtml_content() +
                                                "</body></html>";

                                webContent.loadDataWithBaseURL(
                                        null,
                                        styledHtml,
                                        "text/html",
                                        "UTF-8",
                                        null
                                );
                            }

                        } else {
                            Toast.makeText(getContext(),
                                    "İçerik bulunamadı",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContentResponse> call, Throwable t) {
                        Log.e(TAG, "API ERROR", t);
                        Toast.makeText(getContext(),
                                "Bağlantı hatası",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================= FAVORİ KISMI =================

    private void checkIsFavorite() {
        ApiService apiService = ApiClient.getApiService(requireContext());

        apiService.isFavorite(String.valueOf(contentId))
                .enqueue(new Callback<IsFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<IsFavoriteResponse> call,
                                           Response<IsFavoriteResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            isFavorite = response.body().isFavorite();
                            updateFavoriteText();
                        }
                    }

                    @Override
                    public void onFailure(Call<IsFavoriteResponse> call, Throwable t) {}
                });
    }

    private void updateFavoriteText() {
        if (isFavorite) {
            btnFavorite.setText("Favorilendi ❤️");
        } else {
            btnFavorite.setText("Favorilere ekle 🤍");
        }
    }

    private void addFavorite() {
        ApiService apiService = ApiClient.getApiService(requireContext());

        apiService.addFavorite(String.valueOf(contentId)) // ✅ BURASI
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call,
                                           Response<SimpleResponse> response) {

                        if (response.isSuccessful()) {
                            isFavorite = true;
                            updateFavoriteText();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {}
                });
    }


    private void removeFavorite() {
        ApiService apiService = ApiClient.getApiService(requireContext());

        apiService.removeFavorite(String.valueOf(contentId)) // ✅ BURASI
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call,
                                           Response<SimpleResponse> response) {

                        if (response.isSuccessful()) {
                            isFavorite = false;
                            updateFavoriteText();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {}
                });
    }
}


