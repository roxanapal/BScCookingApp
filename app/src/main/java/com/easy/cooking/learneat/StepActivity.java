package com.easy.cooking.learneat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.easy.cooking.learneat.models.Recipe;
import com.easy.cooking.learneat.models.Step;
import com.easy.cooking.learneat.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements StepFragment.OnFragmentInteractionListener {

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

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_frame_layout, StepFragment.newInstance(recipe.getStepList().get(0)))
                .commit();
    }

    private void showErrorMessage() {
        Toast.makeText(this, R.string.recipe_data_error, Toast.LENGTH_SHORT).show();
    }

    private void switchFragment(Step step) {
        // FragmentManager and transaction to add the fragment to the screen
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_frame_layout, StepFragment.newInstance(step))
                .commit();
    }

    @Override
    public void showNextStep(Step currentStep) {
        Step nextStep = null;

        if (currentStep.getStepNumber() <= recipe.getStepList().size() - 1) {

            for (Step step : recipe.getStepList()) {
                if (step.getStepNumber() <= currentStep.getStepNumber()) {
                    continue;
                }
                nextStep = step;
                break;
            }

            switchFragment(nextStep);
        } else {
            Intent uploadPhototIntent = new Intent(StepActivity.this, UploadPhotoActivity.class);
            uploadPhototIntent.putExtra(Constants.EXTRA_RECIPE, recipe);
            startActivity(uploadPhototIntent);
            finish();
            Toast.makeText(this, getString(R.string.recipe_final_toast) + recipe.getTitleRecipe() + "!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
