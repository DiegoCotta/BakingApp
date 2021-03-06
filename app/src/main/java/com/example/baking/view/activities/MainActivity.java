package com.example.baking.view.activities;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.baking.util.Utils;
import com.example.baking.viewmodel.MainViewModel;
import com.example.baking.R;
import com.example.baking.databinding.ActivityMainBinding;
import com.example.baking.model.Recipe;
import com.example.baking.view.adapter.RecipeAdapter;
import com.example.baking.widget.BakingWidget;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterListener, MainViewModel.MainViewModelListener {

    ActivityMainBinding binding;

    MainViewModel viewModel;
    RecipeAdapter recipeAdapter;


    @Nullable
    private CountingIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CountingIdlingResource("MainActivityIdlingResource");
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (!getResources().getBoolean(R.bool.portrait_enable)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        GridLayoutManager manager = new GridLayoutManager(this, getResources().getInteger(R.integer.gridSize));

        binding.rvReceipes.setLayoutManager(manager);
        binding.rvReceipes.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(this);
        binding.rvReceipes.setAdapter(recipeAdapter);
        binding.flLoading.setOnTouchListener((v, event) -> true);

        setupViewModel();

    }


    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setListener(this);
        if (mIdlingResource != null)
            mIdlingResource.increment();
        viewModel.getReceipes().observe(this, recipes -> {
            recipeAdapter.setReceipeList(recipes);
            binding.flLoading.setVisibility(View.GONE);
            if (mIdlingResource != null)
                mIdlingResource.decrement();
        });
        binding.flLoading.setVisibility(View.VISIBLE);
        viewModel.getRecipesFromService();
    }

    @Override
    public void onClick(Recipe recipe) {
        Utils.saveRecipePref(recipe, this);
        updateWidgets();
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }

    private void updateWidgets() {
        Intent intentWidget = new Intent(this, BakingWidget.class);
        intentWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ComponentName componentName = new ComponentName(this, BakingWidget.class);
        int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(componentName);
        intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intentWidget);
    }

    @Override
    public void onError(String error) {
        binding.flLoading.setVisibility(View.GONE);
        if (error == null)
            Toast.makeText(this, R.string.service_error, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


}
