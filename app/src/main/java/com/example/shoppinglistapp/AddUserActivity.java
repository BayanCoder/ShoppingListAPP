package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String phoneNumber = phoneEditText.getText().toString();
            String userId = generateUniqueUserId(); // You need to implement this method

            if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                User user = new User(name, phoneNumber);
                UserManger userManager = new UserManger(this);
                userManager.saveUser(userId, user);
                Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();

                // Start MainActivity and finish AddUserActivity
                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generateUniqueUserId() {
        // Implement your logic to generate a unique user ID
        return String.valueOf(System.currentTimeMillis());
    }
}
