package com.example.apnabank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginTask extends AsyncTask<String,Void,String> {

    AlertDialog dialog;
    Context context;
    String result = "";
    public LoginTask(Context context)
    {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage("Signing In");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String pan = strings[0];
        String uid = strings[1];
        String pwd = strings[2];
        String connect = "https://dusc.000webhostapp.com/index.php";
        try {
            URL url = new URL(connect);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream ops = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("pan","UTF-8")+"="+URLEncoder.encode(pan,"UTF-8")+"&"+URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+URLEncoder.encode("pwd","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line = "";
            while ((line=reader.readLine())!=null)
            {
                result+=line;
            }
            reader.close();
            ips.close();
            con.disconnect();
            return result;
        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if(result.equals("true"))
        {
            context.startActivity(new Intent(context,HomeScreen.class));
        }
        else
            Toast.makeText(context, "Login Failed: Enter correct Details", Toast.LENGTH_LONG).show();
    }
}
