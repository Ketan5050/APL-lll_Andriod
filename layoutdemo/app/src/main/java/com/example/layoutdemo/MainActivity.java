package com.example.layoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLinear, btnRelative, btnConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLinear = findViewById(R.id.btnLinear);
        btnRelative = findViewById(R.id.btnRelative);
        btnConstraint = findViewById(R.id.btnConstraint);

        btnLinear.setOnClickListener(v ->
                startActivity(new Intent(this, LinearActivity.class)));

        btnRelative.setOnClickListener(v ->
                startActivity(new Intent(this, RelativeActivity.class)));

        btnConstraint.setOnClickListener(v ->
                startActivity(new Intent(this, ConstraintActivity.class)));
    }
}