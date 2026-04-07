package com.example.datepickerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DatePicker picker;
    Button btnDate;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picker = findViewById(R.id.datePicker);
        btnDate = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        // Show current date
        textView.setText("Current Date: " + getDate());

        // Button Click Event
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Selected Date: " + getDate());
            }
        });
    }

    // Method to get date
    public String getDate() {
        StringBuilder builder = new StringBuilder();
        builder.append((picker.getMonth() + 1) + "/"); // month is 0 based
        builder.append(picker.getDayOfMonth() + "/");
        builder.append(picker.getYear());
        return builder.toString();
    }
}