package com.administrator.freda_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Registration extends AppCompatActivity {
    EditText name,lstname,mail,password;
    public static final String PREFS_NAME = "LoginPrefs";
    Button btn_sign;
    String Search="@";
    ProgressDialog pb;
    String name1,lstname1,mail1,password1;
    SharedPreferences settings,pwd;
    TextView ret,skip;
    ImageView iv;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings  = getSharedPreferences(PREFS_NAME, 0);
        setContentView(R.layout.activity_registration);
         pb = new ProgressDialog(Registration.this);
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

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registration.this);
            TextView myMsg = new TextView(Registration.this);
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

         name=(EditText)findViewById(R.id.ed_firstname);
         lstname=(EditText)findViewById(R.id.ed_lastname);
         mail=(EditText)findViewById(R.id.ed_mail);

        password=(EditText)findViewById(R.id.ed_password);
        ret=(TextView)findViewById(R.id.tv_return);

        btn_sign=(Button)findViewById(R.id.btnLogin);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = name.getText().toString().trim();
                lstname1 = lstname.getText().toString().trim();
                mail1=mail.getText().toString().trim();
                password1= password.getText().toString();

                if (name1.replaceAll(" ","").matches("") || lstname1.replaceAll(" ","").matches("") || mail1.replaceAll(" ","").matches("")
                        || password1.replaceAll(" ","").matches("")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registration.this);
                    TextView myMsg = new TextView(Registration.this);
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
                    if(mail1.toLowerCase().indexOf(Search.toLowerCase())!=-1)
                    {
                        new Regist().execute();
                    }
                    else{
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registration.this);
                        TextView myMsg = new TextView(Registration.this);
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

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(Registration.this,MainActivity.class);
                startActivity(it);
            }
        });

    }

    private class Regist extends AsyncTask<String,String,String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
            name1 = name.getText().toString().trim();
            lstname1 = lstname.getText().toString().trim();
            mail1=mail.getText().toString().trim();
            password1= password.getText().toString();
           }
        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url= "http://outsourcingservicesusa.com/freda_app/insert_data.php?" +
                    "case_id=1&name="+name1.replaceAll(" ","")+"&email="+mail1.replaceAll(" ","")+"&lstname="+lstname1.replaceAll(" ","")+"" +
                    "&password="+password1.replaceAll(" ","")+"&phone=";
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
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.commit();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                    TextView myMsg = new TextView(Registration.this);
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
                                    Intent it= new Intent(Registration.this,MainActivity.class);
                                    startActivity(it);
                                }
                            });
                    builder.show();
                }
                else
                {
                    if(message.equals("0"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                        TextView myMsg = new TextView(Registration.this);
                        myMsg.setText("Alert!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);
                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("Email id is already registered.");
                        builder.setPositiveButton("Ok.",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                });
                        builder.show();
                    }
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
