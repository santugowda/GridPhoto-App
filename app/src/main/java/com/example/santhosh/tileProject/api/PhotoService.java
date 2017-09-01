package com.example.santhosh.tileProject.api;

import com.example.santhosh.tileProject.model.PhotoApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {

    String PHOTO_SERVICE_PATH =
            "rest/?method=flickr.photos.search&api_key=949e98778755d1982f537d56236bbb42&" +
                    "tags=tile&format=json&nojsoncallback=1";

    String PHOTO_SERVICE_EXTRAS= "url_t,url_l";

    @GET(PHOTO_SERVICE_PATH)
    Call<PhotoApiResponse> getPhotosQuery(
            @Query("page") int page,
            @Query("extras") String extras
    );
}
