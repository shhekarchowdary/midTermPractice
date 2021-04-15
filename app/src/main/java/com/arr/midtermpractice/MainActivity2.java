package com.arr.midtermpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView totalFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        totalFee = findViewById(R.id.txtFee);

        totalFee.setText(String.format("%.2f",MainActivity.totalFee));
    }
}