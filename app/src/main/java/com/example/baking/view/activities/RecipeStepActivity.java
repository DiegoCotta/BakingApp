package com.example.baking.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.baking.R;
import com.example.baking.model.Recipe;
import com.example.baking.model.Step;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "RECIPE_KEY";
    public static final String STEP_POS_KEY = "STEP_POS_KEY";

    private Recipe recipe;
    private int stepPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra(RECIPE_KEY)) {
            recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        }

        stepPos = getIntent().getIntExtra(STEP_POS_KEY, 0);

        setTitle(recipe.getName());

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
