package com.example.gst;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GetStarted extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getstarted);
        button = findViewById(R.id.button2);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(GetStarted.this, choose.class);
            startActivity(intent);

        });

    }
}
