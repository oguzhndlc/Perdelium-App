package com.example.perdelium.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.perdelium.model.LoginResponse;
import com.example.perdelium.model.User;
import com.google.gson.Gson;

public class SharedPreferencesUtil {

    private static final String PREF_NAME = "user_preferences";

    // Kullanıcı bilgilerini SharedPreferences'a kaydetme
    public static void saveUserInfo(Context context, LoginResponse loginResponse) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Token'ı kaydet
        editor.putString("auth_token", loginResponse.getToken());

        // User bilgilerini kaydet
        if (loginResponse.getUser() != null) {
            // User nesnesini JSON formatında kaydediyoruz
            Gson gson = new Gson();
            String userJson = gson.toJson(loginResponse.getUser());  // User'ı JSON string'e çeviriyoruz
            editor.putString("user", userJson);  // JSON formatında kaydediyoruz
        }

        editor.apply();
    }

    // SharedPreferences'tan kullanıcı bilgilerini okuma
    public static User getUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // User'ı JSON formatında al
        String userJson = prefs.getString("user", "");

        // Eğer User verisi varsa, JSON'dan User nesnesi oluştur
        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(userJson, User.class);  // JSON'dan User objesi oluştur
        }

        return null;  // User bilgisi yoksa null döndür
    }

    // Kullanıcı token'ını almak
    public static String getAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString("auth_token", "");  // Varsayılan olarak boş bir string döndürür
    }

    public static boolean isLoggedIn(Context context){
        String token = getAuthToken(context);
        return !token.isEmpty();
    }

    // Kullanıcı bilgilerini temizleme (log out)
    public static void clearUserInfo(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
