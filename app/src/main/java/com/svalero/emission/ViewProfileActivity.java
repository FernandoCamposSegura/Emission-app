package com.svalero.emission;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.User;

public class ViewProfileActivity extends AppCompatActivity {

    Uri pathImg;
    String transPathImg;
    Button bt_camara;
    ImageView im_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // IMAGEN DE PERFIL
        bt_camara = findViewById(R.id.bt_camara);
        im_profile = findViewById(R.id.im_profile);

        bt_camara.setOnClickListener(new View.OnClickListener() {
            //Método que verifica versiones y permisos
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkExternalStoragePermissions();
                }
                camaraLauncher.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                if(transPathImg != null) {
                    updateImg(transPathImg);
                }
            }
        });

        //FIN DE IMAGEN DE PERFIL

        TextView tv_profileName = findViewById(R.id.tv_profileName);
        TextView tv_profileAdress = findViewById(R.id.tv_profileAdress);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();
        User user = db.userDao().getByUserName(MainActivity.currentUser);

        tv_profileName.setText(user.getName());
        tv_profileAdress.setText(user.getAdress());
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView tv_profileName = findViewById(R.id.tv_profileName);
        TextView tv_profileAdress = findViewById(R.id.tv_profileAdress);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();
        User user = db.userDao().getByUserName(MainActivity.currentUser);

        tv_profileName.setText(user.getName());
        tv_profileAdress.setText(user.getAdress());
    }

    public void modifyUser(View view) {
        Intent intent = new Intent(this, ModifyUserActivity.class);
        startActivity(intent);
    }

    public void updateImg(String path) {
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                .allowMainThreadQueries().build();
        db.userDao().updateImgUser(MainActivity.currentUser, path);
    }

    public void deleteUser(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_message)
                .setTitle(R.string.remove_task_message)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users")
                            .allowMainThreadQueries().build();
                    User user = db.userDao().getByUserName(MainActivity.currentUser);

                    db.userDao().delete(user);
                    MainActivity.currentUser = "";

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        pathImg = result.getData().getData();
                        im_profile.setImageURI(pathImg);
                        transPathImg = getRealPathFromURI(pathImg);
                    }
                }

            });

    //Comprobación de permisos
    private void checkExternalStoragePermissions() {
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        }
    }

    //Obtenemos el path real
    private String getRealPathFromURI(Uri path) {
        String res;
        Cursor cursor = getContentResolver().query(path, null, null, null, null);
        if(cursor == null) {
            res = path.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            res = cursor.getString(index);
            cursor.close();
        }

        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.register_vehicle) {
            Intent intent = new Intent(this, RegisterVehicleActivity.class);
            startActivity(intent);
            return  true;
        } else if(item.getItemId() == R.id.view_vehicles) {
            Intent intent = new Intent(this, ViewVehiclesActivity.class);
            startActivity(intent);
            return  true;
        }
        return false;
    }
}