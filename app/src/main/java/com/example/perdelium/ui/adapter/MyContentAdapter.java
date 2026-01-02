package com.example.perdelium.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.Content;
import com.example.perdelium.model.SimpleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyContentAdapter extends RecyclerView.Adapter<MyContentAdapter.ViewHolder> {

    private Context context;
    private List<Content> list;
    private ApiService apiService;

    public MyContentAdapter(Context context, List<Content> list) {
        this.context = context;
        this.list = list;
        this.apiService = ApiClient.getApiService(context);
    }

    public void update(List<Content> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.my_item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Content content = list.get(position);

        holder.title.setText(content.getTitle());
        holder.desc.setText(content.getExplanation());
        holder.writer.setText("Sen");

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eseri Sil")
                    .setMessage("Bu eseri silmek istediğine emin misin?")
                    .setPositiveButton("Sil", (d, w) ->
                            deleteMyContent(content.getId(), holder.getAdapterPosition())
                    )
                    .setNegativeButton("Vazgeç", null)
                    .show();
        });
    }

    // ✅ TEK VE DOĞRU METOT
    private void deleteMyContent(int contentId, int position) {

        Log.d("DELETE_DEBUG", "Silme isteği atılıyor. ID = " + contentId);

        apiService.deleteContent(String.valueOf(contentId))
                .enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call,
                                           Response<SimpleResponse> response) {

                        Log.d("DELETE_DEBUG", "HTTP CODE: " + response.code());
                        Log.d("DELETE_DEBUG", "isSuccessful: " + response.isSuccessful());

                        if (response.body() != null) {
                            Log.d("DELETE_DEBUG", "BODY success: " + response.body().isSuccess());
                            Log.d("DELETE_DEBUG", "BODY message: " + response.body().getMessage());
                        } else {
                            Log.d("DELETE_DEBUG", "BODY NULL");
                        }

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            list.remove(position);
                            notifyItemRemoved(position);

                            Toast.makeText(context,
                                    "Eser silindi",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,
                                    "Silme başarısız",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                        Log.e("DELETE_DEBUG", "FAILURE", t);
                        Toast.makeText(context,
                                "Bağlantı hatası",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, writer;
        ImageView btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.contentTitle);
            desc = itemView.findViewById(R.id.contentDescription);
            writer = itemView.findViewById(R.id.contentWriter);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
