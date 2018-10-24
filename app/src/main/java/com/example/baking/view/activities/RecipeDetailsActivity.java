package com.example.baking.view.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.baking.R;
import com.example.baking.databinding.ActivityRecipeDetailsBinding;
import com.example.baking.model.Recipe;
import com.example.baking.view.fragments.RecipeDetailFragment;
import com.example.baking.view.fragments.StepFragment;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {

    public static final String RECIPE_KEY = "recipe-key";
    private Recipe recipe;
    private ActivityRecipeDetailsBinding binding;
    private boolean mTwoPane = false;
    private FragmentManager fragmentManager;
    RecipeDetailFragment fragment;
    private int stepPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getResources().getBoolean(R.bool.portrait_enable)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);


        if (binding.flRecipeStep != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }


        if (getIntent().hasExtra(RECIPE_KEY)) {
            recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(recipe.getName());
        fragmentManager = getSupportFragmentManager();
        fragment = RecipeDetailFragment.newInstance(recipe, mTwoPane);
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_fragment, fragment)
                .commit();

        if (mTwoPane) {
            stepPos = 0;
            changeFragment();
            if (binding.stepControl != null) {
                binding.stepControl.flBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (stepPos > 0) {
                            stepPos--;
                            changeFragment();
                        }
                    }
                });
                binding.stepControl.flNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (stepPos < recipe.getSteps().size() - 1) {
                            stepPos++;
                            changeFragment();
                        }
                    }
                });
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepItemClick(int pos) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(RecipeStepActivity.RECIPE_KEY, recipe);
            intent.putExtra(RecipeStepActivity.STEP_POS_KEY, pos);

            startActivity(intent);
        } else {
            stepPos = pos;
            changeFragment();
        }
    }

    private void changeFragment() {
        fragment.setPositionSelect(stepPos);
        StepFragment fragment1 = StepFragment.newInstance(recipe.getSteps().get(stepPos));
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flRecipeStep, fragment1)
                .commit();
    }
}
