package com.fci.e_com;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import layout.gradesFragment;
import layout.gradesMyFragment;
import layout.homeFragment;
import layout.inboxFragment;
import layout.inboxMainFragment;
import layout.newsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView webViewer;
    WebHandler handler = new WebHandler(this);
    E_Mails allMails;
    Top_50 top;
    WebAppInterface webInterface;
    GWebAppInterface GInterface;

    public UserSettings user;
    public List<NewsObj> News = new ArrayList<NewsObj>();
    public int loggedIn = 0;
    public boolean isInstantiated = false;
    String currSelectedYear = "";
    String CurrentselectedYear2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        allMails = new E_Mails(this);
        top = new Top_50(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(loggedIn == 0) {
            handler.MainActv = this;
            handler.StartUp();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //initGradeSpinner(1);
//            if(GetFragClass() == newsFragment.class)
//                fillFragment(News.size(), 0);
//            else if(GetFragClass() == homeFragment.class)
//                fillFragment(0, 1);
//            else if(GetFragClass() == gradesFragment.class) {
//                //ArrayAdapter<String> adap = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, handler.YearOptions);
//
//                Spinner s = ((Spinner) findViewById(R.id.spinner3));
//                handler.GetGrades(1, s.getSelectedItem().toString());
//            }
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            trans.runOnCommit(new Runnable() {
                                  @Override
                                  public void run() {
                                      handler.GetNews();
                                  }
                              });
            trans.replace(R.id.fragContainer, new homeFragment()).commit();
        }
        else if (id == R.id.nav_inbox) {
            try {
                trans.runOnCommit(new Runnable() {
                    @Override
                    public void run() {

                        TabHost host = findViewById(R.id.inboxtabhost);
                        host.setup();

                        TabHost.TabSpec spec = host.newTabSpec("Inbox");
                        spec.setContent(R.id.inbox);
                        spec.setIndicator("Inbox");
                        host.addTab(spec);
                        spec = host.newTabSpec("Received Files");
                        spec.setContent(R.id.files);
                        spec.setIndicator("Received Files");
                        host.addTab(spec);

                        allMails.loadPage();
                    }
                });
                trans.replace(R.id.fragContainer, new inboxFragment()).commit();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (id == R.id.nav_grades) {
            trans.runOnCommit(new Runnable() {
                @Override
                public void run() {
                    TabHost host = findViewById(R.id.tabhost);
                    host.setup();

                    TabHost.TabSpec spec = host.newTabSpec("My Grades");
                    spec.setContent(R.id.my_grades);
                    spec.setIndicator("My Grades");
                    host.addTab(spec);
                    spec = host.newTabSpec("Top 50");
                    spec.setContent(R.id.top_50);
                    spec.setIndicator("Top 50");
                    host.addTab(spec);

                    Spinner spin = initGradeSpinner(0);
                    if(spin.getSelectedItem() != null)
                        handler.GetGrades(1, spin.getSelectedItem().toString());
                    else
                        handler.GetGradeYears(1);
                    Spinner TypeSpinner = initGradeSpinner(1);
                    Spinner YearsSpinner = initGradeSpinner(2);

                }
            });
            trans.replace(R.id.fragContainer, new gradesFragment()).commit();
        }
        else if (id == R.id.nav_news) {
            trans.runOnCommit(new Runnable() {
                @Override
                public void run() {
                    handler.GetNews();
                }
            });
            trans.replace(R.id.fragContainer, new newsFragment()).commit();
        }
        else if (id == R.id.nav_schedule) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, placeholder).commit();
        }
        else if (id == R.id.nav_logout) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, placeholder).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Class<? extends android.support.v4.app.Fragment> GetFragClass()
    {
        return getSupportFragmentManager().getFragments().get(0).getClass();
    }

    Spinner initGradeSpinner(int SpinnerType)
    {
        Spinner TempSpinner = null;
        ArrayAdapter<String> adap;
        switch(SpinnerType) {
            case 0:
                adap = new ArrayAdapter<String>(handler.MainActv, R.layout.support_simple_spinner_dropdown_item, handler.YearOptions);
                TempSpinner = ((Spinner) findViewById(R.id.YearSpin));

                TempSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        currSelectedYear = parentView.getSelectedItem().toString();
                        ((TextView) findViewById(R.id.myGrades_year)).setText("Year " + Integer.toString(parentView.getCount() - position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }

                });
                TempSpinner.setAdapter(adap);

                if (currSelectedYear != "") {
                    for (int i = 0; i < TempSpinner.getCount(); i++) {
                        if (currSelectedYear.equals(TempSpinner.getItemAtPosition(i).toString())) {
                            TempSpinner.setSelection(i);
                            break;
                        }
                    }
                }
                break;

            case 1: {
                TempSpinner = ((Spinner) findViewById(R.id.TypeSpin));
                List<String>  DepartmentsType=new ArrayList<String>();
                DepartmentsType.add("ALL");
                DepartmentsType.add("CS");
                DepartmentsType.add("IT");
                DepartmentsType.add("IS");
                DepartmentsType.add("DS");
                adap = new ArrayAdapter<String>(handler.MainActv, R.layout.support_simple_spinner_dropdown_item,DepartmentsType);
                TempSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        /*currSelectedYear = parentView.getSelectedItem().toString();
                        ((TextView) findViewById(R.id.rank_top50)).setText("Year " + Integer.toString(parentView.getCount() - position));*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }

                });
                TempSpinner.setAdapter(adap);

                break;

            }
            case 2: {
                TempSpinner = ((Spinner) findViewById(R.id.Top50YearSpin));
                List<String>  Years=new ArrayList<String>();
                Years.add("1");
                Years.add("2");
                Years.add("3");
                Years.add("4");
                adap = new ArrayAdapter<String>(handler.MainActv, R.layout.support_simple_spinner_dropdown_item, Years);
                TempSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        CurrentselectedYear2 = parentView.getSelectedItem().toString();
                        ((TextView) findViewById(R.id.rank_top50)).setText("Year " + Integer.toString(position+1));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }

                });
                TempSpinner.setAdapter(adap);

                if (CurrentselectedYear2 != "") {
                    for (int i = 0; i < TempSpinner.getCount(); i++) {
                        if (CurrentselectedYear2.equals(TempSpinner.getItemAtPosition(i).toString())) {
                            TempSpinner.setSelection(i);
                            break;
                        }
                    }
                }
                break;

            }
        }


        return TempSpinner;
    }


    public void fillFragment(int num, int type)
    {
        class FillFragmentsRunnable implements Runnable{
            int num, type;
            FillFragmentsRunnable(int numT, int typeT) {num = numT; type = typeT;}
            public void run()
            {
                LayoutInflater infl = getLayoutInflater();
                switch(type)
                {
                    //News Fragment
                    case 0: {
                        LinearLayout ll = (LinearLayout)findViewById(R.id.LLNews);

                        ll.removeAllViews();
                        for (int i = 0; i < num; i++) {
                            infl.inflate(R.layout.home_news, (ViewGroup) ll);

                            TextView txtV = (TextView) (((ViewGroup) ll.getChildAt(i)).getChildAt(1));
                            txtV.setMovementMethod(LinkMovementMethod.getInstance());
                            txtV.setText(Html.fromHtml(News.get(i).Data));

                            txtV = (TextView) (((ViewGroup) ll.getChildAt(i)).getChildAt(0));
                            txtV.setMovementMethod(LinkMovementMethod.getInstance());

                            txtV.setText(Html.fromHtml(News.get(i).Date));
                        }
                        break;
                    }
                    //Home Fragment
                    case 1: {
                        LinearLayout ll = (LinearLayout) findViewById(R.id.LLHome);

                        ll.removeViewAt(6);
                        ll.removeViewAt(6);

                        if(News.size() != 0)
                            for(int i = 0; i < 2; i++)
                            {
                                infl.inflate(R.layout.home_news, (ViewGroup) ll);

                                TextView txtV = (TextView) (((ViewGroup) ll.getChildAt(6 + i)).getChildAt(1));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());

                                txtV.setText(Html.fromHtml(News.get(i).Data));

                                txtV = (TextView) (((ViewGroup) ll.getChildAt(6 + i)).getChildAt(0));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());

                                txtV.setText(Html.fromHtml(News.get(i).Date));
                            }

                        break;
                    }
                    //Grades Fragment
                    case 2:
                    {
                        LinearLayout ll = (LinearLayout) findViewById(R.id.LLMyGrades);

                        for(int i = 2; i < ll.getChildCount(); i++)
                            ll.removeViewAt(i);

                        if(num != 0)
                        {
                            for(int i = 2; i < num + 2; i++)
                            {
                                infl.inflate(R.layout.grades_my_grades, (ViewGroup) ll);

                                TextView txtV = (TextView) (((ViewGroup) ll.getChildAt(i)).getChildAt(0));
                                txtV.setText(user.Grades.get(i-2).CName);
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(i)).getChildAt(1));
                                txtV.setText(Integer.toString(user.Grades.get(i-2).TotalMarks));
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(i)).getChildAt(2));
                                txtV.setText(user.Grades.get(i-2).Grade);
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(i)).getChildAt(3));
                                txtV.setText(Integer.toString(user.Grades.get(i-2).TotalAbs));
                            }
                        }

                        break;
                    }
                    case 3:
                    {
                        LinearLayout ll = (LinearLayout) findViewById(R.id.LLTop50);

                        for (int i = 0; i < 50; i++)
                        {
                                TextView txtV = (TextView) (((ViewGroup) ll.getChildAt(3+i)).getChildAt(0));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());
                                txtV.setText(top.Top_50[i][0]);
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(3+i)).getChildAt(2));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());
                                txtV.setText(top.Top_50[i][2]);
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(3+i)).getChildAt(3));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());
                                txtV.setText(top.Top_50[i][3]);


                        }
                        break;
                    }
                    case 4:
                    {
                        LinearLayout inboxLayout = (LinearLayout)findViewById(R.id.inbox);
                        inboxLayout.removeAllViews();

                        for(int i = 0; i < 7; i++) {
                            infl.inflate(R.layout.home_inbox, (ViewGroup) inboxLayout);

                            Toast.makeText(MainActivity.this, allMails.e_mails.get(i).from, Toast.LENGTH_LONG).show();

                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(0))).setText(allMails.e_mails.get(i).from);
                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(1))).setText(allMails.e_mails.get(i).to);                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(0))).setText(allMails.e_mails.get(i).from);
                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(2))).setText(allMails.e_mails.get(i).date);
                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(3))).setText(allMails.e_mails.get(i).msg);
                        }
                        Toast.makeText(MainActivity.this, "FILL", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }
        findViewById(R.id.drawer_layout).post(new FillFragmentsRunnable(num,type));

    }
}
