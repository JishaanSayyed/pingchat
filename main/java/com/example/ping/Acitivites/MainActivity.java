package com.example.ping.Acitivites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ping.R;
import com.example.ping.Adapters.UserAdapter;
import com.example.ping.Models.Users;
import com.example.ping.SettingsActivity;
import com.example.ping.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseDatabase database;
    ArrayList<Users> users;
    UserAdapter usersAdapter;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            database =FirebaseDatabase.getInstance();
            users = new ArrayList<>();
            usersAdapter = new UserAdapter(this, users);
            binding.recyclerView.setAdapter(usersAdapter);

            database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    users.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        Users user = snapshot1.getValue(Users.class);

                        if(!user.getUid().equals(FirebaseAuth.getInstance().getUid()))
                            users.add(user);
                    }
                    usersAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });




        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.search:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;

            case R.id.groups:
                Toast.makeText(this, "Coming soon!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Invite:
                Toast.makeText(this, "Linked copied", Toast.LENGTH_SHORT).show();
                break;
        }

            return super.onOptionsItemSelected(item);
    }
}
