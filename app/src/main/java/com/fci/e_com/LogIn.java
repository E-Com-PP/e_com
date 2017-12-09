package com.fci.e_com;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LogIn extends AppCompatActivity {
    String NameStr="";
    String PasswordStr="";
    private EditText Name;
    private EditText Password;
    private Button LogInBnt;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("ACCOUNT", 0);
        SharedPreferences.Editor editor = prefs.edit();
        if(prefs.getString("Username", null) != null && prefs.getString("Password", null) != null)
        {
            NameStr = prefs.getString("Username", "");
            PasswordStr = prefs.getString("Password", "");

            SwitchActivity();
        }

        Name = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        LogInBnt = (Button) findViewById(R.id.email_sign_in_button);
        LogInBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    NameStr = Name.getText().toString();
                    PasswordStr = Password.getText().toString();
                    SwitchActivity();
                }
                else
                    Toast.makeText(LogIn.this, "No Internet Connection detected. Please try again while you are connected to the Internet.", Toast.LENGTH_LONG).show();
            }


        });
    }
    public void SwitchActivity()
    {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("NameStr", NameStr);
        intent.putExtra("PasswordStr",PasswordStr);
        startActivity(intent);
    }
    public String getEmail(){return NameStr;}
    public String getPassword(){return PasswordStr;}

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
