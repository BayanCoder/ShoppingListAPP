package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {

    private ListView userListView;
    private ArrayAdapter<String> adapter;
    private UserManger userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userListView = findViewById(R.id.userListView);
        Button addUserButton = findViewById(R.id.addUserButton); // הוספת כפתור הוספת משתמשים
        userManager = new UserManger(this);

        // שמור משתמשים לצורך בדיקה
        userManager.saveUser("user1", new User("John Doe", "123-456-7890"));
        userManager.saveUser("user2", new User("Jane Smith", "987-654-3210"));

        // קבלת המשתמשים והצגתם
        updateUserList();

        // הגדרת פעולה לכפתור הוספת משתמשים
        addUserButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, AddUserActivity.class);
            startActivityForResult(intent, 1); // Use startActivityForResult to get the result
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // עדכן את רשימת המשתמשים לאחר הוספת משתמש חדש
            updateUserList();
        }
    }

    private void updateUserList() {
        Map<String, User> usersMap = userManager.getUsersMap();
        List<String> userNames = new ArrayList<>();

        for (User user : usersMap.values()) {
            userNames.add(user.getName() + " (" + user.getPhoneNumber() + ")");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNames);
        userListView.setAdapter(adapter);
    }
}
