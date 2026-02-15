package com.example.speechrecognizationcar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String securityCode = "2";
    Button connectBtn, pressedSecurityBtn;
    FloatingActionButton mikeBtn;
    View MainSheet;
    EditText addressEtx;
    Socket carSocket;
    OutputStreamWriter socketMessageSender;


    Runnable hideMikeBtn = new Runnable() {
        @Override
        public void run() {
            mikeBtn.hide();
            pressedSecurityBtn.setBackgroundResource(R.drawable.round_btn_unslected_bkg);
        }
    };

    Runnable resetSecurityBtn = new Runnable() {
        @Override
        public void run() {
            pressedSecurityBtn.setBackgroundResource(R.drawable.round_btn_unslected_bkg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainSheet = findViewById(R.id.MAIN_SHEET);
        connectBtn = findViewById(R.id.BTNCONNECT);
        addressEtx = findViewById(R.id.ETXADDRESS);
        mikeBtn = findViewById(R.id.BTNSPEAK);


        mikeBtn.hide();

        mikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, 99);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            carSocket = new Socket(addressEtx.getText().toString().split(":")[0], Integer.parseInt(addressEtx.getText().toString().split(":")[1]));
                            socketMessageSender = new OutputStreamWriter(carSocket.getOutputStream());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                                    MainSheet.setVisibility(View.VISIBLE);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                }).start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 99: {
                if (resultCode == RESULT_OK && null != data) {
                    final ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(MainActivity.this, "Sendimg Message :" + result.get(0), Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socketMessageSender.write(String.valueOf(result.get(0)));
                                socketMessageSender.flush();

                            } catch (IOException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Failed to Send Command", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();

                }
                break;
            }
        }
    }


    public void securityBtnClick(View view) {

        pressedSecurityBtn = (Button) view;
        pressedSecurityBtn.setBackgroundResource(R.drawable.round_btn_slected_bkg);

        if (pressedSecurityBtn.getText().toString().equals(securityCode)) {
            mikeBtn.show();
            new Handler().postDelayed(hideMikeBtn, 3000);
        } else {
            Toast.makeText(MainActivity.this, "Wrong Security Code", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(resetSecurityBtn, 250);
        }
    }


}
