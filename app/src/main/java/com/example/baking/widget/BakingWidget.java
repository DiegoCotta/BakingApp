package com.example.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.baking.R;
import com.example.baking.model.Recipe;
import com.example.baking.util.Utils;
import com.example.baking.view.activities.RecipeDetailsActivity;

public class BakingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        views.setRemoteAdapter(R.id.lvRecipe, new Intent(context, WidgetService.class));

        Recipe recipe = Utils.getRecipePref(context);
        if (recipe != null) {
            views.setTextViewText(R.id.tvRecipeTitleWidget, recipe.getName());
        } else {
            views.setTextViewText(R.id.tvRecipeTitleWidget, context.getString(R.string.no_recipe_selected));
        }


        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvRecipe);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

