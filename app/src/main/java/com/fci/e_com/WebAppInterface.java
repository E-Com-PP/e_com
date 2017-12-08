package com.fci.e_com;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.ShowableListMenu;
import android.webkit.JavascriptInterface;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import layout.homeFragment;
import layout.newsFragment;

public class WebAppInterface {
    Context mContext;
    String data;

    WebAppInterface(Context ctx) {
        this.mContext = ctx;
    }

    @JavascriptInterface
    public void sendData(String dataz) {
        ((MainActivity)mContext).webInterface.data = dataz;
    }

    @JavascriptInterface
    public void sendNews(String dataz)
    {
        MainActivity MainActv = ((MainActivity)mContext);
        String[] dataA = dataz.split("±");
        List<NewsObj> result = new ArrayList<NewsObj>();

        for(int i = 0; i < dataA.length; i++)
        {
            String[] toadd = dataA[i].split("╖");
            result.add(new NewsObj(toadd[1], toadd[0]));
        }

        MainActv.News = result;

        if(MainActv.GetFragClass() == newsFragment.class)
            MainActv.fillFragment(result.size(), 0);
        else if(MainActv.GetFragClass() == homeFragment.class)
            MainActv.fillFragment(0, 1);
    }

    @JavascriptInterface
    public void isValidLogin(String valid)
    {
        MainActivity ma = (MainActivity)mContext;
        if(valid.equals("true"))
        {
            ma.loggedIn = 1;
            ma.ShowDialogProgress(false);

            Toast.makeText(ma, "Logged in", Toast.LENGTH_LONG).show();
        }
        else
        {
            //ma.ShowDialogProgress(false);
            Intent LogOutIntent = new Intent(ma, LogIn.class);
            ma.startActivity(LogOutIntent);
        }
    }

    @JavascriptInterface
    public void sendGrades(String dataz)
    {
        List<Grade> Grades = new ArrayList<Grade>();
        String[] courses = dataz.split("±");

        for(int i = 0; i < courses.length; i++)
        {
            Grades.add(new Grade(courses[i]));
        }

        ((MainActivity)mContext).user.Grades = Grades;
        ((MainActivity) mContext).fillFragment(Grades.size(), 2);
    }

    @JavascriptInterface
    public void IsLoggedIn(String logged)
    {
        MainActivity MA = ((MainActivity)mContext);
        MA.loggedIn = (logged.equals("true")) ? 1 : 0;
        MA.handler.Login(MA.Name, MA.UserPassword);
        MA.Name="";
        MA.UserPassword="";
    }

    @JavascriptInterface
    public void UserDataShow()
    {
        MainActivity MainActv = ((MainActivity)mContext);
        MainActv.user = new UserSettings(MainActv.webInterface.data);

        ((TextView)MainActv.findViewById(R.id.nameTxt)).setText(MainActv.user.Name);
        //Toast.makeText(MainActv, MainActv.user.Name, Toast.LENGTH_SHORT).show();
        //((TextView)ShowData.showData.findViewById(R.id.lblName)).setText(MainActv.user.Name);
    }

    @JavascriptInterface
    public void AddOption(String option)
    {
        MainActivity MainActv = (MainActivity)mContext;
        MainActv.handler.YearOptions.add(option);
    }
}
