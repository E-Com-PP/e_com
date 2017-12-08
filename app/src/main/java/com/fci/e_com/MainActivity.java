package com.fci.e_com;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.fci.e_com.Database.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import layout.gradesFragment;
import layout.gradesMyFragment;
import layout.homeFragment;
import layout.inboxFragment;
import layout.inboxMainFragment;
import layout.newsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView webViewer;
    public WebHandler handler = new WebHandler(this);
    public E_Mails allMails;
    public Top_50 top;
    WebAppInterface webInterface;
    GWebAppInterface GInterface;
    Synchronizer Synchro = new Synchronizer(this, 500);

    public DatabaseOperations ops = new DatabaseOperations(this, "ECOMT3");
    public UserSettings user;
    public List<NewsObj> News = new ArrayList<NewsObj>();
    public int loggedIn = 0;
    public boolean isInstantiated = false;
    String currSelectedYear = "";
    String CurrentselectedYear2="";
    String CurrentSelectedType="";
    String UserPassword="";
    public String Name="";
    int GraterThan2 =0;
    boolean EqualALl=true;
    public LogIn MyLogIn;

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

        Timer t = new Timer();
        t.schedule(Synchro, 0, Synchro.IntervalMS);

        allMails = new E_Mails(this);
        top = new Top_50(this);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("NameStr");
        UserPassword = bundle.getString("PasswordStr");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        handler.StartUp();
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
            Cursor cur = ops.Query("Grade", ops.GradeColumnNames);
            cur.moveToFirst();
            do
            {
                if(cur.getString(0).equals("1"))
                {
                    Toast.makeText(this, cur.getString(2), Toast.LENGTH_SHORT).show();
                }
            }
            while (cur.moveToNext());
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
                                      if(ops.LoadExistingData(handler.MainActv, 1, 0))
                                          fillFragment(News.size(),1);
                                      else
                                      Synchro.AddTask(new NetTask(){
                                          @Override
                                          public void run()
                                          {
                                              handler.GetNews();
                                          }}, false);
                                  }
                              });
            trans.replace(R.id.fragContainer, new homeFragment()).commit();
        }
        else if (id == R.id.nav_inbox) {
            try {
                trans.runOnCommit(new Runnable() {
                    @Override
                    public void run() {

                        TabHost host = (TabHost)findViewById(R.id.inboxtabhost);
                        host.setup();

                        TabHost.TabSpec spec = host.newTabSpec("Inbox");
                        spec.setContent(R.id.inbox);
                        spec.setIndicator("Inbox");
                        host.addTab(spec);
                        spec = host.newTabSpec("Received Files");
                        spec.setContent(R.id.files);
                        spec.setIndicator("Received Files");
                        host.addTab(spec);
                        
                        if(ops.LoadEmail(MainActivity.this, Name, 0)) {
                            Toast.makeText(MainActivity.this, "Loaded Inbox from DB", Toast.LENGTH_SHORT).show();
                            fillFragment(0, 4);
                        }
                        else
                            Synchro.AddTask(new NetTask(){
                                @Override
                                public void run() {
                                    allMails.loadPage(1);
                                }}, false);
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
                    TabHost host = (TabHost)findViewById(R.id.tabhost);
                    host.setup();

                    TabHost.TabSpec spec = host.newTabSpec("My Grades");
                    spec.setContent(R.id.my_grades);
                    spec.setIndicator("My Grades");
                    host.addTab(spec);
                    spec = host.newTabSpec("Top 50");
                    spec.setContent(R.id.top_50);
                    spec.setIndicator("Top 50");
                    host.addTab(spec);


                    final Spinner spin = initGradeSpinner(0);
                    if(spin.getSelectedItem() != null) {
                        //if(ops.LoadExistingData(handler.MainActv, 0, spin.getSelectedItemPosition()))
                        //    fillFragment(user.Grades.size(), 2);
                        //else
                        //    Synchro.AddTask(new NetTask() {
                        //        @Override
                        //        public void run() {
                        //            handler.GetGrades(1, spin.getSelectedItem().toString());
                        //        }
                        //    }, false);
                    }
                    else
                        Synchro.AddTask(new NetTask(){
                            @Override
                            public void run() {
                                handler.GetGradeYears(1);
                            }}, false);

                    final Spinner TypeSpinner = initGradeSpinner(1);
                    final Spinner YearsSpinner = initGradeSpinner(2);

                    host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                        @Override
                        public void onTabChanged(String s) {
                            if(s == "Top 50")
                            {
                                if(ops.LoadTop50(handler.MainActv, YearsSpinner.getSelectedItem().toString(), TypeSpinner.getSelectedItem().toString())) 
                                {
                                    fillFragment(GraterThan2, 3);
                                    Toast.makeText(MainActivity.this, "Loaded Top 50 from DB", Toast.LENGTH_SHORT).show();
                                }
                                else 
                                {
                                    //top.getTop_50(Integer.parseInt(YearsSpinner.getSelectedItem().toString()),GInterface);
                                    Synchro.AddTask(new NetTask() {
                                        @Override
                                        public void run() {
                                            top.getTop_50(Integer.parseInt(YearsSpinner.getSelectedItem().toString()), TypeSpinner.getSelectedItem().toString(), GInterface);
                                        }
                                    }, false);
                                }
                            }
                        }
                    });
                }
            });
            trans.replace(R.id.fragContainer, new gradesFragment()).commit();
        }
        else if (id == R.id.nav_news) {
            trans.runOnCommit(new Runnable() {
                @Override
                public void run() {
                    if(ops.LoadExistingData(handler.MainActv, 1, 0))
                        fillFragment(News.size(),0);
                    else
                        Synchro.AddTask(new NetTask(){
                            @Override
                            public void run() {
                                handler.GetNews();
                            }}, false);
                }
            });
            trans.replace(R.id.fragContainer, new newsFragment()).commit();
        }
        else if (id == R.id.nav_schedule) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, placeholder).commit();
        }
        else if (id == R.id.nav_logout) {
            Intent LogOutIntent = new Intent(this, LogIn.class);
            startActivity(LogOutIntent);

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
                final Spinner toUse = TempSpinner;
                TempSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        currSelectedYear = parentView.getSelectedItem().toString();
                        ((TextView) findViewById(R.id.myGrades_year)).setText("Year " + Integer.toString(parentView.getCount() - position));

                        if(ops.LoadExistingData(handler.MainActv, 0, toUse.getSelectedItemPosition()))
                            fillFragment(user.Grades.size(), 2);
                        else
                            Synchro.AddTask(new NetTask() {
                                @Override
                                public void run() {
                                    handler.GetGrades(1, toUse.getSelectedItem().toString());
                                }
                            }, false);
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
                        CurrentSelectedType = parentView.getSelectedItem().toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }

                });
                TempSpinner.setAdapter(adap);
                if (CurrentSelectedType != "") {
                    if (CurrentSelectedType!="ALL")
                    {
                        EqualALl=true;

                    }
                    else {EqualALl=false;}
                    for (int i = 0; i < TempSpinner.getCount(); i++) {
                        if (CurrentSelectedType.equals(TempSpinner.getItemAtPosition(i).toString())) {
                            TempSpinner.setSelection(i);
                            break;
                        }
                    }
                }


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
                    if((CurrentselectedYear2=="3"||CurrentselectedYear2=="4")&&!EqualALl)
                    {

                       GraterThan2=1;
                    }
                    else
                        {
                            GraterThan2=0;
                        }
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

                        if(News.size() != 0)
                            for(int i = 0; i < 3; i++)
                            {
                                ll.removeViewAt(6);
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

                        int s = ll.getChildCount();
                        for(int i = 2; i < s; i++)
                            ll.removeViewAt(2);

                        if(num != 0)
                        {
                            int toloop = num + 2;
                            for(int i = 2; i < toloop; i++)
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
                        //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //Top50 Fragment
                    case 3:
                    {
                        LinearLayout ll = (LinearLayout) findViewById(R.id.LLTop50);
                        int sizeT = top.Top_50.length;

                        if(sizeT != 50)
                        {
                            int tempSize =  ll.getChildCount() - 2;
                            for(int z = 0; z < tempSize; z++)
                            {
                                ll.removeViewAt(2);
                            }
                            for(int z = 0; z < sizeT; z++)
                            {
                                infl.inflate(R.layout.grades_top50, (ViewGroup)ll);
                            }
                        }

                        for (int i = 0; i < sizeT; i++)
                        {
                                TextView txtV = (TextView) (((ViewGroup) ll.getChildAt(2+i)).getChildAt(0));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());
                                txtV.setText(top.Top_50[i][0]);
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(2+i)).getChildAt(1));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());
                                txtV.setText(top.Top_50[i][2]);
                                txtV = (TextView) (((ViewGroup) ll.getChildAt(2+i)).getChildAt(2));
                                txtV.setMovementMethod(LinkMovementMethod.getInstance());
                                txtV.setText(top.Top_50[i][3+num]);
                        }
                        break;
                    }
                    //Inbox Fragment
                    case 4:
                    {
                        LinearLayout inboxLayout = (LinearLayout)((ViewGroup)findViewById(R.id.inbox)).getChildAt(0);
                        inboxLayout.removeAllViews();

                        for(int i = 0; i < 7; i++) {
                            infl.inflate(R.layout.home_inbox, (ViewGroup) inboxLayout);

                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(0))).setText(allMails.e_mails.get(i).from);
                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(1))).setText(allMails.e_mails.get(i).to);
                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(2))).setText(allMails.e_mails.get(i).date);
                            ((TextView) (((ViewGroup) inboxLayout.getChildAt(i)).getChildAt(3))).setText(allMails.e_mails.get(i).msg);
                        }
                        if(ops.LoadEmail(MainActivity.this, Name, 1))
                        {
                            Toast.makeText(MainActivity.this, "Loaded Files from DB", Toast.LENGTH_SHORT).show();
                            fillFragment(0, 5);
                        }
                        else
                            Synchro.AddTask(new NetTask(){
                                @Override
                                public void run() {
                                    allMails.sendFiles();
                                }}, false);
                        break;
                    }
                    //Files Fragment
                    case 5:
                    {
                        LinearLayout filesLayout = (LinearLayout)((ViewGroup)findViewById(R.id.files)).getChildAt(0);
                        filesLayout.removeAllViews();

                        for(int i = 0; i < 7; i++) {
                            infl.inflate(R.layout.home_inbox, (ViewGroup) filesLayout);

                            ((TextView) (((ViewGroup) filesLayout.getChildAt(i)).getChildAt(0))).setText(allMails.recievedFile.get(i).from);
                            ((TextView) (((ViewGroup) filesLayout.getChildAt(i)).getChildAt(1))).setText(allMails.recievedFile.get(i).to);
                            ((TextView) (((ViewGroup) filesLayout.getChildAt(i)).getChildAt(2))).setText(allMails.recievedFile.get(i).date);
                            ((TextView) (((ViewGroup) filesLayout.getChildAt(i)).getChildAt(3))).setText(allMails.recievedFile.get(i).fileDescription);
                        }
                        break;
                    }
                }
            }
        }
        findViewById(R.id.drawer_layout).post(new FillFragmentsRunnable(num,type));

    }
}
