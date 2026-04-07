package com.example.menuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle Menu Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item1) {
            Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.item2) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.item3) {
            Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}