package com.fci.e_com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LogIn extends AppCompatActivity {
    String NameStr="";
    String PasswordStr="";
    private EditText Name;
    private EditText Password;
    private Button LogInBnt;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        Name = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        LogInBnt = (Button) findViewById(R.id.email_sign_in_button);
        LogInBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NameStr = Name.getText().toString();
                PasswordStr = Password.getText().toString();

                SwitchActivity();
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



}
