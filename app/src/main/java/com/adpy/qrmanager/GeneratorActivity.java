package com.adpy.qrmanager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GeneratorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView qrCodePlaceholder;
    EditText contentText;
    Button generateQrBtn;
    LinearLayout getSourceBtn;
    RelativeLayout activity_generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        qrCodePlaceholder = findViewById(R.id.qrCodePlaceholder);
        contentText = findViewById(R.id.contentTxtBox);
        generateQrBtn = findViewById(R.id.generateCodeBtn);
        getSourceBtn = findViewById(R.id.getSourceBtn);
        activity_generator = findViewById(R.id.activity_generator);

        generateQrBtn.setOnClickListener(this);

        contentText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    getSourceBtn.setVisibility(View.INVISIBLE);
                }else{
                    getSourceBtn.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public Bitmap generate(String content) {

        Bitmap bitmap = null;
        int size = (int) getResources().getDimension(R.dimen.content_width);

        try {
            BarcodeEncoder barcodeEncoder;
            barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(content,
                    BarcodeFormat.QR_CODE, size, size);

        } catch (WriterException e) {
            Log.e("generateQR()", e.getMessage());
        };

        return bitmap;
    }

    @Override
    public void onClick(View view) {
        String text = String.valueOf(contentText.getText());
        if(text.length()==0){
            Toast.makeText(GeneratorActivity.this, "OOPS, Content is empty", Toast.LENGTH_LONG).show();
            return;
        }
        else if(text.length()>2334){
            Toast.makeText(GeneratorActivity.this, "OOPS, Too long content", Toast.LENGTH_LONG).show();
            return;
        }
        qrCodePlaceholder.setImageBitmap(generate(text));
    }
}
