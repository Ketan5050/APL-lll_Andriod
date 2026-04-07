package com.example.layouts;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    TextView resultText;
    Button submitBtn;
    CheckBox checkBox;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        resultText = findViewById(R.id.resultText);
        submitBtn = findViewById(R.id.submitBtn);
        checkBox = findViewById(R.id.checkBox);
        radioGroup = findViewById(R.id.radioGroup);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameInput.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);

                String gender = (radioButton != null) ? radioButton.getText().toString() : "Not Selected";

                String checkStatus = checkBox.isChecked() ? "Subscribed" : "Not Subscribed";

                resultText.setText("Name: " + name +
                        "\nGender: " + gender +
                        "\nStatus: " + checkStatus);
            }
        });
    }
}