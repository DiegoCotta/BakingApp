package com.example.baking.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
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

    private boolean isTwoPanel;
    private int selectedpos = -1;
    private Context context;

    public StepAdapter(List<Step> stepList, StepViewHolderListener listener, boolean isTwoPanel, Context context) {
        this.stepList = stepList;
        selectedpos = 0;
        this.listener = listener;
        this.isTwoPanel = isTwoPanel;
        this.context = context;
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
            if (position != 0)
                binding.tvStep.setText(position + context.getString(R.string.point_separ) + stepList.get(position).getShortDescription());
            else {
                binding.tvStep.setText(stepList.get(position).getShortDescription());
            }
            if (isTwoPanel)
                if (selectedpos == position)
                    binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentTransparent));
                else
                    binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            binding.getRoot().setOnClickListener(v -> {
                if (selectedpos != position) {
                    listener.onStepClick(position);
                    if (isTwoPanel) {
                        selectedpos = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public int getSelectedpos() {
        return selectedpos;
    }

    public void setSelectedpos(int selectedpos) {
        this.selectedpos = selectedpos;
        notifyDataSetChanged();

    }

    public interface StepViewHolderListener {
        void onStepClick(int pos);
    }
}
