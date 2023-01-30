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

public class ModifyUserActivity extends AppCompatActivity {

    User user;

    EditText et_modifyUserName;
    EditText et_modifyUserUsername;
    EditText et_modifyUserPassword;
    EditText et_modifyUserAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();
        user = db.userDao().getByUserName(MainActivity.currentUser);

        et_modifyUserName = findViewById(R.id.et_modifyUserName);
        et_modifyUserUsername = findViewById(R.id.et_modifyUserUsername);
        et_modifyUserPassword = findViewById(R.id.et_modifyUserPassword);
        et_modifyUserAdress = findViewById(R.id.et_modifyUserAdress);

        et_modifyUserName.setText(user.getName());
        et_modifyUserUsername.setText(user.getUsername());
        et_modifyUserPassword.setText(user.getPassword());
        et_modifyUserAdress.setText(user.getAdress());
    }

    public void modifyUser(View view) {

        String name = et_modifyUserName.getText().toString();
        String username = et_modifyUserUsername.getText().toString();
        String password = et_modifyUserPassword.getText().toString();
        String adress = et_modifyUserAdress.getText().toString();


        User newUser = new User(name, username, password, adress);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();
        db.userDao().update(newUser);

        MainActivity.currentUser = newUser.getUsername();

        Snackbar.make(et_modifyUserAdress, R.string.update_user, BaseTransientBottomBar.LENGTH_LONG).show();
        et_modifyUserName.setText("");
        et_modifyUserUsername.setText("");
        et_modifyUserPassword.setText("");
        et_modifyUserAdress.setText("");

    }

    public void goBack(View view) {
        onBackPressed();
    }
}