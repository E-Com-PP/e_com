package com.fci.e_com;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WebAppInterface
{
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
        String[] dataA = dataz.split("±");
        List<String> result = new ArrayList<String>();

        for(int i = 0; i < dataA.length; i++)
        {
            String[] msgs = dataA[i].split("╖");
            String toadd = "";

            for(int n = 0; n < msgs.length; n++)
            {
                toadd += msgs[n];
            }
            result.add(toadd);
        }

        ((MainActivity)mContext).News = result;
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
            MA.handler.Login("", "");
        }

        @JavascriptInterface
        public void UserDataShow()
        {
            MainActivity MainActv = ((MainActivity)mContext);
            MainActv.user = new UserSettings(MainActv.webInterface.data);
            //TODO show data
        }
}
