package com.mldt.puppypals.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.mldt.puppypals.R;

public class LogIn extends AppCompatActivity {
    public static final String TAG = "LogInActivity";
    public static final String USER_EMAIL_TAG= "";
    SharedPreferences preferences;

    //set shared preferences for user email when user log in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setUpLogInForm();
    }

    public void setUpLogInForm(){
        Intent callingIntent = getIntent();
        String userEmail = callingIntent.getStringExtra(Verify.VERIFY_ACCOUNT_EMAIL_TAG);
        EditText emailET = findViewById(R.id.loginActivityEmailInput);
        emailET.setText(userEmail);
        SharedPreferences.Editor preferenceEditor =preferences.edit();
        preferenceEditor.putString(USER_EMAIL_TAG,userEmail);
        findViewById(R.id.loginActivityLoginButton).setOnClickListener(view -> {
            String userSelectedEmail = emailET.getText().toString();
            String userPassword = ((EditText) findViewById(R.id.loginActivityPasswordInput)).getText().toString();

            Amplify.Auth.signIn(
                    userSelectedEmail,
                    userPassword,
                    success -> {
                        Log.i(TAG, "Login succeeded " + success);
                        Intent goToMainActivity = new Intent(LogIn.this, MainActivity.class);
                        startActivity(goToMainActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Login failed: " + failure);
                        runOnUiThread(() -> {
                            Toast.makeText(LogIn.this, "Login failed", Toast.LENGTH_SHORT).show();
                        });
                    }
            );
        });
    }
}