package com.ikiugu.at_voice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import static com.ikiugu.at_voice.MainActivity.AGENT_USERNAME;
import static com.ikiugu.at_voice.MainActivity.SHARED_PREFS_NAME;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIMER = 100L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //open the sign up screen or home screen depending on whether logged in
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, 0);

                String username = sharedPreferences.getString(AGENT_USERNAME, "");
                if (TextUtils.isEmpty(username)) {
                    Intent intent = new Intent(getApplicationContext(), AgentSignupActivity.class);
                    startActivity(intent);

                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        }, SPLASH_TIMER);
    }
}