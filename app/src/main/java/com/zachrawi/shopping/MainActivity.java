package com.zachrawi.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Shopping> mShoppings;
    private ShoppingAdapter mShoppingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // referensi ke recycler view
        mRecyclerView = findViewById(R.id.recyclerView);

        // inisialisasi data
        mShoppings = new ArrayList<>();
        mShoppings.add(new Shopping(false, "Beli makan siang"));
        mShoppings.add(new Shopping(true, "Beli makan pagi"));

        // inisialisasi adapter
        mShoppingAdapter = new ShoppingAdapter(this, R.layout.item_shopping, mShoppings, new ShoppingAdapter.OnClickListener() {
            @Override
            public void onCheck(int position, boolean checked) {
                // variable sementara
                Shopping belanja = mShoppings.get(position);
                belanja.setDone(checked);

                // ubah data utama
                mShoppings.set(position, belanja);

                // notify adapter
                mShoppingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRemove(int position) {
                mShoppings.remove(position);

                // notify adapter
                mShoppingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEdit(int position) {
                Shopping shopping = mShoppings.get(position);

                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra("description", shopping.getDescription());
                intent.putExtra("position", position);
                intent.putExtra("done", shopping.isDone());
                startActivityForResult(intent, 2);
            }
        });

        // layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(mShoppingAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // ambil data intent
                String description = data.getStringExtra("shopping");

                // tambahkan ke shopping list
                mShoppings.add(new Shopping(false, description));

                // notify adapter
                mShoppingAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // ambil data intent
                String description = data.getStringExtra("shopping");
                int position = data.getIntExtra("position", 0);
                boolean done = data.getBooleanExtra("done",false);

                // update data pada posisi tertentu
                mShoppings.set(position, new Shopping(done, description));

                // notify adapter
                mShoppingAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
