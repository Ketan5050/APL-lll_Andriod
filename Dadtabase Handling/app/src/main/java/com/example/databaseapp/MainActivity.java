package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.*;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button insertBtn, viewBtn;
    TextView result;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        insertBtn = findViewById(R.id.insertBtn);
        viewBtn = findViewById(R.id.viewBtn);
        result = findViewById(R.id.result);

        db = new DBHelper(this);

        // ✅ INSERT BUTTON
        insertBtn.setOnClickListener(v -> {
            String n = name.getText().toString().trim();

            if(n.isEmpty()){
                name.setError(getString(R.string.error_enter_name));
                return;
            }

            boolean res = db.insertData(n);

            if(res) {
                Toast.makeText(this, getString(R.string.msg_inserted), Toast.LENGTH_SHORT).show();
                name.setText("");
                name.clearFocus();
                hideKeyboard();
            } else {
                Toast.makeText(this, getString(R.string.msg_failed), Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ VIEW BUTTON
        viewBtn.setOnClickListener(v -> {
            new Thread(() -> {
                Cursor c = db.getData();
                
                if (c == null || c.getCount() == 0) {
                    runOnUiThread(() -> result.setText(R.string.text_no_data));
                    if (c != null) c.close();
                    return;
                }

                StringBuilder data = new StringBuilder();
                while (c.moveToNext()) {
                    data.append("ID: ").append(c.getInt(0)).append("\n");
                    data.append("Name: ").append(c.getString(1)).append("\n\n");
                }
                c.close();

                runOnUiThread(() -> result.setText(data.toString()));
            }).start();
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
