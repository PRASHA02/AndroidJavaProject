package com.example.gst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Login extends AppCompatActivity {

    // Define the correct username and password
    private static final String CORRECT_USERNAME = "admin@gmail.com";
    private static final String CORRECT_PASSWORD = "password";

    private TextView referenceTextView;
    private WebView webView;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    // Firebase Database variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize the Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");

        // Initialize the views
        usernameEditText = findViewById(R.id.editTextText2);
        passwordEditText = findViewById(R.id.editTextText8);
        loginButton = findViewById(R.id.button4);

        referenceTextView = findViewById(R.id.textView23);
        webView = new WebView(this);

        referenceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = referenceTextView.getText().toString();
                navigateToWebPage(url);
            }
        });

        // Set a click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();
                // Perform authentication
                if (enteredUsername.equals(CORRECT_USERNAME) && enteredPassword.equals(CORRECT_PASSWORD)) {
                    // Authentication successful
                    createDatabaseForUser(enteredUsername,enteredPassword);
                    Toast.makeText(Admin_Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_Login.this, admin_dashboard.class);
                    startActivity(intent);
                }
                else {
                    if(!enteredUsername.equals(CORRECT_USERNAME)) {
                        Toast.makeText(Admin_Login.this, "Invalid username", Toast.LENGTH_SHORT).show();
                        
                    }
                    if(!enteredPassword.equals(CORRECT_PASSWORD) ){
                        Toast.makeText(Admin_Login.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        });
    }

    private void navigateToWebPage(String url) {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        setContentView(webView);
    }

    private void createDatabaseForUser(String username,String password) {
        // Save the user to the Firebase Database
        User user = new User(username,password);
        usersReference.child("User").setValue(user);
    }

    // User model class
    public static class User {
        private String username;
        private String password;

        public User() {
            // Required default constructor for Firebase Database
        }

        public User(String username,String password) {
            this.username = username;
            this.password=password;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }
    }
}