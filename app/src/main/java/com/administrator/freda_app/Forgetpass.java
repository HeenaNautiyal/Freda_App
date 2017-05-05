package com.administrator.freda_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Forgetpass extends AppCompatActivity {
    public static final String PREFS_NAME = "LoginPrefs";
     Button btn;
     EditText email;
     ProgressDialog pb;
     TextView tv,skip;
     SharedPreferences settings;
     String mail;
     ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        pb = new ProgressDialog(Forgetpass.this);
        settings  = getSharedPreferences(PREFS_NAME, 0);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(
                R.drawable.gradient));
        iv = (ImageView) findViewById(R.id.imageView1);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);
        imageButton.setVisibility(View.INVISIBLE);
        skip=(TextView)findViewById(R.id.tv_skip);
        skip.setVisibility(View.INVISIBLE);
        email=(EditText)findViewById(R.id.email);
        btn=(Button)findViewById(R.id.btnsubmit);
        tv=(TextView)findViewById(R.id.cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=email.getText().toString();

                new password().execute();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent it= new Intent(Forgetpass.this,MainActivity.class);
                startActivity(it);
            }
        });
    }

    private class password extends AsyncTask<String,String,String>{
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
            mail = email.getText().toString().trim();
            mail=mail.replaceAll(" ","");
            }
        @Override
        protected String doInBackground(String... strings) {

            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://outsourcingservicesusa.com/freda_app/forgot_api.php?case_id=1&email="+mail+"";
            String SetServerString = "";
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + SetServerString);
            return SetServerString;
        }
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                pb.dismiss();
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Forgetpass.this);
                    TextView myMsg = new TextView(Forgetpass.this);
                    myMsg.setText("Alert");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Password sent successfully to registered email id.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    email.setText("");
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Forgetpass.this);
                    TextView myMsg = new TextView(Forgetpass.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Email is invalid.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
