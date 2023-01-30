package com.svalero.emission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.User;
import com.svalero.emission.domain.Vehicle;

public class MainActivity extends AppCompatActivity {

    public static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        currentUser = "";
    }

    public void login(View view) {
        EditText et_mainUserName = findViewById(R.id.et_mainUserName);
        EditText et_mainPassword = findViewById(R.id.et_mainPassword);

        String username = et_mainUserName.getText().toString();
        String password = et_mainPassword.getText().toString();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();

        User user = db.userDao().login(username, password);


        if(user != null) {
            currentUser = user.getUsername();
            Intent intent = new Intent(this, ViewChargingPointsActivity.class);
            startActivity(intent);
        }
        else {
            Snackbar.make(et_mainUserName, R.string.task_registered_error, BaseTransientBottomBar.LENGTH_LONG).show();
        }

    }

    public void registerUser(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }
}