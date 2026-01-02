package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.perdelium.R;
import com.example.perdelium.api.*;
import com.example.perdelium.model.*;

import retrofit2.*;

public class RegisterFragment extends Fragment {

    EditText etUsername, etEmail, etName, etSurname, etPassword, etConfirmPassword;
    Button btnRegister,btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            NavHostFragment.findNavController(RegisterFragment.this)
                    .navigate(R.id.loginFragment);
        });

        btnRegister.setOnClickListener(v -> register());

        return view;
    }

    private void register() {

        if (etUsername.getText().toString().isEmpty()){
            Toast.makeText(getContext(),
                    "Lütfen kullanıcı adınızı giriniz",
                    Toast.LENGTH_SHORT).show();

        }

        if (etEmail.getText().toString().isEmpty()){
            Toast.makeText(getContext(),
                    "Lütfen email adresinizi giriniz",
                    Toast.LENGTH_SHORT).show();

        }

        if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
            Toast.makeText(getContext(),
                    "Şifreler uyuşmuyor",
                    Toast.LENGTH_SHORT).show();
        }

        RegisterRequest request = new RegisterRequest(
                etUsername.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etName.getText().toString(),
                etSurname.getText().toString()
        );

        ApiService apiService = ApiClient.getApiService(requireContext());

        apiService.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(),
                            "Kayıt başarılı, giriş yapabilirsiniz",
                            Toast.LENGTH_SHORT).show();

                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.loginFragment);
                } else {
                    Toast.makeText(getContext(),
                            "Kayıt başarısız",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Hata: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
