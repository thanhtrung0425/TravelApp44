package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Authentication;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajendra.vacationtourapp.Login.LoginScreen;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtEmail, edtPassword, edtConfirmPass, edtContry, edtPhone;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private  static final String USER = "user";
    private User users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        edtUsername = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtCrPass);
        edtConfirmPass = findViewById(R.id.edtComfirmPass);
        edtContry = findViewById(R.id.edtCountry);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        btnRegister = findViewById(R.id.btnCrRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername = edtUsername.getText().toString();
                String mEmail = edtEmail.getText().toString();
                String mPassword = edtPassword.getText().toString();
                String mComfirmpass = edtConfirmPass.getText().toString();
                String mCountry = edtContry.getText().toString();
                String mPhone = edtPhone.getText().toString();


                if (!mUsername.isEmpty() && !mEmail.isEmpty() && !mPassword.isEmpty() && !mComfirmpass.isEmpty()
                    && !mCountry.isEmpty() && !mPhone.isEmpty()){
                    if(mComfirmpass.equals(mPassword)){
                        users = new User(mUsername, mEmail, mPassword, mCountry, mPhone, "");
                        firebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        FirebaseUser users = firebaseAuth.getCurrentUser();
                                        updateUI(users, getIdUser(mEmail));
                                        Toast.makeText(RegisterActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Passwords are not the same!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Fill in all the information!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateUI(FirebaseUser currentUser, String getEmail) {
        mDatabase.child("" + getEmail).setValue(users); //adding user info to database
        Log.e("DATA", currentUser.getUid());
        Intent loginIntent = new Intent(this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
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