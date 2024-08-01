package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemEditText = findViewById(R.id.itemEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String itemName = itemEditText.getText().toString().trim();
            if (!itemName.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ITEM_NAME", itemName);
                setResult(RESULT_OK, resultIntent);
                finish();  // סיום הפעילות וחזרה ל-`MainActivity`
            } else {
                // הצג הודעה למשתמש אם השם ריק
                Toast.makeText(AddItemActivity.this, "Item name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
