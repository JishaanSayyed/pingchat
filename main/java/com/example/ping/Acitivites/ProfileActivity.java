package com.example.ping.Acitivites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ping.Models.Users;
import com.example.ping.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseStorage storage;
    ProgressDialog dialog;
    Uri selectedImage;
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Setting up profile");
        dialog.setCancelable(false);


        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.personImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);

            }
        });
        binding.setUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.personName.getText().toString();
                if(name.isEmpty()){
                    binding.personName.setError("Please Enter your name");
                    return;
                }
                dialog.show();
                if(selectedImage != null){
//                    progressBar.setVisibility(View.VISIBLE);
//                    binding.setUpButton.setVisibility(View.INVISIBLE);
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
//                                dialog.dismiss();
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        dialog.dismiss();
                                   String imageUrl = uri.toString();
                                   String uid = auth.getUid();
                                   String phone = auth.getCurrentUser().getPhoneNumber();
                                   String name = binding.personName.getText().toString();


                                        Users users = new Users(uid, name, phone,imageUrl);


                                        database.getReference()
                                                .child("users")
                                                .child(uid)
                                                .setValue(users)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

//                                                        progressBar.setVisibility(View.GONE);
//                                                        binding.setUpButton.setVisibility(View.VISIBLE);
                                                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                        Toast.makeText(ProfileActivity.this, "Profile Created!", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    });
                }else {
                    String uid = auth.getUid();
                    String phone = auth.getCurrentUser().getPhoneNumber();


                    Users users = new Users(uid, name, phone,"NO IMAGE");


                    database.getReference()
                            .child("users")
                            .child(uid)
                            .setValue(users)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

//                                                        progressBar.setVisibility(View.GONE);
//                                                        binding.setUpButton.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(ProfileActivity.this, "Profile Created!", Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null){
            binding.personImage.setImageURI(data.getData());
            selectedImage = data.getData();
        }
    }
}