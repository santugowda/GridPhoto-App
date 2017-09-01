package com.example.santhosh.tileProject;

import com.example.santhosh.tileProject.model.PhotoApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PhotosApiService {
    @GET
    Call<PhotoApiResponse> getPhotosDetails(@Url String url);
}