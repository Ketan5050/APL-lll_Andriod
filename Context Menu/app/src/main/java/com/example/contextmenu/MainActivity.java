package com.example.contextmenu;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // Register context menu
        registerForContextMenu(textView);
    }

    // Create context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.textView) {
            menu.setHeaderTitle("Choose Option");
            menu.add(0, 1, 0, "Edit");
            menu.add(0, 2, 1, "Delete");
            menu.add(0, 3, 2, "Share");
        }
    }

    // Handle item clicks
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "Edit Selected", Toast.LENGTH_SHORT).show();
                return true;

            case 2:
                Toast.makeText(this, "Delete Selected", Toast.LENGTH_SHORT).show();
                return true;

            case 3:
                Toast.makeText(this, "Share Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}