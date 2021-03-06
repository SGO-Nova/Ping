package com.example.ping_application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class updatebackground extends AsyncTask <String, Void, String> {

    private boolean updateSuccess;

    AlertDialog dialog;
    Context context;
    public updatebackground(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Update Info Status");
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String fullName = voids[0];
        String birthday = voids[1];
        String phoneNum = voids[2];
        String driverBool = voids[3];
        String origName = voids[4];
        String origPhone = voids[5];

        System.out.println(driverBool);
        System.out.println(driverBool);
        System.out.println(driverBool);
        System.out.println(driverBool);
        System.out.println(driverBool);


        String connstr = "http://BLAH/updateuser.php"; // Replace BLAH with IPv4 address of computer hosting XAMPP server

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            updateSuccess = false; // Default

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("fullName", "UTF-8")+"="+URLEncoder.encode(fullName, "UTF-8")
                    +"&&"+URLEncoder.encode("birthday", "UTF-8")+"="+URLEncoder.encode(birthday, "UTF-8")
                    +"&&"+URLEncoder.encode("phoneNum", "UTF-8")+"="+URLEncoder.encode(phoneNum, "UTF-8")
                    +"&&"+URLEncoder.encode("driverBool", "UTF-8")+"="+URLEncoder.encode(driverBool, "UTF-8")
                    +"&&"+URLEncoder.encode("origName", "UTF-8")+"="+URLEncoder.encode(origName, "UTF-8")
                    +"&&"+URLEncoder.encode("origPhone", "UTF-8")+"="+URLEncoder.encode(origPhone, "UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1")); //ENCODING SCHEME (ISO)

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                    result += line;
            }

            reader.close();
            ips.close();
            http.disconnect();

            if(result.equals("Update successful!"))
            {
                updateSuccess = true;
            }

            return result;

        }
        catch (MalformedURLException e) {
            result = e.getMessage();
        }
        catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }
}