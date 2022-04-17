package com.example.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class HomeScreen extends AppCompatActivity {

    LinearLayout scrollView;
    String pan="";
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        scrollView = findViewById(R.id.scrollView2);
        Intent intent = getIntent();
        pan = intent.getStringExtra("pan");
        BackgroundTask b = new BackgroundTask(HomeScreen.this);
        b.execute("https://dusc.000webhostapp.com/query.php","SELECT cNAME FROM customer WHERE PAN_NO='"+pan+"'");
        BackgroundTask bt = new BackgroundTask(HomeScreen.this);
        bt.execute("https://dusc.000webhostapp.com/query.php","SELECT * FROM account WHERE PAN_NO='"+pan+"'");
        try {
            String temp = b.get();
            String s[] = temp.split(":");
            temp = s[s.length-1];
            temp = temp.replace(";","");
            temp = temp.replace("#","");
            TextView name = findViewById(R.id.textName);
            name.setText(temp);
            result = bt.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        displayAccountDetails();
    }

    private void displayAccountDetails()
    {
        String[] s = result.split("#");
        for (int i=0;i<s.length;i++)
        {
            String s1[]=s[i].split(";");
            LinearLayout ll = new LinearLayout(HomeScreen.this);
            ll.setBackground(getDrawable(R.drawable.ll_border));
            LinearLayout.LayoutParams tempParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setLayoutParams(tempParams);
            tempParams.setMargins(0,15,0,15);
            ll.setOrientation(LinearLayout.VERTICAL);
            for(int j=0;j<s1.length-1;j++)
            {
                TextView tv = new TextView(HomeScreen.this);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setText(s1[j]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv.setTextColor(getColor(R.color.black));
                    tv.setTextSize(18);
                }
                tv.setPadding(20,20,20,20);
                ll.addView(tv);
            }
            scrollView.addView(ll);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.profile:
                showProfile();
                return true;
            case R.id.transaction:
                showTransactions();
                return true;
            case R.id.deposit:
                deposit();
                return true;
            case R.id.withdraw:
                withdraw();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout()
    {
        finishAndRemoveTask();
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
    }

    private void showProfile()
    {
        Intent intent = new Intent(HomeScreen.this,Profile.class);
        intent.putExtra("pan",pan);
        startActivity(intent);
        Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
    }

    private void showTransactions()
    {
        Intent intent = new Intent(HomeScreen.this,Transaction.class);
        intent.putExtra("pan",pan);
        startActivity(intent);
        Toast.makeText(this, "Transactions", Toast.LENGTH_SHORT).show();
    }

    private void deposit()
    {
        Intent intent = new Intent(HomeScreen.this,Deposit.class);
        intent.putExtra("pan",pan);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Deposit", Toast.LENGTH_SHORT).show();
    }

    private void withdraw()
    {
        Intent intent = new Intent(HomeScreen.this,Withdraw.class);
        intent.putExtra("pan",pan);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Withdraw", Toast.LENGTH_SHORT).show();
    }
}