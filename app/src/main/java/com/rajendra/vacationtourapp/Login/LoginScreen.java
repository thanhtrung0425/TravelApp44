package com.rajendra.vacationtourapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Views.ProfileActivity;
import com.rajendra.vacationtourapp.Views.RegisterActivity;

public class LoginScreen extends AppCompatActivity {

    private EditText edtAccount, edtPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = edtAccount.getText().toString();
                String getPassword = edtPassword.getText().toString();
                if (getEmail.isEmpty()){
                    Toast.makeText(LoginScreen.this, "Please enter your account", Toast.LENGTH_SHORT).show();
                }
                else  if (getPassword.isEmpty()){
                    Toast.makeText(LoginScreen.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(getEmail, getPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent loginIntent = new Intent(LoginScreen.this, MainActivity.class);
                                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(loginIntent);
                                    Toast.makeText(LoginScreen.this, "Login success!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginScreen.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });

    }


}