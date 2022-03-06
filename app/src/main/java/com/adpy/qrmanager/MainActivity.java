package com.adpy.qrmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnsClicked(View view){
        switch (view.getId()){
            case R.id.scanBtn:
                Intent scanIntent = new Intent(this, ScanActivity.class);
                startActivity(scanIntent);
                break;
            case R.id.generateBtn:
                Intent generateIntent = new Intent(this, ScanActivity.class);
                break;
        }
    }

}