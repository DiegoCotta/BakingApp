package com.example.baking.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.R;
import com.example.baking.databinding.StepItemBinding;
import com.example.baking.model.Step;

import java.util.List;

/**
 * Created by diegocotta on 19/10/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> stepList;

    private StepViewHolderListener listener;

    public StepAdapter(List<Step> stepList, StepViewHolderListener listener) {
        this.stepList = stepList;
        this.listener = listener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        StepItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.step_item, parent, false);
        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (stepList == null) {
            return 0;
        }
        return stepList.size();
    }


    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public StepViewHolderListener getListener() {
        return listener;
    }

    public void setListener(StepViewHolderListener listener) {
        this.listener = listener;
    }


    public class StepViewHolder extends RecyclerView.ViewHolder {

        StepItemBinding binding;

        public StepViewHolder(StepItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final int position) {
            binding.tvStep.setText(stepList.get(position).getShortDescription());
            if (stepList.get(position).getVideoURL().isEmpty()) {
                binding.tvShowStep.setVisibility(View.INVISIBLE);
            } else {
                binding.getRoot().setOnClickListener(v -> listener.onStepClick(position));
            }
        }
    }

    public interface StepViewHolderListener {
        void onStepClick(int pos);
    }
}
