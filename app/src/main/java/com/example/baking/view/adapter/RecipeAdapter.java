package com.example.baking.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.R;
import com.example.baking.databinding.ItemRecipeBinding;
import com.example.baking.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by diegocotta on 19/10/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    List<Recipe> receipeList;

    RecipeAdapterListener listener;

    public RecipeAdapter(RecipeAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(receipeList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        if (receipeList == null)
            return 0;
        else return receipeList.size();
    }


    public List<Recipe> getReceipeList() {
        return receipeList;
    }

    public void setReceipeList(List<Recipe> receipeList) {
        this.receipeList = receipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ItemRecipeBinding binding;

        public RecipeViewHolder(ItemRecipeBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.tvRecipeTitle.setText(receipeList.get(position).getName());
            if (!receipeList.get(position).getImage().isEmpty())
                Picasso.get().load(receipeList.get(position).getImage()).into(binding.ivRecipe);
        }
    }

    public interface RecipeAdapterListener {
        void onClick(Recipe recipe);
    }
}
