package com.example.seniorproject.activty.Restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.seniorproject.ConnectionToServer.TCPClient;
import com.example.seniorproject.R;
import com.example.seniorproject.fragments.ExampleFragment;
import com.example.seniorproject.fragments.HomeFragment;
import com.example.seniorproject.fragments.MessageFragment;
import com.example.seniorproject.fragments.ProfileFragment;
import com.example.seniorproject.fragments.RestaurantExampleFragment;
import com.example.seniorproject.fragments.RestaurantHomeFragment;
import com.example.seniorproject.fragments.RestaurantPostFragment;
import com.example.seniorproject.fragments.RestaurantToolsFragment;

public class RestaurantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getItemId()){
            case R.id.nav_home_restaurant:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantHomeFragment()).commit();
                break;
            case R.id.nav_home_restaurants:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantExampleFragment()).commit();
                break;
            case R.id.nav_post_restaurant:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantPostFragment()).commit();
                break;
            case R.id.nav_manage_restaurant:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantToolsFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_res, menu);
        return true;
    }

}
