package com.example.office_n205.galleryandcameramodule.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.office_n205.galleryandcameramodule.ImageExtended;
import com.example.office_n205.galleryandcameramodule.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by OFFICE-N205 on 2018-04-24.
 */

public class GalleryGridViewLayoutAdapter extends RecyclerView.Adapter<GalleryGridViewLayoutAdapter.ViewHolder>{
    Context context;
    private List<ImageExtended> mFiles;
    private File latestSelectedFile;

    public void setItems(List<ImageExtended> items) {
        if (items != null) {
            this.mFiles = items;
            notifyDataSetChanged();
        }
    }

    public GalleryGridViewLayoutAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GalleryGridViewLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_imageview_layout_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){

        if(mFiles.get(position).getSelected()) holder.mImageView.setAlpha(0.4f);
        else holder.mImageView.setAlpha(1.0f);

        Picasso.with(context)
                .load(mFiles.get(position).file)
                .config(Bitmap.Config.RGB_565)
                .resize(350, 350)
                .centerCrop()
                .noFade().into(holder.mImageView);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(0.4f == holder.mImageView.getAlpha()) {
                    holder.mImageView.setAlpha(1.0f);
                }else{
                    holder.mImageView.setAlpha(0.4f);
                    latestSelectedFile = mFiles.get(position).file;
                }
                if(mFiles.get(position).getSelected()){
                    mFiles.get(position).setSelected(false);
                }else{
                    mFiles.get(position).setSelected(true);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(null != mFiles) return mFiles.size();
        else return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.mImageView);
        }
    }

    public File getLatestSelectedFile(){
        return latestSelectedFile;
    }

}
