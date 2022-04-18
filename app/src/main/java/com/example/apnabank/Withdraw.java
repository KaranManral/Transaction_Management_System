package com.example.apnabank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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

public class Withdraw extends AppCompatActivity {

    LinearLayout transferContainer,withdrawContainer;
    String pan="",otp="";
    Button transfer,withdraw,transferbtn,withdrawbtn;
    EditText tamt,wamt;
    Spinner selectAccount,selectyourAccount,selecttransferAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        selectAccount = findViewById(R.id.waccno);
        selectyourAccount = findViewById(R.id.yaccno);
        selecttransferAccount = findViewById(R.id.taccno);
        transferContainer = findViewById(R.id.transferContainer);
        withdrawContainer = findViewById(R.id.withdrawalContainer);
        transfer = findViewById(R.id.transfer);
        withdraw = findViewById(R.id.withdrawal);
        transferbtn = findViewById(R.id.transferButton);
        withdrawbtn = findViewById(R.id.withdrawButton);
        tamt = findViewById(R.id.tamt);
        wamt = findViewById(R.id.wamt);
        Intent intent = getIntent();
        pan = intent.getStringExtra("pan");
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transfer.setText(R.string.transfer);
                withdraw.setText(R.string.withdraw1);
                transferContainer.setVisibility(View.GONE);
                withdrawContainer.setVisibility(View.VISIBLE);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transfer.setText(R.string.transfer1);
                withdraw.setText(R.string.withdraw);
                transferContainer.setVisibility(View.VISIBLE);
                withdrawContainer.setVisibility(View.GONE);
            }
        });
        BackgroundTask b = new BackgroundTask(Withdraw.this);
        b.execute("https://dusc.000webhostapp.com/profile.php","SELECT ACC_NO FROM account where PAN_NO='"+pan+"'");
        BackgroundTask bt = new BackgroundTask(Withdraw.this);
        bt.execute("https://dusc.000webhostapp.com/profile.php","SELECT ACC_NO FROM account");
        String temp = null;
        try {
            temp = b.get();
            String acc_nos[] = temp.split(";");
            String[] newArray = Arrays.copyOf(acc_nos, acc_nos.length + 1);
            newArray[0] = "Select Your Account";
            System.arraycopy(acc_nos, 0, newArray, 1, acc_nos.length);
            temp = bt.get();
            acc_nos = temp.split(";");
            String[] newArray1 = Arrays.copyOf(acc_nos, acc_nos.length + 1);
            newArray1[0] = "Select Reciever's Account";
            System.arraycopy(acc_nos, 0, newArray1, 1, acc_nos.length);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, newArray);
            selectAccount.setAdapter(adapter);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, newArray);
            selectyourAccount.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, newArray1);
            selecttransferAccount.setAdapter(adapter2);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        transferbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transferMoney();
            }
        });
        withdrawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withDrawMoney();
            }
        });
    }

    private void transferMoney()
    {
        if(selectyourAccount.getSelectedItem().toString().equals("Select Your Account"))
            Toast.makeText(this, "Select valid account number", Toast.LENGTH_SHORT).show();
        else if(selecttransferAccount.getSelectedItem().toString().equals("Select Reciever's Account"))
            Toast.makeText(this, "Select valid account number", Toast.LENGTH_SHORT).show();
        else if(tamt.getText().toString().equals(""))
            Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show();
        else if(Double.parseDouble(tamt.getText().toString())<100)
            Toast.makeText(this, "Enter an amount more than Rs 99", Toast.LENGTH_SHORT).show();
        else if(Double.parseDouble(tamt.getText().toString())>100000)
            Toast.makeText(this, "Enter an amount less than Rs 100001", Toast.LENGTH_SHORT).show();
        else
        {
            String yourAccNO=selectyourAccount.getSelectedItem().toString();
            String receiverAccNO=selecttransferAccount.getSelectedItem().toString();
            BackgroundTask b = new BackgroundTask(this);
            b.execute("https://dusc.000webhostapp.com/profile.php","SELECT Balance FROM account where ACC_NO='"+yourAccNO+"'");
            String temp ="",balance=tamt.getText().toString();
            try {
                temp=b.get();
                String bal[] = temp.split(";");
                if(Double.parseDouble(balance)>Double.parseDouble(bal[0]))
                {
                    Toast.makeText(this, "Not enough amount in your account", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,HomeScreen.class);
                    intent.putExtra("pan",pan);
                    startActivity(intent);
                    finish();
                }
                else{
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    otp = String.format("%06d", number);
                    NotificationChannel notificationChannel = new NotificationChannel("002","APNA BANK", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.setDescription("OTP for transaction");
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);
                    Notification.Builder notificationbuilder = new Notification.Builder(Withdraw.this,"002");
                    notificationbuilder.setSmallIcon(R.drawable.bank).setContentTitle("OTP").setContentText(otp).setPriority(Notification.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Withdraw.this);
                    notificationManagerCompat.notify(002,notificationbuilder.build());

                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setCanceledOnTouchOutside(false);
                    EditText enterotp = new EditText(this);
                    enterotp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    enterotp.setEms(6);
                    enterotp.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
                    enterotp.setHint("Enter 6 digit OTP");
                    Button submit = new Button(this);
                    submit.setText("Submit");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    enterotp.setLayoutParams(lp);
                    LinearLayout ll = new LinearLayout(this);
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
                                BackgroundTask b1 = new BackgroundTask(Withdraw.this);
                                b1.execute("https://dusc.000webhostapp.com/update.php","acc "+yourAccNO+" amt "+balance+" type SUB");
                                BackgroundTask b2 = new BackgroundTask(Withdraw.this);
                                b2.execute("https://dusc.000webhostapp.com/update.php","acc "+receiverAccNO+" amt "+balance+" type ADD");
                                try {
                                    if(b1.get().equals("true")&&b2.get().equals("true"))
                                    {
                                        long num = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                                        String tid= String.format("%10d", num);
                                        long num1 = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                                        String tid1= String.format("%10d", num1);
                                        BackgroundTask bt = new BackgroundTask(Withdraw.this);
                                        bt.execute("https://dusc.000webhostapp.com/insert.php","INSERT INTO transaction values('"+tid+"','DEBIT','"+yourAccNO+"',"+balance+")");
                                        BackgroundTask bt1 = new BackgroundTask(Withdraw.this);
                                        bt1.execute("https://dusc.000webhostapp.com/insert.php","INSERT INTO transaction values('"+tid1+"','CREDIT','"+receiverAccNO+"',"+balance+")");
                                        if(bt.get().equals("true")&&bt1.get().equals("true"))
                                        {
                                            Toast.makeText(Withdraw.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(Withdraw.this,HomeScreen.class);
                                            intent.putExtra("pan",pan);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(Withdraw.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(Withdraw.this,HomeScreen.class);
                                            intent.putExtra("pan",pan);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(Withdraw.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Intent intent = new Intent(Withdraw.this,HomeScreen.class);
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
                                Toast.makeText(Withdraw.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(Withdraw.this,HomeScreen.class);
                                intent.putExtra("pan",pan);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void withDrawMoney()
    {
        if(selectAccount.getSelectedItem().toString().equals("Select Your Account"))
            Toast.makeText(this, "Select valid account number", Toast.LENGTH_SHORT).show();
        else if(wamt.getText().toString().equals(""))
            Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show();
        else if(Double.parseDouble(wamt.getText().toString())<100)
            Toast.makeText(this, "Enter a withdraw amount more than Rs 99", Toast.LENGTH_SHORT).show();
        else if(Double.parseDouble(tamt.getText().toString())>100000)
            Toast.makeText(this, "Enter an amount less than Rs 100001", Toast.LENGTH_SHORT).show();
        else
        {
            String accNO=selectAccount.getSelectedItem().toString();
            BackgroundTask b = new BackgroundTask(this);
            b.execute("https://dusc.000webhostapp.com/profile.php","SELECT Balance FROM account where ACC_NO='"+accNO+"'");
            String temp ="",balance=wamt.getText().toString();
            try {
                temp=b.get();
                String bal[] = temp.split(";");
                if(Double.parseDouble(balance)>Double.parseDouble(bal[0]))
                {
                    Toast.makeText(this, "Not enough amount in account", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,HomeScreen.class);
                    intent.putExtra("pan",pan);
                    startActivity(intent);
                    finish();
                }
                else{
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    String code = String.format("%06d", number);
                    int number1 = rnd.nextInt(999999);
                    otp = String.format("%06d", number1);
                    NotificationChannel notificationChannel = new NotificationChannel("002","APNA BANK", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.setDescription("OTP for transaction");
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);
                    Notification.Builder notificationbuilder = new Notification.Builder(Withdraw.this,"002");
                    notificationbuilder.setSmallIcon(R.drawable.bank).setContentTitle("OTP").setContentText(otp).setPriority(Notification.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Withdraw.this);
                    notificationManagerCompat.notify(002,notificationbuilder.build());

                    long num = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                    String tid= String.format("%10d", num);

                    BackgroundTask backgroundTask = new BackgroundTask(Withdraw.this);
                    backgroundTask.execute("https://dusc.000webhostapp.com/insert.php","INSERT INTO transaction values('"+tid+"','DEBIT','"+accNO+"',"+balance+")");
                    BackgroundTask backgroundTask1 = new BackgroundTask(Withdraw.this);
                    backgroundTask1.execute("https://dusc.000webhostapp.com/update.php","acc "+accNO+" amt "+balance+" type SUB");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(Html.fromHtml("Your code for withdrawal is: "+code+"<br><br>"+"1)Go to the nearest APNA BANK ATM"+"<br>"+"2)Select Mobile Banking"+"<br>"+"3)Enter above code and otp received for withdrawal"))
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    try {
                                        if(backgroundTask.get().equals("true")&&backgroundTask1.get().equals("true"))
                                        {
                                            Toast.makeText(Withdraw.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(Withdraw.this,HomeScreen.class);
                                            intent.putExtra("pan",pan);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(Withdraw.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(Withdraw.this,HomeScreen.class);
                                            intent.putExtra("pan",pan);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}