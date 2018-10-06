package com.example.rahulk.imagefromcloud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListViewFragmnet extends Fragment {




    private List<Bitmap> mData = new ArrayList();
    private CustomAdapter mAdapter;
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;


    public ListViewFragmnet(){

    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View v = layoutInflater.inflate(R.layout.fragment_view,container,false);
       mRecyclerView = (RecyclerView)v.findViewById(R.id.fragmentView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("https://lh3.googleusercontent.com/-Y_J3VCQOgrI/AAAAAAAAAAI/AAAAAAAAAAA/IgpyZta5ccw/photo.jpg");
        } else {
            Toast mToast = Toast.makeText(getActivity(),"Not connected to network", Toast.LENGTH_SHORT);
            mToast.show();
        }

        return v;
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            /*ImageView imageView = (ImageView) findViewById(R.id.listitem);
            imageView.setImageBitmap(result);*/
            getdata(result);
            bindfragmentwithadapter();
        }
    }

    private Bitmap downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.v("imageFromCloud","error in getting image from specified URL");
            is = conn.getInputStream();

            // Convert the InputStream into a string

            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    public void getdata(Bitmap result){

        mData.add(result);
    }

    public void bindfragmentwithadapter(){
        mAdapter = new CustomAdapter((ArrayList) mData);
        mRecyclerView.setAdapter(mAdapter);
    }





}
