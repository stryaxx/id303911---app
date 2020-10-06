package com.example.storefrontv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storefrontv2.data.model.RegisterDataSource;
import com.example.storefrontv2.ui.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

public class createUser extends AppCompatActivity {

    RegisterDataSource registerDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        EditText firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        EditText lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        EditText streetNameEdit = (EditText) findViewById(R.id.streetNameEdit);
        EditText cityNameEdit = (EditText) findViewById(R.id.cityNameEdit);
        EditText postNameEdit = (EditText) findViewById(R.id.postNameEdit);
        EditText emailNameEdit = (EditText) findViewById(R.id.emailNameEdit);
        EditText usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        EditText passEdit = (EditText) findViewById(R.id.passEdit);
        Button createAccBtn = (Button) findViewById(R.id.createAccBtn);

        registerDataSource = new RegisterDataSource();


        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerDataSource.register(usernameEdit.getText().toString(),
                        passEdit.getText().toString(),
                        firstNameEdit.getText().toString(),
                        lastNameEdit.getText().toString(),
                        emailNameEdit.getText().toString(),
                        postNameEdit.getText().toString(),
                        streetNameEdit.getText().toString(),
                        cityNameEdit.getText().toString())) {
                    //Success
                    System.out.println("SUCCESS ON REGISTER");
                    Toast.makeText(createUser.this, "ACCOUNT CREATED", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(createUser.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    //Failure
                    System.out.println("FAILURE ON REGISTER");
                    Toast.makeText(createUser.this, "FAILURE! TRY AGAIN!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
