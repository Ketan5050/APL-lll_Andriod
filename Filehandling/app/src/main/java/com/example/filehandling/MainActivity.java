package com.example.filehandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;
    EditText ed1;
    TextView tv;

    String fileName = "mydata.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        ed1 = findViewById(R.id.editText);
        tv = findViewById(R.id.textview2);

        // SAVE DATA
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = ed1.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
                            fos.write(data.getBytes());
                            fos.close();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_SHORT).show();
                                    ed1.setText(""); // Clear input after saving
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // LOAD DATA
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileInputStream fis = openFileInput(fileName);
                            StringBuilder sb = new StringBuilder();
                            int c;

                            while ((c = fis.read()) != -1) {
                                sb.append((char) c);
                            }
                            fis.close();

                            final String result = sb.toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText(result);
                                    Toast.makeText(MainActivity.this, "File Loaded", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Error loading file", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
}