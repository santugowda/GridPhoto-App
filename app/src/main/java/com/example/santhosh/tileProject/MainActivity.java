package com.example.santhosh.tileProject;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.santhosh.tileProject.api.PhotoApi;
import com.example.santhosh.tileProject.api.PhotoService;
import com.example.santhosh.tileProject.model.PhotoApiResponse;
import com.example.santhosh.tileProject.model.PhotoResponse;
import com.example.santhosh.tileProject.views.PhotoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.santhosh.tileProject.R.id.recyclerView;
import static com.example.santhosh.tileProject.api.PhotoService.PHOTO_SERVICE_EXTRAS;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private PhotoAdapter mPhotoAdapter;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoading = true;
    private int currentPage = 1;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private PhotoService photoService;
    private List<PhotoResponse> photoSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) findViewById(recyclerView);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPhotoAdapter = new PhotoAdapter(this);
        mRecyclerView.setAdapter(mPhotoAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy >0){
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            isLoading = false;
                            Log.i("...", "last i guess");
                            currentPage++;
//                            loadNextPage(currentPage);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadNextPage(currentPage);
                }
            }
        });

        photoService = PhotoApi.getClient().create(PhotoService.class);
        getGridImages(1);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void refreshItems() {
        getGridImages(1);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private Call<PhotoApiResponse> photoApiResponseCall(int page) {
        return photoService.getPhotosQuery(
                page, PHOTO_SERVICE_EXTRAS
        );
    }

    private void getGridImages(int firstPage) {
        photoApiResponseCall(firstPage).enqueue(new Callback<PhotoApiResponse>() {
            @Override
            public void onResponse(Call<PhotoApiResponse> call, Response<PhotoApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                    mPhotoAdapter.setPhotoList(response.body().getPhotos().getPhoto());
                    isLoading = true;
                }
            }
            @Override
            public void onFailure(Call<PhotoApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadNextPage(final int pageNumber) {
        Log.d(TAG, "loadNextPage: " + pageNumber);

        photoApiResponseCall(pageNumber).enqueue(new Callback<PhotoApiResponse>() {
            @Override
            public void onResponse(Call<PhotoApiResponse> call, Response<PhotoApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                    mPhotoAdapter.setPhotoList(response.body().getPhotos().getPhoto());
                    isLoading = true;
                }
            }

            @Override
            public void onFailure(Call<PhotoApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }
}