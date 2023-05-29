package com.example.shakeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText edt;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt = findViewById(R.id.editTextPhone);
        btn = findViewById(R.id.button);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(edt);
        btn.setOnClickListener(view -> {
            Intent intent  = new Intent(MainActivity.this,ManageOtp.class);
            intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
            startActivity(intent);


        });

    }
}