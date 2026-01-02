package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.perdelium.model.User;
import com.example.perdelium.R;
import com.example.perdelium.utils.SharedPreferencesUtil;

public class ProfileFragment extends Fragment {

    // Sınıf seviyesinde TextView tanımladık
    private TextView TextUserName, TextEmail, TextName, TextSurName;
    private NavController navController;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // TextView'ları bağlama
        TextUserName = view.findViewById(R.id.username);
        TextEmail = view.findViewById(R.id.email);
        TextName = view.findViewById(R.id.name);
        TextSurName = view.findViewById(R.id.surname);

        Button btnEdit = view.findViewById(R.id.editBtn);
        Button btnFav = view.findViewById(R.id.favoriteBtn);
        Button btnLogin = view.findViewById(R.id.LoginBtn);
        Button btnRegister = view.findViewById(R.id.RegisterBtn);
        Button btnFeedBack = view.findViewById(R.id.FeedBackBtn);
        Button btnMyContents = view.findViewById(R.id.MyContentsBtn);
        Button btnUProf = view.findViewById(R.id.UserProfileBtn);

        navController =
                NavHostFragment.findNavController(ProfileFragment.this);


        if (!SharedPreferencesUtil.isLoggedIn(requireContext())) {
            navController.navigate(R.id.loginFragment);
            return view;
        }

        showProfileInfo(view);

        btnEdit.setOnClickListener(v -> navController.navigate(R.id.editProfileFragment));
        btnFav.setOnClickListener(v -> navController.navigate(R.id.favoritesFragment));
        btnLogin.setOnClickListener(v -> navController.navigate(R.id.loginFragment));
        btnRegister.setOnClickListener(v -> navController.navigate(R.id.registerFragment));
        btnFeedBack.setOnClickListener(v -> navController.navigate(R.id.feedBackFragment));
        btnMyContents.setOnClickListener(v -> navController.navigate(R.id.myContentsFragment));
        btnUProf.setOnClickListener(v -> navController.navigate(R.id.userProfileFragment));

        return view;
    }

    private void showProfileInfo(View view) {
        // Kullanıcı bilgilerini SharedPreferences'tan al
        User user = SharedPreferencesUtil.getUserInfo(requireContext());

        if (user == null || user.getUsername() == null) {
            navController.navigate(R.id.loginFragment);
            return;
        }else {
            // Kullanıcı bilgilerini göster
            String username = user.getUsername();
            String name = user.getName();
            String surname = user.getSurname();
            String email = user.getEmail();

            // TextView'ları güncelle
            TextUserName.setText(username);
            TextName.setText(name);
            TextEmail.setText(email);
            TextSurName.setText(surname);
        }
    }


}
