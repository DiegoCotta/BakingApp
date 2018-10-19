package com.example.baking.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.baking.R;
import com.example.baking.databinding.IngredientItemBinding;
import com.example.baking.model.Ingredient;

import java.util.List;

/**
 * Created by diegocotta on 19/10/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IngredientItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (ingredientList == null)
            return 0;
        return ingredientList.size();
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        IngredientItemBinding binding;

        public IngredientViewHolder(IngredientItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.tvIngredient.setText(ingredientList.get(position).toString());

        }
    }
}
