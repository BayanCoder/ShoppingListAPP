package com.example.shoppinglistapp;

import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private Button addButton;
    private Button deleteButton;
    private List<String> items;
    private ArrayAdapter<String> adapter;
    private static final String PREFS_NAME = "ShoppingListPrefs";
    private static final String KEY_ITEMS = "items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button viewUsersButton = findViewById(R.id.viewUsersButton);
        viewUsersButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        listView = findViewById(R.id.ListView1);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        listView.setAdapter(adapter);

        loadItems();

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivityForResult(intent, 1);
        });

        deleteButton.setOnClickListener(v -> {
            deleteSelectedItems();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String newItem = data.getStringExtra("ITEM_NAME");
            if (newItem != null && !newItem.isEmpty()) {
                boolean itemExists = false;
                for (int i = 0; i < items.size(); i++) {
                    String item = items.get(i);
                    if (item.startsWith(newItem)) {
                        int count = Integer.parseInt(item.replaceAll(".*\\((\\d+)\\).*", "$1")) + 1;
                        items.set(i, newItem + " (" + count + ")");
                        itemExists = true;
                        break;
                    }
                }
                if (!itemExists) {
                    items.add(newItem + " (1)");
                }
                adapter.notifyDataSetChanged();
                saveItems();
            }
        }
    }


    private void saveItems() {
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> itemsSet = new HashSet<>(items);
        editor.putStringSet(KEY_ITEMS, itemsSet);
        editor.apply();
    }

    private void loadItems() {
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> itemsSet = sharedPref.getStringSet(KEY_ITEMS, new HashSet<>());
        items.clear();
        items.addAll(itemsSet);
        adapter.notifyDataSetChanged();
    }

    private void deleteSelectedItems() {
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        List<String> itemsToRemove = new ArrayList<>();

        for (int i = 0; i < checkedItems.size(); i++) {
            if (checkedItems.valueAt(i)) {
                String item = adapter.getItem(checkedItems.keyAt(i));
                if (item != null) {
                    itemsToRemove.add(item);
                }
            }
        }

        if (itemsToRemove.isEmpty()) {
            Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show();
            return;
        }

        items.removeAll(itemsToRemove);
        adapter.notifyDataSetChanged();
        saveItems();
        Toast.makeText(this, "Items deleted", Toast.LENGTH_SHORT).show();
    }


}