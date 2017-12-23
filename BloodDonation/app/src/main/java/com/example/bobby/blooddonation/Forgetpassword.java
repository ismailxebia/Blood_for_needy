package com.example.bobby.blooddonation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Forgetpassword extends AppCompatActivity {

    String emailpass;
    EditText e1,e2,e3,e4;
    Button b1, b2;
    TextView t1,t2,t3,t4,t5,t7,t8,t9,t10;
    String password,OTP,passwordcopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

    }

    @Override
    protected void onResume() {
        super.onResume();

        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button5);

        e1=(EditText) findViewById(R.id.editText7);
        e2=(EditText) findViewById(R.id.editText8);
        e3=(EditText) findViewById(R.id.editText9);
        e4=(EditText) findViewById(R.id.editText10);

        t1=(TextView) findViewById(R.id.textView5);
        t2=(TextView) findViewById(R.id.textView4);
        t3=(TextView) findViewById(R.id.textView6);
        t4=(TextView) findViewById(R.id.textView7);
        t5=(TextView) findViewById(R.id.textView8);
        t7=(TextView) findViewById(R.id.textView2);
        t8=(TextView) findViewById(R.id.textView3);
        t9=(TextView) findViewById(R.id.textView9);
        t10=(TextView) findViewById(R.id.textView10);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailpass= e1.getText().toString();
                if(!emailpass.contains("@") ||  !emailpass.contains("."))
                {
                    Toast.makeText(Forgetpassword.this, "Check your email-id.", Toast.LENGTH_SHORT).show();
                }
                else {
                    new JSONP().execute();
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OTP= e2.getText().toString();
                passwordcopy= e3.getText().toString();
                password= e4.getText().toString();
                if(e4.getText().toString().isEmpty() || e3.getText().toString().isEmpty() || e2.getText().toString().isEmpty())
                {
                    Toast.makeText(Forgetpassword.this, "Please fill your password", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(passwordcopy))
                {
                    Toast.makeText(Forgetpassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else  if(password.length()<8)
                {
                    Toast.makeText(Forgetpassword.this, "Password length cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    new JSONP2().execute();
                }

            }
        });

    }

    class JSONP extends AsyncTask<Void,Void,String> {
        String jsonurl, jsonstring,regidlogin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonurl ="http://sixthsenseit.com/blood/forgetpassword.php";

        }

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder  stringBuilder;
            String data = "";
            try {
                URL url = new URL(jsonurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata = URLEncoder.encode("email") + "=" + URLEncoder.encode(emailpass);

                bufferedWriter.write(postdata);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder=new StringBuilder();
                while((jsonstring= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(jsonstring+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                data=stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("successfully"))
            {
                e1.setVisibility(EditText.GONE);
                b1.setVisibility(Button.GONE);
                t1.setVisibility(TextView.GONE);
                t7.setVisibility(TextView.GONE);
                t8.setVisibility(TextView.GONE);

                e2.setVisibility(EditText.VISIBLE);
                e3.setVisibility(EditText.VISIBLE);
                e4.setVisibility(EditText.VISIBLE);
                b2.setVisibility(Button.VISIBLE);
                t2.setVisibility(TextView.VISIBLE);
                t3.setVisibility(TextView.VISIBLE);
                t4.setVisibility((TextView.VISIBLE));
                t5.setVisibility(TextView.VISIBLE);
                t9.setVisibility(View.VISIBLE);
                t10.setVisibility(View.VISIBLE);

                Toast.makeText(Forgetpassword.this, "OTP sent successfully to your registered email id", Toast.LENGTH_SHORT).show();

            }
            else
            {
                e1.setText("");
                Toast.makeText(Forgetpassword.this,s, Toast.LENGTH_SHORT).show();
            }
        }
    }


    class JSONP2 extends AsyncTask<Void,Void,String> {
        String jsonurl, jsonstring,regidlogin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonurl ="http://sixthsenseit.com/blood/forgetpassword2.php";

        }

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder  stringBuilder;
            String data = "";
            try {
                URL url = new URL(jsonurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata = URLEncoder.encode("email") + "=" + URLEncoder.encode(emailpass) + "&" + URLEncoder.encode("password") + "=" + URLEncoder.encode(password) + "&" + URLEncoder.encode("OTP") + "=" + URLEncoder.encode(OTP);

                bufferedWriter.write(postdata);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder=new StringBuilder();
                while((jsonstring= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(jsonstring+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                data=stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("successfully"))
            {
                Toast.makeText(Forgetpassword.this, "Password reset successful.Pease sign in again using your new login credentials", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Forgetpassword.this, Signin.class);
                startActivity(intent);
                finish();
            }
            else
            {
                e2.setText("");
                e3.setText("");
                e4.setText("");
                Toast.makeText(Forgetpassword.this,s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
