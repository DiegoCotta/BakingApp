package com.example.baking.view.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.example.baking.R;
import com.example.baking.databinding.FragmentRecipeDetailBinding;
import com.example.baking.model.Recipe;
import com.example.baking.view.adapter.IngredientAdapter;
import com.example.baking.view.adapter.StepAdapter;

public class RecipeDetailFragment extends Fragment implements StepAdapter.StepViewHolderListener {
    private static final String RECIPE_KEY = "recipe_key";
    private static final String TWO_PANEL = "two_panel";


    private Recipe recipe;
    private boolean isTwoPanel;
    private StepAdapter stepAdapter;

    private OnFragmentInteractionListener mListener;

    private FragmentRecipeDetailBinding binding;

    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance(Recipe recipe, boolean isTwoPanel) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY, recipe);
        args.putBoolean(TWO_PANEL, isTwoPanel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
            isTwoPanel = getArguments().getBoolean(TWO_PANEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        LinearLayoutManager layoutManagerIngredients
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvIngredients.setLayoutManager(layoutManagerIngredients);
        binding.rvIngredients.setHasFixedSize(true);
        IngredientAdapter adapter = new IngredientAdapter(recipe.getIngredients());
        binding.rvIngredients.setAdapter(adapter);
        LinearLayoutManager layoutManagerSteps
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvSteps.setLayoutManager(layoutManagerSteps);
        binding.rvSteps.setHasFixedSize(true);
        stepAdapter = new StepAdapter(recipe.getSteps(), this, isTwoPanel, getContext());
        binding.rvSteps.setAdapter(stepAdapter);

        return binding.getRoot();
    }


    @Override
    public void onStepClick(int pos) {
        mListener.onStepItemClick(pos);
    }

    public void setPositionSelect(int pos) {
        if (stepAdapter != null)
            stepAdapter.setSelectedpos(pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onStepItemClick(int position);
    }
}
