package com.example.imageviewproj;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.TextView;

public class DownloadContentClass extends AsyncTask<String, Void, String> {

    private TextView textView;
    private ProgressDialog downloadProgressDialogue;

    DownloadContentClass(Context context, TextView textView){
        this.textView = textView;
        downloadProgressDialogue = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        downloadProgressDialogue.show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadImageFromWebServer(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        image_view.setImageBitmap(bitmap);
        if(downloadProgressDialogue.isShowing()){
            downloadProgressDialogue.dismiss();
        }

}
