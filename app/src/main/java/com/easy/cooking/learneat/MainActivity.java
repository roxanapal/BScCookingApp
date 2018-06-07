package com.easy.cooking.learneat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.easy.cooking.learneat.ui.menu.MenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;

    private MenuFragment menuFragment;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setMenuFragment();
    }

    public void setMenuFragment(){
        menuFragment = new MenuFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_menu_container, menuFragment)
                .commit();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, 0, 0);
        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
