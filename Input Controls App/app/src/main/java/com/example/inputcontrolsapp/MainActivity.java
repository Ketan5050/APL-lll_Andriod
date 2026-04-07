package com.example.inputcontrolsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    CheckBox checkBox;
    RadioGroup radioGroup;
    ToggleButton toggleButton;
    Spinner spinner;
    RatingBar ratingBar;
    ProgressBar progressBar;
    Button btnAlert, btnProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imageButton);
        checkBox = findViewById(R.id.checkBox);
        radioGroup = findViewById(R.id.radioGroup);
        toggleButton = findViewById(R.id.toggleButton);
        spinner = findViewById(R.id.spinner);
        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);
        btnAlert = findViewById(R.id.btnAlert);
        btnProgress = findViewById(R.id.btnProgress);

        // Image Button
        imageButton.setOnClickListener(v ->
                Toast.makeText(this, "Image Button Clicked", Toast.LENGTH_SHORT).show());

        // CheckBox
        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked())
                Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Unchecked", Toast.LENGTH_SHORT).show();
        });

        // RadioGroup
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
        });

        // Toggle Button
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
        });

        // Spinner
        String[] items = {"Java", "Android", "Python"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, items[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // RatingBar
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->
                Toast.makeText(this, "Rating: " + rating, Toast.LENGTH_SHORT).show());

        // Alert Dialog
        btnAlert.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialog, which) ->
                            Toast.makeText(this, "Yes Clicked", Toast.LENGTH_SHORT).show())
                    .setNegativeButton("No", null)
                    .show();
        });

        // Progress Bar
        btnProgress.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.postDelayed(() -> progressBar.setVisibility(View.GONE), 3000);
        });
    }
}