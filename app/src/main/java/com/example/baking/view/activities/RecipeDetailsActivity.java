package com.example.baking.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.baking.R;
import com.example.baking.databinding.ActivityRecipeDetailsBinding;
import com.example.baking.model.Recipe;
import com.example.baking.model.Step;
import com.example.baking.view.adapter.IngredientAdapter;
import com.example.baking.view.adapter.StepAdapter;

public class RecipeDetailsActivity extends AppCompatActivity implements StepAdapter.StepViewHolderListener {

    public static final String RECIPE_KEY = "recipe-key";
    private Recipe recipe;
    private ActivityRecipeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        if (getIntent().hasExtra(RECIPE_KEY)) {
            recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(recipe.getName());

        LinearLayoutManager layoutManagerIngredients
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvIngredients.setLayoutManager(layoutManagerIngredients);
        binding.rvIngredients.setHasFixedSize(true);
        IngredientAdapter adapter = new IngredientAdapter(recipe.getIngredients());
        binding.rvIngredients.setAdapter(adapter);
        LinearLayoutManager layoutManagerSteps
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvSteps.setLayoutManager(layoutManagerSteps);
        binding.rvSteps.setHasFixedSize(true);
        StepAdapter stepAdapter = new StepAdapter(recipe.getSteps(), this);
        binding.rvSteps.setAdapter(stepAdapter);
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
    public void onStepClick(int pos) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(RecipeStepActivity.RECIPE_KEY, recipe);
        intent.putExtra(RecipeStepActivity.STEP_POS_KEY, pos);

        startActivity(intent);
    }
}
