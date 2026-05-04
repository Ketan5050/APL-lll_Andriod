package com.example.databasewithfirebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRollNo;
    private EditText editTextName;
    private Button buttonSave;
    private TextView textViewDisplay;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRollNo = findViewById(R.id.editTextRollNo);
        editTextName = findViewById(R.id.editTextName);
        buttonSave = findViewById(R.id.buttonSave);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance("https://database-with-firebase-ba993-default-rtdb.firebaseio.com/").getReference("students");

        buttonSave.setOnClickListener(v -> {
            Log.d("FirebaseDebug", "Save button clicked");
            String rollNo = editTextRollNo.getText().toString().trim();
            String name = editTextName.getText().toString().trim();

            if (!rollNo.isEmpty() && !name.isEmpty()) {
                // Create student object
                Student student = new Student(rollNo, name);

                Log.d("FirebaseDebug", "Attempting to save student: " + rollNo);
                // Write student to Firebase using rollNo as key
                databaseReference.child(rollNo).setValue(student)
                        .addOnSuccessListener(aVoid -> {
                            Log.d("FirebaseDebug", "Data saved successfully");
                            Toast.makeText(MainActivity.this, "Student saved!", Toast.LENGTH_SHORT).show();
                            editTextRollNo.setText("");
                            editTextName.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Log.e("FirebaseDebug", "Failed to save data", e);
                            Toast.makeText(MainActivity.this, "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(MainActivity.this, "Please enter both Roll No and Name", Toast.LENGTH_SHORT).show();
            }
        });

        // Read data from Firebase in real-time
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseDebug", "Data changed. Exists: " + snapshot.exists());
                if (snapshot.exists()) {
                    StringBuilder builder = new StringBuilder();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Student student = child.getValue(Student.class);
                        if (student != null) {
                            builder.append("Roll No: ").append(student.getRollNo())
                                   .append(", Name: ").append(student.getName())
                                   .append("\n");
                        }
                    }
                    textViewDisplay.setText(builder.toString());
                } else {
                    textViewDisplay.setText("No students found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDebug", "Database error: " + error.getMessage(), error.toException());
                Toast.makeText(MainActivity.this, "Failed to read: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}