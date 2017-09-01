package com.example.santhosh.tileProject.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.santhosh.tileProject.R;
import com.example.santhosh.tileProject.model.PhotoResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<PhotoResponse> mPhotoList;
    private LayoutInflater mInflater;
    private Context mContext;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public PhotoAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public PhotoAdapter(List<PhotoResponse> mPhotoSet, RecyclerView recyclerView) {
        mPhotoList = mPhotoSet;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.photo_row, parent, false);
        final PhotoViewHolder viewHolder = new PhotoViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_PHOTO, mPhotoList.get(position));
                mContext.startActivity(intent);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                removeDialog(position);
                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        PhotoResponse photo = mPhotoList.get(position);
        Picasso.with(mContext)
                .load(photo.getUrlT())
                .placeholder(R.color.colorPrimary)
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (mPhotoList == null) ? 0 : mPhotoList.size();
    }

    public void setPhotoList(List<PhotoResponse> photoList) {
        this.mPhotoList = new ArrayList<>();
        this.mPhotoList.addAll(photoList);
        notifyDataSetChanged();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public void removeDialog(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(R.string.remove_dialog_title);
        alert.setMessage(R.string.remove_dialog_message);
        alert.setPositiveButton(R.string.remove_dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPhotoList.remove(position);
                List<PhotoResponse> responseList = new ArrayList<PhotoResponse>(mPhotoList);
                setPhotoList(responseList);
            }
        });
        alert.setNegativeButton(R.string.remove_dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
