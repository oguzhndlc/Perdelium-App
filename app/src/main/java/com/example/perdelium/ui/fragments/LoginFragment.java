package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import java.io.IOException;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import androidx.fragment.app.Fragment;

import com.example.perdelium.R;
import com.example.perdelium.api.ApiClient;
import com.example.perdelium.api.ApiService;
import com.example.perdelium.model.LoginRequest;
import com.example.perdelium.model.LoginResponse;
import com.example.perdelium.utils.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText etIdentifier, etPassword;
    private Button btnLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etIdentifier = view.findViewById(R.id.etIdentifier); // Email veya Username
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String identifier = etIdentifier.getText().toString();  // Email veya Username
            String password = etPassword.getText().toString();

            // Input validation
            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            } else {
                login(identifier, password);
            }
        });

        return view;
    }

    private void login(String identifier, String password) {
        ApiService apiService = ApiClient.getApiService(getContext());
        LoginRequest loginRequest = new LoginRequest(identifier, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Başarılı yanıt, token'ı SharedPreferences'a kaydet
                    String token = response.body().getToken();
                    Log.d("Login Success", "Token: " + token);  // Token'ı logla

                    // SharedPreferences'a kullanıcı bilgilerini kaydet
                    SharedPreferencesUtil.saveUserInfo(getContext(), response.body());

                    // Başarılı giriş sonrası ana ekranı göster
                    showHomeScreen();
                } else {
                    // Hatalı giriş durumunda
                    try {
                        // Hata mesajını al
                        String errorResponse = response.errorBody().string();
                        Log.e("Login Error", errorResponse);  // Hata mesajını logla
                        Toast.makeText(getContext(), "Login Failed: " + errorResponse, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e("Login Error", "Error reading error body", e);
                        Toast.makeText(getContext(), "An error occurred while reading the error response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Ağ hatası veya diğer hatalar
                Log.e("Login Failure", "Error: " + t.getMessage(), t);  // Hata mesajını logla
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHomeScreen() {
        NavController navController =
                NavHostFragment.findNavController(LoginFragment.this);

        navController.navigate(R.id.homeFragment);
    }
}
