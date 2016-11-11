package com.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by osman on 09-11-2016.
 */

public class SignUpActivity extends AppCompatActivity {
    LinearLayout signup_user, login, signup_storeowner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No title bar will be shown
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //This will show the screen in full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup);

        signup_user = (LinearLayout)findViewById(R.id.signup_user);
        signup_storeowner = (LinearLayout)findViewById(R.id.signup_storeowner);
        login = (LinearLayout)findViewById(R.id.login);

        signup_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, RegistrationActivity.class);
                i.putExtra("usertype","user");
                startActivity(i);
            }
        });
        signup_storeowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, RegistrationActivity.class);
                i.putExtra("usertype","store");
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
