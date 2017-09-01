package com.example.santhosh.tileProject.api;

import com.example.santhosh.tileProject.model.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by santhosh on 4/13/17.
 */

public interface TaskService {

    @POST("photo")
    Call<PhotoResponse> createTask(@Body PhotoResponse photoResponse);

}
