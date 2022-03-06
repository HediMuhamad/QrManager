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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// implements onClickListener for the onclick behaviour of button
public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    TextView outputTxtView;
    ImageView helperCopyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();

        outputTxtView = findViewById(R.id.outputTxtView);
        helperCopyBtn = findViewById(R.id.helperCopyBtn);

        helperCopyBtn.setOnClickListener(this);
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
                outputTxtView.setText(intentResult.getContents());
                String text = String.valueOf(outputTxtView.getText());
                if(URLUtil.isNetworkUrl(text)){
                    SpannableString mSpannableString = new SpannableString(text);
                    mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);
                    outputTxtView.setText(mSpannableString);
                    outputTxtView.setOnClickListener(this);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.helperCopyBtn:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("qrCode", String.valueOf(outputTxtView.getText()));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Copied",  Toast.LENGTH_LONG).show();
                break;

            case R.id.outputTxtView:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(outputTxtView.getText())));
                startActivity(browserIntent);
                break;
        }

    }
}