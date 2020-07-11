package com.example.imageviewproj;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = MainActivity.class.getName() ;
    private ImageView image_view;
    private ProgressDialog downloadProgressDialog;

    public DownloadImageTask(Context context, ImageView image_view) {
        this.image_view = image_view;
        downloadProgressDialog = new ProgressDialog(context);
        downloadProgressDialog.setTitle("Downloading");
        downloadProgressDialog.setMessage("Downloading image, please wait");
        downloadProgressDialog.setIndeterminate(true);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        downloadProgressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadImageFromWebServer(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        image_view.setImageBitmap(bitmap);
        if(downloadProgressDialog.isShowing()){
            downloadProgressDialog.dismiss();
        }
    }

    private Bitmap downloadImageFromWebServer(String imageurl){

        try {
            // 1. URL Object
            URL imagewebURL = new URL(imageurl);
            // url could be grammatically wrong

            // 2. HTTPURLConnection Object
            HttpURLConnection connection = (HttpURLConnection)imagewebURL.openConnection();

            //Add Header
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // milliseconds
            connection.setReadTimeout(15000);      // milliseconds
            connection.setDoInput(true); // avoids multiple requests if user taps multiple times

            // connection
            connection.connect();

            // check response code
            int responsecode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responsecode){
                // collect response from connection into inputstreamobject
                InputStream inputStream = connection.getInputStream();

                if(null != inputStream){

                    // convert inputstream into bitmap : use bitmapfactory
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    return bitmap;
                }
            }

        }
        catch (MalformedURLException exception){
            Log.e(TAG, "Error: Invalid URL " + exception.getLocalizedMessage());
        }
        catch (IOException ex){
            Log.e(TAG, "Error: Error to open HTTPURLConnection " + ex.getLocalizedMessage());
        }
        return null;
    }

}
