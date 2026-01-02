package com.example.perdelium.api;

import com.example.perdelium.model.*;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // USER
    @GET("users/username/{username}")
    Call<UserResponse> getUserByUsername(@Path("username") String username);

    // Login methodu: Artık LoginRequest modelini alacak şekilde değiştirilmiş
    @POST("users/signin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);  // @Field yerine @Body kullandık

    // REGISTER
    @POST("users/signup")
    Call<RegisterResponse> register(@Body RegisterRequest request);


    // CONTENT
    @GET("contents/all")
    Call<ContentResponse> getAllContents();

    @GET("contents/{id}")
    Call<ContentResponse> getContentById(@Path("id") String id);

    @GET("contents/my")
    Call<ContentResponse> getMyContents();

    @GET("contents/suggestions")
    Call<ContentResponse> getSuggestionContents();


    @POST("contents/create")
    Call<ContentResponse> createContent(@Body AddContentRequest request);

    @DELETE("contents/condel/{id}")
    Call<SimpleResponse> deleteContent(@Path("id") String id);

    // FAVORITE
    @GET("favorites")
    Call<FavoriteResponse> getFavorites();

    @GET("favorites/{contentId}/is-favorite")
    Call<IsFavoriteResponse> isFavorite(@Path("contentId") String contentId);

    @POST("favorites/{contentId}")
    Call<SimpleResponse> addFavorite(@Path("contentId") String contentId);

    @DELETE("favorites/{contentId}")
    Call<SimpleResponse> removeFavorite(@Path("contentId") String contentId);
}
