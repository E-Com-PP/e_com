package com.fci.e_com;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShowData extends AppCompatActivity {
    public static  ShowData showData;
    MainActivity MainActv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#AA66CC")));
        showData = (ShowData)this;
        MainActv = WebHandler.MainActv;
        MainActv.handler.GetUserData();
    }

    public void GetData(View view)
    {
        String btnText = ((Button)view).getText().toString();
        String toshow = "";
        if(btnText.equals("GPA"))
        {
            MainActv.handler.GetGrades(1);
        }
        else if(btnText.equals("Status"))
        {
            toshow = MainActv.user.Grades.get(0).CName;
        }
        else if(btnText.equals("Year"))
        {
            toshow = Integer.toString(MainActv.user.Year);
        }
        else if(btnText.equals("ID"))
        {
            toshow = Integer.toString(MainActv.user.ID);
        }

        Toast.makeText(this, toshow, Toast.LENGTH_LONG).show();
    }
}
