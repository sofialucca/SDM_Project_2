package com.cs478.sofialucca.project_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageViewActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Get the Intent used to start this Activity
        Intent intent = getIntent();

        // Make a new ImageView
        ImageView imageView = new ImageView(getApplicationContext());

        // Get the ID of the image to display from the intent
        //   and set it as the image for this ImageView

        imageView.setImageResource(intent.getIntExtra("RESOURCE", 0));

        // Define the content of this programmatically
        setContentView(imageView);
    }
}
