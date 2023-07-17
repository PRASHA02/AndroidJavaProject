package com.example.gst;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button signIn;
    EditText email,password;
    TextView signup;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        signIn = (Button) findViewById(R.id.button);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        signup = (TextView) findViewById(R.id.textView2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loginUser();
            }
        });
    }

    private void loginUser() {
        String UserEmail = email.getText().toString();
        String UserPassword = password.getText().toString();
        if(TextUtils.isEmpty(UserEmail)){
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(UserPassword)){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(UserPassword.length() < 8){
            Toast.makeText(this, "Password must contain atleat 8 letters", Toast.LENGTH_SHORT).show();
            return;
        }

        //login
        auth.signInWithEmailAndPassword(UserEmail, UserPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            sendUserToNext();
                        }
                        else {
                            Toast.makeText(Login.this, "Error:"+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    private void sendUserToNext()
                    {
                        Intent intent=new Intent(Login.this,Dashboard.class);
                        startActivity(intent);

                    }
                });
    }
}