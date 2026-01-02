package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.AddContentRequest;
import com.example.perdelium.model.ContentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContentFragment extends Fragment {

    private static final String TAG = "ADD_CONTENT_DEBUG";

    private EditText etTitle, etExplanation, etHtmlContent;
    private EditText etCastCount, etMaleCast, etFemaleCast;
    private Spinner spType, spTheme, spTime, spAgeLimit;
    private Button btnAddContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_content, container, false);

        etTitle = v.findViewById(R.id.etTitle);
        etExplanation = v.findViewById(R.id.etExplanation);
        etHtmlContent = v.findViewById(R.id.etHtmlContent);

        etCastCount = v.findViewById(R.id.etCastCount);
        etMaleCast = v.findViewById(R.id.etMaleCast);
        etFemaleCast = v.findViewById(R.id.etFemaleCast);

        spType = v.findViewById(R.id.spType);
        spTheme = v.findViewById(R.id.spTheme);
        spTime = v.findViewById(R.id.spTime);
        spAgeLimit = v.findViewById(R.id.spAgeLimit);

        btnAddContent = v.findViewById(R.id.btnAddContent);

        setupSpinners();

        btnAddContent.setOnClickListener(vw -> validateAndSend());

        return v;
    }

    private void setupSpinners() {

        spType.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Trajedi", "Drama", "Komedi", "Opera", "Müzikal", "Diğer"}));

        spTheme.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Aile", "Aşk", "Toplumsal", "Tarih", "Psikoloji", "Fantastik", "Diğer"}));

        spTime.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Kısa Oyun", "Tek Perde", "Çift Perde", "3+ Perde"}));

        spAgeLimit.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Genel İzleyici", "+13", "+18"}));
    }

    private void validateAndSend() {

        String title = etTitle.getText().toString().trim();
        String explanation = etExplanation.getText().toString().trim();
        String html = etHtmlContent.getText().toString().trim();

        if (title.isEmpty() || html.isEmpty()) {
            Toast.makeText(getContext(),
                    "Eser adı ve içerik zorunludur",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int cast = parseInt(etCastCount);
        int male = parseInt(etMaleCast);
        int female = parseInt(etFemaleCast);

        if (cast > 0 && male + female != cast) {
            Toast.makeText(getContext(),
                    "Oyuncu sayıları uyuşmuyor",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        sendContent(
                title,
                explanation,
                html,
                spType.getSelectedItem().toString(),
                spTheme.getSelectedItem().toString(),
                spTime.getSelectedItem().toString(),
                spAgeLimit.getSelectedItem().toString(),
                cast,
                male,
                female
        );
    }

    private void sendContent(String title,
                             String explanation,
                             String htmlContent,
                             String type,
                             String theme,
                             String time,
                             String ageLimit,
                             int cast,
                             int male,
                             int female) {

        ApiService apiService = ApiClient.getApiService(requireContext());

        AddContentRequest request = new AddContentRequest(
                title,
                explanation,
                htmlContent,
                type,
                theme,
                time,
                ageLimit,
                cast,
                male,
                female
        );

        // 🔍 REQUEST LOG
        Log.d(TAG, "REQUEST GÖNDERİLİYOR");
        Log.d(TAG, "title=" + title);
        Log.d(TAG, "html_length=" + htmlContent.length());
        Log.d(TAG, "type=" + type + ", theme=" + theme);
        Log.d(TAG, "cast=" + cast + " male=" + male + " female=" + female);

        apiService.createContent(request)
                .enqueue(new Callback<ContentResponse>() {
                    @Override
                    public void onResponse(Call<ContentResponse> call,
                                           Response<ContentResponse> response) {

                        Log.d(TAG, "HTTP CODE: " + response.code());
                        Log.d(TAG, "isSuccessful: " + response.isSuccessful());

                        if (response.body() != null) {
                            Log.d(TAG, "BODY VAR");
                            Log.d(TAG, "content NULL MU? "
                                    + (response.body().getContent() == null));
                        } else {
                            Log.d(TAG, "BODY NULL");
                        }

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getContent() != null) {

                            Toast.makeText(getContext(),
                                    "Eser başarıyla eklendi",
                                    Toast.LENGTH_SHORT).show();

                            NavHostFragment.findNavController(AddContentFragment.this)
                                    .popBackStack();

                        } else {
                            Toast.makeText(getContext(),
                                    "Eser eklenemedi",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContentResponse> call, Throwable t) {
                        Log.e(TAG, "FAILURE", t);
                        Toast.makeText(getContext(),
                                "Bağlantı hatası",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int parseInt(EditText et) {
        try {
            return Integer.parseInt(et.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
