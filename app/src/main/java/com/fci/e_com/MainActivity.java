package com.fci.e_com;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

import layout.gradesFragment;
import layout.gradesTopFragment;
import layout.homeFragment;
import layout.inboxFragment;
import layout.inboxMainFragment;
import layout.newsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static WebView webViewer;
    WebHandler handler = new WebHandler(this);
    E_Mails allMails;
    Top_50 top;
    WebAppInterface webInterface;
    GWebAppInterface GInterface;
    FragmentTransaction fragmentTransaction;

    public UserSettings user;
    public List<String> News = new ArrayList<String>();
    public int loggedIn = 0;
    public boolean isInstantiated = false;

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
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.idFragment,new homeFragment());
        fragmentTransaction.commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId())
        {
            case (R.id.nav_home):
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.idFragment,new homeFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Home");
                item.setChecked(true);
                break;
            case (R.id.inbox):
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.idFragment,new inboxFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Inbox");
                item.setChecked(true);
                break;
            case (R.id.nav_grades):
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.idFragment,new gradesFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Grades");
                item.setChecked(true);
                break;
            case (R.id.nav_news):
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.idFragment,new newsFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("News");
                item.setChecked(true);
                break;
            case (R.id.nav_schedule):
                break;
            case (R.id.nav_logout):
                break;


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
