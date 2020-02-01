package com.zachrawi.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText etShopping;
    int position = 0;
    boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etShopping = findViewById(R.id.etShopping);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        position = intent.getIntExtra("position", 0);
        done = intent.getBooleanExtra("done", false);

        etShopping.setText(description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {
            if (etShopping.getText().length() == 0) {
                Toast.makeText(this, "Daftar belanja mesti diisi", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("shopping", etShopping.getText().toString());
                intent.putExtra("position", position);
                intent.putExtra("done", done);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
