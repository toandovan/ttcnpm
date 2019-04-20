package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login=(Button)findViewById(R.id.btn_login);
        findViewById(R.id.btn_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,Home.class);
        startActivity(intent);
    }
}
