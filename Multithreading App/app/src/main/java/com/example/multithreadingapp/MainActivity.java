package com.example.multithreadingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.button);
        img = findViewById(R.id.imageView);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create new Thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            // Simulate delay (5 seconds)
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Update UI using post()
                        img.post(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MainActivity.this).load(R.drawable.java).into(img);
                            }
                        });
                    }
                }).start();
            }
        });
    }
}