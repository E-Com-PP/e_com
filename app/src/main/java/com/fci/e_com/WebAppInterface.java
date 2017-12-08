package com.fci.e_com;

import android.content.Context;
import android.content.Intent;
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
        MainActv.Synchro.TaskDone();

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

            Toast.makeText(ma, "Logged in", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent LogOutIntent = new Intent(ma, LogIn.class);
            ma.startActivity(LogOutIntent);
        }
    }

    @JavascriptInterface
    public void sendGrades(String dataz, String sem, String level)
    {
        MainActivity ma = ((MainActivity)mContext);
        ma.Synchro.TaskDone();

        List<Grade> Grades = new ArrayList<Grade>();
        String[] courses = dataz.split("±");

        for(int i = 0; i < courses.length; i++)
        {
            Grades.add(new Grade(courses[i]));
        }

        ma.ops.createGradesTable(ma.Name, sem, level, Grades);

        ma.user.Grades = Grades;
        ma.fillFragment(Grades.size(), 2);
    }

    @JavascriptInterface
    public void IsLoggedIn(String logged)
    {
        MainActivity MA = ((MainActivity)mContext);
        MA.loggedIn = (logged.equals("true")) ? 1 : 0;
        MA.handler.Login(MA.Name, MA.UserPassword);
    }

    @JavascriptInterface
    public void UserDataShow()
    {
        ((MainActivity)mContext).Synchro.TaskDone();

        MainActivity MainActv = ((MainActivity)mContext);
        MainActv.user = new UserSettings(MainActv.webInterface.data);

        ((TextView)MainActv.findViewById(R.id.nameTxt)).setText(MainActv.user.Name);
        //Toast.makeText(MainActv, MainActv.user.Name, Toast.LENGTH_SHORT).show();
        //((TextView)ShowData.showData.findViewById(R.id.lblName)).setText(MainActv.user.Name);
    }

    @JavascriptInterface
    public void AddOptions(String option)
    {
        final MainActivity MainActv = (MainActivity)mContext;

        String[] options = option.split("╖");

        for(int i = 0; i < options.length; i++) {
            MainActv.handler.YearOptions.add(options[i]);
        }

        MainActv.findViewById(R.id.YearSpin).post(new Runnable() {
            @Override
            public void run() {
                MainActv.initGradeSpinner(0);
            }
        });
    }
}
