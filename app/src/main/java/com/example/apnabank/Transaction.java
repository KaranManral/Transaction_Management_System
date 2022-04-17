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

public class Transaction extends AppCompatActivity {

    String pan="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Intent intent = getIntent();
        pan = intent.getStringExtra("pan");
        BackgroundTask b = new BackgroundTask(Transaction.this);
        b.execute("https://dusc.000webhostapp.com/profile.php","SELECT ACC_NO FROM account where PAN_NO='"+pan+"'");
        try {
            String temp = b.get();
            String acc_nos[] = temp.split(";");

            for(int i=0;i<acc_nos.length;i++)
            {
                BackgroundTask bt = new BackgroundTask(Transaction.this);
                bt.execute("https://dusc.000webhostapp.com/profile.php","SELECT T_ID,TYPE,ACC_NO,AMOUNT FROM transaction where ACC_NO='"+acc_nos[i]+"'");
                temp = bt.get();
                if(!temp.equals("")) {
                    String transactDetails[] = temp.split(";");

                    for (int j = 0; j < transactDetails.length; j += 4) {
                        LinearLayout ll = new LinearLayout(Transaction.this);
                        ll.setBackground(getDrawable(R.drawable.ll_border));
                        LinearLayout.LayoutParams tempParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        ll.setLayoutParams(tempParams);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        TextView tv1 = new TextView(this);
                        tv1.setTextSize(18);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv1.setTextColor(getColor(R.color.black));
                        }
                        tv1.setText("Transaction ID : " + transactDetails[j]);
                        tv1.setPadding(20, 20, 20, 20);
                        TextView tv2 = new TextView(this);
                        tv2.setTextSize(18);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv2.setTextColor(getColor(R.color.black));
                        }
                        tv2.setText("Acc No. : " + transactDetails[j + 2]);
                        tv2.setPadding(20, 20, 20, 20);
                        TextView tv3 = new TextView(this);
                        tv3.setTextSize(18);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv3.setTextColor(getColor(R.color.black));
                        }
                        tv3.setText("Transaction Type : " + transactDetails[j + 1]);
                        tv3.setPadding(20, 20, 20, 20);
                        TextView tv4 = new TextView(this);
                        tv4.setTextSize(18);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tv4.setTextColor(getColor(R.color.black));
                        }
                        tv4.setText("Amount : " + transactDetails[j + 3]);
                        tv4.setPadding(20, 20, 20, 20);
                        ll.addView(tv1);
                        ll.addView(tv2);
                        ll.addView(tv3);
                        ll.addView(tv4);
                        View v = new View(this);
                        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,30));
                        LinearLayout linearLayout = findViewById(R.id.addtransactions);
                        linearLayout.addView(ll);
                        linearLayout.addView(v);
                    }
                }
            }
            LinearLayout linearLayout = findViewById(R.id.addtransactions);
            if(linearLayout.getChildCount()==2)
            {
                TextView tv1 = new TextView(this);
                tv1.setTextSize(18);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv1.setTextColor(getColor(R.color.black));
                }
                tv1.setText("No Transactions done yet");
                tv1.setPadding(20, 20, 20, 20);
                linearLayout.addView(tv1);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}