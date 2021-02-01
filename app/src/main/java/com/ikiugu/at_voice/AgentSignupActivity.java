package com.ikiugu.at_voice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ikiugu.at_voice.api.RetrofitClient;
import com.ikiugu.at_voice.api.model.Agent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ikiugu.at_voice.MainActivity.AGENT_PHONE;
import static com.ikiugu.at_voice.MainActivity.AGENT_USERNAME;
import static com.ikiugu.at_voice.MainActivity.SHARED_PREFS_NAME;

public class AgentSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agent_sign_up);


        ProgressBar progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.add_agent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView username = findViewById(R.id.agent_username);
                TextView phoneNumber = findViewById(R.id.agent_phone_number);

                String name = username.getText() != null ? username.getText().toString() : "";
                String phone = phoneNumber.getText() != null ? phoneNumber.getText().toString() : "";

                progressBar.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
                    RetrofitClient.getInstance().getApi()
                            .createAgent(new Agent(name, phone))
                            .enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    progressBar.setVisibility(View.GONE);

                                    if (response.isSuccessful()) {
                                        if (response.body()) {
                                            // save in shared preferences
                                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, 0).edit();
                                            editor.putString(AGENT_USERNAME, name);
                                            editor.putString(AGENT_PHONE, phone);
                                            editor.apply();

                                            //navigate to the main fragment
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);

                                            finish();
                                        } else {
                                            showMessage("An error occurred");
                                        }
                                    } else {
                                        showMessage("Something went wrong");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    showMessage(t.getMessage().toString());
                                }
                            });
                } else {
                    progressBar.setVisibility(View.GONE);

                    showMessage("Please fill both fields in");
                }
            }
        });
    }

    private void showMessage(String toString) {
        Toast.makeText(this, toString, Toast.LENGTH_SHORT).show();
    }
}