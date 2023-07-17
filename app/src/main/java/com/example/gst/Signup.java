package com.example.gst;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gst.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Signup extends AppCompatActivity {

    Button signup;
    EditText fullName,email,password;
    TextView signin;
    FirebaseAuth auth;
    FirebaseDatabase db;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        signup = findViewById(R.id.button);
        fullName =  findViewById(R.id.editTextText);
        email =  findViewById(R.id.editTextTextEmailAddress);
        password =  findViewById(R.id.editTextTextPassword);
        signin =  findViewById(R.id.textView2);



        signin.setOnClickListener(view -> {
            Intent intent = new Intent(Signup.this,Login.class);
            startActivity(intent);
        });
        signup.setOnClickListener(view -> createUser());

    }

    private void createUser() {
        String  userName= fullName.getText().toString();
        String userEmail= email.getText().toString();
        String userPassword= password.getText().toString();
        
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Name is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userName.matches("[a-zA-Z]+")) {
            Toast.makeText(this, "Username should only contain alphabetic characters", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Password is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 8){
            Toast.makeText(this,"Password length must be atleast 8 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean upperCase = false;
        boolean lowerCase = false;
        boolean Digit = false;
        boolean sc = false;

        for (int i = 0; i < userPassword.length(); i++) {
            char c = userPassword.charAt(i);
            if (Character.isUpperCase(c)) {
                upperCase = true;
            } else if (Character.isLowerCase(c)) {
                lowerCase = true;
            } else if (Character.isDigit(c)) {
                Digit = true;
            } else if (isSpecialCharacter(c)) {
                sc = true;
            }
        }

        if (!upperCase) {
            Toast.makeText(this, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!lowerCase) {
            Toast.makeText(this, "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Digit) {
            Toast.makeText(this, "Password must contain at least one  Digit", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!sc) {
            Toast.makeText(this, "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
            return;
        }

//        String specialCharacters = "!@#$%^&*()-_=+[{]};:',<.>/?";
//        boolean containsSpecialCharacter = false;
//
//        for (int i = 0; i < userPassword.length(); i++) {
//            if (specialCharacters.contains(String.valueOf(userPassword.charAt(i)))) {
//                containsSpecialCharacter = true;
//                break;
//            }
//        }
//
//        if (!containsSpecialCharacter) {
//            Toast.makeText(this, "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
//            return;
//        }


        //create User

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            UserModel userModel = new UserModel(userName,userEmail,userPassword);
                            String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                            db.getReference("Users").child(id).setValue(userModel);
                            Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            sendUserToNext();
                        }
                        else{
                            Toast.makeText(Signup.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    private void sendUserToNext()
                    {
                        Intent intent=new Intent(Signup.this, Login.class);
                        startActivity(intent);

                    }
                });
    }
    private boolean isSpecialCharacter(char c) {
        String specialCharacters = "!@#$%^&*()-_=+[{]};:',<.>/?";
        return specialCharacters.contains(String.valueOf(c));
    }
}