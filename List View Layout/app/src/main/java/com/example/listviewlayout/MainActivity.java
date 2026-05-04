package com.example.listviewlayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String[] students = {
            "Ketan", "Rahul", "Priya", "Amit", "Sneha",
            "Rohit", "Pooja", "Neha", "Vikas", "Anjali"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                students
        );

        listView.setAdapter(adapter);

        // Click event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = students[position];
                Toast.makeText(MainActivity.this,
                        "Selected: " + selected,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}