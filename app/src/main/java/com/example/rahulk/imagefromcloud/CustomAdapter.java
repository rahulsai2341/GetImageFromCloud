package com.example.rahulk.imagefromcloud;


import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Bitmap> myList;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;
        public ViewHolder(ImageView v){
            super(v);
            mImage = (ImageView)v.findViewById(R.id.recycleritem);

        }

    }
    public CustomAdapter(ArrayList mList){
        myList=mList;
    }
@Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
    ImageView v = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewitem,parent,false);
    return new ViewHolder(v);
}
@Override
    public void onBindViewHolder(ViewHolder holder, int position){
    Bitmap currentData = (Bitmap) myList.get(position);
    holder.mImage.setImageBitmap(currentData);
}
    @Override
    public int getItemCount(){
        return myList.size();
    }

}

