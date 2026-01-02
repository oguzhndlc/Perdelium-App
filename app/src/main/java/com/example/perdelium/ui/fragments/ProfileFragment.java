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

import com.example.perdelium.model.User;
import com.example.perdelium.ui.MainActivity;
import com.example.perdelium.R;
import com.example.perdelium.utils.SharedPreferencesUtil;

public class ProfileFragment extends Fragment {

    // Sınıf seviyesinde TextView tanımladık
    private TextView TextUserName, TextEmail, TextName, TextSurName;

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

        // Kullanıcının giriş yapıp yapmadığını kontrol et
        if (!SharedPreferencesUtil.isLoggedIn(requireContext())) {
            // Eğer giriş yapılmamışsa, LoginFragment'a yönlendirelim
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment()) // LoginFragment'a yönlendir
                    .addToBackStack(null)
                    .commit();
        } else {
            // Giriş yapılmış, profil bilgilerini göster
            showProfileInfo(view);
        }

        Button btnEdit = view.findViewById(R.id.editBtn);
        Button btnFav = view.findViewById(R.id.favoriteBtn);
        Button btnLogin = view.findViewById(R.id.LoginBtn);
        Button btnRegister = view.findViewById(R.id.RegisterBtn);
        Button btnFeedBack = view.findViewById(R.id.FeedBackBtn);
        Button btnMyContents = view.findViewById(R.id.MyContentsBtn);
        Button btnUProf = view.findViewById(R.id.UserProfileBtn);

        btnEdit.setOnClickListener(v -> GoToFrag(new EditProfileFragment()));
        btnFav.setOnClickListener(v -> GoToFrag(new FavoritesFragment()));
        btnLogin.setOnClickListener(v -> GoToFrag(new LoginFragment()));
        btnRegister.setOnClickListener(v -> GoToFrag(new RegisterFragment()));
        btnFeedBack.setOnClickListener(v -> GoToFrag(new FeedBackFragment()));
        btnMyContents.setOnClickListener(v -> GoToFrag(new MyContentsFragment()));
        btnUProf.setOnClickListener(v -> GoToFrag(new UserProfileFragment()));

        return view;
    }

    private void showProfileInfo(View view) {
        // Kullanıcı bilgilerini SharedPreferences'tan al
        User user = SharedPreferencesUtil.getUserInfo(requireContext());

        if (user == null || user.getUsername() == null) {
            // Eğer kullanıcı bilgileri eksik veya null ise, LoginFragment'a yönlendirelim
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment()) // LoginFragment'a yönlendir
                    .addToBackStack(null)
                    .commit();
        } else {
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

    private void GoToFrag(Fragment fragment) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).loadFragment(fragment);
        }
    }
}
