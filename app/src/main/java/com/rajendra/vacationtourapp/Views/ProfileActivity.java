package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rajendra.vacationtourapp.Login.LoginScreen;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.User;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtnameuser, txtCountry, txtEmail, txtPhone;
    private ImageView imgBackFromProfile, imgAvatar;
    private Button btnLogout;
    private String emailUser;


    private FirebaseDatabase mDatabase;
    private DatabaseReference mUserRef;

    private FirebaseStorage mFirestorage;
    private StorageReference mStoreRef;

    private FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtnameuser = findViewById(R.id.txtUsername);
        txtCountry = findViewById(R.id.textCountry);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        btnLogout = findViewById(R.id.btnLogout);
        imgBackFromProfile = findViewById(R.id.imgBackFromProfile);
        imgAvatar = findViewById(R.id.imgAvatar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        emailUser = firebaseUser.getEmail();

        mFirestorage = FirebaseStorage.getInstance();
        mStoreRef = mFirestorage.getReference("ImageFolder/");

        mDatabase = FirebaseDatabase.getInstance();
        mUserRef = mDatabase.getReference("user");



        mUserRef.addValueEventListener(new ValueEventListener() {
            String username, email, country, phone, avatar;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyID : snapshot.getChildren()){
                    if(keyID.child("email").getValue().equals(emailUser)){
                        username = keyID.child("user_name").getValue(String.class);
                        email = keyID.child("email").getValue(String.class);
                        country = keyID.child("country").getValue(String.class);
                        phone = keyID.child("phone").getValue(String.class);
                        avatar = keyID.child("img_avatar").getValue(String.class);
                        break;
                    }
                }
                txtnameuser.setText(username);
                txtCountry.setText(country);
                txtEmail.setText(email);
                txtPhone.setText(phone);
                if (!avatar.equals(""))
                    Picasso.get().load(avatar).into(imgAvatar);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", "Failed to read value.", error.toException());
            }
        });

        imgBackFromProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                toMainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toMainIntent);
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imgUri = data.getData();
            //Picasso.get().load(imgUri).into(imgAvatar);
            uploadPicture(imgUri);
        }
    }

    private void uploadPicture(Uri imgUri) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();

        StorageReference Imagename = mFirestorage.getReference().child("image" + imgUri.getLastPathSegment());
        Imagename.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference ImgAvatar = FirebaseDatabase.getInstance()
                                        .getReference("user")
                                        .child(getIdUser(emailUser))
                                        .child("img_avatar");

                                ImgAvatar.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Picasso.get().load(uri).into(imgAvatar);
                                        Toast.makeText(ProfileActivity.this, "Image Uploaded to RealtimeDatabase", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        Toast.makeText(ProfileActivity.this, "Image Uploaded to FireStore", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                     public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(ProfileActivity.this, "Upload image failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double percent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) percent);
                    }
                });
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

    public void logout(View view) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        Intent logoutIntent = new Intent(ProfileActivity.this, LoginScreen.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutIntent);
        finish();
    }
    

}