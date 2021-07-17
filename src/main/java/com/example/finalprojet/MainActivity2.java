package com.example.finalprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity2 extends AppCompatActivity {

TextView initDisplay;
Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initDisplay=findViewById(R.id.textView);
        b=findViewById(R.id.button2);

        initDisplay.setText("Welcome to COVID Vaccine allocations tracker");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(MainActivity2.this,MainActivity.class);
                        startActivity(i);
                    }
                }, 5000);
            }
        });





    }
}