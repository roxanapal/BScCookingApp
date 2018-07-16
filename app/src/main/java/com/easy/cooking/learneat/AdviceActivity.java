package com.easy.cooking.learneat;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.easy.cooking.learneat.adapters.AdviceAdapter;
import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.Advice;
import com.easy.cooking.learneat.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdviceActivity extends AppCompatActivity {

    private static final String TAG = AdviceActivity.class.getSimpleName();

    @BindView(R.id.advice_toolbar)
    Toolbar adviceToolbar;

    @BindView(R.id.advice_recycler_view)
    RecyclerView adviceRecyclerView;

    @BindView(R.id.advice_progressbar)
    ProgressBar adviceProgressBar;

    private List<Advice> adviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);

        adviceRecyclerView.setFocusable(false);
        initToolbar();

        initFirebaseController();
        // setSpinnerAdapter();
    }

    /*private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> spnAdapter = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.list_type_values,
                android.R.layout.simple_spinner_item);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adviceSpinner.setAdapter(spnAdapter);

        adviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                }
                if(position == 0){

                } else if(position == 1){
                    //getTopRatedMovies();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }*/

    public void initToolbar() {
        adviceToolbar.setTitle(R.string.menu_advice_chefs);
        setSupportActionBar(adviceToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void initFirebaseController() {
        FirebaseController firebaseController = FirebaseController.getInstance();
        firebaseController.getAdviceList(getAdviceListFromFirebase());
    }

    private ValueEventListener getAdviceListFromFirebase() {
        adviceProgressBar.setVisibility(View.VISIBLE);
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adviceList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Advice advice = data.getValue(Advice.class);
                    if (advice != null) {
                        adviceList.add(advice);
                        Log.i(TAG, "Selected advice: " + advice.toString());
                    } else {
                        Log.i(TAG, "Selected advice is null");
                    }
                }
                initRvAdviceList(adviceList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Data is not available.");
            }
        };
    }

    private void initRvAdviceList(List<Advice> listAdvice) {
        AdviceAdapter adviceAdapter = new AdviceAdapter(listAdvice, AdviceActivity.this);
        adviceRecyclerView.setNestedScrollingEnabled(false);
        adviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adviceRecyclerView.setAdapter(adviceAdapter);
        adviceProgressBar.setVisibility(View.GONE);
    }


}
