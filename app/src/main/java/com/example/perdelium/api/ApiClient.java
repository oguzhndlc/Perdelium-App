package com.example.perdelium.api;

import android.content.Context;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ApiClient {

    private static final String BASE_URL = "https://perdelium-render.onrender.com/api/";
    private static ApiService apiService;

    public static ApiService getApiService(Context context) {
        // Token'ı SharedPreferences'ten alıyoruz (ya da başka bir güvenli depolama yöntemiyle)
        String token = getTokenFromSharedPrefs(context);

        // Interceptor ile Authorization Header ekliyoruz
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        // Eğer token varsa header'a ekle
                        if (token != null && !token.isEmpty()) {
                            Request modifiedRequest = originalRequest.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token) // Token'ı Authorization header'ına ekle
                                    .build();
                            return chain.proceed(modifiedRequest);
                        }

                        // Token yoksa, orijinal isteği gönder
                        return chain.proceed(originalRequest);
                    }
                })
                .build();

        if (apiService == null) {
            apiService = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // OkHttpClient'ı kullan
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }

        return apiService;
    }

    private static String getTokenFromSharedPrefs(Context context) {
        // Burada SharedPreferences'ten token'ı alıyoruz (ya da başka bir depolama yöntemi)
        // Örnek: SharedPreferences kullanımı
        android.content.SharedPreferences prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        return prefs.getString("auth_token", null); // auth_token, giriş yaptıktan sonra kaydedilecek token anahtarı
    }
}
