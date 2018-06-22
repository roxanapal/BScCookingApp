package com.easy.cooking.learneat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.models.Recipe;
import com.easy.cooking.learneat.models.Step;
import com.easy.cooking.learneat.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements StepFragment.OnFragmentInteractionListener{

    @BindView(R.id.steps_toolbar)
    Toolbar stepsToolbar;
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        setSupportActionBar(stepsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            showErrorMessage();
            return;
        }

        recipe = intent.getParcelableExtra(Constants.EXTRA_RECIPE);
        if (recipe.getIdRecipe() == Constants.DEFAULT_ID) {
            showErrorMessage();
            return;
        }

        switchFragment(recipe.getStepList().get(0));
    }

    private void showErrorMessage() {
        Toast.makeText(this, R.string.recipe_data_error, Toast.LENGTH_SHORT).show();
    }

    private void switchFragment(Step step) {
        // FragmentManager and transaction to add the fragment to the screen
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.step_frame_layout, StepFragment.newInstance(step))
                .commit();
    }

    @Override
    public void showNextStep(Step step) {
        Step nextStep = null;
        for (int i = 0; i < recipe.getStepList().size()-1; i++) {
            if (step.getStepNumber() == i) {
                nextStep = recipe.getStepList().get(i);
            }
        }
        switchFragment(nextStep);
    }
}
