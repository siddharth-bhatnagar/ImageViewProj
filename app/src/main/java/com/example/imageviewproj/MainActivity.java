package com.example.imageviewproj;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button downloadbtn;
    private EditText imgURL;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadbtn = (Button)findViewById(R.id.download_btn);
        imgURL = (EditText)findViewById(R.id.image_url_edittext);
        imageView = (ImageView)findViewById(R.id.image_view);

        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // download button tap event
                int id = v.getId();

                if(R.id.download_btn == id){

                    // get value from editText
                    String imageURL = imgURL.getText().toString();

                    if(!imageURL.isEmpty()){
                        // download image operation
                        new DownloadImageTask(MainActivity.this, imageView).execute(imageURL); // this is anonymous object of DownloadImageTask class
                        // If we had created a named object, due to the limitation, the object would have become useless for 2nd use onwards
                    }
                    else{
                        // show some errors
                        Toast.makeText(MainActivity.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }


}