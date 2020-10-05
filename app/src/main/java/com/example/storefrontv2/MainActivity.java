package com.example.storefrontv2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ChatService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = ChatService.getInstance();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            setUserInfo();
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void setUserInfo() {
        User user = service.getUser();
        if(user != null) {
            TextView uid = findViewById(R.id.uid);
            TextView firstName = findViewById(R.id.last_name);
            TextView lastName = findViewById(R.id.first_name);

            uid.setText(user.getUserid());
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
        }
    }
}