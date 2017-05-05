package com.administrator.freda_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    private static final String TWITTER_KEY = "9s6tM2Jmld5xH5NjXmx6OJcsp";
    private static final String TWITTER_SECRET = "33jdmlKsIbjmuqokuPOqq4ZhSSdNQULZjJOXMB7wqVuwSh1uBn";

    EditText ed_email,ed_password;
    String email,password;
    Button btnLog;
    ProgressDialog pb;
    TextView fgpass,signup,skip;
    ImageView iv;
   // SharedPreferences settings,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

         pb = new ProgressDialog(MainActivity.this);
         ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
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
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(MainActivity.this,Home.class);
                startActivity(it);
            }
        });

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING

                ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
        } else if(connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            TextView myMsg = new TextView(MainActivity.this);
            myMsg.setText("Mobile Data is off");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(20);
            myMsg.setTextColor(Color.BLACK);
            builder.setCustomTitle(myMsg);
            builder.setMessage("Turn on mobile data or use Wi-Fi to access data.");
            builder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton("Setting", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int which) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    dialog.cancel();
                }
            });
            builder.show();
        }
          ed_email=(EditText)findViewById(R.id.email);
          ed_password=(EditText)findViewById(R.id.password);
          fgpass=(TextView)findViewById(R.id.fpass);
          signup=(TextView)findViewById(R.id.tv_signup);
          btnLog=(Button)findViewById(R.id.btnLogin);

          btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              email=ed_email.getText().toString();
              password=ed_password.getText().toString();
                if(email.matches("")||password.matches(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    TextView myMsg = new TextView(MainActivity.this);myMsg.setText("Warning!");myMsg.setGravity(Gravity.CENTER_HORIZONTAL);myMsg.setTextSize(20);myMsg.setTextColor(Color.BLACK);builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    }
                            });
                    builder.show();
                    ed_email.setText("");
                    ed_password.setText("");
                }
                else{
                    new Login().execute();
                }
            }
          });

        fgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent it= new Intent(MainActivity.this,Forgetpass.class);
                startActivity(it);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(MainActivity.this,Registration.class);
                startActivity(it);
            }
        });
    }
    private class Login extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
            email = ed_email.getText().toString().trim();
            password = ed_password.getText().toString().trim();

        }
        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://outsourcingservicesusa.com/freda_app/insert_data.php?case_id=2&email="+email.replaceAll(" ","")+"&password="+password.replaceAll(" ","")+"";
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
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    TextView myMsg = new TextView(MainActivity.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have logged in successfully.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it =new Intent(MainActivity.this,Home.class);
                                    startActivity(it);
                                    }
                            });
                    builder.show();
                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    TextView myMsg = new TextView(MainActivity.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Email/Password is invalid.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();}
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
