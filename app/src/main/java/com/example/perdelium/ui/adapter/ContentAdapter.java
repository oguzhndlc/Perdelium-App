package com.example.perdelium.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.Content;
import com.example.perdelium.model.IsFavoriteResponse;
import com.example.perdelium.model.SimpleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private List<Content> contentList;
    private OnDetailClickListener listener;
    private ApiService apiService;

    // 🔹 Detay tıklama interface
    public interface OnDetailClickListener {
        void onDetailClick(Content content);
    }

    // 🔹 Constructor (Context EKLENDİ)
    public ContentAdapter(Context context,
                          List<Content> contentList,
                          OnDetailClickListener listener) {

        this.contentList = contentList;
        this.listener = listener;
        this.apiService = ApiClient.getApiService(context);
    }

    // 🔹 Liste güncelleme
    public void updateContentList(List<Content> newList) {
        contentList.clear();
        contentList.addAll(newList);
        notifyDataSetChanged();
    }

    // 🔹 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView contentTitle, contentDescription, contentWriter;
        ImageView btnFavorite;
        View btnDetail;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTitle = itemView.findViewById(R.id.contentTitle);
            contentDescription = itemView.findViewById(R.id.contentDescription);
            contentWriter = itemView.findViewById(R.id.contentWriter);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {

        Content content = contentList.get(position);

        // 🔹 Güvenli veri setleme
        holder.contentTitle.setText(
                content.getTitle() != null ? content.getTitle() : "Başlık yok"
        );

        holder.contentDescription.setText(
                content.getExplanation() != null ? content.getExplanation() : "Açıklama yok"
        );

        if (content.getUsers() != null && content.getUsers().getUsername() != null) {
            holder.contentWriter.setText(content.getUsers().getUsername());
        } else {
            holder.contentWriter.setText("Anonim");
        }

        // ❤️ Favori durumu sor
        apiService.isFavorite(String.valueOf(content.getId()))
                .enqueue(new Callback<IsFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<IsFavoriteResponse> call,
                                           Response<IsFavoriteResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            boolean isFav = response.body().isFavorite();

                            holder.btnFavorite.setImageResource(
                                    isFav ? R.drawable.ic_favorite_filled
                                            : R.drawable.ic_favorite_outline
                            );

                            holder.btnFavorite.setOnClickListener(v -> {
                                if (isFav) {
                                    removeFavorite(content.getId(), holder.btnFavorite);
                                } else {
                                    addFavorite(content.getId(), holder.btnFavorite);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<IsFavoriteResponse> call, Throwable t) {}
                });

        // 🔹 Detay butonu
        holder.btnDetail.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDetailClick(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentList != null ? contentList.size() : 0;
    }

    // ❤️ Favoriye ekle
    private void addFavorite(int contentId, ImageView btn) {
        apiService.addFavorite(String.valueOf(contentId))
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call,
                                           Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            btn.setImageResource(R.drawable.ic_favorite_filled);
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {}
                });
    }

    // 💔 Favoriden çıkar
    private void removeFavorite(int contentId, ImageView btn) {
        apiService.removeFavorite(String.valueOf(contentId))
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call,
                                           Response<SimpleResponse> response) {
                        if (response.isSuccessful()) {
                            btn.setImageResource(R.drawable.ic_favorite_outline);
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {}
                });
    }
}
