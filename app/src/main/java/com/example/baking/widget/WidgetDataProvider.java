package com.example.baking.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.baking.R;
import com.example.baking.model.Ingredient;
import com.example.baking.model.Recipe;
import com.example.baking.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diegocotta on 29/10/2018.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {


    List<Ingredient> ingredients = new ArrayList<>();
    Context mContext = null;

    public WidgetDataProvider(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.item_ingredient_widget);
        view.setTextViewText(R.id.tvItem, ingredients.get(position).toString());
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        ingredients.clear();
        Recipe recipe = Utils.getRecipePref(mContext);
        if (recipe != null)
            ingredients = Utils.getRecipePref(mContext).getIngredients();
        else
            ingredients = new ArrayList<>();
    }

}
