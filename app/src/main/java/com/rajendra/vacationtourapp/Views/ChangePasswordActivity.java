package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.User;

public class ChangePasswordActivity extends AppCompatActivity {
    
    private EditText edtCurrent, edtNewPass, edtRe_type;
    private Button btnSaveChanges;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String email, passCheck = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        CreateActionbar();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail();
        
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

                
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current = edtCurrent.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reType = edtRe_type.getText().toString();

                CheckData(current, newPass, reType);

            }
        });
    }

    private void CheckData(String current, String newPass, String reType) {
        if(current.isEmpty() || newPass.isEmpty() || reType.isEmpty()){
            Toast.makeText(getApplicationContext(), "Fill all information", Toast.LENGTH_SHORT).show();
        }else {
            databaseReference.child(getIdUser(email)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    String passCheckData;
                    Log.d("PASSINDATABASE: ", String.valueOf(task.getResult().getValue()));
                    if (task.isSuccessful()){
                        DataSnapshot dataSnapshot = task.getResult();
                        User user = dataSnapshot.getValue(User.class);
                        passCheckData = user.getPassword();
                        if (current.equals(passCheckData)){
                            if(newPass.equals(reType)){
                                SaveChangesPass(newPass);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }


    private void SaveChangesPass(String newPass) {
        firebaseUser.updatePassword(newPass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("PASSWORDS", "User password updated.");
                            Notification();
                        }
                    }
                });
        databaseReference.child(getIdUser(email)).child("password").setValue(newPass);
    }
    
    private void CreateActionbar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Change password");
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_close_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(drawable);
        
        edtCurrent = findViewById(R.id.edtCurrentPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtRe_type = findViewById(R.id.edtRe_typePass);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Notification(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
        builder.setTitle("Notification!");
        builder.setMessage("The password was changed");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private String getIdUser(String getEmail){
        String key = "";
        for (int i = 0; i < getEmail.length(); i++){
            if (getEmail.charAt(i) != '@'){
                key += getEmail.charAt(i);
            }
            else
                break;
        }
        return key.trim();
    }
}