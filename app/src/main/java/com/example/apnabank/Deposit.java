package com.example.apnabank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Deposit extends AppCompatActivity {

    String pan="",otp="";
    Button dep;
    EditText cno,cvv,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Spinner selectAccount = findViewById(R.id.spinner);
        dep = findViewById(R.id.addmoney);
        cno = findViewById(R.id.cardno);
        cvv = findViewById(R.id.cvv);
        amount = findViewById(R.id.amount);
        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectAccount.getSelectedItem().toString().equals("Select Account"))
                    Toast.makeText(Deposit.this, "Select valid account to deposit", Toast.LENGTH_SHORT).show();
                else if(amount.getText().toString().equals(""))
                    Toast.makeText(Deposit.this, "Enter valid amount i.e more than 100", Toast.LENGTH_SHORT).show();
                else  if(Double.parseDouble(amount.getText().toString())<=100.0)
                    Toast.makeText(Deposit.this, "Enter valid amount i.e more than 100", Toast.LENGTH_SHORT).show();
                else if((cno.getText().toString()).length()!=12)
                    Toast.makeText(Deposit.this, "Enter correct card number", Toast.LENGTH_SHORT).show();
                else if((cvv.getText().toString()).length()!=3)
                    Toast.makeText(Deposit.this, "Enter correct cvv number", Toast.LENGTH_SHORT).show();
                else
                {
                    depositMoney(selectAccount.getSelectedItem().toString(),Double.parseDouble(amount.getText().toString()));
                }
            }
        });
        Intent intent = getIntent();
        pan = intent.getStringExtra("pan");
        BackgroundTask b = new BackgroundTask(Deposit.this);
        b.execute("https://dusc.000webhostapp.com/profile.php","SELECT ACC_NO FROM account where PAN_NO='"+pan+"'");
        String temp = null;
        try {
            temp = b.get();
            String acc_nos[] = temp.split(";");
            String[] newArray = Arrays.copyOf(acc_nos, acc_nos.length + 1);
            newArray[0] = "Select Account";
            System.arraycopy(acc_nos, 0, newArray, 1, acc_nos.length);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, newArray);
            selectAccount.setAdapter(adapter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void depositMoney(String accNO,double amt)
    {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        otp = String.format("%06d", number);
        NotificationChannel notificationChannel = new NotificationChannel("002","APNA BANK",NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("OTP for transaction");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(Deposit.this,"002");
        builder.setSmallIcon(R.drawable.bank).setContentTitle("OTP").setContentText(otp).setPriority(Notification.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Deposit.this);
        notificationManagerCompat.notify(002,builder.build());
        AlertDialog dialog = new AlertDialog.Builder(Deposit.this).create();
        dialog.setCanceledOnTouchOutside(false);
        EditText enterotp = new EditText(Deposit.this);
        enterotp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        enterotp.setEms(6);
        enterotp.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
        enterotp.setHint("Enter 6 digit OTP");
        Button submit = new Button(Deposit.this);
        submit.setText("Submit");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        enterotp.setLayoutParams(lp);
        LinearLayout ll = new LinearLayout(Deposit.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lp);
        ll.addView(enterotp);
        ll.addView(submit);
        dialog.setView(ll);
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterotp.getText().toString().equals(otp))
                {
                    BackgroundTask b = new BackgroundTask(Deposit.this);
                    b.execute("https://dusc.000webhostapp.com/update.php","acc "+accNO+" amt "+amt+" type ADD");
                    try {
                        if(b.get().equals("true"))
                        {
                            long num = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                            String tid= String.format("%10d", num);
                            BackgroundTask bt = new BackgroundTask(Deposit.this);
                            bt.execute("https://dusc.000webhostapp.com/insert.php","INSERT INTO transaction values('"+tid+"','CREDIT','"+accNO+"',"+amt+")");
                            if(bt.get().equals("true"))
                            {
                                Toast.makeText(Deposit.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(Deposit.this,HomeScreen.class);
                                intent.putExtra("pan",pan);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Deposit.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(Deposit.this,HomeScreen.class);
                                intent.putExtra("pan",pan);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(Deposit.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intent = new Intent(Deposit.this,HomeScreen.class);
                            intent.putExtra("pan",pan);
                            startActivity(intent);
                            finish();
                        }


                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(Deposit.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(Deposit.this,HomeScreen.class);
                    intent.putExtra("pan",pan);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}