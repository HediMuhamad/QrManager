package com.adpy.qrmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// implements onClickListener for the onclick behaviour of button
public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    TextView qrcodeOutput;
    ImageView toClipboardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();

        qrcodeOutput = findViewById(R.id.qr_code_output);
        toClipboardBtn = findViewById(R.id.to_clipboard_btn);

        toClipboardBtn.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Intent startIntent = new Intent(this, MainActivity.class);
                startActivity(startIntent);
            } else {
                qrcodeOutput.setText(intentResult.getContents());
                String text = String.valueOf(qrcodeOutput.getText());
                if(URLUtil.isNetworkUrl(text)){
                    SpannableString mSpannableString = new SpannableString(text);
                    mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);
                    qrcodeOutput.setText(mSpannableString);
                    qrcodeOutput.setOnClickListener(this);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.to_clipboard_btn:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("qrCode", String.valueOf(qrcodeOutput.getText()));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Copied",  Toast.LENGTH_LONG).show();
                break;

            case R.id.qr_code_output:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(qrcodeOutput.getText())));
                startActivity(browserIntent);
                break;
        }

    }
}