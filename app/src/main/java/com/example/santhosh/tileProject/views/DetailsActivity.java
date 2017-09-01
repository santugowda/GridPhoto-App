package com.example.santhosh.tileProject.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santhosh.tileProject.R;
import com.example.santhosh.tileProject.model.PhotoResponse;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView titleTextView;
    public static final String EXTRA_PHOTO = "photo";

    private PhotoResponse mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting details screen layout
        setContentView(R.layout.activity_details_view);
        if (getIntent().hasExtra(EXTRA_PHOTO)) {
            mPhoto = getIntent().getParcelableExtra(EXTRA_PHOTO);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a photo parcelable");
        }
        //set image title
        titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(mPhoto.getTitle());
        //Set image url
        imageView = (ImageView) findViewById(R.id.detailed_image);
        Picasso.with(this)
                .load(mPhoto.getUrlL())
                .into(imageView);

    }
}
