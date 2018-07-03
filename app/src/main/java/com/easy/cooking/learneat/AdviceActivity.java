package com.easy.cooking.learneat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdviceActivity extends AppCompatActivity {

    @BindView(R.id.advice_toolbar)
    Toolbar adviceToolbar;

    @BindView(R.id.advice_spinner)
    Spinner adviceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);

        initToolbar();

        ArrayAdapter adapter = new ArrayAdapter(AdviceActivity.this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Alege categoria");
        adviceSpinner.setAdapter(adapter);
    }

    public void initToolbar() {
        adviceToolbar.setTitle(R.string.menu_advice_chefs);
        setSupportActionBar(adviceToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
