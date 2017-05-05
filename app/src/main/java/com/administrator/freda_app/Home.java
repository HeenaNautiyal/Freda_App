package com.administrator.freda_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;

public class Home extends AppCompatActivity {
 ImageView home1,collage1,custome1,blog1,gift1,account1,contactus,facebook,mail,twitter;
    TextView fgpass,signup,skip;
    ImageView iv;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String shareURL="http://www.officialdiplomaframes.com/";
    String email,subject,message;
    Uri URI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        facebookSDKInitialize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        shareDialog = new ShareDialog(this);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(
                R.drawable.gradient));
        iv = (ImageView) findViewById(R.id.imageView1);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view = getSupportActionBar().getCustomView();


        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);
        imageButton.setVisibility(View.INVISIBLE);
        skip = (TextView) findViewById(R.id.tv_skip);
        skip.setVisibility(View.INVISIBLE);

        home1 = (ImageView) findViewById(R.id.home);
        collage1 = (ImageView) findViewById(R.id.collage);
        custome1 = (ImageView) findViewById(R.id.customframe);
        blog1 = (ImageView) findViewById(R.id.blog);
        gift1 = (ImageView) findViewById(R.id.gift);
        account1 = (ImageView) findViewById(R.id.account);
        contactus = (ImageView) findViewById(R.id.i_contact);
        facebook = (ImageView) findViewById(R.id.facebookshare);
        mail=(ImageView)findViewById(R.id.emailshare);
        twitter=(ImageView)findViewById(R.id.twittershare);

        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Home.this, Homeactivity.class);
                startActivity(it);
            }
        });

        collage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Collage.class);
                startActivity(intent);
            }
        });

        custome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, CustomeFrames.class);
                startActivity(intent);
            }
        });
        blog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Blog.class);
                startActivity(intent);
            }
        });
        gift1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Gift_frame.class);
                startActivity(intent);
            }
        });
        account1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Account.class);
                startActivity(intent);
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Contactus.class);
                startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(" Professional Diploma Frames ")
                            .setContentDescription(
                                    "Checkout Professional Diploma Frames on your smartphone. Download it today from:")
                            .setContentUrl(Uri.parse(shareURL))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SentMail();
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TweetComposer.Builder builder = null;
                try {
                    builder = new TweetComposer.Builder(Home.this)
                            .url(new URL("http://www.officialdiplomaframes.com"))
                            .text("Checkout Professional Diploma Frames on your smartphone. Download it today from:\n");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                builder.show();
            }
        });
    }

    private void SentMail() {

        subject = "Checkout Professional Diploma Frames.";
        message = "Download it today from: http://www.officialdiplomaframes.com";
        final Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        if (URI != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
        }
        emailIntent
                .putExtra(android.content.Intent.EXTRA_TEXT, message);
        this.startActivity(Intent.createChooser(emailIntent,
                "Sending email..."));
    }

    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
