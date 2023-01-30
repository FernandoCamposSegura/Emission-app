package com.svalero.emission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.User;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
    }

    public void registerUser(View view) {
        EditText et_name = findViewById(R.id.et_registerName);
        EditText et_username = findViewById(R.id.et_registerUserName);
        EditText et_password = findViewById(R.id.et_registerPassword);
        EditText et_adress = findViewById(R.id.et_registerAdress);

        String name = et_name.getText().toString();
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        String adresss = et_adress.getText().toString();

        User user = new User(name, username, password, adresss);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();
        db.userDao().insert(user);

        Snackbar.make(et_name, R.string.add_user, BaseTransientBottomBar.LENGTH_LONG).show();
        et_name.setText("");
        et_username.setText("");
        et_password.setText("");
        et_adress.setText("");

    }

    public void goBack(View view) {
        onBackPressed();
    }
}