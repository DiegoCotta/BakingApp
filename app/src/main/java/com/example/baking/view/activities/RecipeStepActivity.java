package com.example.baking.view.activities;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.example.baking.R;
import com.example.baking.databinding.ActivityRecipeStepBinding;
import com.example.baking.model.Recipe;
import com.example.baking.view.fragments.StepFragment;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "RECIPE_KEY";
    public static final String STEP_POS_KEY = "STEP_POS_KEY";

    ActivityRecipeStepBinding binding;

    private Recipe recipe;
    private int stepPos;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_step);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (getIntent().hasExtra(RECIPE_KEY)) {
            recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        }

        stepPos = getIntent().getIntExtra(STEP_POS_KEY, 0);

        setTitle(recipe.getName());

        StepFragment fragment = StepFragment.newInstance(recipe.getSteps().get(stepPos));
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.stepFragment, fragment)
                .commit();

        if (binding.stepControl != null)
            binding.stepControl.flBack.setOnClickListener(view -> {
                if (stepPos > 0) {
                    StepFragment fragment1 = StepFragment.newInstance(recipe.getSteps().get(--stepPos));
                    fragmentManager.beginTransaction()
                            .replace(R.id.stepFragment, fragment1)
                            .commit();
                }
            });
        if (binding.stepControl != null)
            binding.stepControl.flNext.setOnClickListener(view -> {
                if (stepPos < recipe.getSteps().size() - 1) {
                    StepFragment fragment1 = StepFragment.newInstance(recipe.getSteps().get(++stepPos));
                    fragmentManager.beginTransaction()
                            .replace(R.id.stepFragment, fragment1)
                            .commit();

                }
            });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
