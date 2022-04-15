package com.example.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.StatusBarManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    Button uid,mpin,login;
    EditText m_pin,userid,pwd,pan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.pink2));
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.pink));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(cd);
        uid = findViewById(R.id.uid);
        mpin = findViewById(R.id.mpin);
        login = findViewById(R.id.login);
        m_pin = findViewById(R.id.m_pin);
        userid = findViewById(R.id.userid);
        pan = findViewById(R.id.pan);
        pwd = findViewById(R.id.pwd);
        uid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpin.setText(R.string.mpin);
                uid.setText(R.string.user_id1);
                m_pin.setVisibility(View.GONE);
                userid.setVisibility(View.VISIBLE);
                pwd.setVisibility(View.VISIBLE);
            }
        });
        mpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid.setText(R.string.user_id);
                mpin.setText(R.string.mpin1);
                userid.setVisibility(View.GONE);
                pwd.setVisibility(View.GONE);
                m_pin.setVisibility(View.VISIBLE);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_pin.getVisibility()==View.GONE)
                {
                    String usrid = userid.getText().toString();
                    String password = pwd.getText().toString();
                    String panno = pan.getText().toString();

                    if(panno.equals(""))
                        Toast.makeText(LoginScreen.this, "Enter 10-Digit PAN Number", Toast.LENGTH_SHORT).show();
                    else if(usrid.equals(""))
                        Toast.makeText(LoginScreen.this, "Enter User Id", Toast.LENGTH_SHORT).show();
                    else if(password.equals(""))
                        Toast.makeText(LoginScreen.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    else
                    {
                        LoginTask lt = new LoginTask(LoginScreen.this);
                        lt.execute(panno,usrid,password);
                    }
                }
                else
                {

                    String mp = m_pin.getText().toString();
                    String panno = pan.getText().toString();
                    if(mp.equals(""))
                        Toast.makeText(LoginScreen.this, "Enter 6-Digit MPIN", Toast.LENGTH_SHORT).show();
                    else if(panno.equals(""))
                        Toast.makeText(LoginScreen.this, "Enter 10-Digit PAN Number", Toast.LENGTH_SHORT).show();
                    else
                    {
                        LoginTask lt = new LoginTask(LoginScreen.this);
                        lt.execute(panno,"",mp);
                    }
                }
            }
        });
    }
}