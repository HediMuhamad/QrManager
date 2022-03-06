package com.adpy.qrmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClicked(View view){
        switch (view.getId()){
            case R.id.scanIntentBtn:
                Intent scanIntent = new Intent(this, ScanActivity.class);
                startActivity(scanIntent);
                break;
            case R.id.generateIntentBtn:
                Intent generateIntent = new Intent(this, GeneratorActivity.class);
                startActivity(generateIntent);
                break;
            case R.id.getSourceBtn:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(getResources().getString(R.string.source_code_link))));
                startActivity(browserIntent);
                break;
        }
    }

}