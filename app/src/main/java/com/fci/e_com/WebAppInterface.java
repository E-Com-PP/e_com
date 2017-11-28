package com.fci.e_com;

import android.content.Context;
import android.webkit.JavascriptInterface;
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
    public void sendGrades(String dataz)
    {
        List<Grade> Grades = new ArrayList<Grade>();
        String[] courses = dataz.split("±");

        for(int i = 0; i < courses.length; i++)
        {
            Grades.add(new Grade(courses[i]));
        }

        ((MainActivity)mContext).user.Grades = Grades;
    }

    @JavascriptInterface
    public void IsLoggedIn(String logged)
    {
        MainActivity MA = ((MainActivity)mContext);
        MA.loggedIn = (logged.equals("true")) ? 1 : 0;
        MA.handler.Login("20160124", "testing123");
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
