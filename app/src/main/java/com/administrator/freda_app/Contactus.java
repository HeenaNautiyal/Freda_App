package com.administrator.freda_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class Contactus extends AppCompatActivity {
EditText name,lstname,mail,message2;
    String fname,lname,email,message1;
    public static final String PREFS_NAME = "LoginPrefs";
    String Search="@";
    ImageView iv,iv_logout,iv_fb,iv_pin,iv_twit,iv_insta,iv_yout,iv_google;
    SharedPreferences settings;
    Button btn;
    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageButton imageButton= (ImageButton)findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Contactus.this, Home.class);
                startActivity(it);
            }
        });

        pb = new ProgressDialog(Contactus.this);
        pb.setMessage("Please wait while Loading...");

        settings  = getSharedPreferences(PREFS_NAME, 0);
        name=(EditText)findViewById(R.id.ed_firstname);
        lstname=(EditText)findViewById(R.id.ed_lastname);
        mail=(EditText)findViewById(R.id.ed_mail);
        message2=(EditText)findViewById(R.id.ed_message);
        iv_logout=(ImageView)findViewById(R.id.logout);

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Contactus.this);
                TextView myMsg = new TextView(Contactus.this);
                myMsg.setText("Log out!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);

                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Are you Sure ?");
                builder.setPositiveButton("Yes,Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.remove("admin");
                                editor.remove("logged");
                                editor.commit();
                                Intent intent = new Intent(Contactus.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
                builder.show();
            }
        });

        btn=(Button)findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname= name.getText().toString().trim();
                fname=fname.replaceAll(" ","");
                lname = lstname.getText().toString().trim();
                lname=lname.replaceAll(" ","");
                email=mail.getText().toString().trim();
                email=email.replaceAll(" ","");
                message1= message2.getText().toString();
                message1=message1.replaceAll(" ","");
                message1=message1.replaceAll("\n","");
                if (fname.matches("") || lname.matches("") || email.matches("")
                        || message1.matches("")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Contactus.this);
                    TextView myMsg = new TextView(Contactus.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
                else
                {
                    if(email.toLowerCase().indexOf(Search.toLowerCase())!=-1)
                    {
                        new contact().execute(); }
                    else{
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Contactus.this);
                        TextView myMsg = new TextView(Contactus.this);
                        myMsg.setText("Warning!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);
                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("Email is invalid.");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                        mail.setText("");
                                    }
                                });
                        builder.show();
                    }
                }
            }
        });

        iv_fb=(ImageView)findViewById(R.id.i_facebook);
        iv_pin=(ImageView)findViewById(R.id.i_pinint);
        iv_insta=(ImageView)findViewById(R.id.i_insta);
        iv_twit=(ImageView)findViewById(R.id.i_twi);
        iv_yout=(ImageView)findViewById(R.id.i_you);
        iv_google=(ImageView)findViewById(R.id.i_google);
        iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/ProfessionalFraming"));
                startActivity(intent);
            }
        });
        iv_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.pinterest.com/diplomaframing/"));
                startActivity(intent);
            }
        });
        iv_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/proframeco/"));
                startActivity(intent);
            }
        });
        iv_twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/ProFrameCo"));
                startActivity(intent);
            }
        });
       iv_yout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_VIEW,
                       Uri.parse("https://www.youtube.com/watch?v=SVyzfvaQT5Q"));
               startActivity(intent);
           }
       });
        iv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://plus.google.com/+Officialdiplomaframes"));
                startActivity(intent);
            }
        });

    }
    private class contact extends AsyncTask<String,String,String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.show();
            fname= name.getText().toString().trim();
            fname=fname.replaceAll(" ","");
            lname = lstname.getText().toString().trim();
            lname=lname.replaceAll(" ","");
            email=mail.getText().toString().trim();
            email=email.replaceAll(" ","");
            message1= message2.getText().toString();
            message1=message1.replaceAll(" ","");
            message1=message1.replaceAll("\n","");
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url= "http://outsourcingservicesusa.com/" +
                    "freda_app/contact_send_mail.php?case_id=1" +
                    "&fname="+fname+"&lname="+lname+"&email="+email+"" +
                    "&message="+message1+"";

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
                pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                final String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Contactus.this);
                    TextView myMsg = new TextView(Contactus.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your message has been sent.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                       name.setText("");
                                       lstname.setText("");
                                        message2.setText("");
                                        mail.setText("");
                                }
                            });
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
