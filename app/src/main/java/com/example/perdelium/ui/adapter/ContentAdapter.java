package com.example.perdelium.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perdelium.R;
import com.example.perdelium.model.Content;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private List<Content> contentList;

    public ContentAdapter(List<Content> contentList) {
        this.contentList = contentList;
    }

    // ViewHolder sınıfı, her bir öğe için kullanılan layout'u referans alır
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTitle, contentDescription, contentWriter;

        public ContentViewHolder(View itemView) {
            super(itemView);
            contentTitle = itemView.findViewById(R.id.contentTitle);
            contentDescription = itemView.findViewById(R.id.contentDescription);
            contentWriter = itemView.findViewById(R.id.contentWriter);
        }
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Item layout'ı burada inflate ediliyor
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Content content = contentList.get(position);

        // Başlık, açıklama ve yazar bilgilerini bağla
        if (content.getTitle() != null && !content.getTitle().isEmpty()) {
            holder.contentTitle.setText(content.getTitle());
        } else {
            holder.contentTitle.setText("No Title");
        }

        if (content.getExplanation() != null && !content.getExplanation().isEmpty()) {
            holder.contentDescription.setText(content.getExplanation());
        } else {
            holder.contentDescription.setText("No Description");
        }

        if (content.getUsers() != null && content.getUsers().getUsername() != null && !content.getUsers().getUsername().isEmpty()) {
            holder.contentWriter.setText(content.getUsers().getUsername());
        } else {
            holder.contentWriter.setText("No Writer");
        }
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    // Adapter'ı güncelleyerek yeni içerikleri ekle
    public void updateContentList(List<Content> newList) {
        contentList.clear();
        contentList.addAll(newList);
        notifyDataSetChanged();  // RecyclerView'ı güncelle
    }
}
