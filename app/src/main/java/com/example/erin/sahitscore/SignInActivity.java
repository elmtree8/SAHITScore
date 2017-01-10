package com.example.erin.sahitscore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appdatasearch.GetRecentContextCall;

import java.io.IOException;
import com.sendgrid.*;

/**
 *
 * Created by erin on 21/07/16.
 * @author erin
 */

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        EditText region = (EditText) findViewById(R.id.region);
        EditText email = (EditText) findViewById(R.id.email);
        Button signIn = (Button) findViewById(R.id.signIn);
        assert region != null;
        assert email != null;
        assert signIn != null;

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Check that both EditTexts are filled in
                //TODO: Send data from EditTexts using SendGrid

                Email from = new Email("test@example.com");
                String subject = "Hello World from the SendGrid Java Library!";
                Email to = new Email("elmtree8@gmail.com");
                Content content = new Content("text/plain", "Hello, Email!");
                Mail mail = new Mail(from, subject, to, content);

                SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
                Request request = new Request();
                try {
                    request.method = Method.POST;
                    request.endpoint = "mail/send";
                    request.body = mail.build();
                    GetRecentContextCall.Response response = sg.api(request);
                    System.out.println(response.statusCode);
                    System.out.println(response.body);
                    System.out.println(response.headers);
                } catch (IOException ex) {
                    throw ex;
                }

                Intent intent = new Intent(view.getContext(), InformationActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = getSharedPreferences("SignInActivity", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, InformationActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.apply();
        }
    }
}
