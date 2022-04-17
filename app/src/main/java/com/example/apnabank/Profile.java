package com.example.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class Profile extends AppCompatActivity {

    String pan="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        pan = intent.getStringExtra("pan");
        BackgroundTask b = new BackgroundTask(Profile.this);
        b.execute("https://dusc.000webhostapp.com/profile.php","SELECT PAN_NO,cNAME,bDATE,ADDRESS,PhNUMBER FROM customer where PAN_NO='"+pan+"'");
        BackgroundTask bt = new BackgroundTask(Profile.this);
        bt.execute("https://dusc.000webhostapp.com/profile.php","SELECT ACC_NO FROM account where PAN_NO='"+pan+"'");
        try {
            String temp = b.get();
            String details[] = temp.split(";");
            temp = bt.get();
            String acc_nos[] = temp.split(";");
            TextView pname = findViewById(R.id.pname);
            TextView dob = findViewById(R.id.pdate);
            TextView ppan = findViewById(R.id.ppan);
            TextView addr = findViewById(R.id.paddress);
            TextView pno = findViewById(R.id.pnumber);
            pname.setText(pname.getText().toString()+details[1]);
            dob.setText(dob.getText().toString()+details[2]);
            ppan.setText(ppan.getText().toString()+details[0]);
            addr.setText(addr.getText().toString()+details[3]);
            pno.setText(pno.getText().toString()+details[4]);

            for(int i=0;i<acc_nos.length;i++)
            {
                TextView tv = new TextView(this);
                tv.setTextSize(20);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv.setTextColor(getColor(R.color.black));
                }
                tv.setText("Account "+(i+1)+" : "+acc_nos[i]);
                LinearLayout ll = findViewById(R.id.addprofile);
                View v = new View(this);
                v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,25));
                ll.addView(tv);
                ll.addView(v);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}